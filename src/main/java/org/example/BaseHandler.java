package org.example;

/**
 * Базовый обработчик запроса
 */
public abstract class BaseHandler
{
    /**
     * Обрабатывает запрос, не дожидаясь ответа.
     *
     * @param request запрос пользователя.
     * @return
     */
    public abstract String handle(String request, User user);
    /*
    /**
     * Обрабатывает запрос и дожидается ответа.
     * @param request запрос пользователя.
     * @return ответ на запрос пользователя.
     */

    //public abstract Response handleWithResponse(String request);

    public abstract void Start();

    public abstract Game mathGame();

    public abstract void gameQuestion(Game game);

    public abstract String gameCompareResults(String res, String userAnswer);
}
