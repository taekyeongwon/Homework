package com.example.tkw33.homework3;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    ListView listView;
    ParsingActivity parsingActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("my hash key : ", GetHashKey.myKey(this));

        Button parse_btn = (Button) findViewById(R.id.parsing_btn);
        Button list_up_btn = (Button) findViewById(R.id.list_up_btn);
        listView = (ListView) findViewById(android.R.id.list);

        parse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskRestart();
            }
        });
        list_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setSelectionFromTop(0, 0);
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
    public void taskRestart(){
        parsingActivity = new ParsingActivity();
        parsingActivity.execute();
        ListAdapter listAdapter = new JsonListAdapter(MainActivity.this, JsonItemData.jsonDataArray);
        setListAdapter(listAdapter);
    }
}
