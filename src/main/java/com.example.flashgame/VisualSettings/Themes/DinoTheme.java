package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class DinoTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(85, 107, 47);
            case "Secondary":
                return new Color(139, 69, 19);
            case "Third":
                return new Color(210, 105, 30);
            case "Fourth":
                return new Color(205, 133, 63);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(161, 134, 98);
        }
    }
}
