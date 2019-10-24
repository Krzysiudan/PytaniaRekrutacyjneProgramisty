package com.krzysiudan.pytaniarekrutacyjne;

public class Category {
    private String name;
    private int allAnswers;
    private int knownAnswers;
    private int questionsToAnswer;

    public Category(String name,int allAnswers, int knownAnswers){
        this.name = name;
        this.allAnswers = allAnswers;
        this.knownAnswers = knownAnswers;
        this.questionsToAnswer = allAnswers-knownAnswers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAllAnswers() {
        return allAnswers;
    }

    public void setAllAnswers(int allAnswers) {
        this.allAnswers = allAnswers;
    }

    public int getKnownAnswers() {
        return knownAnswers;
    }

    public void setKnownAnswers(int knownAnswers) {
        this.knownAnswers = knownAnswers;
    }
}
