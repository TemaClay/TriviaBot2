package tg.project.TelegramGameBot.service;

/**
 * Класс пользователя, в котором хранится его имя, возраст, а также количество правильных ответов
 * из общего количества ответов
 */
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

    public void increaseNumberOfQuestions()
    {
        numberOfQuestion++;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestion;
    }
}
