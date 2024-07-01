package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class JungleTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(31, 103, 31);
            case "Secondary":
                return new Color(0, 100, 0);
            case "Third":
                return new Color(87, 110, 34);
            case "Fourth":
                return new Color(85, 107, 47);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(41, 112, 41);
        }
    }
}