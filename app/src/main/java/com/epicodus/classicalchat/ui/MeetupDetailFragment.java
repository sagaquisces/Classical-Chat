package com.epicodus.classicalchat.ui;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.classicalchat.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeetupDetailFragment extends Fragment {


    public MeetupDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meetup_detail, container, false);
    }

}
