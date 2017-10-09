package com.epicodus.classicalchat.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.classicalchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.settings_toolbar) Toolbar mToolbar;

    @Bind(R.id.settings_image) CircleImageView mCircleImageView;
    @Bind(R.id.settings_name_textView) TextView mNameTextView;
    @Bind(R.id.settings_status_textView) TextView mStatusTextView;
    @Bind(R.id.settings_change_image_btn) Button mChangeImageBtn;
    @Bind(R.id.settings_change_status_btn) Button mChangeStatusBtn;

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private static final int GALLERY_PIC = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 111;

    // Storage Firebase

    private StorageReference mImageStorage;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        mImageStorage = FirebaseStorage.getInstance().getReference();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String currentUser = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mNameTextView.setText(name);
                mStatusTextView.setText(status);

                if (!image.equals("default")) {
                    Picasso
                            .with(SettingsActivity.this)
                            .load(image)
                            .into(mCircleImageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Settings");

        mChangeImageBtn.setOnClickListener(this);
        mChangeStatusBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view == mChangeImageBtn) {
            CropImage.startPickImageActivity(this);
//            Intent gallery_intent = new Intent();
//            gallery_intent.setType("image/*");
//            gallery_intent.setAction(Intent.ACTION_GET_CONTENT);
//
//            startActivityForResult(Intent.createChooser(gallery_intent, "SELECT_IMAGE"), GALLERY_PIC);

//            CropImage.activity()
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .start(SettingsActivity.this);
        }

        if (view == mChangeStatusBtn) {
            String status_value = mStatusTextView.getText().toString();

            Intent status_intent = new Intent(SettingsActivity.this, StatusActivity.class);
            status_intent.putExtra("status_value", status_value);
            startActivity(status_intent);
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//
//        getMenuInflater().inflate(R.menu.menu_photo, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_photo:
//                onLaunchCamera();
//            default:
//                break;
//        }
//        return false;
//    }

//    private void onLaunchCamera() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mCircleImageView.setImageBitmap(imageBitmap);
//            Uri imageUri = data.getData();
//
//            CropImage.activity(imageUri)
//                    .setAspectRatio(1, 1)
//                    .start(this);
//            encodeBitmapAndSaveToFirebase(imageBitmap);
//        }

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            mProgressDialog = new ProgressDialog(SettingsActivity.this);
            mProgressDialog.setTitle("Uploading Image");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

//        if((requestCode == GALLERY_PIC) && resultCode == RESULT_OK) {
//
//            mProgressDialog = new ProgressDialog(SettingsActivity.this);
//            mProgressDialog.setTitle("Uploading Image");
//            mProgressDialog.setMessage("Please wait...");
//            mProgressDialog.setCanceledOnTouchOutside(false);
//            mProgressDialog.show();
//
//            Uri imageUri = data.getData();
//
//            CropImage.activity(imageUri)
//                    .setAspectRatio(1, 1)
//                    .start(this);
//
//        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                String current_user_id = mCurrentUser.getUid();

                StorageReference filepath = mImageStorage.child("profile_images").child(current_user_id + ".jpg");

                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            String download_url = task.getResult().getDownloadUrl().toString();

                            mUserDatabase.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(SettingsActivity.this, "Success uploading", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {

                            Toast.makeText(SettingsActivity.this, "Not Working", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();

                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .start(this);
    }

//    private void encodeBitmapAndSaveToFirebase(Bitmap imageBitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//        mUserDatabase.child("image").setValue(imageEncoded);
//    }

}
