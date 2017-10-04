package com.epicodus.classicalchat.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.classicalchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.main_toolbar) Toolbar mToolbar;

    @Bind(R.id.mainAppNameTextView) TextView mAppNameTextView;
    @Bind(R.id.mainMeetupBtn) Button mMeetupBtn;
    @Bind(R.id.mainChatBtn) Button mChatBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Typeface sansationBoldFont = Typeface.createFromAsset(getAssets(), "fonts/SansationBold.ttf");
        mAppNameTextView.setTypeface(sansationBoldFont);

        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Classical Chat");

        mMeetupBtn.setOnClickListener(this);
        mChatBtn.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null) {
            sendToStart();
        }
    }

    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.mainLogoutBtn) {
            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }

        if(item.getItemId() == R.id.mainSettingsBtn) {
            Intent settings_intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settings_intent);
        }

        if(item.getItemId() == R.id.mainAllUsersBtn) {
            Intent all_users_intent = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(all_users_intent);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == mMeetupBtn) {
            Intent main_intent = new Intent(MainActivity.this, MeetupListActivity.class);
            startActivity(main_intent);
        }

        if(view == mChatBtn) {
            Intent main_intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(main_intent);
        }

    }
}
