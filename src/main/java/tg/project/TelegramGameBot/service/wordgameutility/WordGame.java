package tg.project.TelegramGameBot.service.wordgameutility;

import java.util.*;

import tg.project.TelegramGameBot.service.interfaces.Game;
import tg.project.TelegramGameBot.service.wordgameutility.WordGameQuestions;

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
        List<String> keys = new ArrayList<>(Collections.list(WordGameQuestions.questions.keys()));
        Random rand = new Random();
        int randomIndex;
        do
        {
            randomIndex = rand.nextInt(keys.size());
        } while (Objects.equals(WordGameQuestions.questions.get(keys.get(randomIndex))[1], "0"));

        this.question = keys.get(randomIndex);
        this.result = WordGameQuestions.questions.get(question)[0];
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
