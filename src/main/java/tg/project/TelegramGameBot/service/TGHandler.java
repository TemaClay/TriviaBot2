package tg.project.TelegramGameBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import tg.project.TelegramGameBot.config.BotConfig;

import java.security.URIParameter;
import java.util.Objects;

public class TGHandler extends BaseHandler {

    private BotConfig config;
    private User user;

    private final Update update;

    public TGHandler(Update update, BotConfig config){
        this.update = update;
        this.config = config;
    }

    public TGHandler(Update update){
        this.update = update;
    }
    /**
     * @param request запрос пользователя.
     * @param user экземпляр пользователя для запоминания личных данных
     * @return null, если запрос пользователя это комманда
     *         "exit", если введёно /exit - флаг для выхода
     */
    @Override
    public String handle(String request, User user)
    {

        // Вид команды /command args
        String[] splitedString = request.split(" ");
        String[] cmd = new String[2];
        if (splitedString[0].charAt(0) == '/') {
            Command command = new Command();
            cmd = command.getCommand(splitedString, user);
            Response response = new TGResponse(config, user.getName() + ",\n" + cmd[0],update.getMessage().getChatId());
            response.getResponse();
        }
        if (Objects.equals(cmd[1], "exit"))
            return "exit";


        return null;
        //TODO: обработка полученной команды

    }
    /*
    @Override
    public Response handleWithResponse(String request)
    {
        String request1 = request;
        // Вид команды /command args
        String[] splitedString = request1.split(" ");

        //TODO: обрабатываем команду
        if (Objects.equals(splitedString[0].charAt(0), '/'))
        {
            Command command = new Command();
            return new CLIResponse(command.getCommand(splitedString, user)[0]);
        }
        return new CLIResponse("Response");
    }
*/
    /**
     * начало диалога

     */
    public void Start()
    {
        Request request = new TGRequest(update);
        if (request.getRequest().equals("/start")) {
            String name = update.getMessage().getChat().getFirstName();
            Response ageQuestionResponse = new TGResponse(config, name + ", сколько тебе лет?", update.getMessage().getChatId());
            ageQuestionResponse.getResponse();

            int age = Integer.parseInt(request.getRequest());
            this.user = new User(name, age);
            Response startResponse = new TGResponse(config, "Это бот для участия в викторине, давай начнем!", update.getMessage().getChatId());
            startResponse.getResponse();
    }
    }



    public Game mathGame()
    {
        return new MathGame();
    }

    public void gameQuestion(Game game)
    {
        long chatId = update.getMessage().getChatId();

        Response response = new TGResponse(config, game.getQuestion(),chatId);
        response.getResponse();
    }

    /**
     * @param game текущая игра
     * @param request запрос/ответ пользователя
     * @return string
     */
    @Override
    public String gameCompareResults(Game game, Request request)
    {
        long chatId = update.getMessage().getChatId();

        String userAnswer = request.getRequest();
        String[] splitedString = userAnswer.split(" ");
        if (Objects.equals(game.getResult(), userAnswer))
        {
            Response response = new TGResponse(config, "Верно, " + user.getName(),chatId);
            response.getResponse();
            user.increaseCorrectAnswers();
            user.increaseNumberOfQuestions();
            return "successfulCompare";
        }
        else
        {
            try
            {
                int answer = Integer.parseInt(splitedString[0]);
                Response response = new TGResponse(config, "Неверно, " + user.getName(),chatId);
                response.getResponse();
                user.increaseNumberOfQuestions();
                return "successfulCompare";
            }
            catch (NumberFormatException e)
            {
                String r = handle(userAnswer, user);
                if (Objects.equals(r, "exit"))
                {
                    return "exit";
                }
                return null;
            }
        }
    }

}
