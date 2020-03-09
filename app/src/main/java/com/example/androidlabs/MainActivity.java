package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_relative);

        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast=Toast.makeText(getApplicationContext(),"@string/toast_message",Toast.LENGTH_LONG);
                toast.show();
            }
        });



        CheckBox cb = (CheckBox)findViewById( R.id.checkBox);
        boolean b = cb.isChecked();
        cb.setOnCheckChangedListener((cb, b)->{
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "@string/snack_message", Snackbar.LENGTH_LONG);
            Snackbar.setAction( "Undo", click -> cb.setChecked(!b));
            snackbar.show(); } );


        //Context context = getActivity();
        SharedPreferences prefs = getSharedPreferences("prefs.txt", Context.MODE_PRIVATE);

    }


    @Override
    protected void onPause() {
        super.onPause();

    }


}
