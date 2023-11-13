package tg.project.TelegramGameBot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tg.project.TelegramGameBot.TelegramGameBotApp;
import tg.project.TelegramGameBot.config.BotConfig;

import java.util.Objects;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private boolean isStarted = false;
    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        BaseHandler handler = new TGHandler(update, config);
        handler.handle()


    }
}
