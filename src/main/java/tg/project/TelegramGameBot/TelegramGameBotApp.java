package tg.project.TelegramGameBot;

import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tg.project.TelegramGameBot.service.BaseHandler;
import tg.project.TelegramGameBot.service.TGHandler;

/**
 * Запуск бота через фреймворк Spring
 */
@SpringBootApplication
public class TelegramGameBotApp {

	public static void main(String[] args) {
		SpringApplication.run(TelegramGameBotApp.class, args);
	}

}
