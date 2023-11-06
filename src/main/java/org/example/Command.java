package org.example;

public class Command
{
    private final String[] command;

    public Command(String[] command) {
        this.command = command;
    }

    public String getCommand()
    {
        return switch (this.command[0]) {
            case "/help" -> "Это помощь вы можете...";
            case "/exit" -> "12";
            default -> "Неверная команда";
        };
    }
}
