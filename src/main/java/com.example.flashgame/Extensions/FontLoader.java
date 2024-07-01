package com.example.flashgame.Extensions;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader
{
    /**
     * Loads the custom font from the resource folder.
     */
    public Font loadCustomFont(String fontFamily, int fontSize)
    {
        try
        {
            // Load the font from the resource folder
            String fontPath = "/fonts/" + fontFamily + ".ttf";
            InputStream is = FontLoader.class.getResourceAsStream(fontPath);

            // If the font is not found, throw an IOException
            if (is == null)
            {
                throw new IOException("Font resource not found: " + fontPath);
            }

            // Create the custom font
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont((float) fontSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            // Return the custom font
            return customFont;
        }
        catch (Exception e)
        {
            // If the font is not found, print the stack trace and return a fallback font
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, fontSize); // Fallback font
        }
    }
}