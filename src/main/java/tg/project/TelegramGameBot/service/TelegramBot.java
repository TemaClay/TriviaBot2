package tg.project.TelegramGameBot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import tg.project.TelegramGameBot.config.BotConfig;


import java.util.Objects;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    static BotConfig config = null;
    private boolean isStarted = false;
    private final BaseHandler handler = new TGHandler(new Update());

    public TelegramBot(BotConfig config) {
        this.config = config;
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



    @Override
    public void onUpdateReceived(Update update) {
            handler.handle(update);
            }
        }
