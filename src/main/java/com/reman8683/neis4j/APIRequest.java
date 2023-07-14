package com.reman8683.neis4j;

import com.squareup.okhttp.*;

import java.io.IOException;

public class APIRequest {
     public static String getResponseFromNeisApi(HttpUrl httpUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder().url(httpUrl).get();
        Request request = builder.build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            // get response and analyze
            ResponseBody body = response.body();
            if (body != null) {
                return body.string();
            }
        }
        return null;
    }
}
