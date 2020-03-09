package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import android.content.Intent;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(ACTIVITY_NAME, "In function:" + "main oncreate");


        SharedPreferences prefs = getSharedPreferences("prefs.txt", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_main_lab3);
        EditText email = (EditText) findViewById(R.id.editText5);
        email.setText(prefs.getString("EMAIL",""));

        Button login = (Button) findViewById(R.id.button5);
        login.setOnClickListener ( bt -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("EMAIL",email.getText().toString());
            startActivity(goToProfile);
        });



/*
        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast=Toast.makeText(getApplicationContext(),getString (R. string.toast_message),Toast.LENGTH_LONG);
                toast.show();
            }
        });

        CompoundButton cb = (CheckBox)findViewById( R.id.checkBox);
        //boolean b = cb.isChecked();
        cb.setOnCheckedChangeListener((v,b)->{
            Snackbar snackbar = Snackbar.make(v, getString (R. string.snack_message), Snackbar.LENGTH_LONG);

            snackbar.setAction( "Undo", click -> cb.setChecked(!b));
            snackbar.show(); } );


        //Context context = getActivity();
*/

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function:" + "main onpause");
        SharedPreferences prefs = getSharedPreferences("prefs.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        EditText email = (EditText) findViewById(R.id.editText5);
        editor.putString(email.getText().toString(), "EMAIL");
        editor.apply();
        editor.commit();


    }


}
