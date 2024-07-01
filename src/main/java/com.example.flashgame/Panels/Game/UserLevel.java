package com.example.flashgame.Panels.Game;

import com.example.flashgame.Interface.UserDao;
import com.example.flashgame.Panels.Auth.LogIn;
import com.example.flashgame.Panels.Auth.User;

public class UserLevel
{
    int pointsPerQuestion = 10;
    int pointsForMemoryMatch = 2;

    private User getCurrentUser()
    {
        return LogIn.currentUser;
    }
    private UserDao getCurrentUserDao() { return LogIn.userDao; }

    /**
     * Gets the users score
     * @return          The score being retrieved
     */
    public int getScore() {
        return Integer.parseInt(getCurrentUserDao().getScore(getCurrentUser().getId()));
    }

    /**
     * Set the users score
     * @param score The score being recorded
     */
    public void setScore(int score) {
        getCurrentUserDao().setScore(getCurrentUser().getId(), String.valueOf(score));
    }

    /**
     * Convert the number of questions answered correctly into a user score
     * @param numberOfQuestionsCorrect  The number of questions answered correctly
     * @return      The calculated user score for the answered questions
     */
    public int calculateScore(int numberOfQuestionsCorrect)
    {
        return numberOfQuestionsCorrect * pointsPerQuestion;
    }

    /**
     * Convert the difficulty of the memory match game into a user score
     * @param difficulty The difficulty the user set
     * @return      The calculated user score for completing the memory match
     */
    public int calculateScoreMemoryMatch(int difficulty) {return difficulty * pointsForMemoryMatch; }

    /**
     * Convert score to a user level
     * @param score     Current user score
     * @return          The current user level
     */
    public int calculateLevel(int score)
    {
        return (score / 100) + 1;
    }

    /**
     * If the user score crosses a threshold, it is considered a milestone for a level up
     * Milestones occur every 100 points
     * @param startScore    The users current score
     * @param endScore      The users score after answering correct answers
     * @return              Returns whether a level up milestone has occurred
     */
    public boolean isScoreAMilestone(int startScore, int endScore)
    {
        int startMilestone = (int) Math.floor(startScore / 100.0);
        int endMilestone = (int) Math.floor(endScore / 100.0);

        return startMilestone != endMilestone;
    }
}
