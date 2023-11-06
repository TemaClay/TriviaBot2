package org.example;

public class CLIResponse implements Response
{
    private final String response;

    public CLIResponse(String response)
    {
        this.response = response;
    }

    @Override
    public String getResponse()
    {
        return response;
    }


}
