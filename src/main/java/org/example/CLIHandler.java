package org.example;

import java.util.Objects;

public class CLIHandler extends BaseHandler
{



    @Override
    public void handle(Request request)
    {
        String request1 = request.getRequest();
        // Вид команды /command args
        String[] splitedString = request1.split(" ");

        //TODO: обработка полученной команды


    }

    @Override
    public Response handleWithResponse(Request request)
    {
        String request1 = request.getRequest();
        // Вид команды /command args
        String[] splitedString = request1.split(" ");

        //TODO: обрабатываем команду
        if (Objects.equals(splitedString[0].charAt(0), '/'))
        {
            Command command = new Command(splitedString);
            return new CLIResponse(command.getCommand());
        }
        return new CLIResponse("Response");
    }

    /**
     * начало диалога
     * @return
     */




}
