package com.epicodus.classicalchat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.startAppNameTextView) TextView mAppNameTextView;
    @Bind(R.id.startRegBtn) Button mRegBtn;
    @Bind(R.id.startLoginBtn) Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ButterKnife.bind(this);

        Typeface sansationBoldFont = Typeface.createFromAsset(getAssets(), "fonts/SansationBold.ttf");
        mAppNameTextView.setTypeface(sansationBoldFont);

        mRegBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mRegBtn) {
            Intent reg_intent = new Intent(StartActivity.this, RegisterActivity.class);
            startActivity(reg_intent);
        }
    }
}
