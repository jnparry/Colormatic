package com.example.jordan.colormatic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Logan on 11/8/2017.
 */

public class SecondActivity extends AppCompatActivity {
    public static final String TAG = "SECOND ACTIVITY USER";

    public static final String APP_PREFS = "APPLICATION_PREFERENCES";
    String _text;
    //private object used to reference userText box : type EditText
    private EditText userText;
    // object used to save string from userText box
    private String userName;
    private String text;


    private TextView displayText;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();

        _text = intent.getStringExtra(MainActivity.TEST_TEXT);

        TextView label = (TextView) findViewById(R.id.displayText);
        label.setText(_text);

        //userText boxed is referenced and initialized for use in program
        userText = (EditText) findViewById(R.id.userText);

        //sets a value for user text default
        //text = "input your preset name";
       // userText.setText(text);

        //Creates the save button and values
        Button saveBttn = (Button) findViewById(R.id.saveBttn);
        assert saveBttn != null;

        //The display text object and initialization
        displayText = (TextView) findViewById(R.id.displayText);
        message = "YOUR Text from above loads here when read in";
        displayText.setText(message);

        // Finds the object and what the button will do when pressed
        saveBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveText();
            }
        });
    }

    /******************************************************************
     * SAVE TEXT
     * Called by saveBttn when pressed: saves the data within the
     * EditText (User Input) box object and Updates text in TextView
     * object(Display Box)
    *******************************************************************/
    protected void saveText() {

        String msg = "THIS ";
        String tag = "SECOND ACTIVITY";
        Throwable tr = new Throwable();

        Log.e(tag, msg, tr);
        //Log.e(tag, msg);

        SharedPreferences sharedPrefs = getSharedPreferences(MainActivity.APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(MainActivity.TEST_TEXT, _text);
        Toast.makeText(this, "Saved Text", Toast.LENGTH_SHORT).show();

        userName = userText.getText().toString();
        editor.putString("USER_NAME", userName);
        Toast.makeText(this, "USER's NAME: Saved", Toast.LENGTH_SHORT).show();

        editor.apply();
        message = "Updated text in preset name to:"  + userName;
        displayText.setText(message);


   }

//    protected void loadText() {
//        SharedPreferences sharedPref = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
//        EditText testTxt = (EditText) findViewById(R.id.testTxt);
//        String text = testTxt.getText().toString();
//        String testText = sharedPref.getString(TEST_TEXT, null);
//        testTxt.setText(testText);

//    }

}




