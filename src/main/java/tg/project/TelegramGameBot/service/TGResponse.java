    package tg.project.TelegramGameBot.service;

    import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
    import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
    import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
    import tg.project.TelegramGameBot.config.BotConfig;
    import tg.project.TelegramGameBot.service.interfaces.Response;

    public class TGResponse extends TelegramBot implements Response {
        private final String respondingText;

        private InlineKeyboardMarkup inlineKeyboardMarkup;
        private final long chatId;

        /** Конструктор
         * @param config нужен для работы execute
         * @param respondingText то, что нужно отправить
         * @param chatId то, по какому ChatID отправить
         */
        public TGResponse(BotConfig config, String respondingText, long chatId) {
            super(config);
            this.chatId = chatId;
            this.respondingText = respondingText;
        }

        public TGResponse(BotConfig config, String respondingText, InlineKeyboardMarkup inlineKeyboardMarkup, long chatId) {
            super(config);
            this.chatId = chatId;
            this.respondingText = respondingText;
            this.inlineKeyboardMarkup = inlineKeyboardMarkup;
        }


        /**
         * Функция, которая с помощью execute и получением конфигурации бота config отправляет запрос
         */
        @Override
        public void getResponse() {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText(respondingText);
            if (inlineKeyboardMarkup != null) message.setReplyMarkup(inlineKeyboardMarkup);
            try {
                execute(message);
            }
            catch (TelegramApiException e) {
            }
        }

    }