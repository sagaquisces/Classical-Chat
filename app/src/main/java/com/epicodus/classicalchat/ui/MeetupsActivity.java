package com.epicodus.classicalchat.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.classicalchat.adapters.MeetupListAdapter;
import com.epicodus.classicalchat.services.MeetupService;
import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.models.Meetup;

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
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private MeetupListAdapter mAdapter;

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
                        mAdapter = new MeetupListAdapter(getApplicationContext(), mMeetups);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MeetupsActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                    }
                });
            }
        });
    }
}
