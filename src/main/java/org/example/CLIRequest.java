package org.example;

import java.util.Scanner;

public class CLIRequest implements Request
{



    /**
     * @return ввод пользователя
     */
    @Override
    public String getRequest()
    {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}
