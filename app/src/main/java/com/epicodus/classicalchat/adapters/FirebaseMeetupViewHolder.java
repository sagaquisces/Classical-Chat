package com.epicodus.classicalchat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.models.Meetup;
import com.squareup.picasso.Picasso;

/**
 * Created by Guest on 10/4/17.
 */

public class FirebaseMeetupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseMeetupViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindMeetup(Meetup meetup) {
        ImageView meetupImageView = (ImageView) mView.findViewById(R.id.meetupImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.meetupNameTextView);
        TextView locationTextView = (TextView) mView.findViewById(R.id.meetupLocationTextView);
        TextView scoreTextView = (TextView) mView.findViewById(R.id.meetupScoreTextView);

        Picasso.with(mContext)
                .load(meetup.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(meetupImageView);

        nameTextView.setText(meetup.getName());
        locationTextView.setText(meetup.getLocation());
        scoreTextView.setText(Double.toString(meetup.getScore()));
    }

    @Override
    public void onClick(View v) {

    }
}
