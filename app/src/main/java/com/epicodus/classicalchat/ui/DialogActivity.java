package com.epicodus.classicalchat.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.epicodus.classicalchat.R;

public class DialogActivity extends AppCompatActivity {

    private String mOtherUser;

    private Toolbar mDialogToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        mDialogToolbar = (Toolbar) findViewById(R.id.dialog_app_bar);
        setSupportActionBar(mDialogToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOtherUser = getIntent().getStringExtra("user_id");

        getSupportActionBar().setTitle(mOtherUser);
    }
}
