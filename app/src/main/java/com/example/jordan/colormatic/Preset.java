package com.example.jordan.colormatic;

/**
 * Created by Logan on 11/1/2017.
 */

public class Preset {

    // Variables
    private String presetName;
    private String hexColor;

    //Default Constructor
    Preset(){
        presetName = "";
        hexColor   = "";
    }

    //Non-Default constructor
    Preset(String stringTemp){
        presetName = stringTemp;
        hexColor   = "";
    }

    // Methods
    public void       setName(String name)     { presetName = name;  }
    public void       setColor(String color)   { hexColor = color;   }
    public String     getName()                { return presetName;  }
    public String     getColor()               { return hexColor;    }
}
