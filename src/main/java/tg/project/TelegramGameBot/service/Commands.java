package tg.project.TelegramGameBot.service;
import java.io.*;

/**
 * Класс, реализующий функцию анализа команд, отправленной пользователем
 */
public class Commands
{


    public Object[] getCommand(String[] command, User user)
    {
        Object[] res = new Object[2];
        switch (command[0]) {
            case "/exit":
                res[0] = """
                        До скорых встреч!
                        """;
                res[1] = TGHandler.commandAction.COMMAND_EXIT;
                break;
            case "/help":
                res[0] = """
                        Бот предствляет собой игру, где нужно правильно отвечать на\s
                        математические вопросы или на вопросы, проверяющие эрудицию.
                        /result - посмотреть количество Вашу статистику.
                        /report <полный текст вопроса> - пожаловаться на вопрос
                        /change - поменять тип игры. После ответа не предыдущий вопрос у Вас появится окошко, где Вы сможете выбрать интересующую Вас игру.
                        /exit - выход из бота. Внимание: Ваша статистика в /result будет удалена!
                        """;
                res[1] = TGHandler.commandAction.COMMAND_DEFAULT;
                break;
            case "/result":
                res[0] = "количество правильных ответов: " + user.getCorrectAnswers() + " из " + user.getNumberOfQuestions();
                res[1] = TGHandler.commandAction.COMMAND_DEFAULT;
                break;
            case "/report":
                res[0] = "приносим свои извинения, обязательно проверим вопрос";
                StringBuilder text = new StringBuilder();
                for (int i = 1; i < command.length; ++i)
                    text.append(command[i]).append(' ');
                try (FileWriter writer = new FileWriter("Жалобы.txt", false)) {
                    writer.write(String.valueOf(text) + '\n');

                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
                res[1] = TGHandler.commandAction.COMMAND_DEFAULT;
                break;
            case "/change":
                if (TGHandler.botCondition == TGHandler.startConditions.CHANGING_GAME_TYPE) res[0] = "Вы уже находитесь в состоянии изменения игры. Ответьте на этот вопрос и появится окошко с выбором новой игры.";
                else res[0] = "Вы сможете выбрать новый режим после следующего вопроса";
                res[1] = TGHandler.commandAction.COMMAND_CHANGEGAME;
                break;
            default:
                res[0] = "Неверная команда";
                res[1] = TGHandler.commandAction.COMMAND_DEFAULT;
                break;
        }
        return res;
    }
}
