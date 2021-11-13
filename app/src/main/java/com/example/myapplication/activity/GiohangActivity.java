package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class GiohangActivity extends AppCompatActivity {
    private ListView mListView;
    int [] images = {R.drawable.a , R.drawable.b , R.drawable.c};
    String [] names = {"Istanbul" , "Ladakh" , "Tokyo" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.listview);

        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);

    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.card_giohang ,parent , false);
            TextView textView = convertView.findViewById(R.id.textview);
            ImageView imageView = convertView.findViewById(R.id.imageview);
            textView.setText(names[position]);
            imageView.setImageResource(images[position]);
            return  convertView;
        }
    }
}
