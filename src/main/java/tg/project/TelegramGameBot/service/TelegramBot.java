package tg.project.TelegramGameBot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import tg.project.TelegramGameBot.config.BotConfig;


import java.util.Objects;

/**
 * Класс, реализующий в основе Телеграм бота, берущий TelegramLongPollingBot за основу
 *
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {

    static BotConfig config;
    private final BaseHandler handler = new TGHandler(new Update());

    public TelegramBot(BotConfig config) {
        TelegramBot.config = config;
    }

    public static BotConfig getConfig() {
        return config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }


    /**
     * Функция, которая по факту отправки пользователем информации, начинает работу с ней
     * @param update данные пользователя
     */
    @Override
    public void onUpdateReceived(Update update) {
            handler.handle(update);

            }
}
