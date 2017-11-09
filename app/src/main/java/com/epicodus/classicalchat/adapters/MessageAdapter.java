package com.epicodus.classicalchat.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.epicodus.classicalchat.R;
import com.epicodus.classicalchat.models.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by michaeldunlap on 11/6/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;

    public MessageAdapter(List<Messages> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int i) {

        Messages c = mMessageList.get(i);

        String from_user = c.getFrom();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thumb_image").getValue().toString();
                String from_user = dataSnapshot.getKey();
                Log.v("HERE'S THE FROM USER", from_user);
                Log.v("HERE'S THE CURRENT USER", mCurrentUserId);

                RelativeLayout.LayoutParams paramsMsg = (RelativeLayout.LayoutParams) holder.messageText.getLayoutParams();
                RelativeLayout.LayoutParams paramsName = (RelativeLayout.LayoutParams) holder.displayName.getLayoutParams();

                if(from_user.equals(mCurrentUserId)) {
                    holder.profileImage.setVisibility(View.GONE);
                    paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_END,1);
                    paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_START,0);
                    paramsName.addRule(RelativeLayout.ALIGN_PARENT_END,1);
                    paramsName.addRule(RelativeLayout.ALIGN_PARENT_START,0);

                    holder.messageText.setLayoutParams(paramsMsg);
                } else {
                    holder.profileImage.setVisibility(View.VISIBLE);
                    paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_END,0);
                    paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_START,1);
                    paramsName.addRule(RelativeLayout.ALIGN_PARENT_END,0);
                    paramsName.addRule(RelativeLayout.ALIGN_PARENT_START,1);

                    holder.messageText.setLayoutParams(paramsMsg);
                    Picasso.with(holder.profileImage.getContext()).load(image)
                            .placeholder(R.drawable.hermione_granger).into(holder.profileImage);
                }

                holder.displayName.setText(name);
                Picasso.with(holder.profileImage.getContext()).load(image)
                            .placeholder(R.drawable.hermione_granger).into(holder.profileImage);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(mCurrentUserId.equals(from_user)) {
            holder.displayName.setText("me");
        }

        holder.messageText.setText(c.getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public CircleImageView profileImage;
        public TextView displayName;

        public MessageViewHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_text_layout);
            profileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image_layout);
            displayName = (TextView) itemView.findViewById(R.id.name_text_layout);
        }
    }

}
