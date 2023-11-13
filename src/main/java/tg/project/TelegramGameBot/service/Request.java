package tg.project.TelegramGameBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Запрос пользователя
 */
public interface Request
{
    /**
     * @return возвращает запрос пользователя
     */
    String getRequest();
}
