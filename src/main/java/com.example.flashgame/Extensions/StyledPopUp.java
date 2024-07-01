package com.example.flashgame.Extensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static com.example.flashgame.VisualSettings.VisualSettings.*;

/**
 * Create a customisable popup window with either one or two buttons.
 * It supports a single button hiding the popup or a single and double button configurations
 * with custom action listeners as parameters.
 */
public class StyledPopUp extends JPanel
{
    private Color backgroundColour; // Background colour of the popup
    private TextLabel textLabel; // Text to display in the popup
    private StyledButton button1, button2; // Buttons to display in the popup

    // Drop Shadow properties
    int shadowOffset = 5; // Offset of the shadow
    int shadowAlpha = 75; // Alpha value of the shadow

    /**
     * Create a popup window with one button that hides the popup when clicked
     * @param frame             The JFrame to attach the popup to
     * @param popUpText         The text to be displayed in the popup
     * @param button1Text       The text for the single button
     */
    public StyledPopUp(JFrame frame, String popUpText, String button1Text)
    {
        // Set up the popup window with the text and default layout configuration
        setupPopUp(frame, popUpText);

        // Add a single button to the popup
        addSingleButton(button1Text, event -> closePopUp());
        showPopUp();
    }


    /**
     * Create a popup window with one button with a customisable ActionListener
     * @param frame             The JFrame to attach the popup to
     * @param popUpText         The text to be displayed in the popup
     * @param button1Text       The text for the single button
     * @param button1Listener   The ActionListener for the single button
     */
    public StyledPopUp(JFrame frame, String popUpText, String button1Text, ActionListener button1Listener)
    {
        // Set up the popup window with the text and default layout configuration
        setupPopUp(frame, popUpText);

        // Add a single button to the popup
        addSingleButton(button1Text, button1Listener);
        showPopUp();
    }

    /**
     * Create a popup window with two buttons, each with a customisable ActionListener
     * @param frame             The JFrame to attach the popup to
     * @param popUpText         The text to be displayed in the popup
     * @param button1Text       The text for the first button
     * @param button1Listener   The ActionListener for the first button
     * @param button2Text       The text for the second button
     * @param button2Listener   The ActionListener for the second button
     */
    public StyledPopUp(JFrame frame, String popUpText, String button1Text, ActionListener button1Listener, String button2Text, ActionListener button2Listener)
    {
        // Set up the popup window with the text and default layout configuration
        setupPopUp(frame, popUpText);
        // Add two buttons to the popup
        addDoubleButton(button1Text, button1Listener, button2Text, button2Listener);
        showPopUp();
    }

    /**
     * Sets up the popup with a specific text and a default layout configuration
     * @param popUpText         The text to display in the popup
     */
    private void setupPopUp(JFrame frame, String popUpText)
    {
        // Calculate popup size
        int width = getApplicationWidth() / 2;
        int height = getApplicationHeight() / 4;

        // Calculate popup position
        int x = (getApplicationWidth() - width) / 2;
        int y = (getApplicationHeight() - height) / 2;

        // PopUp appearance
        backgroundColour = getPopUpBackgroundColour();
        setBounds(x, y, width, height);
        setOpaque(false);
        setVisible(true);

        // Text label
        int textLabelHeight = height / 2;
        Font customFont = new FontLoader().loadCustomFont(getTextFont(), 14);
        textLabel = new TextLabel(
                popUpText,
                customFont.getSize(),
                customFont.getStyle(),
                getPopUpFontColour(),
                0,
                0,
                width,
                textLabelHeight);
        textLabel.setFont(customFont);
        textLabel.setPreferredSize(new Dimension(width, textLabelHeight));
        add(textLabel);

        addPopUpToFrame(frame);
    }

    /**
     * Add a single button to the popup
     * @param button1Text       Text for the button
     * @param button1Listener   ActionListener for the button
     */
    private void addSingleButton(String button1Text, ActionListener button1Listener)
    {
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        // Create buttons
        button1 = new StyledButton(
                button1Text,
                100, 30, 0, 0
        );
        button1.addActionListener(button1Listener);

        buttonPanel.add(button1);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds two buttons to the popup
     * @param button1Text       Text for the first button
     * @param button1Listener   ActionListener for the first button
     * @param button2Text       Text for the second button
     * @param button2Listener   ActionListener for the second button
     */
    private void addDoubleButton(String button1Text, ActionListener button1Listener, String button2Text, ActionListener button2Listener)
    {
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        // Create buttons
        button1 = new StyledButton(
                button1Text,
                100, 30, 0, 0
        );
        button1.addActionListener(button1Listener);

        button2 = new StyledButton(
                button2Text,
                100, 30, 0, 0
        );
        button2.addActionListener(button2Listener);

        // Add buttons to the panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        // Add the panel to the popup
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds the popup to the layered pane of the specified pane
     * @param frame     The JFrame to attach the popup to
     */
    private void addPopUpToFrame(JFrame frame)
    {
        // Add the popup to the layered pane
        frame.getLayeredPane().add(this, JLayeredPane.MODAL_LAYER);

        // Move the popup to the front
        frame.getLayeredPane().moveToFront(this);

        // Repaint the layered pane
        frame.getLayeredPane().revalidate();

        // Repaint the frame
        frame.getLayeredPane().repaint();
    }

    /**
     * Displays the popup and enables its buttons
     */
    public void showPopUp()
    {
        setVisible(true);
        setButtonEnabled(true);
    }

    /**
     * Hides the popup and disables its buttons
     */
    public void hidePopUp()
    {
        setVisible(false);
        setButtonEnabled(false);
    }

    /**
     * Closes the popup by removing it from the frames layered pane
     */
    public void closePopUp()
    {
        // Remove the popup from the frame
        Container parent = getParent();

        // Check if the parent is not null
        if (parent != null)
        {
            // Remove the popup from the parent
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        }
    }

    /**
     * Enalbes or disables the buttons in the popup
     * @param isEnabled True to enable, false to disable
     */
    private void setButtonEnabled(boolean isEnabled)
    {
        // Enable or disable the buttons
        if (button1 != null)
        {
            button1.setEnabled(isEnabled);
        }
        if (button2 != null)
        {
            button2.setEnabled(isEnabled);
        }
    }

    /**
     * Prints the background and shadow of the popup
     * @param g Graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        // Call the super method
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Drop Shadow
        Color shadowColour = new Color(0, 0, 0, shadowAlpha);
        g2d.setColor(shadowColour);
        g2d.fillRoundRect(5, 5, getWidth() - shadowOffset, getHeight() - shadowOffset, getPopUpCornerRadius(), getPopUpCornerRadius());

        // Main Panel
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColour);
        g2d.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, getPopUpCornerRadius(), getPopUpCornerRadius());
    }
}
