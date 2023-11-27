package tg.project.TelegramGameBot.service;

import java.util.Random;

public class MathOperations
{
    public enum Operation
    {
        ADD,
        SUBTRACT,
        MULTIPLY
    }
    public static Operation getRandomOperation()
    {
        Random random = new Random();
        return Operation.values()[random.nextInt(Operation.values().length)];
    }

    public static String getStringOfRandomOperation(Operation operation)
    {
        return switch (operation) {
            case ADD -> "+";
            case SUBTRACT -> "-";
            case MULTIPLY -> "*";
        };
    }
}
