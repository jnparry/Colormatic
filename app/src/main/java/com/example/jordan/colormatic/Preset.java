package com.example.jordan.colormatic;

import android.graphics.ColorSpace;

/**
 * Created by Logan on 11/1/2017.
 */

public class Preset {
    String presetName;
    ColorSpace colorSpace;

    String getString() {return presetName;}
    void setString(String name) {presetName = name;}

    ColorSpace getColor() {return colorSpace;}
    void setColor(ColorSpace color) {colorSpace = color;}
}
