package com.epicodus.classicalchat;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.login_toolbar) Toolbar mToolbar;

    @Bind(R.id.loginEmail) TextInputLayout mEmail;
    @Bind(R.id.loginPassword) TextInputLayout mPassword;

    @Bind(R.id.loginLoginBtn) Button mLoginBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();

        boolean validEmail = isValidEmail(email);

        login_user(email, password);

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

    private void login_user(String email, String password) {
        Toast.makeText(LoginActivity.this, "Login code will go here", Toast.LENGTH_LONG).show();
    }
}
