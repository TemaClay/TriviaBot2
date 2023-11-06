package org.example;

import java.util.Objects;

public class CLIHandler extends BaseHandler {

    private User user;

    @Override
    public String handle(String request, User user)
    {

        // Вид команды /command args
        String[] splitedString = request.split(" ");
        String[] cmd = new String[2];
        if (splitedString[0].charAt(0) == '/') {
            Command command = new Command();
            cmd = command.getCommand(splitedString, user);
            System.out.println(user.getName() + ",\n" + cmd[0]);
        }
        if (Objects.equals(cmd[1], "1"))
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
     * @return
     */
    public void Start()
    {
        Request req = new CLIRequest();
        System.out.println("Привет! Как тебя зовут?");
        String name = req.getRequest();
        System.out.println(name + ", сколько тебе лет?");
        int age = Integer.parseInt(req.getRequest());
        User user = new User(name, age);
        this.user = user;
        System.out.println("Это бот для участия в викторине, давай начнем!");
    }



    public Game mathGame()
    {
        Game game = new MathGame();
        return game;
    }

    public void gameQuestion(Game game)
    {
        System.out.println(game.getQuestion());
    }

    @Override
    public String gameCompareResults(String res, String userAnswer)
    {
        String[] splitedString = userAnswer.split(" ");
        if (Objects.equals(res, userAnswer))
        {
            System.out.println("Верно, " + user.getName());
            user.increaseCorrectAnswers();
            user.increaseNumberOfQuestion();
            return "0";
        }
        else
        {
            try
            {
                int answer = Integer.parseInt(splitedString[0]);
                System.out.println("Неверно, " + user.getName());
                user.increaseNumberOfQuestion();
                return "0";
            }
            catch (NumberFormatException e)
            {
                String r = handle(userAnswer, user);
                if (Objects.equals(r, "exit"))
                {
                    return "2";
                }
                return "1";
            }
        }
    }

}
