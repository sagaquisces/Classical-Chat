package com.epicodus.classicalchat.services;

import com.epicodus.classicalchat.Constants;
import com.epicodus.classicalchat.models.Meetup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        urlBuilder.addQueryParameter(Constants.MEETUP_RADIUS_QUERY_PARAMETER, Constants.MEETUP_RADIUS_VALUE);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Meetup> processResults(Response response) {
        ArrayList<Meetup> meetups = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONArray meetupsJSON = new JSONArray(jsonData);

                for (int i = 0; i < meetupsJSON.length(); i++) {
                    JSONObject meetupJSON = meetupsJSON.getJSONObject(i);

                    double score = meetupJSON.getDouble("score");
                    String name = meetupJSON.getString("name");
                    String link = meetupJSON.getString("link");
                    String description = meetupJSON.getString("description");
                    String location = meetupJSON.getString("localized_location");
                    String organizer = meetupJSON.getJSONObject("organizer").optString("name", "No organizer name found.");
                    String imageUrl = meetupJSON.getJSONObject("organizer").getJSONObject("photo").getString("photo_link");

                    Meetup meetup = new Meetup(score, name, link, description, location, organizer, imageUrl);
                    meetups.add(meetup);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return meetups;

    }

}
