package com.example.flashgame.Panels.Game;

import com.example.flashgame.Extensions.RewardProgressRing;
import com.example.flashgame.Extensions.RoundedPanel;
import com.example.flashgame.Extensions.StyledButton;
import com.example.flashgame.Extensions.TextLabel;
import com.example.flashgame.Extensions.VFXConfetti;
import com.example.flashgame.Interface.FlashCardDao;
import com.example.flashgame.Panels.Game.TopicFlashCards;
import com.example.flashgame.Panels.LearningMaterial.FlashCard;
import com.example.flashgame.Panels.LearningMaterial.FlashCardDaoImpl;
import com.example.flashgame.Panels.Game.UserLevel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.flashgame.VisualSettings.VisualSettings.*;
import com.example.flashgame.Main;

public class PanelFlashCards extends RoundedPanel
{
    private JFrame mainFrame;
    private List<FlashCard> flashCards;
    private FlashCardDao flashCardDao;
    private int currentCardIndex = 0;
    private int correctAnswersCount = 0;
    private boolean isGameCompleted = false;

    // GUI
    private TextLabel completedLabel;
    private TextLabel progressLabel;
    private TextLabel questionLabel;
    private List<StyledButton> answerButtons = new ArrayList<>();
    private TextLabel resultsLabel;
    private StyledButton playAgainButton;

    // Timer
    private TextLabel timerLabel;
    private Timer timer;
    private int timeRemaining = 10; // Time in seconds
    private boolean inputLocked = false;

    // VFX
    private static VFXConfetti confetti;

    // Score
    private UserLevel userLevel;
    private int scoreOfUser = 0;
    private int scoreObtained = 0;
    private int scoreUpdated = 0;

    public PanelFlashCards(JFrame mainFrame)
    {
        super(getApplicationCornerRadius(), getCardColour(), getCardWidth(), getCardHeight());

        this.mainFrame = mainFrame;

        // Initialise flashcard data and load random flashcards
        flashCardDao = new FlashCardDaoImpl();
        flashCards = flashCardDao.getRandomFlashCards();

        // Initialise UserLevel
        userLevel = new UserLevel();
        scoreOfUser = userLevel.getScore();

        initialisePanel();
    }

    private void initialisePanel()
    {
        createProgressText();
        updateFlashCard();

        //Lock Menu Buttons
        Main.lockButtons(true);

        // If any confetti is active, then remove it
        if (confetti != null)
        {
            confetti.removeConfetti();
            confetti = null;
        }

        // Ensure game is reset
        isGameCompleted = false;
    }

    /**
     * Display completed text and percentage
     */
    private void createProgressText()
    {
        completedLabel = new TextLabel(
                "COMPLETED",
                12,
                Font.BOLD,
                getButtonTextNormalColour(),
                630, 20,
                400, 50);

        progressLabel = new TextLabel(
                "0%",
                22,
                Font.BOLD,
                getButtonTextNormalColour(),
                645, 40,
                400, 50);

        add(completedLabel);
        add(progressLabel);
    }

    /**
     * Display take a break text
     */
    private void createTimeoutText()
    {
        TextLabel timeoutLabel = new TextLabel(
                "Take a break!",
                42,
                getTextFont(),
                Font.BOLD,
                getTextColour(),
                305, 95,
                600, 50);

        add(timeoutLabel);
    }

    /**
     * Display results '# out of # correct' text
     */
    private void createResultsText()
    {
        resultsLabel = new TextLabel(
                "",
                22,
                getTextAlternateFont(),
                Font.BOLD,
                getTextColour(),
                305, 205,
                600, 50);

        add(resultsLabel);
    }

    /**
     * Display time remaining and countdown time text
     */
    private void createTimerText()
    {
        timerLabel = new TextLabel(
                "Time remaining:",
                32,
                getTextAlternateFont(),
                Font.BOLD,
                getTextColour(),
                305, 255,
                600, 50);

        add(timerLabel);
    }

    /**
     * Display ready play to again text
     */
    private void createPlayAgainButton()
    {
        playAgainButton = new StyledButton(
                "Ready to play again?",
                360, 50,
                425, 255);
        playAgainButton.addActionListener(event -> Main.createInteriorCard(mainFrame, new TopicFlashCards(mainFrame)));
        add(playAgainButton);
        playAgainButton.setVisible(false);
    }

    /**
     * Update GUI with question and answer for current flashcard
     */
    private void updateFlashCard()
    {
        clearGUI(false);
        updateProgressLabel();

        if (currentCardIndex < flashCards.size())
        {
            FlashCard currentCard = flashCards.get(currentCardIndex);
            createQuestion(currentCard.getQuestion());
            createAnswers(currentCard);
        }
    }

    /**
     * Create question text from current flashcard
     * @param questionText  The question text retrieved from the database
     */
    private void createQuestion(String questionText)
    {
        questionLabel = new TextLabel(
                questionText,
                18,
                getTextFont(),
                Font.BOLD,
                getTextColour(),
                55, 40,
                800, 50);

        add(questionLabel);
    }

    /**
     * Create answer text from current flashcard
     * @param currentCard  The current flashcard active
     */
    private void createAnswers(FlashCard currentCard)
    {
        List<String> incorrect_answers_list = flashCardDao.getIncorrectAnswer(currentCard);

        // Create a list of answers
        List<String> answers = Arrays.asList(
                currentCard.getCorrectAnswer(),
                incorrect_answers_list.get(0),
                incorrect_answers_list.get(1));

        // Shuffle the answers
        Collections.shuffle(answers);

        // Display the flashcard answer buttons
        int yPosition = 125;
        for (String answer : answers)
        {
            StyledButton answerButton = new StyledButton(
                    answer,
                    600, 50,
                    150, yPosition);
            answerButton.addActionListener(event -> checkAnswer(answer, currentCard.getCorrectAnswer()));
            answerButtons.add(answerButton);
            add(answerButton);
            yPosition += 75;
        }

        // Update the GUI
        revalidate();
        repaint();
    }

    /**
     * Check if the user selected the correct answer
     * @param selectedAnswer    The text of the selected answer
     * @param correctAnswer     The text of the correct answer
     */
    private void checkAnswer(String selectedAnswer, String correctAnswer)
    {
        SwingUtilities.invokeLater(() ->
        {
            // If answer is correct, add it to the correct answer count
            if (selectedAnswer.equals(correctAnswer))
            {
                correctAnswersCount++;
            }

            // Proceed to the next flashcard
            currentCardIndex++;

            // Update the GUI with the next flashcard if more flashcards remaining in this game
            if (currentCardIndex < flashCards.size())
            {
                updateFlashCard();
            }
            else
            {
                completedGame();
            }
        });
    }

    /**
     * Handle game completion
     */
    private void completedGame()
    {
        if (isGameCompleted)
            return;

        clearGUI(true);
        createTimeoutText();
        createResultsText();
        createTimerText();
        createPlayAgainButton();

        updateResultsLabel();

        // Calculate and show score
        int scoreOfUser = userLevel.getScore();
        int levelOfUser = userLevel.calculateLevel(scoreOfUser);
        calculateScore();
        boolean isScoreAMilestone = userLevel.isScoreAMilestone(scoreOfUser, scoreUpdated);

        // Add profile level progress ring to GUI
        addProgressRing(this, isScoreAMilestone, scoreOfUser, levelOfUser);

        startTimer();

        // Add confetti VFX
        if (confetti == null)
        {
            confetti = new VFXConfetti();
            confetti.addConfettiEffect(mainFrame, 100);
        }

        // Update the GUI
        revalidate();
        repaint();

        // Unlock Menu Buttons
        Main.lockButtons(false);

        // Ensure completedGame is only called once
        isGameCompleted = true;
    }

    /**
     * Update the progress label
     */
    private void updateProgressLabel()
    {
        int progressPercentage = 0;

        if (flashCards.size() != 0)
        {
            progressPercentage = (currentCardIndex * 100) / flashCards.size();
        }

        progressLabel.setText(progressPercentage + "%");
    }

    /**
     * Update the results label with the users score
     */
    private void updateResultsLabel()
    {
        resultsLabel.setText("You got " + correctAnswersCount + " out of " + flashCards.size() + " answers correct.");
    }

    /**
     * Clear GUI components
     * @param clearProgressLabel    Boolean to clear game completion GUI labels
     */
    private void clearGUI(boolean clearProgressLabel)
    {
        if (questionLabel != null)
        {
            remove(questionLabel);
        }

        for (StyledButton button: answerButtons)
        {
            remove(button);
        }

        answerButtons.clear();

        if (clearProgressLabel)
        {
            remove(completedLabel);
            remove(progressLabel);
        }
    }

    /**
     * Start the 'take a break' countdown timer
     */
    private void startTimer() {
        if (timer != null)
        {
            timer.cancel();
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (timeRemaining > 0) {
                        timeRemaining--;
                        timerLabel.setText("Time remaining: " + timeRemaining);
                        lockInput(true);
                    } else {
                        timer.cancel();
                        timerLabel.setText("");
                        lockInput(false);

                        playAgainButton.setVisible(true);
                    }
                });
            }
        }, 0, timeRemaining * 100L);
    }

    /**
     * Lock or unlock GUI titlebar buttons
     * @param lock  GUI Buttons locked if true
     */
    private void lockInput(boolean lock)
    {
        inputLocked = lock;
        Main.lockButtons(inputLocked);
    }

    /**
     * Add profile level progress ring to the panel
     * @param panel                 The current panel
     * @param isScoreAMilestone     True if the score triggers a level up
     * @param scoreOfUser           The current score of the user
     * @param levelOfUser           The current level of the user
     */
    private void addProgressRing(JPanel panel, boolean isScoreAMilestone, int scoreOfUser, int levelOfUser)
    {
        RewardProgressRing progressRing = new RewardProgressRing(isScoreAMilestone, scoreOfUser, levelOfUser);
        progressRing.setPreferredSize(new Dimension(200, 200));

        JPanel progressContainer = new JPanel();
        progressContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 80, 0, 0);
        progressContainer.add(progressRing, gbc);
        progressContainer.setOpaque(false);

        panel.setLayout(new BorderLayout());
        panel.add(progressContainer, BorderLayout.WEST);

        // Animate the bar showing the user score inside the progress ring
        progressRing.animateProgress(scoreOfUser, scoreUpdated, 25);
    }

    /**
     * Calculate the users score
     */
    private void calculateScore()
    {
        // Ensure new score is equal to current score
        if (correctAnswersCount <= 0)
        {
            scoreUpdated = scoreOfUser;
            return;
        }

        // Current users score
        scoreOfUser = userLevel.getScore();

        // Score obtained from correct answers
        scoreObtained = userLevel.calculateScore(correctAnswersCount);

        // Users updated score
        scoreUpdated = scoreOfUser + scoreObtained;

        // Determine whether a level up occurred
        boolean milestone = userLevel.isScoreAMilestone(scoreOfUser, scoreUpdated);

        // Cap the score to the nearest milestone value if a milestone is achieved
        if (milestone)
        {
            scoreUpdated = (scoreUpdated / 100) * 100;
        }

        // Set the users new score
        userLevel.setScore(scoreUpdated);
    }
}
