package com.epicodus.classicalchat.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.classicalchat.Constants;
import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.models.Meetup;
import com.epicodus.classicalchat.ui.MeetupDetailActivity;
import com.epicodus.classicalchat.util.OnMeetupSelectedListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 10/3/17.
 */

public class MeetupListAdapter extends RecyclerView.Adapter<MeetupListAdapter.MeetupViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Meetup> mMeetups = new ArrayList<>();
    private Context mContext;
    private OnMeetupSelectedListener mOnMeetupSelectedListener;

    public MeetupListAdapter(Context context, ArrayList<Meetup> meetups, OnMeetupSelectedListener meetupSelectedListener) {
        mContext = context;
        mMeetups = meetups;
        mOnMeetupSelectedListener = meetupSelectedListener;
    }

    @Override
    public MeetupListAdapter.MeetupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetup_list_item, parent, false);
        MeetupViewHolder viewHolder = new MeetupViewHolder(view, mMeetups, mOnMeetupSelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MeetupListAdapter.MeetupViewHolder holder, int position) {
        holder.bindMeetup(mMeetups.get(position));
    }

    @Override
    public int getItemCount() {
        return mMeetups.size();
    }

    public class MeetupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.meetupImageView) ImageView mImageView;
        @Bind(R.id.meetupNameTextView) TextView mNameTextView;
        @Bind(R.id.meetupLocationTextView) TextView mLocationTextView;
        @Bind(R.id.meetupScoreTextView) TextView mScoreTextView;

        private Context mContext;
        private int mOrientation;
        private ArrayList<Meetup> mMeetups = new ArrayList<>();
        private OnMeetupSelectedListener mMeetupSelectedListener;

        public MeetupViewHolder(View itemView, ArrayList<Meetup> meetups, OnMeetupSelectedListener meetupSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            
            mContext = itemView.getContext();
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mMeetups = meetups;
            mMeetupSelectedListener = meetupSelectedListener;
            
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }
            itemView.setOnClickListener(this);
        }

        private void createDetailFragment(int i) {
        }

        public void bindMeetup(Meetup meetup) {
            Picasso.with(mContext).load(meetup.getImageUrl()).into(mImageView);
            mNameTextView.setText(meetup.getName());
            mLocationTextView.setText(meetup.getLocation());
            mScoreTextView.setText("Score: " + meetup.getScore());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            mOnMeetupSelectedListener.onMeetupSelected(itemPosition, mMeetups);
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, MeetupDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_MEETUPS, Parcels.wrap(mMeetups));
                mContext.startActivity(intent);
            }

        }
    }
}
