package com.example.flashgame.Extensions;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import com.example.flashgame.VisualSettings.VisualSettings;

import static com.example.flashgame.VisualSettings.VisualSettings.*;

public class RewardProgressRing extends JComponent {
    private double progress;
    private Timer timer;
    private TextLabel statusLabel;
    private TextLabel numberLabel;
    private TextLabel textLabel;

    // Score
    private boolean milestoneCrossed = false;
    private boolean isScoreAMilestone;
    private int scoreOfUser = 0;
    private int levelOfUser = 0;

    /**
     * Constructor for RewardProgressRing.
     *
     * @param isScoreAMilestone Indicates if the user has increased a level
     * @param scoreOfUser The score of the user
     * @param levelOfUser The level of the user
     */
    public RewardProgressRing(boolean isScoreAMilestone, int scoreOfUser, int levelOfUser)
    {

        this.progress = 0; // Default progress
        this.isScoreAMilestone = isScoreAMilestone; // Is the score a milestone
        this.scoreOfUser = scoreOfUser; // Score of the user
        this.levelOfUser = levelOfUser; // Level of the user

        // Set the layout to null
        setLayout(null);
        displayLevelText();
        displayStatusLabel();
    }

    /**
     * Get the progress of the ring.
     * @return The current progress as a double.
     */
    public double getProgress()
    {
        return progress;
    }

    /**
     * Set the progress of the ring.
     * @param progress The progress value to set.
     */
    public void setProgress(double progress)
    {
        // Set the progress
        this.progress = Math.min(progress, 100);

        // Repaint the component
        repaint();
    }

    /**
     * Animate the progress of the level up ring graphic from a start score to an end score
     * @param startScore The starting score
     * @param endScore The ending score
     * @param speed The speed of the animation
     */
    public void animateProgress(double startScore, double endScore, double speed)
    {
        int speedMultiplier = 25; // Speed multiplier
        // If the timer is not null and is running
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        // Calculate relative progress within the current level
        double relativeStart = getRelativeScore(startScore);
        final double[] relativeEnd = { getRelativeScore(endScore) };

        // If the relative score loops back round, then cap it
        if (relativeEnd[0] < relativeStart)
        {
            relativeEnd[0] = 100;
        }

        // No points were scored
        if (startScore == endScore)
        {
            relativeEnd[0] = getRelativeScore(startScore);
        }

        // Set the progress
        setProgress(relativeStart);

        // Calculate the step
        double step = (relativeEnd[0] - relativeStart) > 0 ? speed : -speed;

        // Create a new timer
        timer = new Timer(speedMultiplier, e -> {
            double updatedProgress = getProgress() + step / (1000.0 / speedMultiplier);
            // If the step is greater than 0 and the updated progress is greater than or equal to the relative end
            // or if the step is less than 0 and the updated progress is less than or equal to the relative end
            if ((step > 0 && updatedProgress >= relativeEnd[0])  ||
                    (step < 0 && updatedProgress <= relativeEnd[0]))
            {
                // Set the progress
                setProgress(relativeEnd[0]);

                // If the updated progress is greater than or equal to 100
                if (updatedProgress >= 100)
                {
                    // Set the milestone crossed to true
                    statusLabel.setText("LEVEL UP!");
                    statusLabel.setVisible(true);

                    // Calculate the new level based on endScore
                    int newLevel = getLevelFromScore((int) endScore);
                    numberLabel.setText(String.valueOf(newLevel));

                    // Wait for 6 seconds before resetting
                    Timer waitTimer = new Timer(6000, evt -> {
                        // Hide the level up message
                        statusLabel.setVisible(false);
                    });

                    // Set the timer to not repeat
                    waitTimer.setRepeats(false);
                    waitTimer.start();
                }
                // Stop the timer
                timer.stop();
            }
            else
            {
                // Set the progress
                setProgress(updatedProgress);
            }
        });

        // Start the timer
        timer.start();
    }

    /**
     * Get the level from the score
     * @param score The score to calculate the level from
     * @return The calculated level
     */
    private int getLevelFromScore(int score)
    {
        // Calculate the level from the score
        int levelFromScore = (int) Math.floor((score / 100) + 1);
        return levelFromScore;
    }

    /**
     * Clamps the user score within 0 to 100 for animation calculations
     * @param score The score to calculate the relative score from
     * @return The relative score within the current level
     */
    private double getRelativeScore(double score)
    {
        // Calculate the level from the score
        int level = getLevelFromScore((int) score);
        double levelStartScore = (level - 1) * 100;
        return score - levelStartScore;
    }

    /**
     * Display the status label for level up
     */
    private void displayStatusLabel()
    {
        // Display the level up message
        statusLabel = new TextLabel(
                "LEVEL UP!",
                36,
                getTextAlternateFont(),
                Font.BOLD,
                getTextColour(),
                0, 0,
                600, 50);
        // Add the status label
        add(statusLabel);
        statusLabel.setVisible(false);
        // Start the rainbow effect
        statusLabel.startRainbowEffect();
    }

    /**
     * Display the level number and text
     */
    private void displayLevelText()
    {
        // Display the level number
        numberLabel = new TextLabel(
                String.valueOf(levelOfUser),
                60,
                getTextFont(),
                Font.BOLD,
                getTextColour(),
                0, 0,
                600, 50);

        // Display the level text
        textLabel = new TextLabel(
                "Level",
                18,
                getTextFont(),
                Font.BOLD,
                getTextColour(),
                0, 0,
                600, 50);
        // Add labels
        add(numberLabel);
        add(textLabel);
    }

    /**
     * Draw the progress ring.
     * @param g2d The Graphics2D object to draw on
     * @param x The x-coordinate of the ring
     * @param y The y-coordinate of the ring
     * @param radius The radius of the ring
     * @param ringThickness The thickness of the ring
     * @param outlineThickness The thickness of the outline
     */
    private void drawProgressRing(Graphics2D g2d, double x, double y, double radius, int ringThickness, int outlineThickness)
    {
        // Calculate the angle of the progress
        int angle = (int) (360.0 * progress / 100);
        // Get the progress ring colour
        Color ringProgress = VisualSettings.getProgressRingBarColour();
        // Create the progress ring
        Shape progressRing = generateRing(x, y, radius, ringThickness);
        g2d.setClip(progressRing);
        g2d.setColor(ringProgress);
        g2d.fillArc(
                (int) (x - radius),
                (int) (y - radius),
                (int) (radius * 2),
                (int) (radius * 2),
                90,
                -angle);
        g2d.setClip(null);
        g2d.setStroke(new BasicStroke(outlineThickness));
        Color ringOutline = VisualSettings.getProgressRingOutlineColour();
        g2d.setColor(ringOutline);
        g2d.draw(progressRing);
    }

    /**
     * Generate a ring shape.
     * @param x The x-coordinate of the ring
     * @param y The y-coordinate of the ring
     * @param radius The radius of the ring
     * @param ringThickness The thickness of the ring
     * @return The generated ring shape
     */
    private Shape generateRing(double x, double y, double radius, int ringThickness)
    {
        // Create outer circle
        Ellipse2D outerCircle = new Ellipse2D.Double(
                x - radius,
                y - radius,
                radius * 2,
                radius * 2);

        // Create inner circle
        Ellipse2D innerCircle = new Ellipse2D.Double(
                x - radius + ringThickness,
                y - radius + ringThickness,
                (radius - ringThickness) * 2,
                (radius - ringThickness) * 2);

        // Create a ring by subtracting the inner circle from the outer circle
        Area ring = new Area(outerCircle);
        ring.subtract(new Area(innerCircle));

        return ring;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        // Call the super method
        super.paintComponent(g);
        // Cast the graphics to 2D
        Graphics2D g2d = (Graphics2D) g;
        // Set the rendering hints
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the dimensions
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) - 5;
        int ringThickness = size / 10;

        // Calculate the position of the ring
        double x = size / 2.0;
        double y = size / 2.0;
        double radius = size / 2.0;

        // Define colour and size of the outline ring
        Color ringBackground = VisualSettings.getProgressRingBackgroundColour();
        Color ringOutline = VisualSettings.getProgressRingOutlineColour();
        int outlineThickness = 2;

        // Create the outline ring
        Shape outlineRing = generateRing(x, y, radius, ringThickness);
        g2d.setColor(ringBackground);
        g2d.fill(outlineRing);
        g2d.setStroke(new BasicStroke(outlineThickness));
        g2d.setColor(ringOutline);
        g2d.draw(outlineRing);

        // Draw the progress ring
        drawProgressRing(g2d, x, y, radius, ringThickness, outlineThickness);

        // Set the text relative to the ring position
        int centerX = (int) x;
        int centerY = (int) y;

        // Set the bounds of the labels
        statusLabel.setBounds(centerX - 297, centerY - 155, 600, 50);
        numberLabel.setBounds(centerX - 297, centerY - 35, 600, 50);
        textLabel.setBounds(centerX - 297, centerY + 5, 600, 50);
    }

    @Override
    // Get the preferred size
    public Dimension getPreferredSize()
    {
        return new Dimension(200, 200);
    }
}
