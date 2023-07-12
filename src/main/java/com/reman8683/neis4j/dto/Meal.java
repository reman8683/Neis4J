package com.reman8683.neis4j.dto;

import com.reman8683.neis4j.api.MealInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Meal {
    private final School school;
    private final String mealYMD;
    private List<String> menu;

    public Meal(School school, String mealYMD, List<String> menu) {
        this.school = school;
        this.mealYMD = mealYMD;
        this.menu = menu;
    }

    public Meal(School school, String mealYMD) throws IOException {
        Meal meal = new MealInfo().getMeal(school, mealYMD);
        this.school = school;
        this.mealYMD = mealYMD;
        this.menu = meal.menu;
    }

    public void removeAllergy() {
        List<String> AllergyRemovedMenuArray = new ArrayList<>();
        for (String menu : menu) {
            AllergyRemovedMenuArray.add(menu.replaceAll("\\(.*?\\)", ""));
        }
        this.menu = AllergyRemovedMenuArray;
    }

    public List<String> getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "school=" + school +
                ", mealYMD='" + mealYMD + '\'' +
                ", menu=" + menu +
                '}';
    }
}
