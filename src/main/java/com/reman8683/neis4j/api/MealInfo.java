package com.reman8683.neis4j.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.reman8683.neis4j.dto.Meal;
import com.reman8683.neis4j.dto.School;
import com.squareup.okhttp.HttpUrl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.reman8683.neis4j.dto.School.NEIS_URL;

public class MealInfo {
    public Meal getMeal(School school, String mealYMD) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host(NEIS_URL)
                .addEncodedPathSegment("hub")
                .addPathSegment("mealServiceDietInfo")
                .addQueryParameter("Type", "json")

                .addQueryParameter("SD_SCHUL_CODE", String.valueOf(school.getSchoolCode()))
                .addQueryParameter("ATPT_OFCDC_SC_CODE", school.getOfficeCode().getOfficeCode())
                .addQueryParameter("MLSV_YMD", mealYMD)

                .build();

        return NeisJsonToMeal(school, mealYMD, httpUrl);
    }

    private Meal NeisJsonToMeal(School school, String mealYMD, HttpUrl httpUrl) throws IOException {
        Gson gson = new Gson();
        JsonArray schoolReq = gson.fromJson(FindSchool.getResponseFromNeisApi(httpUrl), JsonObject.class).get("mealServiceDietInfo").getAsJsonArray();
        if (schoolReq.get(0).getAsJsonObject().get("head").getAsJsonArray().get(1).getAsJsonObject().get("RESULT").getAsJsonObject().get("CODE").getAsString().equals("INFO-000")) {
            JsonObject mealData = schoolReq.get(1).getAsJsonObject().get("row").getAsJsonArray().get(0).getAsJsonObject();
            List<String> menu = Arrays.stream(
                    mealData.get("DDISH_NM").getAsString()
                            .replace(" ", "").split("<br/>")
            ).toList();
            return new Meal(
                    school,
                    mealYMD,
                    menu
            );
        }
        return null;
    }
}
