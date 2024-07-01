package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class FairyTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(255, 182, 193);
            case "Secondary":
                return new Color(235, 162, 173);
            case "Third":
                return new Color(235, 208, 205);
            case "Fourth":
                return new Color(255, 240, 245);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(255, 182, 193);
        }
    }
}