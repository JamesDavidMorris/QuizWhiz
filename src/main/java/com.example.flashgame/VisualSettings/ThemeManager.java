package com.example.flashgame.VisualSettings;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import com.example.flashgame.VisualSettings.Themes.*;

public class ThemeManager
{
    private static final Map<String, Theme> themes = new HashMap<>();

    static
    {
        themes.put("Default Theme", new DefaultTheme());
        themes.put("Alpine Theme", new AlpineTheme());
        themes.put("Candy Theme", new CandyTheme());
        themes.put("Jungle Theme", new JungleTheme());
        themes.put("Ocean Theme", new OceanTheme());
        themes.put("Fairy Theme", new FairyTheme());
        themes.put("Robot Theme", new RobotTheme());
        themes.put("Space Theme", new SpaceTheme());
        themes.put("Dino Theme", new DinoTheme());
        themes.put("Castle Theme", new CastleTheme());
        themes.put("Pirate Theme", new PirateTheme());
        themes.put("Princess Theme", new PrincessTheme());
        themes.put("Safari Theme", new SafariTheme());
        themes.put("Volcano Theme", new VolcanoTheme());
        themes.put("Beach Theme", new BeachTheme());
    }

    public static Color getThemeColour(String themeName, String colourRef)
    {
        Theme theme = themes.get(themeName);

        if (theme != null)
        {
            return theme.getColor(colourRef);
        }

        return Color.BLACK; // Default colour if theme or colorRef not found
    }
}