package tg.project.TelegramGameBot.service;

import java.util.*;

import tg.project.TelegramGameBot.service.interfaces.Game;

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
        /*
        int min = 0;
        int max = WordGameQuestions.countOfQuestions - 1;

        Random rnd = new Random();

        int positionOfQuestion = min + Math.abs(rnd.nextInt()) % (max - min + 1);

        Enumeration<String> keys = WordGameQuestions.questions.keys();
        String key = keys.nextElement();

        for (int i = 0; i < positionOfQuestion; ++i)
        {
            key = keys.nextElement();
        }
        */

        // Создайте список ключей из словаря
        List<String> keys = new ArrayList<>(Collections.list(WordGameQuestions.questions.keys()));

        // Сгенерируйте случайный индекс
        Random rand = new Random();
        int randomIndex = rand.nextInt(keys.size());



        this.question = keys.get(randomIndex);

        this.result = WordGameQuestions.questions.get(question);
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
