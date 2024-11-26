package com.dualforce.fitquest.service.user;

import com.dualforce.fitquest.model.dao.DietDao;
import com.dualforce.fitquest.model.dao.ExerciseDao;
import com.dualforce.fitquest.model.dao.UserDao;
import com.dualforce.fitquest.model.dto.DietDto;
import com.dualforce.fitquest.model.dto.ExerciseDto;
import com.dualforce.fitquest.model.dto.UserDto;
import com.dualforce.fitquest.util.PasswordUtil;
import com.dualforce.fitquest.validator.PasswordValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ExerciseDao exerciseDao;
    private final DietDao dietDao;

    UserServiceImpl(UserDao userDao, ExerciseDao exerciseDao, DietDao dietDao) {
        this.userDao = userDao;
        this.exerciseDao = exerciseDao;
        this.dietDao = dietDao;
    }

    @Override
    public int createUser(UserDto user) {
        if (!PasswordValidator.isValid(user.getPassword())) {
            throw new IllegalArgumentException("Password does not meet the required criteria.");
        }

        String encodedPassword = PasswordUtil.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);

        userDao.insertUser(user);
        return user.getUserId();
    }

    @Override
    public UserDto readUser(int id) {
        UserDto user = userDao.selectUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        return user;
    }

    @Override
    public UserDto readUserByNickname(String nickname) {
        UserDto user = userDao.selectUserByNickname(nickname);
        if (user == null) {
            throw new IllegalArgumentException("User not found with Nickname: " + nickname);
        }
        return user;
    }

    @Override
    public UserDto readUserByEmail(String email) {
        UserDto user = userDao.selectUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with Nickname: " + email);
        }
        return user;
    }

    @Override
    public int editUser(UserDto user) {
        if (!PasswordValidator.isValid(user.getPassword())) {
            throw new IllegalArgumentException("Password does not meet the required criteria.");
        }

        String encodedPassword = PasswordUtil.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        
        userDao.updateUser(user);
        return user.getUserId();
    }

    @Override
    public List<ExerciseDto> readAllMyExercises(int userId) {
        return exerciseDao.selectExercisesByUserId(userId);
    }

    @Override
    public List<DietDto> readAllMyDiets(int userId) {
        return dietDao.selectDietByUserId(userId);
    }

    @Override
    public int removeUser(int id) {
        UserDto user = userDao.selectUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }

        userDao.deleteUser(id);
        return user.getUserId();
    }
}
