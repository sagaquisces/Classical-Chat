package com.epicodus.classicalchat.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        mAuth = FirebaseAuth.getInstance();
        String current_user_id = mAuth.getCurrentUser().getUid();

        Messages c = mMessageList.get(i);

        String from_user = c.getFrom();

        if(from_user.equals(current_user_id)) {
            holder.mMessageText.setBackgroundColor(Color.WHITE);
            holder.mMessageText.setTextColor(Color.BLACK);
        } else {
            holder.mMessageText.setBackgroundResource(R.drawable.message_text_background);
            holder.mMessageText.setTextColor(Color.WHITE);
        }

        holder.mMessageText.setText(c.getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView mMessageText;
        public CircleImageView mProfileImage;
        public TextView mDisplayName;

        public MessageViewHolder(View itemView) {
            super(itemView);

            mMessageText = (TextView) itemView.findViewById(R.id.message_text_layout);
            mProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image_layout);
            mDisplayName = (TextView) itemView.findViewById(R.id.name_text_layout);
        }
    }

}
