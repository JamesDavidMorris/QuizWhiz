package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class DefaultTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(122, 53, 148);
            case "Secondary":
                return new Color(142, 73, 168);
            case "Third":
                return new Color(27, 101, 187);
            case "Fourth":
                return new Color(163, 103, 188);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(218, 75, 204);
        }
    }
}
