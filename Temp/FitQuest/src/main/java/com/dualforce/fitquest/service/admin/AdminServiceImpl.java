package com.dualforce.fitquest.service.admin;

import com.dualforce.fitquest.model.dao.*;
import com.dualforce.fitquest.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserDao userDao;
    private final PostDao postDao;
    private final CommentDao commentDao;
    private final ExerciseDao exerciseDao;
    private final DietDao dietDao;

    public AdminServiceImpl(UserDao userDao, PostDao postDao, CommentDao commentDao, ExerciseDao exerciseDao, DietDao dietDao) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.exerciseDao = exerciseDao;
        this.dietDao = dietDao;
    }

    @Override
    public List<UserDto> readAllUsers() {
        return userDao.selectAllUsers();
    }

    @Override
    public List<PostDto> readPostsByUserId(int userId) {
        return postDao.selectPostsByUserId(userId);
    }

    @Override
    public List<CommentDto> readCommentsByNickname(String nickname) {
        return commentDao.selectUserNicknameComment(nickname);
    }

    @Override
    public List<CommentDto> readCommentsByUserId(int userId) {
        return commentDao.selectUserIdComment(userId);
    }

    @Override
    public List<ExerciseDto> readExercisesByUserId(int userId) {
        return exerciseDao.selectExercisesByUserId(userId);
    }

    @Override
    public List<DietDto> readDietsByUserId(int userId) {
        return dietDao.selectDietByUserId(userId);
    }
}
