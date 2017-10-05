package com.epicodus.classicalchat.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.models.Meetup;
import com.epicodus.classicalchat.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by Guest on 10/4/17.
 */

public class FirebaseMeetupViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;
    public ImageView mMeetupImageView;

    public FirebaseMeetupViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindMeetup(Meetup meetup) {
        mMeetupImageView = (ImageView) mView.findViewById(R.id.meetupImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.meetupNameTextView);
        TextView locationTextView = (TextView) mView.findViewById(R.id.meetupLocationTextView);
        TextView scoreTextView = (TextView) mView.findViewById(R.id.meetupScoreTextView);

        Picasso.with(mContext)
                .load(meetup.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mMeetupImageView);

        nameTextView.setText(meetup.getName());
        locationTextView.setText(meetup.getLocation());
        scoreTextView.setText(Double.toString(meetup.getScore()));
    }

    @Override
    public void onItemSelected() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }

    @Override
    public void onItemClear() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }
}
