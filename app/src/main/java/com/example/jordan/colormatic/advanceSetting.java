package com.example.jordan.colormatic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class advanceSetting extends AppCompatActivity {
    private  ArrayAdapter<String> adapter;
    private ListView preset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_setting);

        //Button changeActivityButton = findViewById(R.id.moveToSecondActivity);
        //assert changeActivityButton != null;
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
//        preset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch( position )
//                {
//                    case 0:  Intent newActivity = new Intent(this, CreatePreset.class);
//                        startActivity(newActivity);
//                        break;
//                    case 1:  Intent newActivity = new Intent(this, youtube.class);
//                        startActivity(newActivity);
//                        break;
//                    case 2:  Intent newActivity = new Intent(this, olympiakos.class);
//                        startActivity(newActivity);
//                        break;
//                    case 3:  Intent newActivity = new Intent(this, karaiskaki.class);
//                        startActivity(newActivity);
//                        break;
//                    case 4:  Intent newActivity = new Intent(this, reservetickets.class);
//                        startActivity(newActivity);
//                        break;
                }
            //}
//        });
//    }
//            changeActivityButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            startActivity(new Intent(MainActivity.this, SecondActivity.class));
//        }
//    });




}
