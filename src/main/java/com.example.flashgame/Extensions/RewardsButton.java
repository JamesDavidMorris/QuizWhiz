package com.example.flashgame.Extensions;

import com.example.flashgame.Main;
import com.example.flashgame.Panels.Auth.User;
import com.example.flashgame.Panels.Auth.UserDaoImpl;
import com.example.flashgame.Panels.ProfilePage.PanelRewards;
import com.example.flashgame.VisualSettings.VisualSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.example.flashgame.VisualSettings.VisualSettings.*;

public class RewardsButton extends StyledButton
{
    private JFrame mainFrame;
    private JPanel rewardPanel;

    private String rewardName;
    private List<String> colorRefs = Arrays.asList("Primary", "PrimaryDark", "Secondary", "Third", "Fourth");
    private int requiredLevel;
    private int playerLevel;
    private boolean isComingSoon;

    public static UserDaoImpl userDao;
    public static User currentuser;

    private static final int PADDING = 10;
    private static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 12);
    private static final Font PLAIN_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Font BOLD_FONT_LARGE = new Font("Arial", Font.BOLD, 16);

    /**
     * Constructor for RewardsButton
     * @param mainFrame      The main JFrame of the application
     * @param rewardPanel    The JPanel for the rewards
     * @param rewardName     The name of the reward
     * @param requiredLevel  The level required to unlock the reward
     * @param playerLevel    The player's current level
     * @param isComingSoon   Flag indicating if the reward is coming soon
     * @param width          The width of the button
     * @param height         The height of the button
     * @param x              The x-coordinate of the button
     * @param y              The y-coordinate of the button
     * @param signedInUser   The currently signed-in user
     */
    public RewardsButton(JFrame mainFrame, JPanel rewardPanel, String rewardName, int requiredLevel, int playerLevel, boolean isComingSoon, int width, int height, int x, int y, User signedInUser)
    {
        super("", width, height, x, y);
        currentuser = signedInUser; // Get the current user
        userDao = new UserDaoImpl(); // Create a new instance of the UserDaoImpl
        this.mainFrame = mainFrame;
        this.rewardPanel = rewardPanel;
        this.rewardName = rewardName;
        this.requiredLevel = requiredLevel;
        this.playerLevel = playerLevel;
        this.isComingSoon = isComingSoon;

        // Disable the button if the player's level is below the required level or if it is marked as "Coming Soon"
        if (playerLevel < requiredLevel || isComingSoon)
        {
            setEnabled(false);
        }
        else
        {
            // Add the action listener to change the theme
            addActionListener(e -> changeTheme());
        }
    }

    /**
     * Change the theme to the reward name
     */
    private void changeTheme()
    {
        // Set the user's theme to the set theme reward name
        VisualSettings.userTheme = rewardName;

        // Update the user's theme in the database
        userDao.updateTheme(currentuser.getCurrentUser(), rewardName);
        VisualSettings.updateThemeColours();
        Main.refreshUI(new PanelRewards(mainFrame));
    }

    /**
     * Draw the button with appropriate text and colors
     * @param g The Graphics object to draw on
     */
    private void drawButton(Graphics g)
    {
        g.setFont(PLAIN_FONT);
        FontMetrics fm = g.getFontMetrics();
        int textHeight = fm.getAscent();
        Color textColor = isEnabled() ? getButtonTextNormalColour() : getTextAlternateColour();
        g.setColor(textColor);

        if (playerLevel >= requiredLevel)
        {
            drawUnlockedReward(g, fm, textHeight);
        }
        else
        {
            drawLockedReward(g, fm, textHeight);
        }
    }

    /**
     * Draw the unlocked reward with the appropriate text and colors
     * @param g         The Graphics object to draw on
     * @param fm        The FontMetrics for measuring text dimensions
     * @param textHeight The height of the text
     */
    private void drawUnlockedReward(Graphics g, FontMetrics fm, int textHeight)
    {
        String levelText = "Level " + requiredLevel + ": ";
        g.setFont(BOLD_FONT);
        fm = g.getFontMetrics();

        int levelTextWidth = fm.stringWidth(levelText);
        int textX = (getWidth() - (levelTextWidth + fm.stringWidth(rewardName))) / 2;
        int textY = getHeight() - PADDING;

        g.drawString(levelText, textX, textY);
        g.setFont(PLAIN_FONT);
        g.drawString(rewardName, textX + levelTextWidth, textY);

        if (isComingSoon)
        {
            drawComingSoonText(g, fm, textHeight);
        } else {
            drawColorRectangles(g, textHeight);
        }
    }

    /**
     * Draw the locked reward with a message and a question mark
     * @param g         The Graphics object to draw on
     * @param fm        The FontMetrics for measuring text dimensions
     * @param textHeight The height of the text
     */
    private void drawLockedReward(Graphics g, FontMetrics fm, int textHeight)
    {
        String unlockMessage = "Unlock at Level " + requiredLevel;
        g.drawString(unlockMessage, (getWidth() - fm.stringWidth(unlockMessage)) / 2, getHeight() - PADDING);
        g.setFont(BOLD_FONT_LARGE);
        String questionMark = "?";

        int qmWidth = fm.stringWidth(questionMark);
        int qmHeight = fm.getAscent();
        g.drawString(questionMark, (getWidth() - qmWidth) / 2, (getHeight() - textHeight - PADDING) / 2 + qmHeight / 2);
    }

    /**
     * Draw the "Coming Soon" text
     * @param g         The Graphics object to draw on
     * @param fm        The FontMetrics for measuring text dimensions
     * @param textHeight The height of the text
     */
    private void drawComingSoonText(Graphics g, FontMetrics fm, int textHeight)
    {
        String comingSoonText = "Coming Soon";
        g.setFont(BOLD_FONT_LARGE);
        fm = g.getFontMetrics();

        int comingSoonTextWidth = fm.stringWidth(comingSoonText);
        int comingSoonTextX = (getWidth() - comingSoonTextWidth) / 2;
        int comingSoonTextY = (getHeight() - textHeight - PADDING) / 2 + fm.getAscent() / 2;
        g.drawString(comingSoonText, comingSoonTextX, comingSoonTextY);
    }

    /**
     * Draw the color rectangles representing the reward's theme colors
     * @param g         The Graphics object to draw on
     * @param textHeight The height of the text
     */
    private void drawColorRectangles(Graphics g, int textHeight)
    {
        int rectAvailableWidth = getWidth() - 2 * PADDING;
        int rectWidth = rectAvailableWidth / colorRefs.size();
        int rectHeight = getHeight() - 3 * PADDING - textHeight;
        int rectY = PADDING;

        for (int i = 0; i < colorRefs.size(); i++) {
            g.setColor(VisualSettings.getThemeColour(rewardName, colorRefs.get(i)));
            int rectX = PADDING + i * rectWidth;
            g.fillRect(rectX, rectY, rectWidth, rectHeight);
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawButton(g);
    }
}