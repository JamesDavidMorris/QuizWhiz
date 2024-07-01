package com.example.flashgame.Extensions;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

import static com.example.flashgame.VisualSettings.VisualSettings.getPopUpCornerRadius;

public class RoundedPanel extends JPanel
{
    private final Color backgroundColour; // Background colour of the panel
    private final int[] cornerRadius; // Corner radius of the panel
    private final Dimension size; // Size of the panel

    // Gradient
    private Color gradientStartColour; // Start colour of the gradient
    private Color gradientEndColour; // End colour of the gradient
    private int[] gradientPositions; // Positions of the gradient

    /**
     * Creates a new rounded panel with definable corner radii and a solid background colour
     * @param radius        the radius of each corner of the panel (top-left, top-right, bottom-right, bottom-left)
     * @param bgColour      the background colour of the panel
     * @param width         the width of the panel
     * @param height        the height of the panel
     */
    public RoundedPanel(int[] radius, Color bgColour, int width, int height)
    {
        super();

        // Set corner radius and background colour
        // The radius corner is twice as round compared to the main JFrame, so divide it by 2
        cornerRadius = new int[]{radius[0] / 2, radius[1] / 2, radius[2] / 2, radius[3] / 2};
        backgroundColour = bgColour;

        // Set the panel size
        size = new Dimension(width, height);
        setPreferredSize(size);

        // Allow button x & y positioning
        setLayout(null);

        // The panel must be set transparent to draw rounded corners
        setOpaque(false);
    }

    /**
     * Creates a new rounded panel with definable corner radii and a gradient background colour
     * @param radius                the radius of each corner of the panel (top-left, top-right, bottom-right, bottom-left)
     * @param gradientStartColour   the start colour of the gradient
     * @param gradientEndColour     the end colour of the gradient
     * @param gradientPositions     an array of integers specifying the positions of the gradient with the first colour
     *                              starting at [0],[1] and ending at [2],[3]
     * @param width                 the width of the panel
     * @param height                the height of the panel
     */
    public RoundedPanel(int[] radius, Color gradientStartColour, Color gradientEndColour, int[] gradientPositions, int width, int height)
    {
        super();

        // Set corner radius
        // The radius corner is twice as round compared to the main JFrame, so divide it by 2
        cornerRadius = new int[]{radius[0] / 2, radius[1] / 2, radius[2] / 2, radius[3] / 2};
        backgroundColour = gradientStartColour;

        // Set the panel size
        size = new Dimension(width, height);
        setPreferredSize(size);

        // Allow button x & y positioning
        setLayout(null);

        // The panel must be set transparent to draw rounded corners
        setOpaque(false);

        // Set gradient
        this.gradientStartColour = gradientStartColour;
        this.gradientEndColour = gradientEndColour;
        this.gradientPositions = gradientPositions;
    }

    /**
     * Overrides the default paintComponent in order to allow both the background
     * and each corner radius to be set uniquely
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Cast the graphics to 2D
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the main panel with the gradient if set or the background colour if not
        if (gradientStartColour != null && gradientEndColour != null && gradientPositions != null)
        {
            GradientPaint gradientPaint = new GradientPaint(
                    gradientPositions[0], gradientPositions[0], gradientStartColour,
                    gradientPositions[3], gradientPositions[3], gradientEndColour);

            graphics.setPaint(gradientPaint);
        }
        else
        {
            if (backgroundColour != null)
            {
                graphics.setColor(backgroundColour);
            }
            else
            {
                graphics.setColor(getBackground());
            }
        }

        // Create the rounded path
        Path2D.Float panelPath = createRoundedPath(0);
        graphics.fill(panelPath);
    }

    /**
     * Update the gradient colors for the panel
     * @param startColor The starting color of the gradient
     * @param endColor   The ending color of the gradient
     */
    public void updateGradientColors(Color startColor, Color endColor)
    {
        // Update the gradient colours
        this.gradientStartColour = startColor;
        this.gradientEndColour = endColor;
        repaint();
    }

    /**
     * Create a rounded path for the panel with specified corner radius offsets
     * @param offset The offset for the corner radius
     * @return The rounded path as a Path2D.Float object
     */
    private Path2D.Float createRoundedPath(int offset)
    {
        // Create the rounded path
        Path2D.Float path = new Path2D.Float();
        path.moveTo(offset, cornerRadius[0] + offset);

        // Top-left corner
        path.quadTo(offset, offset, cornerRadius[0] + offset, offset);
        path.lineTo(getWidth() - cornerRadius[1] - offset, offset);

        // Top-right corner
        path.quadTo(getWidth() - offset, offset, getWidth() - offset, cornerRadius[1] + offset);
        path.lineTo(getWidth() - offset, getHeight() - cornerRadius[2] - offset);

        // Bottom-right corner
        path.quadTo(getWidth() - offset, getHeight() - offset, getWidth() - cornerRadius[2] - offset, getHeight() - offset);
        path.lineTo(cornerRadius[3] + offset, getHeight() - offset);

        // Bottom-left corner
        path.quadTo(offset, getHeight() - offset, offset, getHeight() - cornerRadius[3] - offset);
        path.closePath();

        return path;
    }
}
