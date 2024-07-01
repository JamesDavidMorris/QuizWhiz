package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class AlpineTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(39, 117, 219);
            case "Secondary":
                return new Color(5, 70, 68);
            case "Third":
                return new Color(39, 219, 146);
            case "Fourth":
                return new Color(39, 219, 85);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(39, 174, 219);
        }
    }
}
