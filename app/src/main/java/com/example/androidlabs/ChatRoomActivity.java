package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.widget.TextView;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<Message> messageList = new ArrayList<>(Arrays.asList(new Message("hi",false)));
    private MyListAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        MessageDAL printer = new MessageDAL();
        printer.printCursor(getApplicationContext());
        messageList = printer.getALl(getApplicationContext());

        ListView myList = (ListView) findViewById(R.id.listView);
        adapter= new MyListAdapter(messageList,ChatRoomActivity.this);
        myList.setAdapter(adapter);

        Button sendButton = (Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener ( bt -> {
            EditText chatText = (EditText) findViewById(R.id.chatText);
            Message message =new Message(chatText.getText().toString(),true);
            MessageDAL adder = new MessageDAL();
            adder.addMessage(getApplicationContext(),message);
            chatText.getText().clear();
            messageList.add(message);
            adapter.notifyDataSetChanged();
        });

        Button recieveButton = (Button)findViewById(R.id.recieveButton);
        recieveButton.setOnClickListener ( bt -> {
            EditText chatText = (EditText) findViewById(R.id.chatText);

            Message message =new Message(chatText.getText().toString(),false);
            MessageDAL adder = new MessageDAL();
            adder.addMessage(getApplicationContext(),message);
            chatText.getText().clear();
            messageList.add( message);
            adapter.notifyDataSetChanged();

        });


        myList.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChatRoomActivity.this);
                alertDialogBuilder.setMessage("Do you want to delete this? \nThe selected row is: " + (pos+1) + "\nThe database id is: " + messageList.get(pos).getId());
                alertDialogBuilder.setPositiveButton("Delete", (click, arg) -> {
                    MessageDAL remover = new MessageDAL();
                    remover.removeMessage(getApplicationContext(),messageList.get(pos));
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
    public ArrayList<Message> getMessages() {
        return this.messageList;
    }

    private class MyListAdapter extends BaseAdapter  {

        private ArrayList<Message> messages;
        private Context adapterContext;

        MyListAdapter(ArrayList<Message> messages, Context context) {
            super();
            this.messages = messages;
            this.adapterContext = context;
        }

        private class ViewHolder {
            private TextView written;
            private ImageView image;
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        public long getItemId(int position) {
            return position;
        }

        public Object getItem (int position) {
            return (messages.get(position));
        }

        public View getView(int position, View newView, ViewGroup parent) {
            ViewHolder  tempView = new ViewHolder();
            if (messages.get(position).isSent()) {
                LayoutInflater inflater = getLayoutInflater();
                newView = inflater.inflate(R.layout.send_layout, parent, false);
                tempView.written = newView.findViewById(R.id.textView3);
                tempView.image = newView.findViewById(R.id.imageView);

                newView.setTag(tempView);

                Drawable myDrawable = getResources().getDrawable(R.drawable.row_send);
                tempView.image.setImageDrawable(myDrawable);
            }else{
                LayoutInflater inflater = getLayoutInflater();
                newView = inflater.inflate(R.layout.recieve_layout, parent, false);
                tempView.written = newView.findViewById(R.id.textView3);
                tempView.image = newView.findViewById(R.id.imageView);

                newView.setTag(tempView);

                Drawable myDrawable = getResources().getDrawable(R.drawable.row_receive);
                tempView.image.setImageDrawable(myDrawable);
            }

            tempView.written.setText(messages.get(position).getMessage());
            return newView;
        }
/*
        public void onClick(View view) {

            int position=(Integer) view.getTag();
            Object object= getItem(position);
            Message message=(Message)object;

            switch (view.getId())
            {
                case R.id.activity_chat_window:
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(adapterContext);
                    alertDialogBuilder.setMessage(message.getMessage()).
                            setMessage("The selected row is: "+position+"\nThe database id id: "+position).
                            setPositiveButton("Delete", (click, arg) -> {
                                messageList.remove(position);
                    }).
                            setNegativeButton("Cancel", (click, arg) -> {
                            });
                    AlertDialog descriptionAlert = alertDialogBuilder.create();
                    descriptionAlert.show();
                    break;

            }
        }
*/

    }

}
