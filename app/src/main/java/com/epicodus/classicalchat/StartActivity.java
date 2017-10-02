package com.epicodus.classicalchat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.startAppNameTextView) TextView mAppNameTextView;
    @Bind(R.id.startRegBtn) Button mRegBtn;
    @Bind(R.id.startLoginBtn) Button mLoginBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        Typeface sansationBoldFont = Typeface.createFromAsset(getAssets(), "fonts/SansationBold.ttf");
        mAppNameTextView.setTypeface(sansationBoldFont);

        mRegBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent main_intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(main_intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view == mRegBtn) {
            Intent reg_intent = new Intent(StartActivity.this, RegisterActivity.class);
            startActivity(reg_intent);
        }

        if(view == mLoginBtn) {
            Intent login_intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(login_intent);
        }
    }
}
