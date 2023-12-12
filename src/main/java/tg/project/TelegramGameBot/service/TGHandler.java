package tg.project.TelegramGameBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tg.project.TelegramGameBot.config.BotConfig;
import tg.project.TelegramGameBot.service.interfaces.Game;
import tg.project.TelegramGameBot.service.interfaces.Request;
import tg.project.TelegramGameBot.service.interfaces.Response;
import tg.project.TelegramGameBot.service.mathgameutility.MathGame;
import tg.project.TelegramGameBot.service.wordgameutility.WordComparison;
import tg.project.TelegramGameBot.service.wordgameutility.WordGame;
import tg.project.TelegramGameBot.service.wordgameutility.WordGameQuestions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TGHandler extends BaseHandler {

    private User user;
    private Update update;

    /**
     * Состояния бота во время функционирования
     * INACTIVE - бот не активирован пользователем, т.е не прописана команда /start.
     * STARTING - боту была прописана команда /start.
     * AGE_CHECKING - этап генерации экземпляра пользователя
     * GAME_TYPE_CHECKING - этап выбора режима игры пользователем.
     * ONGOING_MATH_GAME/WORD_GAME - бот задаёт вопросы в зависимости от того, какую игры выбрал пользователь.
     * CHANGING_GAME_TYPE - пользователь находится в состоянии смены режима игры (команда /change)
     */
    protected static enum startConditions{
        INACTIVE, STARTING,
        AGE_CHECKING,
        GAME_TYPE_CHECKING,
        ONGOING_MATH_GAME,
        CHANGING_GAME_TYPE,
        ONGOING_WORD_GAME
        
    }
    protected static startConditions botCondition = startConditions.INACTIVE;
    protected static void setBotCondition(startConditions condition) {
        botCondition = condition;
    }


    /**
     * Возможные доп. действия после работы метода gameCompareResults
     * NO_COMPARE - состояние до инициализации gameCompareResults и во время работы функции
     * SUCCESS - успешное сравнение, на ввод пользователем не была отправлена команда
     * COMMAND_DEFAULT - успешная инициализация команды вместо сравнения, дополнительных действий не требуется
     * COMMAND_EXIT - успешная инициализация команды вместо сравнения, требуется совершить выход из бота
     * COMMAND_CHANGEGAME - успешная инициализация команды вместо сравнения, требуется совершить смену режима игры
     */
    public static enum commandAction {
        NO_COMPARE, SUCCESS, COMMAND_DEFAULT, COMMAND_EXIT, COMMAND_CHANGEGAME
    }
    private static commandAction actionCondition = commandAction.NO_COMPARE;

    private static boolean isFirstQuestionGiven = false;

    private static Game game;

    public TGHandler(Update update, BotConfig config) {
        this.update = update;
    }

    public TGHandler(Update update) {
        this.update = update;
    }

    /**
     * Метод, вызываемый при получении обновления от пользователя
     * (сообщения, CallbackQuery и тд) и работающий
     * с этими сообщениями
     * @param update передаёт полученные данные о пользователе в Телеграм
     */
    @Override
    public void handle(Update update) {
        Request request = new TGRequest(update);
        String userMessage = request.getRequest();
        if (Objects.equals(userMessage, "/start") || !Objects.equals(botCondition, startConditions.INACTIVE)){
            newBotEntranceStartSequence(update);
            if (!(Objects.equals(botCondition, startConditions.ONGOING_MATH_GAME ) || (Objects.equals(botCondition, startConditions.ONGOING_WORD_GAME)))) return;
        }
        else return;

        if (!isFirstQuestionGiven) {
            game = getNewGame();
            gameQuestion(game, update);
            isFirstQuestionGiven = true;
            return;
        }
        else {
            String answer = update.getMessage().getText();
            gameCompareResults(game, answer, update);
            switch (actionCondition){
                case commandAction.COMMAND_DEFAULT -> {
                    gameQuestion(game, update);
                    return;
                }
                case commandAction.COMMAND_EXIT -> {
                    isFirstQuestionGiven = false;
                    botCondition = startConditions.INACTIVE;
                    return;
                }
                case commandAction.COMMAND_CHANGEGAME -> {
                    gameQuestion(game, update);
                    botCondition = startConditions.CHANGING_GAME_TYPE;
                    return;
                }
                default -> {
                    break;
                }
            }
        }
        game = getNewGame();
        gameQuestion(game, update);
        isFirstQuestionGiven = true;
    }


    /**
     *      Функция, создающая новую итерацию игры в зависимости от состояния игры, смотрит на botCondition
     */
    public Game getNewGame() {
        switch (botCondition){
            case ONGOING_MATH_GAME -> {
                return new MathGame();
            }
            case ONGOING_WORD_GAME -> {
                return new WordGame();
            }
        }
        return null;
    }
    /**
     * Функция, вызываемая при старте бота для генерации нового пользователя
     * Нам нужно получить от пользователя /start, затем его возраст, затем игрок должен выбрать тип игры, поэтому функция воспроизводится в
     * несколько итераций, каждая меняет состояние бота в настоящем времени:
     * 1) принимает /start, даёт на него ответ, переводит бота в состояние AGE_CHECKING
     * 2) принимает возраст, создаёт пользователя, переводит бота в состояние GAME_TYPE_CHECKING
     * 3) даёт на выбор игры, пользователь выбирает и бот переходит в состояние, зависимое от игры (ONGOING_MATH_GAME, ONGOING_WORD_GAME)
     * @param update получает данные о сообщении пользователя в телеграм
     */
    public void newBotEntranceStartSequence(Update update) {
        if (Objects.equals(botCondition,startConditions.INACTIVE)) botCondition = startConditions.STARTING;
        switch (botCondition){
            case STARTING -> {
                Response ageQuestionResponse = new TGResponse(TelegramBot.getConfig(), update.getMessage().getChat().getFirstName() + ", сколько тебе лет?", update.getMessage().getChatId());
                ageQuestionResponse.getResponse();
                botCondition = startConditions.AGE_CHECKING;
                break;
            }
            case AGE_CHECKING -> {
                Request request = new TGRequest(update);
                int age = Integer.parseInt(request.getRequest());
                user = new User(update.getMessage().getChat().getFirstName(), age);

                /*создание кнопок*/
                Response gameAskingResponse = responseWithTriviaMathButtonsBuilder(update);
                gameAskingResponse.getResponse();

                botCondition = startConditions.GAME_TYPE_CHECKING;
                break;
            }
            case GAME_TYPE_CHECKING -> {
                Request request = new TGRequest(update);
                if (Objects.equals(request.getRequest(), "Button \"Тривиа\" has been pressed")) {
                    botCondition = startConditions.ONGOING_WORD_GAME;
                }
                if (Objects.equals(request.getRequest(), "Button \"Математика\" has been pressed")) {
                    botCondition = startConditions.ONGOING_MATH_GAME;
                }

                Response startResponse;
                if (Objects.equals(actionCondition,commandAction.COMMAND_CHANGEGAME)) { //GameTypeChecking вызывается в 2 вариантах: запуск бота и прогон команды /change. Поэтому здесь 2 варианта обратного сообщения.
                    startResponse = new TGEditResponse(TelegramBot.getConfig(), "Режим установлен", update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getMessageId());
                    actionCondition = commandAction.NO_COMPARE;
                    isFirstQuestionGiven = false;
                }
                else startResponse = new TGEditResponse(TelegramBot.getConfig(), "Это бот для участия в викторине, подробности: /help. Давай начнем!", update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getMessageId());
                startResponse.getResponse();
                break;
            }
            /*
             * Этот тип игры, в отличие от предыдущих в методе, вызывается во время использования команды /change
             * Для того, чтобы грамотно осуществлять работу команд во время этапа перехода на другой вид игры
             * (пользователь должен ответить на вопрос прежде чем выбрать новый вид),
             * в этом виде переписано как должны вести себя команды.
             * В состоянии перехода в другой вид игры, игрок постоянно проваливается в этот case, пока не ответит на последний вопрос.
             */
            case CHANGING_GAME_TYPE ->{
                String answer = update.getMessage().getText();
                gameCompareResults(game, answer, update);
                switch (actionCondition) {
                    case commandAction.COMMAND_DEFAULT, commandAction.COMMAND_CHANGEGAME -> {
                        gameQuestion(game, update);
                        return;
                    }
                    case commandAction.COMMAND_EXIT -> {
                        isFirstQuestionGiven = false;
                        botCondition = startConditions.INACTIVE;
                        return;
                    }
                    default -> {
                        break;
                    }
                }
                    if (Objects.equals(actionCondition,commandAction.SUCCESS)) { //Если пользователь ввёл не команду, а ответил на вопрос, то запускается смена игры
                        Response gameAskingResponse = responseWithTriviaMathButtonsBuilder(update);
                        gameAskingResponse.getResponse();
                        botCondition = startConditions.GAME_TYPE_CHECKING;
                        actionCondition = commandAction.COMMAND_CHANGEGAME;
                    }
                    break;
            }
        }
    }

    private Response responseWithTriviaMathButtonsBuilder(Update update) {
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

        return new TGResponse(TelegramBot.getConfig(),"В какую игру хотите сыграть? На выбор: игра со случайными вопросами и игра, в которой нужно считать арифметические примеры", inlineKeyboardMarkup, update.getMessage().getChatId());
    }


    /**
     * Метод, отправляющий конкретный вопрос в конкретный чат
     * @param game   получает пример и результат игры
     * @param update передаёт полученные данные пользователя в Телеграм
     */
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
    public void gameCompareResults(Game game, String userAnswer, Update update) {
        Response response;
        long chatId = update.getMessage().getChatId();
        WordComparison wordComparison = new WordComparison(game.getResult().toLowerCase(), userAnswer.toLowerCase());
        if (wordComparison.getResultOfComparison()) {
            response = new TGResponse(TelegramBot.getConfig(), "Верно, " + user.getName(), chatId);
            if (Objects.equals(botCondition, startConditions.ONGOING_WORD_GAME))
            {
                WordGameQuestions.questions.get(game.getQuestion())[1] = "0";
            }
            response.getResponse();
            user.increaseCorrectAnswers();
        } else {
                actionCondition = checkForCommand(userAnswer, update);
                if (!Objects.equals(actionCondition, commandAction.NO_COMPARE)) return;
                response = new TGResponse(TelegramBot.getConfig(), "Неверно, " + user.getName() + ".\nПравильный ответ: " + game.getResult(), chatId);
                response.getResponse();
        }
        user.increaseNumberOfQuestions();
        actionCondition = commandAction.SUCCESS;
    }

    /**
     * Функция для анализа комманды введённой пользователем при проверке ботом правильности ответа
     * @param userMessage то, что ввёл пользователь в качестве ответа
     * @param update данные пользователя
     */
    public commandAction checkForCommand(String userMessage, Update update){
        String[] splitedString = userMessage.split(" ");
        Object[] command;
        if (splitedString[0].charAt(0) == '/') {
            Commands commandAnalyzer = new Commands();
            command = commandAnalyzer.getCommand(splitedString, user);
            Response response = new TGResponse(TelegramBot.getConfig(), user.getName() + ",\n" + command[0], update.getMessage().getChatId());
            response.getResponse();
            return (commandAction) command[1];
        }
        return commandAction.NO_COMPARE;
    }
}
