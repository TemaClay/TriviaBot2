package org.example;

public class Command
{


    public String[] getCommand(String[] command)
    {
        String[] res = new String[2];
        switch (command[0]) {
            case "/help":
                res[0] = "Это помощь вы можете...";
                res[1] = null;
                break;
            case "/exit":
                res[0] = "Пока";
                res[1] = "1";
                break;
            default:
                res[0] = "Неверная команда";
                res[1] = null;
                break;
        };
        return res;
    }
}
