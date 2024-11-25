package com.dualforce.fitquest.service.security;

import com.dualforce.fitquest.model.dao.UserDao;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final UserDao userDao;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
    }

    // 관리자인지 확인
    public boolean isAdmin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            return userDao.selectUserByEmail(email).isAdmin(); // isAdmin()은 UserDto의 메서드
        }

        return false;
    }

    // 사용자 ID로 소유권 확인
    public boolean isOwner(Authentication authentication, int userId) {
        if (isAdmin(authentication)) {
            return true; // 관리자는 항상 권한이 있음
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            return userDao.selectUserByEmail(email).getUserId() == userId;
        }

        return false;
    }

    // 운동 기록 소유자 검증
    public boolean isExerciseOwner(Authentication authentication, int exerciseId) {
        if (isAdmin(authentication)) {
            return true; // 관리자는 항상 권한이 있음
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            int userId = userDao.selectUserByEmail(email).getUserId();
            return userDao.selectExerciseOwnerById(exerciseId) == userId;
        }

        return false;
    }

    // 식단 기록 소유자 검증
    public boolean isDietOwner(Authentication authentication, int dietId) {
        if (isAdmin(authentication)) {
            return true; // 관리자는 항상 권한이 있음
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            int userId = userDao.selectUserByEmail(email).getUserId();
            return userDao.selectDietOwnerById(dietId) == userId;
        }

        return false;
    }

    // 게시글 소유자 검증
    public boolean isPostOwner(Authentication authentication, int postId) {
        if (isAdmin(authentication)) {
            return true; // 관리자는 항상 권한이 있음
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            int userId = userDao.selectUserByEmail(email).getUserId();
            return userDao.selectPostOwnerById(postId) == userId;
        }

        return false;
    }

    // 댓글 소유자 검증
    public boolean isCommentOwner(Authentication authentication, int commentId) {
        if (isAdmin(authentication)) {
            return true; // 관리자는 항상 권한이 있음
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            int userId = userDao.selectUserByEmail(email).getUserId();
            return userDao.selectCommentOwnerById(commentId) == userId;
        }

        return false;
    }
}
