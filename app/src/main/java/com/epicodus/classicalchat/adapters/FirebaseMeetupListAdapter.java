package com.epicodus.classicalchat.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.classicalchat.models.Meetup;
import com.epicodus.classicalchat.ui.MeetupDetailActivity;
import com.epicodus.classicalchat.util.ItemTouchHelperAdapter;
import com.epicodus.classicalchat.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Guest on 10/5/17.
 */

public class FirebaseMeetupListAdapter extends FirebaseRecyclerAdapter<Meetup, FirebaseMeetupViewHolder> implements ItemTouchHelperAdapter{

    /**
     * @param modelClass      Firebase will marshall the data at a location into
     *                        an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list.
     *                        You will be responsible for populating an instance of the corresponding
     *                        view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location,
     *                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private ChildEventListener mChildEventListener;
    private Context mContext;
    private ArrayList<Meetup> mMeetups = new ArrayList<>();

    public FirebaseMeetupListAdapter (Class<Meetup> modelClass, int modelLayout, Class<FirebaseMeetupViewHolder> viewHolderClass, Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mMeetups.add(dataSnapshot.getValue(Meetup.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }


    @Override
    protected void populateViewHolder(final FirebaseMeetupViewHolder viewHolder, Meetup model, int position) {
        viewHolder.bindMeetup(model);

        viewHolder.mMeetupImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MeetupDetailActivity.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("meetups", Parcels.wrap(mMeetups));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        mMeetups.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Meetup meetup : mMeetups) {
            int index = mMeetups.indexOf(meetup);
            DatabaseReference ref = getRef(index);
            meetup.setIndex(Integer.toString(index));
            ref.setValue(meetup);
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }
}
