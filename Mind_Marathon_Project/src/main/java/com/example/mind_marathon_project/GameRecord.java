package com.example.mind_marathon_project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameRecord {
    private String mode;        // Easy, Medium, Epic
    private String category;    // Science, Art, Geography, etc.
    private String date;
    private String status;      // Pass or Fail
    private int score;

    public GameRecord(String mode, String category, String date, String status, int score) {
        this.mode = mode;
        this.category = category;
        this.date = date;
        this.status = status;
        this.score = score;
    }

    public GameRecord(String mode, String category, String status, int score) {
        this.mode = mode;
        this.category = category;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm"));
        this.status = status;
        this.score = score;
    }

    public String getMode() {
        return mode;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getScore() {
        return score;
    }

    // Convert to file format
    public String toFileFormat() {
        return mode + "," + category + "," + date + "," + status + "," + score;
    }

    // Parse from file format
    public static GameRecord fromFileFormat(String line) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
            return new GameRecord(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
        }
        return null;
    }

    @Override
    public String toString() {
        return mode + " - " + category + " - " + date + " - " + status + " - Score: " + score;
    }
}
