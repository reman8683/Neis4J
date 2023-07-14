package com.reman8683.neis4j.meal;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.reman8683.neis4j.school.School;
import com.squareup.okhttp.HttpUrl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.reman8683.neis4j.APIRequest.getResponseFromNeisApi;
import static com.reman8683.neis4j.school.School.NEIS_URL;

public class MealInfoApi {
    protected Meal getMeal(School school, String mealYMD) throws IOException {
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
        JsonArray mealReq = gson.fromJson(getResponseFromNeisApi(httpUrl), JsonObject.class).get("mealServiceDietInfo").getAsJsonArray();
        if (mealReq.get(0).getAsJsonObject().get("head").getAsJsonArray().get(1).getAsJsonObject().get("RESULT").getAsJsonObject().get("CODE").getAsString().equals("INFO-000")) {
            JsonObject mealData = mealReq.get(1).getAsJsonObject().get("row").getAsJsonArray().get(0).getAsJsonObject();
            List<String> menu = Arrays.stream(
                    mealData.get("DDISH_NM").getAsString()
                            .replace(" ", "").split("<br/>")
            ).collect(Collectors.toList());
            return new Meal(
                    school,
                    mealYMD,
                    menu
            );
        }
        return null;
    }
}
