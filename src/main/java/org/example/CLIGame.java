package org.example;

import java.util.Random;

public class CLIGame implements Game
{
    @Override
    public String[] MathematicQuestion()
    {
        int min = 1;
        int max = 1000;

        Random rnd = new Random();

        int firstNumber = min + Math.abs(rnd.nextInt()) % (max - min + 1);
        int secondNumber = min + Math.abs(rnd.nextInt()) % (max - min + 1);

        String operation = null;
        int result = switch (Math.abs(rnd.nextInt()) % (3)) {
            case 0 -> {
                operation = "+";
                yield firstNumber + secondNumber;
            }
            case 1 -> {
                operation = "-";
                yield firstNumber - secondNumber;
            }
            case 2 -> {
                operation = "*";
                yield firstNumber * secondNumber;
            }
            default -> 0;
        };
        String[] res = new String[4];
        res[0] = String.valueOf(firstNumber);
        res[1] = operation;
        res[2] = String.valueOf(secondNumber);
        res[3] = String.valueOf(result);
        return res;
    }
}
