package com.dualforce.fitquest.service.exercise;

import com.dualforce.fitquest.model.dao.ExerciseDao;
import com.dualforce.fitquest.model.dto.ExerciseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseDao exerciseDao;

    public ExerciseServiceImpl(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @Override
    public int createExercise(ExerciseDto exercise) {
        exerciseDao.insertExercise(exercise);
        return exercise.getExerciseId();
    }

    @Override
    public List<ExerciseDto> readTodayMyExercises(int userId) {
        LocalDate today = LocalDate.now();
        String startDate = today.toString();
        String endDate = today.toString();

        return exerciseDao.selectExercisesByUserIdDateRange(userId, startDate, endDate);
    }

    @Override
    public int editExercise(ExerciseDto exerciseDto) {
        exerciseDao.updateExercise(exerciseDto);
        return exerciseDto.getExerciseId();
    }

    @Override
    public int removeExercise(int exerciseId) {
        ExerciseDto exercise = exerciseDao.selectExerciseById(exerciseId);
        if (exercise == null) {
            throw new IllegalArgumentException("Exercise not found with ID: " + exerciseId);
        }
        exerciseDao.deleteExercise(exerciseId);
        return exercise.getExerciseId();
    }

    @Override
    public List<LocalDate> getExerciseDatesForMonth(int userId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<ExerciseDto> exercises = exerciseDao.selectExercisesByUserIdDateRange(userId, startDate.toString(), endDate.toString());

        return exercises.stream()
                .map(exercise -> exercise.getCreatedAt().toLocalDate())
                .distinct()
                .sorted()
                .toList();
    }

    @Override
    public Map<String, Integer> calculateExerciseTypeDistribution(int userId, LocalDate date) {
        String[] exerciseTypes = {"근력", "유산소", "코어"};
        Map<String, Integer> distribution = new HashMap<>();

        for (String type : exerciseTypes) {
            List<ExerciseDto> exercises = exerciseDao.selectExercisesByUserIdExerciseType(userId, type);

            int count = (int) exercises.stream()
                    .filter(e -> e.getCreatedAt().toLocalDate().equals(date))
                    .count();
            distribution.put(type, count);
        }
        return distribution;
    }
}
