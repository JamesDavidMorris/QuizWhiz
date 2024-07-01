package com.example.flashgame.VisualSettings.Themes;

import java.awt.*;

import com.example.flashgame.VisualSettings.Theme;

public class RobotTheme implements Theme
{
    @Override
    public Color getColor(String colorRef)
    {
        switch (colorRef)
        {
            case "PrimaryDark":
                return new Color(161, 66, 66);
            case "Secondary":
                return new Color(205, 83, 83);
            case "Third":
                return new Color(222, 64, 63);
            case "Fourth":
                return new Color(146, 66, 58);
            case "Text":
                return new Color(255, 255, 255);
            default:
                return new Color(130, 59, 57);
        }
    }
}