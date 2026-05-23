package com.example.mind_marathon_project;

public class Question {
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String hint;
    private String correctAnswer;
    private String difficulty;

    public Question(String questionText, String option1, String option2, String option3,
                    String option4, String hint, String correctAnswer, String difficulty) {
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.hint = hint;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    // Getters
    public String getQuestionText() { return questionText; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public String getOption3() { return option3; }
    public String getOption4() { return option4; }
    public String getHint() { return hint; }
    public String getCorrectAnswer() { return correctAnswer; }
    public String getDifficulty() { return difficulty; }
}
