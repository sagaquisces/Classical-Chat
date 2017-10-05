package com.epicodus.classicalchat.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.classicalchat.models.Meetup;
import com.epicodus.classicalchat.ui.MeetupDetailFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Guest on 10/3/17.
 */

public class MeetupPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Meetup> mMeetups;
    private String mSource;

    public MeetupPagerAdapter(FragmentManager fm, ArrayList<Meetup> meetups, String source) {
        super(fm);
        mMeetups = meetups;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return MeetupDetailFragment.newInstance(mMeetups, position, mSource);
    }

    @Override
    public int getCount() {
        return mMeetups.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMeetups.get(position).getName();
    }
}
