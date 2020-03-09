package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatRoomActivity extends AppCompatActivity {

    private Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView myList = (ListView) findViewById(R.id.listView);

        View chatFooter =  ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chat_room_footer, null, false);
        myList.addFooterView(chatFooter);



        MyListAdapter adapter = new MyListAdapter();
        myList.setAdapter( adapter );



        Button sendButton = (Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener ( bt -> {
            EditText editText3 = (EditText) findViewById(R.id.editText3);
            Message sentMessage = new Message(editText3.getText().toString(),true);
           // myList.notifyDatasetChanged();
        });
        Button recieveButton = (Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener ( bt -> {
            EditText editText3 = (EditText) findViewById(R.id.editText3);
            Message recieveMessage = new Message(editText3.getText().toString(),false);
            //myList.notifyDatasetChanged();

        });


    }



}
