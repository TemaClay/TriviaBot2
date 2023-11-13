package tg.project.TelegramGameBot.service;

import java.util.Random;

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
        switch (Math.abs(rnd.nextInt()) % (3)) {
            case 0:
                this.operation = '+';
                this.result = firstNumber + secondNumber;
                break;
            case 1:
                this.operation = '-';
                this.result = firstNumber - secondNumber;
                break;
            case 2:
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
