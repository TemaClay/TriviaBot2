package org.example;

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
            String res = handler.gameQuestion();
            Request userRequest = new CLIRequest();
            String req = userRequest.getRequest();
            handler.gameCompareResults(res, req);
        }


    }
}
