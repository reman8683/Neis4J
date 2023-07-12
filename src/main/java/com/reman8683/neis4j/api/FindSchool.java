package com.reman8683.neis4j.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.reman8683.neis4j.OfficeCode;
import com.reman8683.neis4j.dto.School;
import com.squareup.okhttp.*;

import java.io.IOException;

import static com.reman8683.neis4j.dto.School.NEIS_URL;

public class FindSchool {
    public School byName(String name) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host(NEIS_URL)
                .addEncodedPathSegment("hub")
                .addPathSegment("schoolInfo")
                .addQueryParameter("Type", "json")

                .addQueryParameter("SCHUL_NM", name)

                .build();

        return NeisJsonToSchool(httpUrl);
    }

    public School bySchoolCode(long schoolCode) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host(NEIS_URL)
                .addEncodedPathSegment("hub")
                .addPathSegment("schoolInfo")
                .addQueryParameter("Type", "json")

                .addQueryParameter("SD_SCHUL_CODE", String.valueOf(schoolCode))

                .build();

        return NeisJsonToSchool(httpUrl);
    }

    private School NeisJsonToSchool(HttpUrl httpUrl) throws IOException {
        Gson gson = new Gson();
        JsonArray schoolReq = gson.fromJson(getResponseFromNeisApi(httpUrl), JsonObject.class).get("schoolInfo").getAsJsonArray();

        if (schoolReq.get(0).getAsJsonObject().get("head").getAsJsonArray().get(1).getAsJsonObject().get("RESULT").getAsJsonObject().get("CODE").getAsString().equals("INFO-000")) {
            JsonObject schoolData = schoolReq.get(1).getAsJsonObject().get("row").getAsJsonArray().get(0).getAsJsonObject();
            return new School(
                    schoolData.get("SCHUL_NM").getAsString(),
                    schoolData.get("SD_SCHUL_CODE").getAsLong(),
                    schoolData.get("SCHUL_KND_SC_NM").getAsString(),
                    OfficeCode.find(schoolData.get("ATPT_OFCDC_SC_CODE").getAsString())
            );
        }
        return null;
    }

     protected static String getResponseFromNeisApi(HttpUrl httpUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder().url(httpUrl).get();
        Request request = builder.build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            // 응답 받아서 처리
            ResponseBody body = response.body();
            if (body != null) {
                return body.string();
            }
        }
        return null;
    }
}
