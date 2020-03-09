package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class ProfileActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(ACTIVITY_NAME, "In function:" + "profile oncreate");
        setContentView(R.layout.activity_profile);

        Intent fromMain = getIntent();
        fromMain.getStringExtra("EMAIL");

        EditText emailEditText = (EditText) findViewById(R.id.et32);
        emailEditText.setText(fromMain.getStringExtra("EMAIL"));

        ImageButton mImageButton = (ImageButton)findViewById(R.id.imageButton3);
        mImageButton.setOnClickListener ( bt -> {  dispatchTakePictureIntent();  });







    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageButton mImageButton = (ImageButton)findViewById(R.id.imageButton3);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }


}
