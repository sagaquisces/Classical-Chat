package com.epicodus.classicalchat.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.epicodus.classicalchat.Constants;
import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.adapters.FirebaseMeetupListAdapter;
import com.epicodus.classicalchat.adapters.FirebaseMeetupViewHolder;
import com.epicodus.classicalchat.models.Meetup;
import com.epicodus.classicalchat.util.OnStartDragListener;
import com.epicodus.classicalchat.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedMeetupListActivity extends AppCompatActivity implements OnStartDragListener, View.OnClickListener {
    private DatabaseReference mMeetupReference;
    private FirebaseMeetupListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.meetupsBtn) Button mMeetupsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saved_meetups_list);
        ButterKnife.bind(this);

        setUpFirebaseAdapter();

        mMeetupsBtn.setOnClickListener(this);

    }

    private void setUpFirebaseAdapter() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_MEETUPS)
                .child(uid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        mFirebaseAdapter = new FirebaseMeetupListAdapter(Meetup.class, R.layout.meetup_list_item_drag, FirebaseMeetupViewHolder.class, query, this, this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public void onClick(View view) {
        if(view == mMeetupsBtn) {

            Intent meetups_intent = new Intent(SavedMeetupListActivity.this, MeetupListActivity.class);
            startActivity(meetups_intent);

        }
    }
}
