package tg.project.TelegramGameBot.service.wordgameutility;


import java.util.Objects;

/**
 * Класс, которые примитивизирует ввод из базы данных и ответа пользователя для более нестрогого принятия ответа,
 * после чего сравнивает слова, выдавая Boolean в качестве ответа.
 * Работает на регулярных выражениях, имеет две функции:
 * WordComparison - сравнение ответов
 * cleanWord - "очистка" слова.
 */
public class WordComparison {
    private final Boolean resultOfComparison;

    public WordComparison(String firstWord, String secondWord)
    {
        // Очистка слов от лишних символов
        String cleanWord1 = cleanWord(firstWord);
        String cleanWord2 = cleanWord(secondWord);

        if (Objects.equals(cleanWord1, cleanWord2))
        {
            this.resultOfComparison = true;
        } else
        {
            this.resultOfComparison = false;
        }
    }
    // Метод для очистки слова от лишних символов
    private static String cleanWord(String word) {
        // Используем регулярное выражение для удаления пробелов и других лишних символов
        String cleanWord = word.replaceAll("[^а-яА-Яa-zA-ZёЁ0-9]", "");

        // Замена "ё" на "е"
        cleanWord = cleanWord.replace("ё", "е");
        cleanWord = cleanWord.replace("Ё", "Е");
        return cleanWord;
    }

    public Boolean getResultOfComparison() {
        return resultOfComparison;
    }
}