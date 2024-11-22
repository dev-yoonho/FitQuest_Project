package com.dualforce.fitquest.service.diet;

import com.dualforce.fitquest.model.dao.DietDao;
import com.dualforce.fitquest.model.dto.DietDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DietServiceImpl implements DietService {
    private final DietDao dietDao;

    public DietServiceImpl(DietDao dietDao) {
        this.dietDao = dietDao;
    }

    @Override
    public int createDiet(DietDto diet) {
        dietDao.insertDiet(diet);
        return diet.getDietId();
    }

    @Override
    public List<DietDto> readTodayMyDiets(int userId) {
        LocalDate today = LocalDate.now();
        String startDate = today.toString();
        String endDate = today.toString();

        return dietDao.selectDietByUserIdDateRange(userId, startDate, endDate);
    }

    @Override
    public int editDiet(DietDto diet) {
        return dietDao.updateDiet(diet);
    }

    @Override
    public int removeDiet(int dietId) {
        DietDto diet = dietDao.selectDietById(dietId);
        if (diet == null) {
            throw new IllegalArgumentException("Diet not found with ID: " + dietId);
        }

        dietDao.deleteDiet(dietId);
        return diet.getDietId();
    }

    @Override
    public Map<String, Integer> calculateDietTypeDistribution(int userId, LocalDate date) {
        List<DietDto> diets = readTodayMyDiets(userId);

        Map<String, Integer> distribution = new HashMap<>();
        distribution.put("아침", 0);
        distribution.put("점심", 0);
        distribution.put("저녁", 0);

        for (DietDto diet : diets) {
            String mealType = diet.getMealType();
            distribution.put(mealType, distribution.getOrDefault(mealType, 0) + 1);
        }

        return distribution;

    }
}
