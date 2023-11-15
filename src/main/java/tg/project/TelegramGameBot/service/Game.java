package tg.project.TelegramGameBot.service;

/**
 * Родительский интерфейс, задаются функции на получение результата и вопроса
 */
public interface Game
{
    public String getResult();
    public String getQuestion();
}
