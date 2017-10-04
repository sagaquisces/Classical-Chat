package com.epicodus.classicalchat.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.classicalchat.Constants;
import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.models.Meetup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeetupDetailFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.meetupImageView) ImageView mImageLabel;
    @Bind(R.id.meetupNameTextView) TextView mNameLabel;
    @Bind(R.id.scoreTextView) TextView mScoreLabel;
    @Bind(R.id.locationTextView) TextView mLocationLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.descriptionTextView) TextView mDescriptionLabel;
    @Bind(R.id.saveMeetupButton) Button mSaveBtn;

    private Meetup mMeetup;

    public static MeetupDetailFragment newInstance(Meetup meetup) {
        MeetupDetailFragment meetupDetailFragment = new MeetupDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("meetup", Parcels.wrap(meetup));
        meetupDetailFragment.setArguments(args);
        return meetupDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetup = Parcels.unwrap(getArguments().getParcelable("meetup"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetup_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mMeetup.getImageUrl()).into(mImageLabel);

        mNameLabel.setText(mMeetup.getName());
        mScoreLabel.setText(Double.toString(mMeetup.getScore()));
        mLocationLabel.setText(mMeetup.getLocation());
        mDescriptionLabel.setText(mMeetup.getDescription());

        mWebsiteLabel.setOnClickListener(this);

        mSaveBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Toast.makeText(getContext(), "Will eventually go to website", Toast.LENGTH_SHORT);
        }

        if (v == mSaveBtn) {
            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_MEETUPS);

            restaurantRef.push().setValue(mMeetup);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }

    }
}
