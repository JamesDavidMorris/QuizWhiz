package com.example.flashgame;


import com.example.flashgame.Extensions.RoundedPanel;
import com.example.flashgame.Extensions.StyledButton;
import com.example.flashgame.Extensions.TextLabel;
import com.example.flashgame.Interface.FlashCardDao;
import com.example.flashgame.Interface.UserDao;
import com.example.flashgame.Panels.Auth.LogIn;
import com.example.flashgame.Panels.MainMenu.PanelWatermark;
import com.example.flashgame.Panels.ProfilePage.Profile;
import com.example.flashgame.Panels.Auth.User;
import com.example.flashgame.Panels.Auth.UserDaoImpl;
import com.example.flashgame.Panels.Game.PanelViewGames;
import com.example.flashgame.Panels.LearningMaterial.DataEntry;
import com.example.flashgame.Panels.LearningMaterial.FlashCardDaoImpl;
import com.example.flashgame.Panels.MainMenu.PanelMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.ArrayList;

import static com.example.flashgame.Panels.Auth.LogIn.currentUser;
import static com.example.flashgame.VisualSettings.VisualSettings.*;
public class Main {
    public static JFrame frame;

    private static int mouseX = 0;
    private static int mouseY = 0;
    private static RoundedPanel gradientFrame;
    private static JPanel currentPanel = null;
    private static JPanel currentContainer = null;

    // Drop shadow
    private static JPanel dropShadow = null;

    // Game timeout locking
    private static List<StyledButton> buttonsToLock = new ArrayList<>();

    public static void main(String[] args)
    {
        initialiseApplication();
    }

    /**
     * Create the application frame with the necessary settings to display in the desired size,
     * colour and with rounded corners
     */
    private static void initialiseApplication()
    {
        // Create frame
        frame = new JFrame("Study helper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set application size
        frame.setSize(getApplicationWidth(), getApplicationHeight());

        // Enable undecorated in order to allow rounded corners
        frame.setUndecorated(true);

        // Set application size with rounded corners
        frame.setShape(new RoundRectangle2D.Double(0, 0, getApplicationWidth(), getApplicationHeight(), getApplicationCornerRadius()[0], getApplicationCornerRadius()[0]));

        // Overlay gradient background over application
        gradientFrame = new RoundedPanel(
                getApplicationCornerRadius(),
                getApplicationGradientStartColour(),
                getApplicationGradientEndColour(),
                getApplicationGradientPositions(),
                getApplicationWidth(),
                getApplicationHeight());

        // Add watermark to gradient background panel
        PanelWatermark panelWatermark = new PanelWatermark();
        panelWatermark.setBounds(0, 0, getApplicationWidth(), getApplicationHeight());
        gradientFrame.add(panelWatermark);

        // Add gradient background panel to application frame
        frame.setContentPane(gradientFrame);

        // Create title bar
        createApplicationTitleBar(frame);

        // Add the card which will contain changing application pages defaulting to the main menu
        createInteriorCard(frame, new LogIn(frame));

        // Make the window draggable
        addMouseListeners(frame);

        // Display the application
        frame.setVisible(true);
    }

    /**
     * Create the top bar in which the primary buttons will be shown
     * @param frame     The primary frame of the application
     */
    public static void createApplicationTitleBar(JFrame frame)
    {
        frame.setLayout(new BorderLayout());

        // Set title bar
        JPanel titleBar = new RoundedPanel(getTitleBarCornerRadius(), getTitleBarColour(), getTitleBarWidth(), getTitleBarHeight());
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), getTitleBarHeight()));
        titleBar.setLayout(null); // Set layout to null allows button x & y positioning
        titleBar.setOpaque(false);

        // Create a drop shadow below the title bar
        JLayeredPane layeredPane = createApplicationTitleBarDropShadow(frame, titleBar);

        // Add title bar to the application
        frame.add(layeredPane, BorderLayout.NORTH);

        // Create and add buttons to the title bar
        createTitleBarButtons(frame, titleBar);
    }

    /**
     * Creates a drop shadow for the application title bar and returns a JLayeredPane containing both
     * This method handles the creation of a drop shadow panel, positions it and the title bar, and adds them to a JLayeredPane
     * @param frame         The main application frame
     * @param titleBar      The title bar JPanel to be displayed with a drop shadow
     * @return              A JLayeredPane containing the title bar and its drop shadow
     */
    private static JLayeredPane createApplicationTitleBarDropShadow(JFrame frame, JPanel titleBar)
    {
        int panelWidth = getTitleBarWidth();
        int panelHeight = getTitleBarHeight();
        int shadowOffset = getDropShadowOffset();

        // Create drop shadow panel
        JPanel shadowPanel = new RoundedPanel(getTitleBarCornerRadius(), getDropShadowColour(), panelWidth, panelHeight);
        shadowPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        shadowPanel.setOpaque(false);

        // Create a container to hold the drop shadow panel and offset it
        JPanel container = new JPanel(null);
        container.setPreferredSize(new Dimension(panelWidth, panelHeight + shadowOffset));
        container.setOpaque(false);
        container.add(shadowPanel);

        // Position the drop shadow panel vertically offset
        shadowPanel.setBounds(0, shadowOffset, panelWidth, panelHeight);

        // Create a layered pane to hold both the drop shadow and the title bar
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(frame.getWidth(), getTitleBarHeight() + getDropShadowOffset()));

        // Position the drop shadow and title bar
        container.setBounds(0, 0, frame.getWidth(), getTitleBarHeight() + getDropShadowOffset());
        titleBar.setBounds(0, 0, frame.getWidth(), getTitleBarHeight());

        // Add the drop shadow and title bar to the layered pane
        layeredPane.add(container, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(titleBar, JLayeredPane.PALETTE_LAYER);

        return layeredPane;
    }

    /**
     * Create the buttons which are displayed within the top bar
     * @param frame         The primary frame of the application
     * @param titleBar      The top bar displaying the buttons
     */
    private static void createTitleBarButtons(JFrame frame, JPanel titleBar)
    {
        // Create close button
        StyledButton closeButton = new StyledButton(
                "X", // Button text
                25, 25, // Button width & height
                925, 25); // Button x & y position
        closeButton.addActionListener(event -> System.exit(0));

        // Create profile button
        StyledButton profileButton = new StyledButton(
                "View profile", // Button text
                100, 50, // Button width & height
                800, 25); // Button x & y position
        profileButton.addActionListener(event -> createInteriorCard(frame, new Profile(frame)));

        // Create menu button
        StyledButton menuButton = new StyledButton(
                "Main menu", // Button text
                150, 50, // Button width & height
                50, 25); // Button x & y position
        menuButton.addActionListener(event -> createInteriorCard(frame, new PanelMenu(frame)));

        // Create view games button
        StyledButton viewGamesButton = new StyledButton(
                "View games", // Button text
                150, 50, // Button width & height
                250, 25); // Button x & y position
        viewGamesButton.addActionListener(event -> createInteriorCard(frame, new PanelViewGames(frame)));

        // Create data entry button
        StyledButton dataEntryButton = new StyledButton(
                "Enter learning material", // Button text
                150, 50, // Button width & height
                450, 25); // Button x & y position
        dataEntryButton.addActionListener(event -> createInteriorCard(frame, new DataEntry(currentUser)));

        // Add buttons to the title bar
        titleBar.add(closeButton);
        titleBar.add(profileButton);
        titleBar.add(menuButton);
        titleBar.add(viewGamesButton);
        titleBar.add(dataEntryButton);

        // Store buttons for locking/unlocking
        buttonsToLock.add(profileButton);
        buttonsToLock.add(menuButton);
        buttonsToLock.add(viewGamesButton);
        buttonsToLock.add(dataEntryButton);
    }

    /**
     * A streamlined call to unload any loaded panel, then load the specified panel
     * @param frame     The primary frame of the application
     * @param panel     The panel displaying the content of each card
     */
    public static void createInteriorCard(JFrame frame, JPanel panel)
    {
        // Check if there is an active panel and remove it
        if (currentContainer != null)
        {
            frame.getContentPane().remove(currentContainer);
        }

        // Set the active panel to the selected panel
        currentPanel = panel;
        currentPanel.setOpaque(false);

        // Create a container for the drop shadow and selected panel
        currentContainer = createContainerWithShadow(panel);

        // Add the card to the application
        frame.getContentPane().add(currentContainer, BorderLayout.CENTER);

        // Ensure the application updates to show the change
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }

    /**
     * Creates a container with a drop shadow and the specified panel
     * This method handles the creation of a JLayeredPane that includes a drop shadow behind the provided panel
     * It ensures that the panel is positioned correctly with the drop shadow offset
     * @param panel     The JPanel to be displayed with a drop shadow
     * @return          A JPanel containing the layeredPane with the drop shadow and the specified panel
     */
    private static JPanel createContainerWithShadow(JPanel panel)
    {
        // Dimensions of the frame and panel for positioning
        int panelWidth = panel.getPreferredSize().width;
        int panelHeight = panel.getPreferredSize().height;
        int shadowOffset = getDropShadowOffset();

        // Create a container for the drop shadow and selected panel
        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);
        container.setPreferredSize(new Dimension(panelWidth + shadowOffset, panelHeight + shadowOffset));

        // Create a layered pane to hold both drop shadow and current
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(panelWidth + shadowOffset, panelHeight + shadowOffset));

        // Add panel drop shadow
        if (dropShadow == null)
        {
            dropShadow = new RoundedPanel(getApplicationCornerRadius(), getDropShadowColour(), panelWidth, panelHeight);
            dropShadow.setOpaque(false);
        }

        // Position the drop shadow and current panel
        dropShadow.setBounds(shadowOffset, shadowOffset, panelWidth, panelHeight);
        panel.setBounds(0, 0, panelWidth, panelHeight);

        // Add the drop shadow and current panel to the layered pane
        layeredPane.add(dropShadow, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);

        // Add the layered pane to the container with GridBagConstraints
        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.insets = new Insets(0, shadowOffset, shadowOffset, 0);
        gridBag.anchor = GridBagConstraints.CENTER;
        container.add(layeredPane, gridBag);

        return container;
    }

    /**
     * Add mouse listeners to allow the dragging of the application
     * @param frame     The primary frame of the application
     */
    private static void addMouseListeners(JFrame frame)
    {
        // Record the mouse position at the start of the drag
        frame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent event)
            {
                mouseX = event.getX();
                mouseY = event.getY();
            }
        });

        // Calculate and set the new position of the window
        frame.addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent event)
            {
                int x = event.getXOnScreen();
                int y = event.getYOnScreen();

                frame.setLocation(x - mouseX, y - mouseY);
            }
        });
    }

    /**
     * Locks or unlocks all the buttons in the buttonsToLock list.
     * When the buttons are locked, they cannot be interacted with.
     *
     * @param lock if true, all buttons will be locked (disabled); if false, all buttons will be unlocked (enabled)
     */
    public static void lockButtons(boolean lock)
    {
        for (StyledButton button : buttonsToLock)
        {
            button.setEnabled(!lock);
        }
    }

    /**
     * Clear and recreate title bar and current panel
     */
    public static void refreshUI(JPanel panel)
    {
        frame.getContentPane().removeAll();
        createApplicationTitleBar(frame);

        if (currentPanel != null)
        {
            createInteriorCard(frame, panel);
        }

        // Update gradient background colors
        gradientFrame.updateGradientColors(getApplicationGradientStartColour(), getApplicationGradientEndColour());

        // Ensure buttons are unlocked
        lockButtons(false);

        frame.validate();
        frame.repaint();
    }
}