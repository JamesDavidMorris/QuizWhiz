package com.example.flashgame.Extensions;

import javax.swing.*;
import java.awt.*;

import com.example.flashgame.Extensions.FontLoader;
import static com.example.flashgame.VisualSettings.VisualSettings.*;

public class TextLabel extends JLabel {
    private String textString; // The text of the label
    private String fontFamily; // The font family of the label
    private int fontSize; // The font size of the label
    private int fontStyle; // The font style of the label
    private Color textColour; // The colour of the label
    private int x; // The x position of the label
    private int y; // The y position of the label
    private int width; // The width of the label
    private int height; // The height of the label
    private Timer rainbowTimer; // Timer for the rainbow effect
    private boolean isRainbowColour = false; // Whether the label is using the rainbow effect
    private float hue; // The hue of the rainbow effect

    /**
     * Creates a new text label with a definable font and colour
     * @param text          the text of the label
     * @param fontSize      the size of the text
     * @param fontStyle     the style of the text (e.g. Font.BOLD)
     * @param colour        the colour of the text
     * @param x             the x position of the label
     * @param y             the y position of the label
     * @param width         the width of the label
     * @param height        the height of the label
     */
    public TextLabel(String text, int fontSize, int fontStyle, Color colour, int x, int y, int width, int height)
    {
        // Set the text, font, colour, position, and size of the label with the default font family
        this.textString = text;
        this.fontFamily = getLabelFont().getFamily();
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.textColour = colour;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        initialiseLabel();
    }

    /**
     * Creates a new text label with a default text scheme
     * @param text          the text of the label
     * @param x             the x position of the label
     * @param y             the y position of the label
     * @param width         the width of the label
     * @param height        the height of the label
     */
    public TextLabel(String text, String fontFamily, int x, int y, int width, int height)
    {
        // Set the text, font, colour, position, and size of the label with the default font size and style
        this.textString = text;
        this.fontFamily = fontFamily;
        this.fontSize = 24;
        this.fontStyle = getLabelFont().getStyle();
        this.textColour = getTextColour();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        setFont(new FontLoader().loadCustomFont(fontFamily, fontSize));
        initialiseLabel();
    }

    /**
     * Creates a new text label with a custom font family
     * @param text          the text of the label
     * @param fontSize      the size of the text
     * @param fontFamily    the font family of the text
     * @param fontStyle     the style of the text (e.g. Font.BOLD)
     * @param colour        the colour of the text
     * @param x             the x position of the label
     * @param y             the y position of the label
     * @param width         the width of the label
     * @param height        the height of the label
     */
    public TextLabel(String text, int fontSize, String fontFamily, int fontStyle, Color colour, int x, int y, int width, int height)
    {
        // Set the text, font, colour, position, and size of the label with the default font size
        this.textString = text;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.textColour = colour;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        setFont(new FontLoader().loadCustomFont(fontFamily, fontSize));
        initialiseLabel();
    }

    /**
     * Initialises the label with the correct settings and colours
     */
    private void initialiseLabel()
    {
        // Set the text, font, colour, position, and size of the label
        setText(textString);
        setForeground(textColour);
        setBounds(x, y, width, height);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }

    /**
     * Starts the rainbow color effect
     */
    public void startRainbowEffect()
    {
        // Clear the text and start the rainbow effect
        setText("");
        isRainbowColour = true;
        hue = 0.0f;

        // Create a timer to update the hue of the rainbow effect
        if (rainbowTimer != null) {
            rainbowTimer.stop();
        }

        // Update the hue of the rainbow effect every 25 milliseconds
        rainbowTimer = new Timer (25, e -> {
            hue += 0.01f;
            if (hue >= 1.0f) {
                hue = 0.0f;
            }
            repaint();
        });

        // Start the timer
        rainbowTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        // Paint the label with the rainbow effect if enabled
        super.paintComponent(g);

        // Check if the rainbow effect is enabled and paint the label with the rainbow effect
        if (isRainbowColour)
        {
            // Create a new graphics object
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Set the font and colour of the label
            FontMetrics fontMetrics = g2d.getFontMetrics(getFont());
            int x = (getWidth() - fontMetrics.stringWidth(textString)) / 2;
            int y = (getHeight() + fontMetrics.getAscent()) / 2 - fontMetrics.getDescent();

            // Paint the label with the rainbow effect
            float hueOffset = hue;

            // Iterate through each character in the text
            for (char c : textString.toCharArray())
            {
                // Set the colour of the label
                int minimumColourValue = 196;
                Color colour = Color.getHSBColor(hueOffset, 1.0f, 1.0f);
                int red = Math.max(minimumColourValue, colour.getRed());
                int green = Math.max(minimumColourValue, colour.getGreen());
                int blue = Math.max(minimumColourValue, colour.getBlue());
                g2d.setColor(new Color(red, green, blue));
                g2d.drawString(String.valueOf(c), x, y);
                x += fontMetrics.charWidth(c);

                // Update the hue offset
                hueOffset += 0.1f;

                // Clamp the hue offset between 0 and 1
                if (hueOffset >= 1.0f)
                {
                    hueOffset -= 1.0f;
                }
            }
        }
    }
}
