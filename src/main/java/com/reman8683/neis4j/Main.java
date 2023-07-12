package com.reman8683.neis4j;

import com.reman8683.neis4j.api.MealInfo;
import com.reman8683.neis4j.dto.Meal;
import com.reman8683.neis4j.dto.School;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        School school = new School("정관고등학교");
        Meal meal = new MealInfo().getMeal(school, "20230523");
        meal.removeAllergy();
        System.out.println(meal.getMenu());
        System.out.println(school);
    }
}