package com.reman8683.neis4j.timetable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.reman8683.neis4j.school.School;
import com.squareup.okhttp.HttpUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.reman8683.neis4j.APIRequest.getResponseFromNeisApi;
import static com.reman8683.neis4j.school.School.NEIS_URL;

public class TimeTableApi {

    protected TimeTable getTimeTable(School school, String timeTableYMD, int grade, int classNumber) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host(NEIS_URL)
                .addEncodedPathSegment("hub")
                .addPathSegment(
                        (school.getType().equals("\uACE0\uB4F1\uD559\uAD50") ? "h"
                                : school.getType().equals("\uC911\uD559\uAD50") ? "m"
                                : "e")
                                + "isTimetable"
                )
                .addQueryParameter("Type", "json")
                .addQueryParameter("KEY", "10c97339e65a473ab7ba1ed0047d694f")

                .addQueryParameter("SD_SCHUL_CODE", String.valueOf(school.getSchoolCode()))
                .addQueryParameter("ATPT_OFCDC_SC_CODE", school.getOfficeCode().getOfficeCode())
                .addQueryParameter("ALL_TI_YMD", timeTableYMD)
                .addQueryParameter("GRADE", String.valueOf(grade))
                .addQueryParameter("CLASS_NM", String.valueOf(classNumber))

                .build();

        return NeisJsonToTimeTable(school, timeTableYMD, grade, classNumber, httpUrl);
    }

    private TimeTable NeisJsonToTimeTable(School school, String timeTableYMD, int grade, int classNumber, HttpUrl httpUrl) throws IOException {
        Gson gson = new Gson();
        JsonArray timeTableReq = gson.fromJson(getResponseFromNeisApi(httpUrl), JsonObject.class).get("hisTimetable").getAsJsonArray();
        if (timeTableReq.get(0).getAsJsonObject().get("head").getAsJsonArray().get(1).getAsJsonObject().get("RESULT").getAsJsonObject().get("CODE").getAsString().equals("INFO-000")) {
            timeTableReq.remove(0);
            List<String> timeTable = new ArrayList<>();
            for (JsonElement timeTableDatum : timeTableReq.get(0).getAsJsonObject().get("row").getAsJsonArray()) {
                //JsonObject timeTableData = timeTableDatum.getAsJsonObject().get("row").getAsJsonArray().get(0).getAsJsonObject();
                timeTable.add(timeTableDatum.getAsJsonObject().get("ITRT_CNTNT").getAsString());
            }
            return new TimeTable(
                    school,
                    timeTableYMD,
                    grade,
                    classNumber,
                    timeTable
            );
        }
        return null;
    }
}
