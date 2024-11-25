package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.ExerciseDto;
import com.dualforce.fitquest.service.exercise.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    // 운동 기록 등록 (성공 시 201 Created)
    @PostMapping
    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만
    @Operation(summary = "운동 기록 등록", description = "당일 운동 기록을 등록합니다.")
    public ResponseEntity<ExerciseDto> createExercise(@RequestBody ExerciseDto exercise) {
        int exerciseId = exerciseService.createExercise(exercise);
        ExerciseDto createdExercise = exerciseService.readTodayMyExercises(exercise.getUserId())
                .stream()
                .filter(e -> e.getExerciseId() == exerciseId)
                .findFirst()
                .orElse(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExercise);
    }

    // 당일 운동 기록 조회
    @GetMapping("/today/{userId}")
    @PreAuthorize("@securityService.isOwner(authentication, #userId) or hasRole('ADMIN')")
    @Operation(summary = "당일 운동 기록 조회", description = "당일 운동 기록을 조회합니다.")
    public ResponseEntity<List<ExerciseDto>> readTodayMyExercises(@PathVariable int userId) {
        List<ExerciseDto> exercises = exerciseService.readTodayMyExercises(userId);
        return ResponseEntity.ok(exercises);
    }

    // 운동 기록 수정
    @PutMapping("/{exerciseId}")
    @PreAuthorize("@securityService.isExerciseOwner(authentication, #exerciseId) or hasRole('ADMIN')")
    @Operation(summary = "운동 기록 수정", description = "운동 기록을 수정합니다.")
    public ResponseEntity<ExerciseDto> editExercise(@PathVariable int exerciseId, @RequestBody ExerciseDto exercise) {
        try {
            exercise.setExerciseId(exerciseId);
            exerciseService.editExercise(exercise);
            ExerciseDto updatedExercise = exerciseService.readTodayMyExercises(exercise.getUserId())
                    .stream()
                    .filter(e -> e.getExerciseId() == exerciseId)
                    .findFirst()
                    .orElse(null);
            return ResponseEntity.ok(updatedExercise);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 운동 기록 삭제
    @DeleteMapping("/{exerciseId}")
    @PreAuthorize("@securityService.isExerciseOwner(authentication, #exerciseId) or hasRole('ADMIN')")
    @Operation(summary = "운동 기록 삭제", description = "운동 기록을 삭제합니다.")
    public ResponseEntity<String> removeExercise(@PathVariable int exerciseId) {
        try {
            exerciseService.removeExercise(exerciseId);
            return ResponseEntity.ok("Exercise with ID " + exerciseId + " has been deleted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercise not found.");
        }
    }

    // 당월 운동 날짜 조회
    @GetMapping("/{userId}/month")
    @PreAuthorize("@securityService.isOwner(authentication, #userId) or hasRole('ADMIN')")
    @Operation(summary = "당월 운동 날짜 조회", description = "당월에 운동한 날짜들의 목록을 조회합니다.")
    public ResponseEntity<List<LocalDate>> getExerciseDatesForMonth(
            @PathVariable int userId,
            @RequestParam int year,
            @RequestParam int month) {
        List<LocalDate> exerciseDates = exerciseService.getExerciseDatesForMonth(userId, year, month);
        return ResponseEntity.ok(exerciseDates);
    }

    // 당일 운동 유형 비중 계산
    @GetMapping("/{userId}/distribution")
    @PreAuthorize("@securityService.isOwner(authentication, #userId) or hasRole('ADMIN')")
    @Operation(summary = "당일 운동 유형 계산", description = "당일 운동 유형(근력/유산소/코어)의 비중을 계산합니다.")
    public ResponseEntity<Map<String, Integer>> calculateExerciseTypeDistribution(
            @PathVariable int userId,
            @RequestParam LocalDate date) {
        Map<String, Integer> distribution = exerciseService.calculateExerciseTypeDistribution(userId, date);
        return ResponseEntity.ok(distribution);
    }
}

