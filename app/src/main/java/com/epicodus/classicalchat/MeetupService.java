package com.epicodus.classicalchat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Guest on 10/3/17.
 */

public class MeetupService {

    public static void findMeetups(String location, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MEETUP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.MEETUP_API_KEY_PARAMETER, Constants.MEETUP_API_KEY);
        urlBuilder.addQueryParameter(Constants.MEETUP_TOPIC_QUERY_PARAMETER, Constants.MEETUP_CLASSICAL_MUSIC_TOPIC_ID);
        urlBuilder.addQueryParameter(Constants.MEETUP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
