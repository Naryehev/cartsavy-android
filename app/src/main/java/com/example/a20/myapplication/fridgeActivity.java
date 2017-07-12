package com.example.a20.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by 20 on 7/11/2017.
 */

public class fridgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] mobileArray = {""};

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activitytest, mobileArray);

        ListView listView = (ListView) findViewById(R.id.fridgeList);
        listView.setAdapter(adapter);
    }
}
