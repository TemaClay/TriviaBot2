package org.example;

public class User
{
    private String name;

    private int age;

    private int correctAnswers;

    private int numberOfQuestion;

    public User(String name, int age)
    {
        this.name = name;
        this.age = age;
        this.correctAnswers = 0;
        this.numberOfQuestion = 0;
    }

    public String getName()
    {
        return this.name;
    }

    public int getAge()
    {
        return this.age;
    }

    public void increaseCorrectAnswers()
    {
        correctAnswers++;
    }

    public void increaseNumberOfQuestion()
    {
        numberOfQuestion++;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }
}
