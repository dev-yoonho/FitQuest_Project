package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.ExerciseDto;
import com.dualforce.fitquest.service.exercise.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Integer> createExercise(@RequestBody ExerciseDto exercise) {
        int exerciseId = exerciseService.createExercise(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseId);
    }

    // 당일 운동 기록 조회 (성공 시 200 OK)
    @GetMapping("/today/{userId}")
    public ResponseEntity<List<ExerciseDto>> readTodayMyExercises(@PathVariable int userId) {
        List<ExerciseDto> exercises = exerciseService.readTodayMyExercises(userId);
        return ResponseEntity.ok(exercises);
    }

    // 운동 기록 수정 (성공 시 200 OK, 실패 시 404 Not Found)
    @PutMapping("/{exerciseId}")
    public ResponseEntity<Integer> editExercise(@PathVariable int exerciseId, @RequestBody ExerciseDto exercise) {
        try {
            exercise.setExerciseId(exerciseId);
            int updatedId = exerciseService.editExercise(exercise);
            return ResponseEntity.ok(updatedId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 운동 기록 삭제 (성공 시 200 OK, 실패 시 404 Not Found)
    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Integer> removeExercise(@PathVariable int exerciseId) {
        try {
            int removedId = exerciseService.removeExercise(exerciseId);
            return ResponseEntity.ok(removedId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 당월 운동한 날짜들 반환 (성공 시 200 OK)
    @GetMapping("/{userId}/month")
    public ResponseEntity<List<LocalDate>> getExerciseDatesForMonth(
            @PathVariable int userId,
            @RequestParam int year,
            @RequestParam int month) {
        List<LocalDate> exerciseDates = exerciseService.getExerciseDatesForMonth(userId, year, month);
        return ResponseEntity.ok(exerciseDates);
    }

    // 당일 운동 유형 계산 (성공 시 200 OK)
    @GetMapping("/{userId}/distribution")
    public ResponseEntity<Map<String, Integer>> calculateExerciseTypeDistribution(
            @PathVariable int userId,
            @RequestParam LocalDate date) {
        Map<String, Integer> distribution = exerciseService.calculateExerciseTypeDistribution(userId, date);
        return ResponseEntity.ok(distribution);
    }
}
