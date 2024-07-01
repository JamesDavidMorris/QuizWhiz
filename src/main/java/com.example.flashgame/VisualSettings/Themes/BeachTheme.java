package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class BeachTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(182, 156, 121);
            case "Secondary":
                return new Color(172, 146, 111);
            case "Third":
                return new Color(72, 209, 204);
            case "Fourth":
                return new Color(175, 238, 238);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(192, 166, 131);
        }
    }
}