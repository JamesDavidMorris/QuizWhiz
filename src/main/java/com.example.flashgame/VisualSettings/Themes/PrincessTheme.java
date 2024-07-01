package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class PrincessTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(218, 112, 214);
            case "Secondary":
                return new Color(188, 82, 184);
            case "Third":
                return new Color(255, 105, 180);
            case "Fourth":
                return new Color(255, 240, 245);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(255, 192, 203);
        }
    }
}