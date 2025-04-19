package com.example.lab1.api;

import android.content.Context;
import androidx.annotation.NonNull;

import com.example.lab1.R;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FilmsAPI {
    public static class Requests {
        public static void getTopFilms(Context context, Callback mainCallback) {
            Request request = new Request.Builder()
                    .url("https://kinopoiskapiunofficial.tech/api/v2.2/films/collections")
                    .header("X-API-KEY", context.getResources().getString(R.string.API_KEY))
                    .build();

            final OkHttpClient okhttpclient = new OkHttpClient();
            okhttpclient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    mainCallback.onFailure(call, e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Request failed: " + response.code() + " " + response.message());
                    }
                    mainCallback.onResponse(call, response);
                }
            });
        }
    }
}
