package org.example;

import java.util.Objects;

public class CLIHandler extends BaseHandler {

    private User user;

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
            Response response = new CLIResponse(user.getName() + ",\n" + cmd[0]);
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
        Request req = new CLIRequest();
        Response helloResponse = new CLIResponse("Привет! Как тебя зовут?");
        helloResponse.getResponse();
        String name = req.getRequest();
        Response ageQuestionResponse = new CLIResponse(name + ", сколько тебе лет?");
        ageQuestionResponse.getResponse();
        int age = Integer.parseInt(req.getRequest());
        this.user = new User(name, age);
        Response startResponse = new CLIResponse("Это бот для участия в викторине, давай начнем!");
        startResponse.getResponse();
    }



    public Game mathGame()
    {
        return new MathGame();
    }

    public void gameQuestion(Game game)
    {
        Response response = new CLIResponse(game.getQuestion());
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
        String userAnswer = request.getRequest();
        String[] splitedString = userAnswer.split(" ");
        if (Objects.equals(game.getResult(), userAnswer))
        {
            Response response = new CLIResponse("Верно, " + user.getName());
            response.getResponse();
            user.increaseCorrectAnswers();
            user.increaseNumberOfQuestion();
            return "successfulCompare";
        }
        else
        {
            try
            {
                int answer = Integer.parseInt(splitedString[0]);
                Response response = new CLIResponse("Неверно, " + user.getName());
                response.getResponse();
                user.increaseNumberOfQuestion();
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
