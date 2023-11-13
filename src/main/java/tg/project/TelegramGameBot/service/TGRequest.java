package tg.project.TelegramGameBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;


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
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            return messageText;
        }
        else return null;
    }
}
