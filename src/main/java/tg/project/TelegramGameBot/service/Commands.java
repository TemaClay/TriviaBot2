package tg.project.TelegramGameBot.service;

/**
 * Класс, реализующий функцию анализа команд, отправленной пользователем
 */
public class Commands
{


    public String[] getCommand(String[] command, User user)
    {
        String[] res = new String[2];
        switch (command[0]) {
            case "/help":
                res[0] = """
                            Бот предствляет собой тривиальную игру, где нужно сосчитать результат предложенного примера
                            /result - посмотреть количество Ваших правильных ответовю.
                            """;
                res[1] = null;
                break;
            case "/result":
                res[0] = "количество правильных ответов: " + user.getCorrectAnswers() + " из " + user.getNumberOfQuestions();
                res[1] = null;
                break;
            default:
                res[0] = "Неверная команда";
                res[1] = null;
                break;
        }
        return res;
    }
}
