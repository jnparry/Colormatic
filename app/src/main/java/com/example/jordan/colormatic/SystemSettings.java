package com.example.jordan.colormatic;

import android.content.SharedPreferences;

import java.util.List;

/**
 * Created by Christopher on 11/3/2017.
 */

public class SystemSettings {
    /*
    * Default Constructor
    */
    SystemSettings(){

    }


    private SharedPreferences sharedPrefs;

    //the our list of Presets Items made by the user
    private List<Preset> presetList;

    private Preset selectedPreset;

}
