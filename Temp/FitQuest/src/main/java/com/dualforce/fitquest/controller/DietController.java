package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.DietDto;
import com.dualforce.fitquest.service.diet.DietService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diets")
public class DietController {

    private final DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }

    // 식단 기록 등록 (성공 시 201 Created)
    @PostMapping
    public ResponseEntity<Integer> createDiet(@RequestBody DietDto diet) {
        int dietId = dietService.createDiet(diet);
        return ResponseEntity.status(HttpStatus.CREATED).body(dietId);
    }

    // 당일 식단 기록 조회 (성공 시 200 OK)
    @GetMapping("/today/{userId}")
    public ResponseEntity<List<DietDto>> readTodayMyDiets(@PathVariable int userId) {
        List<DietDto> diets = dietService.readTodayMyDiets(userId);
        return ResponseEntity.ok(diets);
    }

    // 식단 기록 수정 (성공 시 200 OK, 실패 시 404 Not Found)
    @PutMapping("/{dietId}")
    public ResponseEntity<Integer> editDiet(@PathVariable int dietId, @RequestBody DietDto diet) {
        try {
            diet.setDietId(dietId);
            int updatedId = dietService.editDiet(diet);
            return ResponseEntity.ok(updatedId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 식단 기록 삭제 (성공 시 200 OK, 실패 시 404 Not Found)
    @DeleteMapping("/{dietId}")
    public ResponseEntity<Integer> removeDiet(@PathVariable int dietId) {
        try {
            int removedId = dietService.removeDiet(dietId);
            return ResponseEntity.ok(removedId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 당일 식단 유형 계산 (성공 시 200 OK)
    @GetMapping("/{userId}/distribution")
    public ResponseEntity<Map<String, Integer>> calculateDietTypeDistribution(
            @PathVariable int userId,
            @RequestParam LocalDate date) {
        Map<String, Integer> distribution = dietService.calculateDietTypeDistribution(userId, date);
        return ResponseEntity.ok(distribution);
    }
}
