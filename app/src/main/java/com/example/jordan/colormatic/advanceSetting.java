package com.example.jordan.colormatic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class advanceSetting extends AppCompatActivity {
    private  ArrayAdapter<String> adapter;
    private ListView preset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_setting);
        /**
         * initialize adapter
         */
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 );

        /**
         * Sets preset's list view
         */
        preset = (ListView) findViewById(R.id.preset);
        preset.setAdapter(adapter);

        for(int i = 0; i < 10; ++i)
        {
            String value = "this" + i;
            adapter.add(value);
        }
    }




}
