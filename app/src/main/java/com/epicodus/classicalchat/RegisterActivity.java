package com.epicodus.classicalchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.regDisplayName) TextInputLayout mDisplayName;
    @Bind(R.id.regEmail) TextInputLayout mEmail;
    @Bind(R.id.regPassword) TextInputLayout mPassword;
    @Bind(R.id.regConfirmPassword) TextInputLayout mConfirmPassword;

    @Bind(R.id.regCreateBtn) Button mCreateBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mCreateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String display_name = mDisplayName.getEditText().getText().toString().trim();
        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();
        String confirm_password = mConfirmPassword.getEditText().getText().toString().trim();

        boolean validName = isValidName(display_name);
        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password, confirm_password);
        if (!validEmail || !validName || !validPassword) return;

        register_user(display_name, email, password, confirm_password);
    }

    private void register_user(String display_name, String email, String password, String confirm_password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent main_intent = new Intent (RegisterActivity.this, MainActivity.class);
                    startActivity(main_intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "You got an error", Toast.LENGTH_LONG).show();
                }
            }
        });
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

    private boolean isValidName(String name) {
        if (name.equals("")) {
            mDisplayName.setError("Please enter your name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mPassword.setError("Please create a password containing at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}
