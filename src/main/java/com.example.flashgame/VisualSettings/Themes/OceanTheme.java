package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class OceanTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(0, 105, 148);
            case "Secondary":
                return new Color(0, 149, 182);
            case "Third":
                return new Color(30, 144, 255);
            case "Fourth":
                return new Color(70, 130, 180);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(0, 191, 255);
        }
    }
}