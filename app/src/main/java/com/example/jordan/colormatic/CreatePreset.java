package com.example.jordan.colormatic;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    //private object used to reference userText box : type EditText
    private EditText presetText;
    // object used to save string from userText box
    private String presetName;
    private String presetColor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_preset);

        final Intent intent = new Intent(this, MainActivity.class);

        //userText boxed is referenced and initialized for use in program
        presetText = findViewById(R.id.userText);

        //Creates the save button and values
        Button saveBttn = findViewById(R.id.saveBttn);
        assert saveBttn != null;

        //Creates the button to pick a color
        Button pickColorButton = findViewById(R.id.pickColorButton);
        assert pickColorButton != null;

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
                if (Objects.equals(presetName, "")) {
                    Toast.makeText(CreatePreset.this, "You have not named your preset", Toast.LENGTH_SHORT).show();
                    return;
                }
                presetName = presetText.getText().toString();
                Bundle extras = new Bundle();
                extras.putString("PRESET_NAME", presetName);
                extras.putString("COLOR", presetColor);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
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
                       presetColor = Integer.toHexString(selectedColor);
                       presetColor = presetColor.substring(2); // takes off the "ff" from the front
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




