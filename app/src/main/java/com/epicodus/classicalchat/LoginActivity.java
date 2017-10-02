package com.epicodus.classicalchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.login_toolbar) Toolbar mToolbar;

    @Bind(R.id.loginEmail) TextInputLayout mEmail;
    @Bind(R.id.loginPassword) TextInputLayout mPassword;

    @Bind(R.id.loginLoginBtn) Button mLoginBtn;

    private ProgressDialog mLoginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoginProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password);
        if (!validEmail || !validPassword) return;

        if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

            mLoginProgress.setTitle("Logging In");
            mLoginProgress.setMessage("Please wait while we check your credentials.");
            mLoginProgress.setCanceledOnTouchOutside(false);
            mLoginProgress.show();

            login_user(email, password);
        }

    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmail.setError("Please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 6) {
            mPassword.setError("A valid password is 6 characters long or more");
            return false;
        }
        return true;
    }

    private void login_user(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    mLoginProgress.dismiss();

                    Intent main_intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(main_intent);
                    finish();

                } else {
                    mLoginProgress.hide();
                    Toast.makeText(LoginActivity.this, "Cannot sign in. Please check the form and try again.", Toast.LENGTH_LONG);
                }
            }
        });
    }



}
