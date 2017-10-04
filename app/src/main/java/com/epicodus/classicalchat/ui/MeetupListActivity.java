package com.epicodus.classicalchat.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.epicodus.classicalchat.Constants;
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

public class MeetupListActivity extends AppCompatActivity {
    public static final String TAG = MeetupListActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;

    @Bind(R.id.meetups_list_toolbar) Toolbar mToolbar;
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

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Classical Chat");

        //need corresponding intent from MainActivity
//        Intent intent = getIntent();
//        String location = intent.getStringExtra("location")

        getMeetups(location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getMeetups(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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

                MeetupListActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new MeetupListAdapter(getApplicationContext(), mMeetups);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MeetupListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                    }
                });
            }
        });
    }

    private void addToSharedPreferences (String location) {
        mEditor.putString(Constants.PREFERENCE_LOCATION_KEY, location).apply();
    }
}
