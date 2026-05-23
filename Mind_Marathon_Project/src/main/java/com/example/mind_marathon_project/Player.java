package com.example.mind_marathon_project;

import java.time.LocalDateTime;

public class Player {
    private String name;
    private int age;
    private String password;
    private String pin;
    private int coins;
    private int lives;
    private int totalScore;
    private int hints;
    private LocalDateTime lastLifeRegenTime;
    private String profileImagePath;

    public Player(String name, int age, String password, String pin) {
        this.name = name;
        this.age = age;
        this.password = password;
        this.pin = pin;
        this.coins = 10;
        this.lives = 3;
        this.totalScore = 0;
        this.hints = 0;
        this.lastLifeRegenTime = LocalDateTime.now();
        this.profileImagePath = null;
    }

    public Player(String name, int age, String password, String pin, int coins, int lives) {
        this.name = name;
        this.age = age;
        this.password = password;
        this.pin = pin;
        this.coins = coins;
        this.lives = lives;
        this.totalScore = 0;
        this.hints = 0;
        this.lastLifeRegenTime = LocalDateTime.now();
        this.profileImagePath = null;
    }

    public Player(String name, int age, String password, String pin, int coins, int lives, int totalScore, int hints, LocalDateTime lastLifeRegenTime, String profileImagePath) {
        this.name = name;
        this.age = age;
        this.password = password;
        this.pin = pin;
        this.coins = coins;
        this.lives = Math.min(lives, 5); // Max 5 lives
        this.totalScore = totalScore;
        this.hints = hints;
        this.lastLifeRegenTime = lastLifeRegenTime != null ? lastLifeRegenTime : LocalDateTime.now();
        this.profileImagePath = profileImagePath;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getPassword() { return password; }
    public String getPin() { return pin; }
    public int getCoins() { return coins; }
    public int getLives() { return lives; }
    public int getTotalScore() { return totalScore; }
    public int getHints() { return hints; }
    public LocalDateTime getLastLifeRegenTime() { return lastLifeRegenTime; }
    public String getProfileImagePath() { return profileImagePath; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setPassword(String password) { this.password = password; }
    public void setPin(String pin) { this.pin = pin; }
    public void setCoins(int coins) { this.coins = coins; }
    public void setLives(int lives) { this.lives = Math.min(lives, 5); } // Max 5 lives
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }
    public void setHints(int hints) { this.hints = hints; }
    public void setLastLifeRegenTime(LocalDateTime lastLifeRegenTime) { this.lastLifeRegenTime = lastLifeRegenTime; }
    public void setProfileImagePath(String profileImagePath) { this.profileImagePath = profileImagePath; }

    // Game methods
    public void addCoins(int amount) {
        this.coins += amount;
    }

    public void subtractCoins(int amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
        }
    }

    public void addLife() {
        if (this.lives < 5) {
            this.lives++;
        }
    }

    public void loseLife() {
        if (this.lives > 0) {
            this.lives--;
        }
    }

    public void addScore(int score) {
        this.totalScore += score;
    }

    public void addHint() {
        this.hints++;
    }

    public void useHint() {
        if (this.hints > 0) {
            this.hints--;
        }
    }

    // Life regeneration logic - CHANGED TO 3 MINUTES
    public void regenerateLives() {
        if (lives >= 5) {
            return; // Already at max
        }

        LocalDateTime now = LocalDateTime.now();
        long minutesPassed = java.time.Duration.between(lastLifeRegenTime, now).toMinutes();

        // Changed from 5 minutes to 3 minutes
        if (minutesPassed >= 3) {
            int livesToAdd = (int) (minutesPassed / 3); // Divide by 3 instead of 5
            int newLives = Math.min(lives + livesToAdd, 5);

            if (newLives > lives) {
                lives = newLives;
                // Update last regen time to account for lives added
                lastLifeRegenTime = lastLifeRegenTime.plusMinutes(livesToAdd * 3); // Multiply by 3 instead of 5
            }
        }
    }

    // Get minutes until next life - CHANGED TO 3 MINUTES
    public long getMinutesUntilNextLife() {
        if (lives >= 5) {
            return 0;
        }

        LocalDateTime now = LocalDateTime.now();
        long minutesPassed = java.time.Duration.between(lastLifeRegenTime, now).toMinutes();
        return 3 - (minutesPassed % 3); // Changed from 5 to 3
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", coins=" + coins +
                ", lives=" + lives +
                ", hints=" + hints +
                '}';
    }

    public String toFileFormat() {
        return "Name:" + name + "\n" +
                "Password:" + password + "\n" +
                "PIN:" + pin + "\n" +
                "Age:" + age + "\n" +
                "Coins:" + coins + "\n" +
                "Lives:" + lives + "\n" +
                "TotalScore:" + totalScore + "\n" +
                "Hints:" + hints + "\n" +
                "LastLifeRegen:" + (lastLifeRegenTime != null ? lastLifeRegenTime.toString() : LocalDateTime.now().toString()) + "\n" +
                "ProfileImage:" + (profileImagePath != null ? profileImagePath : "") + "\n" +
                "---\n";
    }
}