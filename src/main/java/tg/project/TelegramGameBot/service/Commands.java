package tg.project.TelegramGameBot.service;
import java.io.*;

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
                            /result - посмотреть количество Ваших правильных ответов
                            /report текст вопроса - пожаловаться на вопрос
                            """;
                res[1] = null;
                break;
            case "/result":
                res[0] = "количество правильных ответов: " + user.getCorrectAnswers() + " из " + user.getNumberOfQuestions();
                res[1] = null;
                break;
            case "/report":
                res[0] = "приносим свои извинения, обязательно проверим вопрос";
                res[1] = null;
                StringBuilder text = new StringBuilder();
                for (int i = 1; i < command.length; ++i)
                    text.append(command[i]).append(' ');
                try(FileWriter writer = new FileWriter("Жалобы.txt", false))
                {
                    writer.write(String.valueOf(text) + '\n');

                    writer.flush();
                }
                catch(IOException ex){

                    System.out.println(ex.getMessage());
                }
                break;
            default:
                res[0] = "Неверная команда";
                res[1] = null;
                break;
        }
        return res;
    }
}
