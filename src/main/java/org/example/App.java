package org.example;

import java.util.Objects;


public class App
{
    public static void main( String[] args )
    {
        BaseHandler handler = new CLIHandler();

        handler.Start();

        while (true)
        {
            Game game = handler.mathGame();
            String ex = null;
            while (Objects.equals(ex, null)) {
                handler.gameQuestion(game);
                Request request = new CLIRequest();
                ex = handler.gameCompareResults(game, request);
            }
            if (Objects.equals(ex, "exit"))
            {
                break;
            }
            
        }


    }
}
