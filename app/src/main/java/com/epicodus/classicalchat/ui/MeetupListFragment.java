package com.epicodus.classicalchat.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.classicalchat.Constants;
import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.adapters.MeetupListAdapter;
import com.epicodus.classicalchat.models.Meetup;
import com.epicodus.classicalchat.services.MeetupService;
import com.epicodus.classicalchat.util.OnMeetupSelectedListener;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeetupListFragment extends Fragment {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.locationTextView) TextView mLocationTextView;

    private MeetupListAdapter mAdapter;
    public ArrayList<Meetup> mMeetups = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;
    private OnMeetupSelectedListener mOnMeetupSelectedListener;

    public MeetupListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnMeetupSelectedListener = (OnMeetupSelectedListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetup_list, container, false);
        ButterKnife.bind(this, view);
        mRecentAddress = mRecentAddress==null?"Seattle":mRecentAddress;
        mLocationTextView.setText("Here are all the meetups near " + mRecentAddress);
        getMeetups(mRecentAddress); //default will currently be Seattle
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setQueryHint("Enter a major city");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getMeetups(query);
                mLocationTextView.setText((null == query) ? "Your search returned no results" : ("Here are all the meetups near:" + query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void getMeetups(String location) {
        final MeetupService meetupService = new MeetupService();
        meetupService.findMeetups(location, new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                mMeetups = meetupService.processResults(response);

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new MeetupListAdapter(getActivity(), mMeetups, mOnMeetupSelectedListener);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
