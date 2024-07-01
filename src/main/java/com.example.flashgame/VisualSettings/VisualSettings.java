package com.example.flashgame.VisualSettings;

import java.awt.*;

/**
 * This file contains all the GUI settings for various elements
 * e.g. fonts, font sizes, button colours, etc
 */
public final class VisualSettings
{
    // Application
    private static int applicationWidth = 1000;
    private static int applicationHeight = 600;

    public static String userTheme = "Default Theme";

    /**
     * Retrieve the GUI element colours per theme
     * @param themeName     The name of the theme
     * @param colourRef     The various colour references for each element (e.g. primary, secondary, third)
     * @return
     */
    public static Color getThemeColour(String themeName, String colourRef)
    {
        return ThemeManager.getThemeColour(themeName, colourRef);
    }

    static
    {
        updateThemeColours();
    }

    /**
     * Update the GUI theme colours
     */
    public static void updateThemeColours()
    {
        applicationGradientStartColour = getThemeColour(userTheme, "Third");
        applicationGradientEndColour = getThemeColour(userTheme, "Primary");
        titleBarColour = getThemeColour(userTheme, "PrimaryDark");
        buttonNormalBackgroundColour = getThemeColour(userTheme, "Secondary");
        buttonHoverBackgroundColour = getThemeColour(userTheme, "Primary");
        buttonPressedBackgroundColour = getThemeColour(userTheme, "Primary");
        cardColour = getThemeColour(userTheme, "PrimaryDark");
        popUpBackgroundColour = getThemeColour(userTheme, "PrimaryDark");

        // Progress Ring
        progressRingBackgroundColour = getThemeColour(userTheme, "PrimaryDark");
        progressRingOutlineColour = getThemeColour(userTheme, "PrimaryDark");
        progressRingBarColour = getThemeColour(userTheme, "Third");

        // Text
        textColour = getThemeColour(userTheme, "Text");
        textAlternateColour = getThemeColour(userTheme, "Text");

        // Button
        buttonTextNormalColour = getThemeColour(userTheme, "Text");
        buttonTextInteractedColour = getThemeColour(userTheme, "Text");

        // Label
        labelFontColour = getThemeColour(userTheme, "Text");

        // PopUp
        popUpFontColour = getThemeColour(userTheme, "Text");

        // Styled Text Box
        styledTextBoxBGColour = getThemeColour(userTheme, "Secondary");
        styledTextBoxOutlineColour = getThemeColour(userTheme, "Text");
        styledTextBoxTextColour = getThemeColour(userTheme, "Text");
        styledTextBoxTextAlternateColour = getThemeColour(userTheme, "Text");

        // Scroll Pane / Bar
        scrollPaneBackgroundColour = getThemeColour(userTheme, "Secondary");
        scrollPaneBorderColour = getThemeColour(userTheme, "Fourth");
        scrollBarBackgroundColour = getThemeColour(userTheme, "PrimaryDark");
        scrollBarThumbColour = getThemeColour(userTheme, "Fourth");
    }

    /**
     * Adjust the brightness of a colour by changing its RGB components.

     * @param colour  The original colour to be adjusted.
     * @param amount The amount by which to adjust the brightness of the colour.
     *               Positive values lighten the color, negative values darken it.
     * @return A new Colour object that is the adjusted version of the original colour.
     */
    public static Color adjustBrightness(Color colour, int amount)
    {
        int red = Math.min(Math.max(colour.getRed() + amount, 0), 255);
        int green = Math.min(Math.max(colour.getGreen() + amount, 0), 255);
        int blue = Math.min(Math.max(colour.getBlue() + amount, 0), 255);

        return new Color(red, green, blue);
    }

    // Visual settings
    private static int loginWidth = 900;
    private static int loginHeight = 400;
    private static int[] applicationCornerRadius = {50, 50, 50, 50};
    private static Color applicationGradientStartColour = getThemeColour(userTheme, "Third");
    private static Color applicationGradientEndColour = getThemeColour(userTheme, "Primary");
    private static int[] applicationGradientPositions = new int[]{0, 0, applicationWidth, applicationHeight};


    // Title bar
    private static int titleBarWidth = 1000;
    private static int titleBarHeight = 100;
    private static int[] titleBarCornerRadius = {25, 25, 0, 0};
    private static Color titleBarColour = getThemeColour(userTheme, "PrimaryDark");

    // Text
    private static Color textColour = getThemeColour(userTheme, "Text");
    private static Color textAlternateColour = getThemeColour(userTheme, "Text");
    private static String textFont = "ComicsDeluxe";
    private static String textAlternateFont = "Arial";

    // Button
    private static Color buttonNormalBackgroundColour = getThemeColour(userTheme, "Secondary");
    private static Color buttonHoverBackgroundColour = getThemeColour(userTheme, "Primary");
    private static Color buttonPressedBackgroundColour = getThemeColour(userTheme, "Primary");
    private static Color buttonTextNormalColour = getThemeColour(userTheme, "Text");
    private static Color buttonTextInteractedColour = getThemeColour(userTheme, "Text");
    private static int buttonCornerRadius = 15;

    // Card
    private static int cardWidth = 900;
    private static int cardHeight = 400;
    private static Color cardColour = getThemeColour(userTheme, "PrimaryDark");

    // Label
    private static Font labelFont = new Font("Arial", Font.PLAIN, 12);
    private static Color labelFontColour = getThemeColour(userTheme, "Text");

    // PopUp
    private static Color popUpBackgroundColour = getThemeColour(userTheme, "PrimaryDark");
    private static int popUpCornerRadius = 25;
    private static Font popUpFont = new Font("Arial", Font.BOLD, 18);
    private static Color popUpFontColour = getThemeColour(userTheme, "Text");

    // Styled Text Box
    private static Color styledTextBoxBGColour = getThemeColour(userTheme, "Secondary");
    private static Color styledTextBoxOutlineColour = getThemeColour(userTheme, "Text");
    private static Color styledTextBoxTextColour = getThemeColour(userTheme, "Text");
    private static Color styledTextBoxTextAlternateColour = getThemeColour(userTheme, "Text");

    // Scroll Pane / Bar
    private static Color scrollPaneBackgroundColour = getThemeColour(userTheme, "Secondary");
    private static Color scrollPaneBorderColour = getThemeColour(userTheme, "Fourth");
    private static Color scrollBarBackgroundColour = getThemeColour(userTheme, "PrimaryDark");
    private static Color scrollBarThumbColour = getThemeColour(userTheme, "Fourth");

    // Drop Shadow
    private static Color dropShadowColour = new Color(0, 0, 0, 128);
    private static int dropShadowOffset = 5;

    // Progress Ring
    private static Color progressRingBackgroundColour = getThemeColour(userTheme, "PrimaryDark");
    private static Color progressRingOutlineColour = getThemeColour(userTheme, "PrimaryDark");
    private static Color progressRingBarColour = getThemeColour(userTheme, "Third");

    // Getter and setter methods to access variables
    // Application
    /**
     * Returns the width of the application
     * @return  An int representing the width of the application
     */
    public static int getApplicationWidth()
    {
        return applicationWidth;
    }

    /**
     * Returns the height of the application
     * @return  An int representing the height of the application
     */
    public static int getApplicationHeight()
    {
        return applicationHeight;
    }

    /**
     * Returns the corner radius of the application window in the order of
     * top-left, top-right, bottom-right and bottom-left
     * @return  An array representing the corner radius for each corner
     */
    public static int[] getApplicationCornerRadius()
    {
        return applicationCornerRadius;
    }

    /**
     * Returns the start gradient colour of the application window
     * @return  A colour representing the start gradient colour used for the application window
     */
    public static Color getApplicationGradientStartColour()
    {
        return applicationGradientStartColour;
    }

    /**
     * Returns the end gradient colour of the application window
     * @return  A colour representing the end gradient colour used for the application window
     */
    public static Color getApplicationGradientEndColour()
    {
        return applicationGradientEndColour;
    }

    /**
     * Returns the gradient positions of the application window
     * @return  An array of integers representing the positions (start x, start y, end x, end y) of the gradient
     */
    public static int[] getApplicationGradientPositions() {return applicationGradientPositions; }

    // Title bar
    /**
     * Returns the width of the top title bar
     * @return  An int representing the width of the top title bar
     */
    public static int getTitleBarWidth()
    {
        return titleBarWidth;
    }

    /**
     * Returns the height of the top title bar
     * @return  An int representing the height of the top title bar
     */
    public static int getTitleBarHeight()
    {
        return titleBarHeight;
    }

    /**
     * Returns the corner radius of the top title bar in the order of
     * top-left, top-right, bottom-right and bottom-left
     * @return  An array representing the corner radius for each corner
     */
    public static int[] getTitleBarCornerRadius()
    {
        return titleBarCornerRadius;
    }

    /**
     * Returns the background colour of the top title bar
     * @return  A colour representing the background colour used for the top title bar
     */
    public static Color getTitleBarColour()
    {
        return titleBarColour;
    }

    // Text

    /**
     * Returns the primary colour used for text
     * @return  A colour representing the primary colour used for text
     */
    public static Color getTextColour() { return textColour; }

    /**
     * Returns the alternate colour used for text
     * @return  A colour representing the alternate colour used for text
     */
    public static Color getTextAlternateColour() { return adjustBrightness(textColour, -50); }

    /**
     * Returns the default font used for text
     * @return  A string representing the default font used for text
     */
    public static String getTextFont() { return textFont; }

    /**
     * Returns the alternate font used for text
     * @return  A string representing the alternate font used for text
     */
    public static String getTextAlternateFont() { return textAlternateFont; }

    // Buttons
    /**
     * Returns the background colour for buttons in the default state
     * @return  A colour representing the background colour for buttons in the default state
     */
    public static Color getButtonNormalBackgroundColour()
    {
        return buttonNormalBackgroundColour;
    }

    /**
     * Returns the background colour for buttons in the hover state
     * @return  A colour representing the background colour for buttons in the hover state
     */
    public static Color getButtonHoverBackgroundColour()
    {
        return buttonHoverBackgroundColour;
    }

    /**
     * Returns the background colour for buttons in the pressed state
     * @return  A colour representing the background colour for buttons in the pressed state
     */
    public static Color getButtonPressedBackgroundColour()
    {
        return buttonPressedBackgroundColour;
    }

    /**
     * Returns the background colour for button text in the default state
     * @return  A colour representing the background colour for button text in the default state
     */
    public static Color getButtonTextNormalColour()
    {
        return buttonTextNormalColour;
    }

    /**
     * Returns the background colour for button text in the hovered or pressed state
     * @return  A colour representing the background colour for button text in the hovered or pressed state
     */
    public static Color getButtonTextInteractedColour()
    {
        return buttonTextInteractedColour;
    }

    /**
     * Returns the corner radius for buttons
     * @return  An int representing the radius of a corner for buttons
     */
    public static int getButtonCornerRadius() { return buttonCornerRadius; }

    // Card
    /**
     * Returns the width of the center card
     * @return  An int representing the width of the center card
     */
    public static int getCardWidth()
    {
        return cardWidth;
    }

    /**
     * Returns the height of the center card
     * @return  An int representing the height of the center card
     */
    public static int getCardHeight()
    {
        return cardHeight;
    }

    /**
     * Returns the background colour for center card
     * @return  A colour representing the background colour for center card
     */
    public static Color getCardColour()
    {
        return cardColour;
    }

    // Label

    /**
     * Returns the default font styling used for labels
     * @return  A font object representing the default font styling used for labels
     */
    public static Font getLabelFont() { return labelFont; }

    /**
     * Returns the default colour used for label text
     * @return  A colour representing the default colour used for label text
     */
    public static Color getLabelFontColour() { return labelFontColour; }

    // PopUp
    /**
     * Returns the background colour used for pop-up windows
     * @return A Colour object representing the background colour of pop-up windows
     */
    public static Color getPopUpBackgroundColour()
    {
        return adjustBrightness(popUpBackgroundColour, -10);
    }

    /**
     * Returns the corner radius for pop-up windows
     * @return An int representing the corner radius of pop-up windows
     */
    public static int getPopUpCornerRadius() { return popUpCornerRadius; }

    /**
     * Returns the font used for text in pop-up windows
     * @return A Font object representing the font used in pop-up windows
     */
    public static Font getPopUpFont() { return popUpFont; }

    /**
     * Returns the font colour used for text in pop-up windows
     * @return A Colour object representing the font colour used in pop-up windows
     */
    public static Color getPopUpFontColour() { return popUpFontColour; }

    // Styled Text Box
    /**
     * Returns the background colour for styled text boxes
     * @return A Colour object representing the background colour of styled text boxes
     */
    public static Color getStyledTextBoxBGColour() { return styledTextBoxBGColour; }

    /**
     * Returns the outline colour for styled text boxes
     * @return A Colour object representing the outline colour of styled text boxes
     */
    public static Color getStyledTextBoxOutlineColour() { return styledTextBoxOutlineColour; }

    /**
     * Returns the primary text colour for styled text boxes
     * @return A Colour object representing the primary text colour of styled text boxes
     */
    public static Color getStyledTextBoxTextColour() { return styledTextBoxTextColour; }

    /**
     * Returns the secondary text colour for styled text boxes
     * @return A Colour object representing the secondary text colour of styled text boxes
     */
    public static Color getStyledTextBoxTextAlternateColour() { return adjustBrightness(styledTextBoxTextColour, -85); }

    // Scroll Pane / Bar
    /**
     * Returns the background colour for scroll panes
     * @return A Colour object representing the background colour of scroll panes
     */
    public static Color getScrollPaneBackgroundColour() { return scrollPaneBackgroundColour; }

    /**
     * Returns the border colour for scroll panes
     * @return A Colour object representing the border colour of scroll panes
     */
    public static Color getScrollPaneBorderColour() { return scrollPaneBorderColour; }

    /**
     * Returns the background colour for scroll bars
     * @return A Colour object representing the background colour of scroll bars
     */
    public static Color getScrollBarBackgroundColour() { return scrollBarBackgroundColour; }

    /**
     * Returns the background colour for scroll bar arrows
     * @return A Colour object representing the background colour of scroll bar arrows
     */
    public static Color getScrollBarArrowColour() { return scrollBarThumbColour; }

    /**
     * Returns the background colour for scroll bar thumbs
     * @return A Colour object representing the background colour of scroll bar thumbs
     */
    public static Color getScrollBarThumbColour() { return adjustBrightness(scrollBarThumbColour, 50); }

    // Drop Shadow
    /**
     * Returns the colour used for drop shadows
     * @return A Colour object representing the colour used for drop shadows
     */
    public static Color getDropShadowColour() { return dropShadowColour; }

    /**
     * Returns the offset distance for drop shadows
     * @return An int representing the offset distance for drop shadows
     */
    public static int getDropShadowOffset() { return dropShadowOffset; }

    // Progress Ring
    /**
     * Returns the background colour used for the progress ring
     * @return  A colour representing the background colour used for the progress ring
     */
    public static Color getProgressRingBackgroundColour()
    {
        return adjustBrightness(progressRingBackgroundColour, -10);
    }

    /**
     * Returns the outline colour used for the progress ring
     * @return  A colour representing the outline colour used for the progress ring
     */
    public static Color getProgressRingOutlineColour()
    {
        return adjustBrightness(progressRingOutlineColour, -20);
    }

    /**
     * Returns the bar colour used for the progress ring
     * @return  A colour representing the bar colour used for the progress ring
     */
    public static Color getProgressRingBarColour()
    {
        return adjustBrightness(progressRingBarColour, 60);
    }
}