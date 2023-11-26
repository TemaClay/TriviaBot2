package tg.project.TelegramGameBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import tg.project.TelegramGameBot.service.interfaces.Game;

/**
 * Базовый обработчик запроса
 */
public abstract class BaseHandler
{

    /**
     * Работает с данными бользователя и выдает ответ
     * @param update передаёт полученные данные пользователя в Телеграм
     */
    public abstract void handle(Update update);


    /**
     * Создает новую математическую игру
     *
     */
    public abstract Game mathGame();


    /**
     * Задаёт вопрос пользователю
     * @param game получает пример и результат игры
     * @param update передаёт полученные данные пользователя в Телеграм
     */
    public abstract void gameQuestion(Game game, Update update);

    /**
     * Сравнивает результат между заверенным внутри объекта игры ответом и ответом пользователя.
     * Если сравнение ответа и введённого пользователем ответа совпадает, выдает "верно"
     * и добавляет 1 верный ответ в /result
     * Если не может определить, что пользователь ввёл число, проверяет на команду через checkForCommand()
     * @param game данные об игре
     * @param userAnswer ответ игрока
     * @param update передаёт полученные данные пользователя в Телеграм
     */
    public abstract String gameCompareResults(Game game, String userAnswer, Update update);

}
