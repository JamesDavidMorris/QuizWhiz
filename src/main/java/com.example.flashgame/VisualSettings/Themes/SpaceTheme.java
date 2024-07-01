package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class SpaceTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(18, 18, 30);
            case "Secondary":
                return new Color(39, 39, 68);
            case "Third":
                return new Color(61, 61, 107);
            case "Fourth":
                return new Color(104, 130, 136);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(48, 48, 72);
        }
    }
}