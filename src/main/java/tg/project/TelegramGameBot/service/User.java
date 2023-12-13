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
    private int correctAnswersTrivia;
    private int correctAnswersMathGame;
    private int allNumberOfQuestions;
    private int allAnswersTrivia;
    private int allAnswersMathGame;


    public User(String name, int age)
    {
        this.name = name;
        this.age = age;

        this.correctAnswers = 0;
        this.correctAnswersTrivia = 0;
        this.correctAnswersMathGame = 0;

        this.allNumberOfQuestions = 0;
        this.allAnswersTrivia = 0;
        this.allAnswersMathGame = 0;
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
    public void increaseCorrectAnswersTrivia()
    {
        correctAnswersTrivia++;
    }
    public void increaseCorrectAnswersMathGame()
    {
        correctAnswersMathGame++;
    }



    public void increaseNumberOfQuestions()
    {
        allNumberOfQuestions++;
    }
    public void increaseNumberOfQuestionsTrivia()
    {
        allAnswersTrivia++;
    }
    public void increaseNumberOfQuestionsMathGame()
    {
        allAnswersMathGame++;
    }




    public int getCorrectAnswers() {
        return correctAnswers;
    }
    public int getCorrectAnswersTrivia() {
        return correctAnswersTrivia;
    }
    public int getCorrectAnswersMathGame() {
        return correctAnswersMathGame;
    }



    public int getNumberOfQuestions() {
        return allNumberOfQuestions;
    }

    public int getAllAnswersTrivia() {
        return allAnswersTrivia;
    }

    public int getAllAnswersMathGame() {return allAnswersMathGame;}
}
