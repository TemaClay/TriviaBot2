package org.example;

import java.util.Objects;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        BaseHandler handler = new CLIHandler();
        handler.Start();

        while (true)
        {
            Game game = handler.mathGame();
            String ex = "1";
            while (Objects.equals(ex, "1")) {
                handler.gameQuestion(game);
                Request request = new CLIRequest();
                ex = handler.gameCompareResults(game.getResult(), request.getRequest());
            }
            if (Objects.equals(ex, "2"))
            {
                break;
            }
        }


    }
}
