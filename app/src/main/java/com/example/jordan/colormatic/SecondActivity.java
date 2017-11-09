package com.example.jordan.colormatic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Logan on 11/8/2017.
 */

public class SecondActivity extends AppCompatActivity {

    public static final String APP_PREFS = "APPLICATION_PREFERENCES";
    String _text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        _text = intent.getStringExtra(MainActivity.TEST_TEXT);
        TextView label = (TextView) findViewById(R.id.displayText);
        label.setText(_text);
    }

//    protected void saveText() {
//        SharedPreferences sharedPrefs = getSharedPreferences(MainActivity.APP_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//
//        editor.putString(MainActivity.TEST_TEXT, _text);
//
//        Toast.makeText(this, "Saved Text", Toast.LENGTH_SHORT).show();
//    }

//    protected void loadText() {
//        SharedPreferences sharedPref = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
//        EditText testTxt = (EditText) findViewById(R.id.testTxt);
//        String text = testTxt.getText().toString();
//        String testText = sharedPref.getString(TEST_TEXT, null);
//        testTxt.setText(testText);

//    }

}




