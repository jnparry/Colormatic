package com.example.jordan.colormatic;

import android.graphics.ColorSpace;

/**
 * Created by Logan on 11/1/2017.
 */

public class Preset {

    Preset(){
        presetName = "";
        colorSpace = null;
    }
    Preset(String stringTemp){
        presetName = stringTemp;
        colorSpace = null;
    }

    String getString() {return presetName;}

    void setString(String name) {presetName = name;}
    ColorSpace getColor() {return colorSpace;}

    void setColor(ColorSpace color) {colorSpace = color;}


    private  String presetName; 
    private ColorSpace colorSpace;

}
