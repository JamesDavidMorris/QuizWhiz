package com.example.flashgame.Extensions;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.example.flashgame.VisualSettings.VisualSettings.*;

public class StyledButton extends JButton
{
    // Button
    private Color normalBackgroundColour; // The background colour of the button in its default state
    private Color hoverBackgroundColour; // The background colour of the button in its hovered state
    private Color pressedBackgroundColour; // The background colour of the button in its pressed state

    // Text
    private Color normalTextColour; // The colour of the text in its default state
    private Color interactedTextColour; // The colour of the text in its hovered or pressed state
    private String fontFamily; // The font family of the text
    private int fontSize = 12; // The font size of the text

    // Border
    private int borderNormalBrightness = 10; // The brightness of the default border
    private int borderInteractedBrightness = 50; // The brightness of the border when interacted with
    private int borderThickness = 5; // The thickness of the border

    /**
     * Creates a new button with a definable colour scheme
     * @param text                  the text of the button
     * @param width                 the width of the button
     * @param height                the height of the button
     * @param x                     the x position of the button
     * @param y                     the y position of the button
     * @param normalBgColour        the background colour of the button in its default state
     * @param hoverBgColour         the background colour of the button in its hovered state
     * @param pressedBgColor        the background colour of the button in its pressed state
     * @param normalTextColour      the colour of the text in its default state
     * @param interactedTextColour  the colour of the text in its hovered or pressed state
     */
    public StyledButton(String text, int width, int height, int x, int y, Color normalBgColour, Color hoverBgColour, Color pressedBgColor, Color normalTextColour, Color interactedTextColour)
    {
        super(text);

        // Set the colours
        normalBackgroundColour = normalBgColour;
        hoverBackgroundColour = hoverBgColour;
        pressedBackgroundColour = pressedBgColor;

        this.normalTextColour = normalTextColour;
        this.interactedTextColour = interactedTextColour;

        // Set button position and size
        setButtonBounds(width, height, x, y);

        // Load custom font
        this.fontFamily = getTextAlternateFont();
        setFont(new FontLoader().loadCustomFont(fontFamily, fontSize));

        initialiseButton();
    }

    /**
     * Creates a new button with the default colour scheme
     * @param text      the text of the button
     * @param width     the width of the button
     * @param height    the height of the button
     * @param x         the x position of the button
     * @param y         the y position of the button
     */

    public StyledButton(String text, int width, int height, int x, int y)
    {
        super(text);

        // Set the colours
        normalBackgroundColour = getButtonNormalBackgroundColour();
        hoverBackgroundColour = getButtonHoverBackgroundColour();
        pressedBackgroundColour = getButtonPressedBackgroundColour();

        this.normalTextColour = getButtonTextNormalColour();
        this.interactedTextColour = getButtonTextInteractedColour();

        // Set button position and size
        setButtonBounds(width, height, x, y);

        // Load custom font
        this.fontFamily = getTextAlternateFont();
        setFont(new FontLoader().loadCustomFont(fontFamily, fontSize));

        initialiseButton();
    }

    /**
     * Creates a new button with the default colour scheme
     * @param text      the text of the button
     */
    public StyledButton(String text)
    {
        super(text);

        // Set the colours
        normalBackgroundColour = getButtonNormalBackgroundColour();
        hoverBackgroundColour = getButtonHoverBackgroundColour();
        pressedBackgroundColour = getButtonPressedBackgroundColour();
        this.normalTextColour = getButtonTextNormalColour();
        this.interactedTextColour = getButtonTextInteractedColour();

        // Load custom font
        this.fontFamily = getTextAlternateFont();
        setFont(new FontLoader().loadCustomFont(fontFamily, fontSize));

        initialiseButton();
    }

    /**
     * Set the size and position of the button
     * @param width         the width of the button
     * @param height        the height of the button
     * @param x             the x position of the button
     * @param y             the y position of the button
     */
    private void setButtonBounds(int width, int height, int x, int y)
    {
        // Set the size of the button
        Dimension buttonSize = new Dimension(width, height);
        setPreferredSize(buttonSize);
        setMinimumSize(buttonSize);
        setMaximumSize(buttonSize);
        setMargin(new Insets(0, 0, 0, 0));

        // Set the position of the button
        setBounds(x, y, width, height);
    }

    /**
     * Initialises the button with the correct settings, colours and mouse listener for interaction effects
     */
    private void initialiseButton()
    {
        // Button
        setBackground(normalBackgroundColour); // Set the background colour of the button
        setForeground(normalTextColour); // Set the text colour of the button
        setContentAreaFilled(false); // Set the content area filled to false
        setFocusPainted(false); // Set the focus painted to false
        setOpaque(false); // Set the button to be transparent

        // Border
        setBorderPainted(true);
        Color normalBorderColour = getThemeColour(userTheme, "PrimaryDark");
        Color interactedBorderColour = getThemeColour(userTheme, "Fourth");
        setBorder(new RoundedBorder(getButtonCornerRadius(), normalBorderColour, borderThickness));

        // Mouse listener for changing button visual states
        addMouseListeners(normalBorderColour, interactedBorderColour);
    }

    /**
     * Add mouse listeners to allow the changing of button states
     * @param normalBorderColour        The colour of the default background plus the default brightness
     * @param interactedBorderColour    The colour of the hover background plus the interaction brightness
     */
    private void addMouseListeners(Color normalBorderColour, Color interactedBorderColour)
    {
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent event)
            {
                // Change the background and text colour
                setBackground(hoverBackgroundColour);
                setForeground(interactedTextColour);

                // Change the border colour
                setBorder(new RoundedBorder(getButtonCornerRadius(), interactedBorderColour, borderThickness));
            }

            @Override
            public void mouseExited(MouseEvent event)
            {
                // Change the background and text colour
                setBackground(normalBackgroundColour);
                setForeground(normalTextColour);

                // Change the border colour
                setBorder(new RoundedBorder(getButtonCornerRadius(), normalBorderColour, borderThickness));
            }

            @Override
            public void mousePressed(MouseEvent event)
            {
                // Change the background and text colour
                setBackground(pressedBackgroundColour);
                setForeground(interactedTextColour);

                // Change the border colour
                setBorder(new RoundedBorder(getButtonCornerRadius(), normalBorderColour, borderThickness));
            }

            @Override
            public void mouseReleased(MouseEvent event)
            {
                // Change the background and text colour
                setBackground(contains(event.getPoint()) ? hoverBackgroundColour : normalBackgroundColour);
                setForeground(contains(event.getPoint()) ? interactedTextColour : normalTextColour);

                // Change the border colour
                setBorder(contains(event.getPoint()) ?
                        new RoundedBorder(getButtonCornerRadius(), interactedBorderColour, borderThickness) :
                        new RoundedBorder(getButtonCornerRadius(), normalBorderColour, borderThickness));
            }
        });
    }

    /**
     * Set the background colour of a button
     * @param color     The colour to set the background colour of a button
     */
    public void setBackgroundColor(Color color)
    {
        this.normalBackgroundColour = color;
    }

    /**
     * Overrides the default paintComponent in order to display the button states correctly
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        // Paint the background
        if (getModel().isPressed())
        {
            g.setColor(pressedBackgroundColour);
        }
        else if (getModel().isRollover())
        {
            g.setColor(hoverBackgroundColour);
        }
        else
        {
            g.setColor(normalBackgroundColour);
        }

        g.fillRoundRect(0, 0, getWidth(), getHeight(), getButtonCornerRadius(), getButtonCornerRadius());
        
        super.paintComponent(g);
    }

    /**
     * Allows the easy altering of colours by a specified brightness value for button interaction effects
     * @param colour        the colour of the button border
     * @param brightness    the desired change in colour of the button border
     * @return
     */
    private Color adjustBorderBrightness(Color colour, int brightness)
    {
        // Clamp values between 0 and 255
        int r = Math.min(Math.max(colour.getRed() + brightness, 0), 255);
        int g = Math.min(Math.max(colour.getGreen() + brightness, 0), 255);
        int b = Math.min(Math.max(colour.getBlue() + brightness, 0), 255);

        return new Color(r, g, b);
    }
}
