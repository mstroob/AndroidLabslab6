package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<Message> messageList = new ArrayList<>(Arrays.asList(new Message("hi",false)));
    private MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Button sendButton = (Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener ( bt -> {
            EditText chatText = (EditText) findViewById(R.id.chatText);
            messageList.add( new Message(chatText.getText().toString(),true));
            adapter.notifyDataSetChanged();
        });

        Button recieveButton = (Button)findViewById(R.id.recieveButton);
        recieveButton.setOnClickListener ( bt -> {
            EditText chatText = (EditText) findViewById(R.id.chatText);
            messageList.add( new Message(chatText.getText().toString(),false));
            adapter.notifyDataSetChanged();

        });


        ListView myList = (ListView) findViewById(R.id.listView);
        myList.setAdapter(adapter = new MyListAdapter());

        myList.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChatRoomActivity.this);
                alertDialogBuilder.setMessage("Do you want to delete this? \n The selected row is: " + pos + "\n The database id is: " + "null");
                alertDialogBuilder.setPositiveButton("Delete", (click, arg) -> {
                    messageList.remove(pos);
                    adapter.notifyDataSetChanged();
                });
                alertDialogBuilder.setNegativeButton("Cancel", (click, arg) -> {
                });
                alertDialogBuilder.show();
                return true;
            }
        });

    }

    private class MyListAdapter extends BaseAdapter {


        public int getCount(){
            return messageList.size();
        }

        public Object getItem(int position) {
           return (messageList.get(position));
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View newView = new View(null);
            if (messageList.get(position).isSent()) {
                LayoutInflater inflater = getLayoutInflater();
                newView = inflater.inflate(R.layout.send_layout, parent, false);
            }else{
                LayoutInflater inflater = getLayoutInflater();
                newView = inflater.inflate(R.layout.send_layout, parent, false);
            }
            return newView;
        }

        public long getItemId(int position) {
            return position;
        }
    }

}
