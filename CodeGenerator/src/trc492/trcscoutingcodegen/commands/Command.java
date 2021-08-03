package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.List;

public abstract class Command
{
    private String name;
    private String description;
    private String[] syntax;
    
    public Command(String name, String description, String... syntax)
    {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public String[] getSyntax()
    {
        return syntax;
    }
    
    public void printSyntax()
    {
        for (String line : syntax)
        {
            System.out.println("\t" + line);
        }
    }
    
    public abstract boolean call(List<String> args) throws IOException;
}
