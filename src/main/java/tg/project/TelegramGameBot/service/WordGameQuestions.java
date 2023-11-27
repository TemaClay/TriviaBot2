package tg.project.TelegramGameBot.service;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Вопросы на слова
 * Класс, содержащий набор вопросов - ответов в виде словаря
 * countOfQuestions - количество наших вопросов
 * questions - сам словарь вопросов
 */
public class WordGameQuestions
{
    public static int countOfQuestions;

    public static final Dictionary<String, String> questions = new Hashtable<>();

    static
    {
        questions.put("Верхний цвет флага России", "белый");
        questions.put("Средний цвет флага России", "синий");
        questions.put("Нижний цвет флага России", "красный");
        countOfQuestions = questions.size();
    }

}
