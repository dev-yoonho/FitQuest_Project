package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.DietDto;
import com.dualforce.fitquest.model.dto.ExerciseDto;
import com.dualforce.fitquest.model.dto.UserDto;
import com.dualforce.fitquest.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 사용자 등록 (성공 시 201 Created)
    @PostMapping("/signup")
    public ResponseEntity<Integer> createUser(@RequestBody UserDto user) {
        int userId = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    // 사용자 조회 (ID 기준, 성공 시 200 OK, 실패 시 404 Not Found)
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> readUser(@PathVariable int id) {
        try {
            UserDto user = userService.readUser(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 사용자 조회 (닉네임 기준, 성공 시 200 OK, 실패 시 404 Not Found)
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<UserDto> readUserByNickname(@PathVariable String nickname) {
        try {
            UserDto user = userService.readUserByNickname(nickname);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 사용자 정보 수정 (성공 시 200 OK, 실패 시 404 Not Found)
    @PutMapping("/{id}")
    public ResponseEntity<Integer> editUser(@PathVariable int id, @RequestBody UserDto user) {
        try {
            user.setUserId(id);
            int updatedId = userService.editUser(user);
            return ResponseEntity.ok(updatedId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 사용자 운동 기록 조회 (성공 시 200 OK)
    @GetMapping("/{id}/exercises")
    public ResponseEntity<List<ExerciseDto>> readAllMyExercises(@PathVariable int id) {
        List<ExerciseDto> exercises = userService.readAllMyExercises(id);
        return ResponseEntity.ok(exercises);
    }

    // 사용자 식단 기록 조회 (성공 시 200 OK)
    @GetMapping("/{id}/diets")
    public ResponseEntity<List<DietDto>> readAllMyDiets(@PathVariable int id) {
        List<DietDto> diets = userService.readAllMyDiets(id);
        return ResponseEntity.ok(diets);
    }

    // 사용자 삭제 (성공 시 200 OK, 실패 시 404 Not Found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> removeUser(@PathVariable int id) {
        try {
            int removedUserId = userService.removeUser(id);
            return ResponseEntity.ok(removedUserId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
