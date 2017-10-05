package com.epicodus.classicalchat.ui;

import android.content.Intent;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.epicodus.classicalchat.Constants;
import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.models.Meetup;
import com.epicodus.classicalchat.util.OnMeetupSelectedListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MeetupListActivity extends AppCompatActivity implements View.OnClickListener,OnMeetupSelectedListener {

    @Bind(R.id.meetups_list_toolbar) Toolbar mToolbar;
    @Bind(R.id.savedMeetupsBtn) Button mSavedBtn;

    private Integer mPosition;
    ArrayList<Meetup> mMeetups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetups);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Classical Chat");

        if (savedInstanceState != null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mMeetups = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_MEETUPS));

                if (mPosition != null && mMeetups != null) {
                    Intent intent = new Intent(this, MeetupDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_MEETUPS, Parcels.wrap(mMeetups));
                    startActivity(intent);
                }
            }
        }

        mSavedBtn.setOnClickListener(this);

    }

    @Override
    protected  void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mPosition != null && mMeetups != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_MEETUPS, Parcels.wrap(mMeetups));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mSavedBtn) {
            Intent intent = new Intent(MeetupListActivity.this, SavedMeetupListActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onMeetupSelected(Integer position, ArrayList<Meetup> meetups) {
        mPosition = position;
        mMeetups = meetups;

    }
}
