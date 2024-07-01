package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class SafariTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(160, 82, 45);
            case "Secondary":
                return new Color(210, 105, 30);
            case "Third":
                return new Color(205, 133, 63);
            case "Fourth":
                return new Color(244, 164, 96);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(222, 184, 135);
        }
    }
}