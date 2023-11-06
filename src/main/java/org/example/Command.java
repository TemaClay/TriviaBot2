package org.example;

public class Command
{


    public String getCommand(String[] command)
    {
        return switch (command[0]) {
            case "/help":
                yield "Это помощь вы можете...";
            case "/exit":
                yield "12";
            default:
                yield "Неверная команда";
        };
    }
}
