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
import android.app.AlertDialog;

public class ChatRoomActivity extends AppCompatActivity {

    public ArrayList<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView myList = (ListView) findViewById(R.id.listView);
        MyListAdapter adapter = new MyListAdapter();
        myList.setAdapter(adapter);







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

        private MyListAdapter(){
            super();
        }

        @Override
        public int getCount() {
            return (messageList.size());
        }

        @Override
        public Object getItem(int position) {
           return (messageList.get(position));
        }

        @Override
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

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

}
