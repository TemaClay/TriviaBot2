package org.example;

import java.util.Objects;

public class CLIHandler extends BaseHandler {



    @Override
    public void handle(String request)
    {
        String request1 = request;
        // Вид команды /command args
        String[] splitedString = request1.split(" ");

        if (splitedString[0].charAt(0) == '/')
        {
            Command command = new Command();
            System.out.println(command.getCommand(splitedString));

        } else
        {
            System.out.println("Нет такого");
        }

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
            return new CLIResponse(command.getCommand(splitedString));
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

    public String gameQuestion()
    {
        Game game1 = new CLIGame();
        String[] expression = game1.MathematicQuestion();

        System.out.println(expression[0] + ' ' + expression[1] + ' ' + expression[2] + " = ?");

        return expression[3];


    }

    @Override
    public void gameCompareResults(String res, String userAnswer)
    {
        String[] splitedString = userAnswer.split(" ");
        if (Objects.equals(res, userAnswer))
        {
            System.out.println("Верно");
            return;
        }
        else
        {
            try
            {
                int answer = Integer.parseInt(splitedString[0]);
                System.out.println("Неверно");
                return;
            }
            catch (NumberFormatException e)
            {
                handle(userAnswer);
            }
        }
    }

}
