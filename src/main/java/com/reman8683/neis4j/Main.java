package com.reman8683.neis4j;

import com.reman8683.neis4j.api.MealInfo;
import com.reman8683.neis4j.dto.Meal;
import com.reman8683.neis4j.dto.School;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) throws IOException {
        long avg = 0;
        int count = 25;
        for (int i = 0; i < count; i++) {
            Instant start = Instant.now();
            ////////
            School school = new School("정관고등학교");
            Meal meal = new MealInfo().getMeal(school, "20230523");
            meal.removeAllergy();
            ////////
            Instant end = Instant.now();
            avg += Duration.between(start, end).toMillis();
        }
        System.out.println("수행시간 평균: " + (float) avg / count  + " ms");
    }
}