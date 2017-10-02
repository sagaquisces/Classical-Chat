package com.epicodus.classicalchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.settings_image) CircleImageView mCircleImageView;
    @Bind(R.id.settings_name_textView) TextView mNameTextView;
    @Bind(R.id.settings_status_textView) TextView mSettingsTextView;
    @Bind(R.id.settings_change_image_btn) Button mChangeImageBtn;
    @Bind(R.id.settings_change_status_btn) Button mChangeStatusBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        mChangeImageBtn.setOnClickListener(this);
        mChangeStatusBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == mChangeImageBtn) {
            Toast.makeText(this, "Change Image code", Toast.LENGTH_SHORT);
        }

        if (view == mChangeStatusBtn) {
            Toast.makeText(this, "Change status code", Toast.LENGTH_SHORT);
        }
    }
}
