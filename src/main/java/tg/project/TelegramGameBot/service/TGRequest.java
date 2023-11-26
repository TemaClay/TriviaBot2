package tg.project.TelegramGameBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import tg.project.TelegramGameBot.service.interfaces.Request;


/**
 * Класс для получения сообщения пользователя
 */
public class TGRequest implements Request {

    private final Update update;

    public TGRequest(Update update) {
        this.update = update;
    }

    /**
     * @return ввод пользователя
     */
    @Override
    public String getRequest() {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText();
        }
        else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getText();
        }
        return null;
    }
}
