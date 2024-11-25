package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.DietDto;
import com.dualforce.fitquest.service.diet.DietService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // 식단 기록 등록
    @PostMapping
    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만
    @Operation(summary = "당일 식단 기록 등록", description = "당일 식단 기록을 등록합니다.")
    public ResponseEntity<DietDto> createDiet(@RequestBody DietDto diet) {
        int dietId = dietService.createDiet(diet);
        DietDto createdDiet = dietService.readTodayMyDiets(diet.getUserId())
                .stream()
                .filter(d -> d.getDietId() == dietId)
                .findFirst()
                .orElse(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiet);
    }

    // 당일 식단 기록 조회
    @GetMapping("/today/{userId}")
    @PreAuthorize("@securityService.isOwner(authentication, #userId) or hasRole('ADMIN')")
    @Operation(summary = "당일 식단 기록 조회", description = "당일 식단 기록을 조회합니다.")
    public ResponseEntity<List<DietDto>> readTodayMyDiets(@PathVariable int userId) {
        List<DietDto> diets = dietService.readTodayMyDiets(userId);
        return ResponseEntity.ok(diets);
    }

    // 식단 기록 수정
    @PutMapping("/{dietId}")
    @PreAuthorize("@securityService.isDietOwner(authentication, #dietId) or hasRole('ADMIN')")
    @Operation(summary = "당일 식단 기록 수정", description = "당일 식단 기록을 수정합니다.")
    public ResponseEntity<DietDto> editDiet(@PathVariable int dietId, @RequestBody DietDto diet) {
        try {
            diet.setDietId(dietId);
            dietService.editDiet(diet);
            DietDto updatedDiet = dietService.readTodayMyDiets(diet.getUserId())
                    .stream()
                    .filter(d -> d.getDietId() == dietId)
                    .findFirst()
                    .orElse(null);
            return ResponseEntity.ok(updatedDiet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 식단 기록 삭제
    @DeleteMapping("/{dietId}")
    @PreAuthorize("@securityService.isDietOwner(authentication, #dietId) or hasRole('ADMIN')")
    @Operation(summary = "당일 식단 기록 삭제", description = "당일 식단 기록을 삭제합니다.")
    public ResponseEntity<String> removeDiet(@PathVariable int dietId) {
        try {
            dietService.removeDiet(dietId);
            return ResponseEntity.ok("Diet with ID " + dietId + " has been deleted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Diet not found.");
        }
    }

    // 당일 식단 유형 비중 계산
    @GetMapping("/{userId}/distribution")
    @PreAuthorize("@securityService.isOwner(authentication, #userId) or hasRole('ADMIN')")
    @Operation(summary = "당일 식단 유형 비중 계산", description = "당일 식단 유형(아침/점심/저녁)별 비중을 계산합니다.")
    public ResponseEntity<Map<String, Integer>> calculateDietTypeDistribution(
            @PathVariable int userId,
            @RequestParam LocalDate date) {
        Map<String, Integer> distribution = dietService.calculateDietTypeDistribution(userId, date);
        return ResponseEntity.ok(distribution);
    }
}

