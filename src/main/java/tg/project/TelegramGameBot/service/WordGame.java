package tg.project.TelegramGameBot.service;

import java.util.Enumeration;
import java.util.Random;

/**
 * Класс, выбирающий очередной вопрос из набора вопросов(класса WordGameQuestions)
 * String getQuestion() - возвращает вопрос
 * String getResult() - возвращает ответ на вопрос
 */
public class WordGame implements Game
{

    private final String question;
    private final String result;

    public WordGame()
    {

        int min = 0;
        int max = WordGameQuestions.countOfQuestions - 1;

        Random rnd = new Random();

        int positionOfQuestion = min + Math.abs(rnd.nextInt()) % (max - min + 1);

        Enumeration<String> keys = WordGameQuestions.questions.keys();
        String key;
        for (int i = 0; i < positionOfQuestion; ++i)
        {
            keys.nextElement();
        }

        this.question = String.valueOf(keys);

        this.result = WordGameQuestions.questions.get(this.question);
    }

    @Override
    public String getResult() {
        return this.result;
    }

    @Override
    public String getQuestion() {
        return this.question;
    }


}
