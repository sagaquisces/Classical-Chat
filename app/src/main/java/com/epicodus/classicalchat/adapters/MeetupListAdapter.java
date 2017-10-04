package com.epicodus.classicalchat.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.models.Meetup;
import com.epicodus.classicalchat.ui.MeetupDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 10/3/17.
 */

public class MeetupListAdapter extends RecyclerView.Adapter<MeetupListAdapter.MeetupViewHolder> {
    private ArrayList<Meetup> mMeetups = new ArrayList<>();
    private Context mContext;

    public MeetupListAdapter(Context context, ArrayList<Meetup> meetups) {
        mContext = context;
        mMeetups = meetups;
    }

    @Override
    public MeetupListAdapter.MeetupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetup_list_item, parent, false);
        MeetupViewHolder viewHolder = new MeetupViewHolder(view);
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

        public MeetupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
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
            Intent intent = new Intent(mContext, MeetupDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("meetups", Parcels.wrap(mMeetups));
            mContext.startActivity(intent);
        }
    }
}
