package tg.project.TelegramGameBot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Класс конфигурации бота с получением свойств (имя, индивидуальный токен)
 * для соединения
 */
@Configuration
@PropertySource("application.properties")
public class BotConfig {

    @Value("${name}")
    String botName;
    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    @Value("${token}")
    String botToken;
    public String getToken() {
        return botToken;
    }

    public void setToken(String token) {
        this.botToken = token;
    }
}
