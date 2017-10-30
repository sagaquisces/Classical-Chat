package com.epicodus.classicalchat.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.classicalchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.profile_image) ImageView mProfileImageView;
    @Bind(R.id.profile_name) TextView mProfileName;
    @Bind(R.id.profile_status) TextView mProfileStatus;
    @Bind(R.id.profile_total_friends) TextView mProfileFriendsCount;
    @Bind(R.id.profile_send_req_btn) Button mProfileSendReqBtn;
    @Bind(R.id.profile_decline_btn) Button mProfileDeclineBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabase;

    private ProgressDialog mProgress;

    private FirebaseUser mCurrentUser;

    private DatabaseReference mFriendReqDatabase;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference mNotificationsDatabase;

    private DatabaseReference mRootRef;

    private String mUserProfileID;
    private String mCurrent_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        mUserProfileID = getIntent().getStringExtra("user_id");

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mUserProfileID);
        mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mNotificationsDatabase = FirebaseDatabase.getInstance().getReference().child("notifications");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mCurrent_state = "not_friends";

        mProfileDeclineBtn.setVisibility(View.INVISIBLE);
        mProfileDeclineBtn.setEnabled(false);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading User Data");
        mProgress.setMessage("Please wait while we load the user data");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();

        mProfileSendReqBtn.setOnClickListener(this);

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                mProfileName.setText(name);
                mProfileStatus.setText(status);

                Picasso.with(ProfileActivity.this).load(image).into(mProfileImageView);

                if(mCurrentUser.getUid().equals(mUserProfileID)) {
                    mProfileDeclineBtn.setEnabled(false);
                    mProfileDeclineBtn.setVisibility(View.INVISIBLE);

                    mProfileSendReqBtn.setEnabled(false);
                    mProfileSendReqBtn.setVisibility(View.INVISIBLE);
                }

                // -------------FRIENDS LIST / REQUEST FEATURE ----------- //

                mFriendReqDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       if(dataSnapshot.hasChild(mUserProfileID)) {
                           String req_type = dataSnapshot.child(mUserProfileID).child("request_type").getValue().toString();

                           if(req_type.equals("received")) {

                               mCurrent_state = "req_received";
                               mProfileSendReqBtn.setText("Accept Friend Request");

                               mProfileDeclineBtn.setVisibility(View.VISIBLE);
                               mProfileDeclineBtn.setEnabled(true);
                           } else if (req_type.equals("sent")){
                               mCurrent_state = "req_sent";
                               mProfileSendReqBtn.setText("Cancel Friend Request");

                               mProfileDeclineBtn.setVisibility(View.INVISIBLE);
                               mProfileDeclineBtn.setEnabled(false);
                           }

                           mProgress.dismiss();

                       } else {
                           mFriendDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {
                                   if (dataSnapshot.hasChild(mUserProfileID)) {
                                       mCurrent_state = "friends";
                                       mProfileSendReqBtn.setText("Unfriend");

                                       mProfileDeclineBtn.setVisibility(View.INVISIBLE);
                                       mProfileDeclineBtn.setEnabled(false);
                                   }

                                   mProgress.dismiss();
                               }

                               @Override
                               public void onCancelled(DatabaseError databaseError) {
                                   mProgress.dismiss();

                               }
                           });
                       }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mProgress.dismiss();
                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    @Override
    public void onStart() {
        super.onStart();

        mRootRef.child("Users").child(mCurrentUser.getUid()).child("online").setValue("true");

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mRootRef.child("Users").child(mCurrentUser.getUid()).child("online").setValue(ServerValue.TIMESTAMP);

    }


    @Override
    public void onClick(View view) {

        mProfileSendReqBtn.setEnabled(false);
        if (view == mProfileSendReqBtn) {

            // ----------- NOT FRIENDS STATE

            if(mCurrent_state.equals("not_friends")){

                DatabaseReference newNotificationRef = mRootRef.child("notifications").child(mUserProfileID).push();
                String newNotificationId = newNotificationRef.getKey();

                HashMap<String, String> notificationData = new HashMap<>();
                notificationData.put("from", mCurrentUser.getUid());
                notificationData.put("type", "request");

                Map requestMap = new HashMap();
                requestMap.put("Friend_req/" + mCurrentUser.getUid() + "/" + mUserProfileID + "/request_type", "sent");
                requestMap.put("Friend_req/" + mUserProfileID + "/" + mCurrentUser.getUid() + "/request_type", "received");
                requestMap.put("notifications/" + mUserProfileID + "/" + newNotificationId, notificationData);

                mRootRef.updateChildren(requestMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        if(databaseError != null){

                            Toast.makeText(ProfileActivity.this, "There was an error in sending request", Toast.LENGTH_SHORT).show();
                        } else {

                            mCurrent_state = "req_sent";
                            mProfileSendReqBtn.setText("Cancel Friend Request");
                        }

                        mProfileSendReqBtn.setEnabled(true);

                    }
                });
            }

            // -------------- CANCEL REQUEST STATE -----------------

            if(mCurrent_state.equals("req_sent")) {

                mFriendReqDatabase.child(mCurrentUser.getUid()).child(mUserProfileID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mFriendReqDatabase.child(mUserProfileID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                mProfileSendReqBtn.setEnabled(true);
                                mCurrent_state = "not_friends";
                                mProfileSendReqBtn.setText("Send Friend Request");

                                mProfileDeclineBtn.setVisibility(View.INVISIBLE);
                                mProfileDeclineBtn.setEnabled(false);
                            }
                        });
                    }
                });
            }

            // -------------- REQ RECEIVED STATE -----------------

            if(mCurrent_state.equals("req_received")) {

                final String currentDate = DateFormat.getDateTimeInstance().format(new Date());

                Map friendsMap = new HashMap();
                friendsMap.put("Friends/" + mCurrentUser.getUid() + "/" + mUserProfileID + "/date", currentDate);
                friendsMap.put("Friends/" + mUserProfileID + "/" + mCurrentUser.getUid() + "/date", currentDate);

                friendsMap.put("Friend_req/" + mCurrentUser.getUid() + "/" + mUserProfileID, null);
                friendsMap.put("Friend_req/" + mUserProfileID + "/" + mCurrentUser.getUid(), null);

                mRootRef.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null) {

                            mProfileSendReqBtn.setEnabled(true);
                            mCurrent_state = "friends";
                            mProfileSendReqBtn.setText("Unfriend");

                            mProfileDeclineBtn.setVisibility(View.INVISIBLE);
                            mProfileDeclineBtn.setEnabled(false);

                        } else {

                            String error = databaseError.getMessage();

                            Toast.makeText(ProfileActivity.this, error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

            // ------------- UNFRIENDS ---------------------------

            if(mCurrent_state.equals("friends")) {

                Map unfriendMap = new HashMap();
                unfriendMap.put("Friends/" + mCurrentUser.getUid() + "/" + mUserProfileID, null);
                unfriendMap.put("Friends/" + mUserProfileID + "/" + mCurrentUser.getUid(), null);

                mRootRef.updateChildren(unfriendMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null) {

                            mCurrent_state = "not_friends";
                            mProfileSendReqBtn.setText("Send Friend Request");

                            mProfileDeclineBtn.setVisibility(View.INVISIBLE);
                            mProfileDeclineBtn.setEnabled(false);
                        } else {

                            String error = databaseError.getMessage();

                            Toast.makeText(ProfileActivity.this, error, Toast.LENGTH_SHORT).show();
                        }

                        mProfileSendReqBtn.setEnabled(true);
                    }
                });


            }

        }
    }
}
