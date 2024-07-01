package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class VolcanoTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(139, 0, 0);
            case "Secondary":
                return new Color(178, 34, 34);
            case "Third":
                return new Color(220, 20, 60);
            case "Fourth":
                return new Color(255, 69, 0);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(255, 0, 0);
        }
    }
}