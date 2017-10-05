package com.epicodus.classicalchat.util;

import com.epicodus.classicalchat.models.Meetup;

import java.util.ArrayList;

/**
 * Created by Guest on 10/4/17.
 */

public interface OnMeetupSelectedListener {
    public void onMeetupSelected(Integer position, ArrayList<Meetup> meetups);
}
