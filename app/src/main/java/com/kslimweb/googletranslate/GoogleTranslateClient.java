package com.kslimweb.googletranslate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleTranslateClient {

    private static final String BASE_URL = "https://translation.googleapis.com/language/translate/v2/";
    public static final String TRANSLATION_API_KEY = "AIzaSyCGOWBmAQWqcF1qzVygelsokY5qUCRWujo";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
