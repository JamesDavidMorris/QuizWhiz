package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class CastleTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(105, 105, 105);
            case "Secondary":
                return new Color(128, 128, 128);
            case "Third":
                return new Color(169, 169, 169);
            case "Fourth":
                return new Color(192, 192, 192);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(112, 128, 144);
        }
    }
}
