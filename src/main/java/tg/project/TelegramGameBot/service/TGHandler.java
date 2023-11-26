package tg.project.TelegramGameBot.service;

import org.glassfish.grizzly.utils.DelayedExecutor;
import org.glassfish.grizzly.utils.ExceptionHandler;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tg.project.TelegramGameBot.config.BotConfig;
import tg.project.TelegramGameBot.service.interfaces.Game;
import tg.project.TelegramGameBot.service.interfaces.Request;
import tg.project.TelegramGameBot.service.interfaces.Response;
import tg.project.TelegramGameBot.service.mathgameutility.MathGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TGHandler extends BaseHandler {

    private User user;
    private Update update;

    private static enum startConditions{
        INACTIVE, STARTING, AGE_CHECKING, GAME_TYPE_CHECKING, ONGOING_MATH_GAME, ONGOING_WORD_GAME
    };
    private static startConditions botCondition = startConditions.INACTIVE;
    private static boolean isQuestionGiven = false;
    private static boolean isCommandBeingTyped = false;

    private static Game game;

    public TGHandler(Update update, BotConfig config) {
        this.update = update;
    }

    public TGHandler(Update update) {
        this.update = update;
    }

    @Override
    public void handle(Update update) {
        String ex;
        Request request = new TGRequest(update);
        String userMessage = request.getRequest();

        if (Objects.equals(userMessage, "/start") || !Objects.equals(botCondition, startConditions.INACTIVE)){
            newBotEntranceStartSequence(update);
            if (!(Objects.equals(botCondition, startConditions.ONGOING_MATH_GAME )
                    || Objects.equals(botCondition, startConditions.ONGOING_WORD_GAME)))
                return;
        }
        else return;

        if (!isQuestionGiven) {
            game = getNewGame();
            ex = null;
            gameQuestion(game, update);
            isQuestionGiven = true;
            return;
        }
        else {
            String answer = update.getMessage().getText();
            ex = gameCompareResults(game, answer, update);
            if (isCommandBeingTyped){
                isCommandBeingTyped = false; /* проверка на команду, если была введена команда, то заново отправляем тот же пример и ждем новый запрос */
                gameQuestion(game,update);
                return;
            }
            isQuestionGiven = false;
        }
        Game game = getNewGame();
        ex = null;
        gameQuestion(game, update);
        isQuestionGiven = true;
    }

    public Game getNewGame() {
        switch (botCondition){
            case startConditions.ONGOING_MATH_GAME -> {
                return new MathGame();
            }
            case startConditions.ONGOING_WORD_GAME -> {
                return new WordGame();
            }
        }
        return null;
    }
    /**
     * Функция, вызываемая при старте бота для генерации нового пользователя
     * Нам нужно получить от пользователя /start,затем его возраст,затем игрок должен выбрать тип игры, поэтому функция воспроизводится в
     * несколько итераций, каждая меняет состояние бота в настоящем времени:
     * 1) принимает /start, даёт на него ответ,
     * 2) принимает возраст, создаёт пользователя
     * 3) даёт на выбор игры, пользователь выбирает и бот переходит в конкретное состояние
     * @param update получает данные о сообщении пользователя в телеграм
     */
    public void newBotEntranceStartSequence(Update update) {
        if (Objects.equals(botCondition, startConditions.INACTIVE)) botCondition = startConditions.STARTING;
        switch (botCondition){
            case startConditions.STARTING -> {
                Response ageQuestionResponse = new TGResponse(TelegramBot.getConfig(), update.getMessage().getChat().getFirstName() + ", сколько тебе лет?", update.getMessage().getChatId());
                ageQuestionResponse.getResponse();
                botCondition = startConditions.AGE_CHECKING;
                break;
            }
            case startConditions.AGE_CHECKING -> {
                Request request = new TGRequest(update);
                int age = Integer.parseInt(request.getRequest());
                user = new User(update.getMessage().getChat().getFirstName(), age);

                InlineKeyboardButton inlineKeyboardButtonWordGame = new InlineKeyboardButton();
                inlineKeyboardButtonWordGame.setText("Тривиа");
                inlineKeyboardButtonWordGame.setCallbackData("Button \"Тривиа\" has been pressed");

                InlineKeyboardButton inlineKeyboardButtonMathGame = new InlineKeyboardButton();
                inlineKeyboardButtonMathGame.setText("Математика");
                inlineKeyboardButtonMathGame.setCallbackData("Button \"Математика\" has been pressed");

                List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();
                inlineKeyboardButtonsRow1.add(inlineKeyboardButtonMathGame);
                inlineKeyboardButtonsRow1.add(inlineKeyboardButtonWordGame);

                List<List<InlineKeyboardButton>> buttonsGameChoiceRows = new ArrayList<>();
                buttonsGameChoiceRows.add(inlineKeyboardButtonsRow1);

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(buttonsGameChoiceRows);

                Response gameAskingResponse = new TGResponse(TelegramBot.getConfig(),"В какую игру хотите сыграть? На выбор: игра со случайными вопросами и игра, в которой нужно считать арифметические примеры", inlineKeyboardMarkup, update.getMessage().getChatId());
                gameAskingResponse.getResponse();

                botCondition = startConditions.GAME_TYPE_CHECKING;
                break;
            }
            case startConditions.GAME_TYPE_CHECKING -> {
                Request request = new TGRequest(update);
                if (Objects.equals(request.getRequest(), "Button \"Тривиа\" has been pressed")) botCondition = startConditions.ONGOING_WORD_GAME;
                if (Objects.equals(request.getRequest(), "Button \"Математика\" has been pressed")) botCondition = startConditions.ONGOING_MATH_GAME;
                Response startResponse = new TGResponse(TelegramBot.getConfig(), "Это бот для участия в викторине, давай начнем!", update.getCallbackQuery().getMessage().getChatId());
                startResponse.getResponse();
                break;
            }
        }
    }

    public void gameQuestion(Game game, Update update) {
        long chatId;
        if (update.hasMessage()){
            chatId = update.getMessage().getChatId();
        }
        else {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
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
                isCommandBeingTyped = true;
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
