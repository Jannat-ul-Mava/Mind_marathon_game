package com.example.mind_marathon_project;

public class UserSession {
    private static UserSession instance;
    private Player currentPlayer;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void clearSession() {
        currentPlayer = null;
    }
}
