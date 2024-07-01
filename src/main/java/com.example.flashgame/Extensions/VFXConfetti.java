package com.example.flashgame.Extensions;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates a visual effect of confetti raining down the screen
 * The confetti pieces are randomly generated with varying positions,
 * sizes, colours and speeds. The confetti animation can be added to
 * any JFrame and will run for a specified duration
 */
public class VFXConfetti extends JPanel
{
    private final List<Confetti> confettiList; // List of confetti pieces
    private final Timer timer; // Timer for updating the confetti
    private boolean isEnabled; // Flag to determine if the confetti is enabled
    private static final int max_confetti_count = 200; // Maximum number of confetti pieces
    private long lastUpdateTime; // Time of the last update

    /**
     * Constructs a new VFXConfetti panel
     * Initialises the list of confetti, sets up the timer for animation
     * and prepares the panel for rendering
     */
    public VFXConfetti()
    {
        // Set up the confetti list and timer
        confettiList = new ArrayList<>();
        timer = new Timer(10, e -> {
            // Get the time between updates to smooth out motion
            long currentTime = System.nanoTime();
            float deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0f;
            lastUpdateTime = currentTime;
            // Update the confetti pieces
            updateConfetti(deltaTime);
            repaint();
        });

        // Set up the panel
        setOpaque(false);
        isEnabled = true;
        lastUpdateTime = System.nanoTime();
    }

    /**
     * The confetti class stores all relevant information it requires
     * Information is created when the confetti is added
     */
    private static class Confetti
    {
        int width, height; // Size of the confetti piece
        float x, y, speed, rotationSpeed, angle; // Position, speed and rotation of the confetti piece
        Color colour; // Colour of the confetti piece

        Confetti(float x, float y, int width, int height, float speed, float rotationSpeed, float angle, Color colour)
        {
            // Set the properties of the confetti piece
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.speed = speed;
            this.rotationSpeed = rotationSpeed;
            this.angle = angle;
            this.colour = colour;
        }
    }

    /**
     * Updates the position and rotation of each confetti piece
     * Removes confetti that has moved out of the visible area
     * @param deltaTime     The time elapsed since the last update, used to smooth out movement
     */
    private void updateConfetti(float deltaTime)
    {
        // Update the position and rotation of each confetti piece
        List<Confetti> toRemove = new ArrayList<>();
        for (Confetti confetti : confettiList)
        {
            confetti.y += confetti.speed * deltaTime * 60;
            confetti.angle += confetti.rotationSpeed * deltaTime * 60;

            // Remove confetti that has moved out of the visible area
            if (confetti.y > getHeight())
            {
                toRemove.add(confetti);
            }
        }

        // Remove the confetti pieces that have moved out of the visible area
        confettiList.removeAll(toRemove);

        // Add new confetti pieces if the number of confetti pieces is less than the maximum
        if (!isEnabled && confettiList.isEmpty())
        {
            timer.stop();
            // Remove the confetti panel from its parent container
            SwingUtilities.invokeLater(() -> {
                Container parent = getParent();
                if (parent != null) {
                    parent.remove(this);
                    parent.repaint();
                }
            });
        }

        // Add new confetti pieces if the number of confetti pieces is less than the maximum
        if (isEnabled && confettiList.size() < max_confetti_count)
        {
            addConfetti(1);
        }
    }

    /**
     * Adds new confetti pieces to the list
     * @param count     The number of confetti pieces to add
     */
    private void addConfetti(int count)
    {
        // Do not add confetti if the confetti is disabled
        if (!isEnabled)
            return;

        // Add the specified number of confetti pieces to the list with random properties
        Random random = new Random();
        for (int i = 0; i < count; i++)
        {
            // Generate random properties for the confetti piece
            float x = random.nextInt(getWidth());
            float y = -random.nextInt(getHeight());
            int width = 5 + random.nextInt(5);
            int height = 1 + random.nextInt(8);
            float speed = 1.5f + random.nextFloat() * 1.5f;
            float rotationSpeed = 2f + random.nextFloat() * 5f;
            float angle = random.nextFloat() * 360f;
            Color colour = generateColour(random);

            confettiList.add(new Confetti(x, y, width, height, speed, rotationSpeed, angle, colour));
        }
    }

    /**
     * Generates a random bright colour for the confetti
     * @param random    The random object used to generate the colour
     * @return          A new colour object with random RGB values
     */
    private Color generateColour(Random random)
    {
        // Generate a random bright colour
        int r = 80 + random.nextInt(176);
        int g = 80 + random.nextInt(176);
        int b = 80 + random.nextInt(176);
        return new Color(r, g, b);
    }

    /**
     * Starts the confetti animation
     * Sets the enabled flag to true and starts the timers
     */
    private void startConfetti()
    {
        // Start the confetti animation
        isEnabled = true;
        lastUpdateTime = System.nanoTime();
        timer.start();
    }

    /**
     * Stop the confetti animation
     * Sets the enabled flag to false
     */
    private void stopConfetti()
    {
        // Stop the confetti animation
        isEnabled = false;
    }

    /**
     * Removes the confetti panel from its parent container
     * Stops the animation and triggers a repaint of the parent container
     */
    public void removeConfetti()
    {
        // Stop the confetti animation and remove the panel from its parent container
        isEnabled = false;
        Container parent = getParent();
        if (parent != null) {
            parent.remove(this);
            parent.repaint();
        }
    }

    /**
     * Adds the confetti effect to the specified frame for a given duration
     * @param frame         The frame to which the confetti effect will be added
     * @param duration      The duration in milliseconds for which the confetti effect will be active
     */
    public void addConfettiEffect(JFrame frame, int duration)
    {
        // Add the confetti panel to the frame
        setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.getLayeredPane().add(this, JLayeredPane.POPUP_LAYER);
        addConfetti(100);
        startConfetti();

        // Stop the confetti after the duration
        Timer stopConfettiTimer = new Timer(duration, e -> {
            stopConfetti();
        });

        // Start the timer
        stopConfettiTimer.setRepeats(false);
        stopConfettiTimer.start();
    }

    /**
     * Paints the confetti pieces on the panel
     * @param g     The graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        // Paint the confetti pieces on the panel
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Paint each confetti piece on the panel
        for (Confetti confetti : confettiList)
        {
            // Rotate the confetti piece
            AffineTransform old = g2d.getTransform();
            g2d.rotate(Math.toRadians(confetti.angle), confetti.x + (double) confetti.width / 2, confetti.y + (double) confetti.height / 2);
            g2d.setColor(confetti.colour);
            g2d.fillRect((int) confetti.x, (int) confetti.y, confetti.width, confetti.height);
            g2d.setTransform(old);
        }
    }
}
