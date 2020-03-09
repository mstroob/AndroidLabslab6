package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MyListAdapter extends BaseAdapter {

   // ListView myList = (ListView) findViewById(R.id.listView);
    @Override
    public int getCount() {
return 1;
      //  return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return 1 ;
        //return myList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
