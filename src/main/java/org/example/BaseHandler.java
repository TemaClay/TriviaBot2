package org.example;

/**
 * Базовый обработчик запроса
 */
public abstract class BaseHandler
{
    /**
     * Обрабатывает запрос, не дожидаясь ответа.
     * @param request запрос пользователя.
     */
    public abstract void handle(Request request);

    /**
     * Обрабатывает запрос и дожидается ответа.
     * @param request запрос пользователя.
     * @return ответ на запрос пользователя.
     */
    public abstract Response handleWithResponse(Request request);

    public abstract void Start();

    public abstract String gameQuestion();

    public abstract void CompareResults(String res, String userAnswer);

    public abstract void gameCompareResults(String res, String userAnswer);
}
