package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class CandyTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(160, 43, 147);
            case "Secondary":
                return new Color(121, 101, 174);
            case "Third":
                return new Color(33, 95, 154);
            case "Fourth":
                return new Color(233, 113, 50);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(215, 107, 203);
        }
    }
}
