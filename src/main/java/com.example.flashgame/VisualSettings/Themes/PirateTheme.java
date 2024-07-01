package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class PirateTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(121, 61, 59);
            case "Secondary":
                return new Color(108, 54, 53);
            case "Third":
                return new Color(158, 128, 89);
            case "Fourth":
                return new Color(145, 72, 71);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(93, 45, 45);
        }
    }
}