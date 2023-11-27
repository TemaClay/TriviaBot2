package tg.project.TelegramGameBot.service.mathgameutility;

import tg.project.TelegramGameBot.service.interfaces.Game;

import java.util.Random;

/**
 * Создаёт пример, получаемый из двух псевдослучайных чисел и выборкой из 3 алгебраических
 * операций: умножение, сложение, вычитание
 */
public class MathGame implements Game
{
    private int firstNumber;
    private int secondNumber;
    private char operation;
    private int result;
    public MathGame() {
        int min = 1;
        int max = 1000;

        Random rnd = new Random();

        this.firstNumber = min + Math.abs(rnd.nextInt()) % (max - min + 1);
        this.secondNumber = min + Math.abs(rnd.nextInt()) % (max - min + 1);
        String operation = MathOperations.getStringOfRandomOperation(MathOperations.getRandomOperation());

        switch (operation) {
            case "+":
                this.operation = '+';
                this.result = firstNumber + secondNumber;
                break;
            case "-":
                this.operation = '-';
                this.result = firstNumber - secondNumber;
                break;
            case "*":
                this.operation = '*';
                this.result = firstNumber * secondNumber;
                break;
        }
    }


    @Override
    public String getResult() {
        return result + "";
    }


    @Override
    public String getQuestion() {
        return firstNumber + " " + operation + " " + secondNumber + " = ?";
    }
}
