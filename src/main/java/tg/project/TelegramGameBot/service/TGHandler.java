package tg.project.TelegramGameBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import tg.project.TelegramGameBot.config.BotConfig;

import java.util.Objects;

public class TGHandler extends BaseHandler {

    private User user;
    private Update update;

    private static boolean isStartChecked = false;
    private static boolean isQuestionGiven = false;

    private static Game game;

    public TGHandler(Update update, BotConfig config) {
        this.update = update;
    }

    public TGHandler(Update update) {
        this.update = update;
    }

    @Override
    public void handle(Update update) {
        Request request = new TGRequest(update);
        String userMessage = request.getRequest();

        if (Objects.equals(userMessage, "/start") || isStartChecked){
            newBotEntranceStartSequence(update);
            if (isStartChecked) return;
        }

            String ex;

            if (!isQuestionGiven) {
                game = mathGame();
                ex = null;
                gameQuestion(game, update);
                isQuestionGiven = true;
                return;
        }
            else {
                String answer = update.getMessage().getText();
                ex = gameCompareResults(game, answer, update);
                isQuestionGiven = false;
            }
        if (!isQuestionGiven) {
            game = mathGame();
            ex = null;
            gameQuestion(game, update);
            isQuestionGiven = true;
        }
        //TODO: обработка полученной команды

    }

    /**
     * Функция, вызываемая при старте бота для генерации нового пользователя
     * Нам нужно получить от пользователя /start, а затем его возраст, поэтому функция воспроизводится в
     * 2 итерации:
     * 1) принимает /start, даёт на него ответ,
     * 2) принимает возраст, создаёт пользователя
     * @param update получает данные пользователя Телеграм
     */
    public void newBotEntranceStartSequence(Update update) {
        if (!isStartChecked) {
            Response ageQuestionResponse = new TGResponse(TelegramBot.getConfig(), update.getMessage().getChat().getFirstName() + ", сколько тебе лет?", update.getMessage().getChatId());
            ageQuestionResponse.getResponse();
            isStartChecked = true;
        }
        else {
            Request request = new TGRequest(update);
            int age = Integer.parseInt(request.getRequest());
            user = new User(update.getMessage().getChat().getFirstName(), age);
            Response startResponse = new TGResponse(TelegramBot.getConfig(), "Это бот для участия в викторине, давай начнем!", update.getMessage().getChatId());
            startResponse.getResponse();
            isStartChecked = false;
        }
    }

    public Game mathGame() {
        return new MathGame();
    }

    public void gameQuestion(Game game, Update update) {
        long chatId = update.getMessage().getChatId();
        Response response = new TGResponse(TelegramBot.getConfig(),game.getQuestion(), chatId);
        response.getResponse();
    }
    @Override
    public String gameCompareResults(Game game, String userAnswer, Update update) {
        long chatId = update.getMessage().getChatId();
        String[] splitedString = userAnswer.split(" ");
        if (Objects.equals(game.getResult(), userAnswer)) {
            Response response = new TGResponse(TelegramBot.getConfig(),"Верно, " + user.getName(), chatId);
            response.getResponse();
            user.increaseCorrectAnswers();
            user.increaseNumberOfQuestions();
            return "successfulCompare";
        } else {
            try {
                int answer = Integer.parseInt(splitedString[0]);
                Response response = new TGResponse(TelegramBot.getConfig(),"Неверно, " + user.getName(), chatId);
                response.getResponse();
                user.increaseNumberOfQuestions();
                return "successfulCompare";
            } catch (NumberFormatException e) {
                checkForCommand(userAnswer, update);
            }
        }
        return null;
    }

    /**
     * Функция для анализа комманды введённой пользователем при проверке ботом правильности ответа
     * @param userMessage то, что ввёл пользователь в качестве ответа
     * @param update данные пользователя
     */
    public void checkForCommand(String userMessage, Update update){
        String[] splitedString = userMessage.split(" ");
        String[] command = new String[2];
        if (splitedString[0].charAt(0) == '/') {
            Commands commandAnalyzer = new Commands();
            command = commandAnalyzer.getCommand(splitedString, user);
            Response response = new TGResponse(TelegramBot.getConfig(), user.getName() + ",\n" + command[0], update.getMessage().getChatId());
            response.getResponse();
        }
    }

}
