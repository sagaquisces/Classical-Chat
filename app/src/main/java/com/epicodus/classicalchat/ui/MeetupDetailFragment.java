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

import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.models.Meetup;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeetupDetailFragment extends Fragment {
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

        return view;
    }

}
