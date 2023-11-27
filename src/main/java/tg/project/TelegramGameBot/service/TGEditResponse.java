    package tg.project.TelegramGameBot.service;

    import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
    import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
    import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
    import tg.project.TelegramGameBot.config.BotConfig;
    import tg.project.TelegramGameBot.service.interfaces.Response;

    /**
     * Класс для изменения сообщений, которые уже были отправлены
     */
    public class TGEditResponse extends TelegramBot implements Response {
        private final String respondingText;

        private InlineKeyboardMarkup inlineKeyboardMarkup;
        private final long chatId;
        private final int messageId;

        /**
         * Конструктор
         *
         * @param config         нужен для работы execute
         * @param respondingText то, что нужно отправить
         * @param chatId         то, по какому ChatID отправить
         * @param messageId идентификатор запроса, который программа будет менять
         */
        public TGEditResponse(BotConfig config, String respondingText, long chatId, int messageId) {
            super(config);
            this.chatId = chatId;
            this.respondingText = respondingText;
            this.messageId = messageId;
        }

        /**
         * Конструктор для сценария, где новое сообщение будет содержать кнопки
         * @param config нужен для работы execute
         * @param respondingText то, что нужно отправить
         * @param inlineKeyboardMarkup объект кнопок
         * @param chatId то, по какому ChatID отправить
         * @param messageId идентификатор запроса, который программа будет менять
         */
        public TGEditResponse(BotConfig config, String respondingText, InlineKeyboardMarkup inlineKeyboardMarkup, long chatId,int messageId) {
            super(config);
            this.chatId = chatId;
            this.respondingText = respondingText;
            this.inlineKeyboardMarkup = inlineKeyboardMarkup;
            this.messageId = messageId;
        }

        /**
         * Функция, которая с помощью execute и получением конфигурации бота config меняет некоторый запрос
         */
        @Override
        public void getResponse() {
            EditMessageText message = new EditMessageText();
            message.setChatId(String.valueOf(chatId));
            message.setText(respondingText);
            message.setMessageId(messageId);
            if (inlineKeyboardMarkup != null) message.setReplyMarkup(inlineKeyboardMarkup); /* if для кнопок */
            try {
                execute(message);
            }
            catch (TelegramApiException e) {
            }
        }

    }