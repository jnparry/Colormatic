package com.example.jordan.colormatic;

import android.graphics.ColorSpace;

/**
 * Created by Logan on 11/1/2017.
 */

public class Preset {

    // Variables
    private String     presetName;
    private ColorSpace colorSpace;

    // Methods
    Preset(){
        presetName = "";
        colorSpace = null;
    }

    Preset(String stringTemp){
        presetName = stringTemp;
        colorSpace = null;
    }

    public void       setPresetName(String name) { presetName = name;  }
    public void       setColor(ColorSpace color) { colorSpace = color; }
    public String     getPresetName()            { return presetName;  }
    public ColorSpace getColor()                 { return colorSpace;  }
}
