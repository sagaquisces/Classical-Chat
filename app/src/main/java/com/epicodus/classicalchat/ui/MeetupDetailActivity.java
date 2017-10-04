package com.epicodus.classicalchat.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.adapters.MeetupPagerAdapter;
import com.epicodus.classicalchat.models.Meetup;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MeetupDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private MeetupPagerAdapter adapterViewPager;
    ArrayList<Meetup> mMeetups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetup_detail);
        ButterKnife.bind(this);

        mMeetups = Parcels.unwrap(getIntent().getParcelableExtra("meetups"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new MeetupPagerAdapter(getSupportFragmentManager(), mMeetups);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

    }
}
