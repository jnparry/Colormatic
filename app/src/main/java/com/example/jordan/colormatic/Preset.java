package com.example.jordan.colormatic;

import android.graphics.ColorSpace;

/**
 * Created by Logan on 11/1/2017.
 */

public class Preset {

    private  String presetName;
    private ColorSpace colorSpace;

    Preset(){
        presetName = "";
        colorSpace = null;
    }
    Preset(String stringTemp){
        presetName = stringTemp;
        colorSpace = null;
    }

    public String getPresetName() {return presetName;}
    public void setPresetName(String name) {presetName = name;}

    public ColorSpace getColor() {return colorSpace;}
    public void setColor(ColorSpace color) {colorSpace = color;}
}
