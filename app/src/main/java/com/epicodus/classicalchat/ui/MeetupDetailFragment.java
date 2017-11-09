package com.epicodus.classicalchat.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

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
    private ArrayList<Meetup> mMeetups;
    private int mPosition;
    private String mSource;

    public static MeetupDetailFragment newInstance(ArrayList<Meetup> meetups, Integer position, String source) {
        MeetupDetailFragment meetupDetailFragment = new MeetupDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_KEY_MEETUPS, Parcels.wrap(meetups));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);

        meetupDetailFragment.setArguments(args);
        return meetupDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetups = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_MEETUPS));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mMeetup = mMeetups.get(mPosition);
        mSource = getArguments().getString(Constants.KEY_SOURCE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetup_detail, container, false);
        ButterKnife.bind(this, view);

        String tempText = mMeetup.getDescription();
        Spanned result = Html.fromHtml(tempText);

        Picasso.with(view.getContext()).load(mMeetup.getImageUrl()).into(mImageLabel);

        mNameLabel.setText(mMeetup.getName());
        mScoreLabel.setText(Double.toString(mMeetup.getScore()));
        mLocationLabel.setText(mMeetup.getLocation());
        mDescriptionLabel.setText(result);

        mWebsiteLabel.setOnClickListener(this);

        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveBtn.setVisibility(View.GONE);
        } else {
            mSaveBtn.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mMeetup.getLink()));
            startActivity(webIntent);
        }

        if (v == mSaveBtn) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference meetupRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_MEETUPS)
                    .child(uid);

            DatabaseReference pushRef = meetupRef.push();
            String pushId = pushRef.getKey();
            mMeetup.setPushId(pushId);
            pushRef.setValue(mMeetup);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }

    }
}
