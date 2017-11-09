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

    public void addPreset(Preset preset) {

    }

    public void applyPreset() {

    }

}
