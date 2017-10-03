package com.epicodus.classicalchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MeetupsActivity extends AppCompatActivity {
    public static final String TAG = MeetupsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetups);

        getMeetups("98101");
    }

    private void getMeetups(String location) {
        final MeetupService meetupService = new MeetupService();
        meetupService.findMeetups(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);

                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
