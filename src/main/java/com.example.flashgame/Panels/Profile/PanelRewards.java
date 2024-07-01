package com.example.flashgame.Panels.ProfilePage;

import javax.swing.*;
import java.awt.*;

import com.example.flashgame.Extensions.RoundedPanel;
import com.example.flashgame.Extensions.RewardsButton;
import com.example.flashgame.Extensions.StyledButton;
import com.example.flashgame.Extensions.TextLabel;
import com.example.flashgame.Main;
import com.example.flashgame.Panels.Auth.LogIn;
import com.example.flashgame.Panels.Auth.User;
import com.example.flashgame.Panels.Game.UserLevel;

import static com.example.flashgame.VisualSettings.VisualSettings.*;
import static com.example.flashgame.VisualSettings.VisualSettings.getCardHeight;

/**
 * PanelRewards class is used to display the rewards that the user can unlock when achieving a high enough score
 */
public class PanelRewards extends RoundedPanel
{
    private JFrame mainFrame;
    public User currentUser = LogIn.currentUser;
    private UserLevel userLevel;


    public PanelRewards(JFrame mainFrame)
    {
        super(getApplicationCornerRadius(), getCardColour(), getCardWidth(), getCardHeight());
        this.mainFrame = mainFrame;
        this.userLevel = new UserLevel();

        initialisePanel();
    }

    /**
     * Initializes the panel with components
     */
    private void initialisePanel()
    {
        int levelOfUser = userLevel.calculateLevel(userLevel.getScore());

        addComponents
        (
                createTitleLabel(),
                createBackButton(),
                createRewardsButtons(levelOfUser)
        );
    }

    /**
     * Creates the title label
     * @return a TextLabel component with the title "Unlockable Rewards"
     */
    private TextLabel createTitleLabel()
    {
        return new TextLabel
        (
                "Unlockable Rewards",
                36,
                getTextFont(),
                Font.BOLD,
                getTextColour(),
                50, 20,
                800, 50
        );
    }

    /**
     * Creates the back button
     * @return a JButton component that navigates back to the profile page
     */
    private JButton createBackButton()
    {
        JButton backButton = new StyledButton
        (
                "Go Back",
                150, 50,
                375, 325
        );

        backButton.addActionListener(event -> Main.createInteriorCard(mainFrame, new Profile(mainFrame)));
        return backButton;
    }

    /**
     * Creates an array of rewards buttons
     * @param levelOfUser the current level of the user
     * @return an array of RewardsButton components
     */
    private RewardsButton[] createRewardsButtons(int levelOfUser)
    {
        return new RewardsButton[]
        {
                // Row 1
                new RewardsButton(mainFrame, this, "Default Theme", 1, levelOfUser, false, 150, 50, 28, 90, currentUser),
                new RewardsButton(mainFrame, this, "Pirate Theme", 2, levelOfUser, false, 150, 50, 202, 90, currentUser),
                new RewardsButton(mainFrame, this, "Beach Theme", 7, levelOfUser, false, 150, 50, 376, 90, currentUser),
                new RewardsButton(mainFrame, this, "Jungle Theme", 13, levelOfUser, false, 150, 50, 550, 90, currentUser),
                new RewardsButton(mainFrame, this, "Ocean Theme", 20, levelOfUser, false, 150, 50, 724, 90, currentUser),

                // Row 2
                new RewardsButton(mainFrame, this, "Fairy Theme", 28, levelOfUser, false, 150, 50, 28, 153, currentUser),
                new RewardsButton(mainFrame, this, "Robot Theme", 37, levelOfUser, false, 150, 50, 202, 153, currentUser),
                new RewardsButton(mainFrame, this, "Space Theme", 47, levelOfUser, false, 150, 50, 376, 153, currentUser),
                new RewardsButton(mainFrame, this, "Dino Theme", 58, levelOfUser, false, 150, 50, 550, 153, currentUser),
                new RewardsButton(mainFrame, this, "Castle Theme", 70, levelOfUser, false, 150, 50, 724, 153, currentUser),

                // Row 3
                new RewardsButton(mainFrame, this, "Alpine Theme", 83, levelOfUser, false, 150, 50, 28, 216, currentUser),
                new RewardsButton(mainFrame, this, "Princess Theme", 97, levelOfUser, false, 150, 50, 202, 216, currentUser),
                new RewardsButton(mainFrame, this, "Safari Theme", 112, levelOfUser, false, 150, 50, 376, 216, currentUser),
                new RewardsButton(mainFrame, this, "Volcano Theme", 128, levelOfUser, false, 150, 50, 550, 216, currentUser),
                new RewardsButton(mainFrame, this, "Candy Theme", 145, levelOfUser, false, 150, 50, 724, 216, currentUser)
        };
    }

    /**
     * Adds components to the panel
     * @param titleLabel the title label component
     * @param backButton the back button component
     * @param rewardsButtons the array of rewards buttons
     */
    private void addComponents(TextLabel titleLabel, JButton backButton, RewardsButton[] rewardsButtons)
    {
        add(titleLabel);
        add(backButton);

        for (RewardsButton button : rewardsButtons)
        {
            add(button);
        }
    }
}
