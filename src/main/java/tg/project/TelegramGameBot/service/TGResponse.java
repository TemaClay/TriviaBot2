    package tg.project.TelegramGameBot.service;

    import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
    import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
    import tg.project.TelegramGameBot.config.BotConfig;

    public class TGResponse extends TelegramBot implements Response {
        private final String respondingText;
        private final long chatId;

        public TGResponse(BotConfig config, String respondingText, long chatId) {
            super(config);
            this.chatId = chatId;
            this.respondingText = respondingText;
        }
        @Override
        public void getResponse() {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText(respondingText);
            try {
                execute(message);
            }
            catch (TelegramApiException e) {
            }

        }
    }