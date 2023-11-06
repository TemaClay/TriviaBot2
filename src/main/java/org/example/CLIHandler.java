package org.example;

import java.util.Objects;

public class CLIHandler extends BaseHandler {



    @Override
    public String handle(String request)
    {
        String request1 = request;
        // Вид команды /command args
        String[] splitedString = request1.split(" ");
        String[] cmd = new String[2];
        if (splitedString[0].charAt(0) == '/') {
            Command command = new Command();
            cmd = command.getCommand(splitedString);
            System.out.println(cmd[0]);
        }
        if (Objects.equals(cmd[1], "1"))
            return "exit";


        return null;
        //TODO: обработка полученной команды

    }

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
            return new CLIResponse(command.getCommand(splitedString)[0]);
        }
        return new CLIResponse("Response");
    }

    /**
     * начало диалога
     * @return
     */
    public void Start()
    {
        System.out.println("Привет! Этот бот создан для участия в викторине,\n" +
                "ты сможешь отвечать на вопросы, начнем!");
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
            System.out.println("Верно");
            return "0";
        }
        else
        {
            try
            {
                int answer = Integer.parseInt(splitedString[0]);
                System.out.println("Неверно");
                return "0";
            }
            catch (NumberFormatException e)
            {
                String r = handle(userAnswer);
                if (Objects.equals(r, "exit"))
                {
                    return "2";
                }
                return "1";
            }
        }
    }

}
