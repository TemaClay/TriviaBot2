package tg.project.TelegramGameBot.service;
import java.io.*;
import java.util.Objects;

import static tg.project.TelegramGameBot.service.TGHandler.botCondition;
import static tg.project.TelegramGameBot.service.TGHandler.startConditions.ONGOING_MATH_GAME;
import static tg.project.TelegramGameBot.service.TGHandler.startConditions.ONGOING_WORD_GAME;

/**
 * Класс, реализующий функцию анализа команд, отправленной пользователем
 */
public class Commands
{


    public String[] getCommand(String[] command, User user)
    {
        String[] res = new String[2];
        switch (command[0]) {
            case "/exit":
                res[0] = """
                        До скорых встреч!
                        """;
                res[1] = "NEEDTOEXIT";
                break;
            case "/help":
                res[0] = """
                        Бот предствляет собой игру, где нужно правильно отвечать на\s
                        математические вопросы или на вопросы, проверяющие эрудицию.
                        /result - посмотреть количество Ваших правильных ответов
                        /report <полный текст вопроса> - пожаловаться на вопрос
                        /change <Вид игры> - поменять тип игры. На выбор 2 варианта:\s
                        "/change trivia" - меняет игру на викторину.
                        "/change math" - меняет игру на решатель математических примеров.
                        """;
                res[1] = null;
                break;
            case "/result":
                res[0] = "количество правильных ответов: " + user.getCorrectAnswers() + " из " + user.getNumberOfQuestions();
                res[1] = null;
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
                res[1] = null;
                break;
            case "/change":
                try {
                    switch (command[1]) {
                        case "trivia":
                            if (Objects.equals(botCondition, ONGOING_WORD_GAME)) {
                                res[0] = "Игра уже установлена в этот режим";
                            } else {
                                TGHandler.setBotCondition(ONGOING_WORD_GAME);
                                res[0] = "Игра будет установлена в режим 'Тривиа' после следующего вопроса.";
                            }
                            res[1] = "GameSwitched";
                            break;
                        case "math":
                            if (Objects.equals(botCondition, ONGOING_MATH_GAME)) {
                                res[0] = "Игра уже установлена в этот режим";
                            } else {
                                TGHandler.setBotCondition(ONGOING_MATH_GAME);
                                res[0] = "Игра будет установлена в режим 'Математика' после следующего вопроса";
                            }
                            res[1] = "GameSwitched";
                            break;
                        default:
                            res[0] = "Такого вида игры нет!";
                            res[1] = null;
                            break;
                    }
                } catch (Exception e) {
                    res[0] = "Для /change требуется аргумент. Напишите /help для помощи";
                    res[1] = null;
                    break;
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
