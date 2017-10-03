package com.epicodus.classicalchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MeetupsActivity extends AppCompatActivity {
    public static final String TAG = MeetupsActivity.class.getSimpleName();

    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(R.id.listView) ListView mListView;

    public ArrayList<Meetup> mMeetups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetups);
        ButterKnife.bind(this);

        String location = "98101"; //for temporary use

        mLocationTextView.setText("Here are all the meetups near:" + location);

        //need corresponding intent from MainActivity
//        Intent intent = getIntent();
//        String location = intent.getStringExtra("location")

        getMeetups(location);
    }

    private void getMeetups(String location) {
        final MeetupService meetupService = new MeetupService();
        meetupService.findMeetups(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mMeetups = meetupService.processResults(response);

                MeetupsActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String[] meetupNames = new String [mMeetups.size()];

                        for (int i = 0; i < meetupNames.length; i++) {
                            meetupNames[i] = mMeetups.get(i).getName();
                        }

                        ArrayAdapter adapter = new ArrayAdapter(MeetupsActivity.this, android.R.layout.simple_list_item_1, meetupNames);
                        mListView.setAdapter(adapter);

                        for(Meetup meetup :mMeetups) {
                            Log.d(TAG, "Score: " + meetup.getScore());
                            Log.d(TAG, "Name: " + meetup.getName());
                            Log.d(TAG, "Link: " + meetup.getName());
                            Log.d(TAG, "Description: " + meetup.getName());
                            Log.d(TAG, "Location: " + meetup.getLocation());
                            Log.d(TAG, "Organizer: " + meetup.getOrganizer());
                            Log.d(TAG, "Image: " + meetup.getImageUrl());
                        }
                    }
                });
            }
        });
    }
}
