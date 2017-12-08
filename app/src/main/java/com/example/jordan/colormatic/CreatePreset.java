package com.example.jordan.colormatic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.Objects;

/**
 * Created by Logan on 11/8/2017.
 */

public class CreatePreset extends AppCompatActivity {
    public static final String TAG = "SECOND ACTIVITY USER";

    public static final String APP_PREFS = "APPLICATION_PREFERENCES";
    String _text;
    //private object used to reference userText box : type EditText
    private EditText presetText;
    // object used to save string from userText box
    private String presetName;
    private String presetColor = "";
    private ColorPickerDialogBuilder colorPicker;

    private TextView displayText;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_preset);

        final Intent intent = new Intent(this, MainActivity.class);

        //_text = intent.getStringExtra(MainActivity.TEST_TEXT);

        //userText boxed is referenced and initialized for use in program
        presetText = findViewById(R.id.userText);

        //Creates the save button and values
        Button saveBttn = findViewById(R.id.saveBttn);
        assert saveBttn != null;

        //Creates the button to pick a color
        Button pickColorButton = findViewById(R.id.pickColorButton);
        assert saveBttn != null;

        pickColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickColor();
            }
        });

        // Finds the object and what the button will do when pressed
        saveBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(presetColor, "")) {
                    Toast.makeText(CreatePreset.this, "You have not selected a color", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveText();
                if (Objects.equals(presetName, "")) {
                    Toast.makeText(CreatePreset.this, "You have not named your preset", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle extras = new Bundle();
                extras.putString("PRESET_NAME", presetName);
                extras.putString("COLOR", presetColor);
                intent.putExtras(extras);
                startActivity(intent);
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
        //Throwable tr = new Throwable();

        //Log.e(tag, msg, tr);

        SharedPreferences sharedPrefs = getSharedPreferences(MainActivity.APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(MainActivity.TEST_TEXT, _text);
//        Toast.makeText(this, "Saved Text", Toast.LENGTH_SHORT).show();

        presetName = presetText.getText().toString();
        //Log.e(tag, "Preset name: "+presetName);
        editor.putString("PRESET_NAME", presetName);
        //Toast.makeText(this, "Saved preset name: " + presetName, Toast.LENGTH_SHORT).show();

        editor.apply();
        //message = "Updated text in preset name to:"  + userName;
        //displayText.setText(message);


   }

   private void pickColor() {
       ColorPickerDialogBuilder
               .with(this)
               .setTitle("Choose color")
               //.initialColor(currentBackgroundColor)
               .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
               .density(12)
               .setOnColorSelectedListener(new OnColorSelectedListener() {
                   @Override
                   public void onColorSelected(int selectedColor) {
                       //Toast.makeText(CreatePreset.this, "onColorSelected: 0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();

                       presetColor = Integer.toHexString(selectedColor);
                       presetColor = presetColor.substring(2); // takes off the "ff" from the front

                       //Toast.makeText(CreatePreset.this, "The color you selected: " + presetColor, Toast.LENGTH_SHORT).show();
                   }
               })
               .setPositiveButton("Use This Color", new ColorPickerClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                       //changeBackgroundColor(selectedColor);
                   }
               })
               .setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                   }
               })
               .build()
               .show();
   }
}




