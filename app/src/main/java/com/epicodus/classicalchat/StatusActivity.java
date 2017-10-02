package com.epicodus.classicalchat;

import android.app.ProgressDialog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.status_toolbar) Toolbar mToolbar;
    @Bind(R.id.status_input) TextInputLayout mStatus;
    @Bind(R.id.status_saveBtn) Button mSaveBtn;

    //Firebase
    private DatabaseReference mStatusRef;
    private FirebaseUser mCurrentUser;

    //Progress
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mStatusRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String status_value = getIntent().getStringExtra("status_value");
        mStatus.getEditText().setText(status_value);

        mSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mSaveBtn) {

            //Progress
            mProgress = new ProgressDialog(StatusActivity.this);
            mProgress.setTitle("Saving chnages");
            mProgress.setMessage("Please wait while we save changes.");
            mProgress.show();

            String status = mStatus.getEditText().getText().toString();
            mStatusRef.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {

                        mProgress.dismiss();

                    } else {
                        Toast.makeText(getApplicationContext(), "There was an error in saving changes.", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
    }
}
