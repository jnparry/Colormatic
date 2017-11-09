package com.example.jordan.colormatic;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Christopher on 11/3/2017.
 */

public class SystemSettings extends AppCompatActivity {

    // Variables
    private Preset            selectedPreset;
    private SharedPreferences sharedPrefs;
    private List<Preset>      presetList;     // our list of Presets Items made by the user
    private Drawable          picture;

    public static final String PREFERENCE_FILE = "savedPreference";

    // Methods
    SystemSettings(){
        selectedPreset = new Preset();
        sharedPrefs    = getSharedPreferences(PREFERENCE_FILE, MODE_PRIVATE);
        presetList     = new ArrayList<Preset>();
        picture        = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {

            }

            @Override
            public void setAlpha(int i) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.TRANSPARENT;
            }
        };
    }

    /*
    public Drawable getPicture() {
        return;
    }
    */

    public void setPicture() {

    }

    /**************************************************************************
    * ADD PRESET
    * Gives the ability for another preset to be added to a list of Preset
    * options
    * Parameters: Preset objec to be added
    ***************************************************************************/
    public void addPreset(Preset preset) {
        presetList.add(preset);
    }
    /*********************************************************************
     * GET PRESET
     * Grabs specified presest out of List of Saved Presets
     *  Parameter: String object containing the name of Preset to search
     *  for in Preset list
     * Return: Returns preset object from list
    ************************************************************************/
    public void getPreset(String presetName) {

    }

    /***********************************************************************
     * APPLY PRESET
     * Loads the Preset selected by the User
     * Parameter: Preset object to be loaded into system
    **********************************************************************/
    public void applyPreset(Preset presetToApply) {

    }

}
