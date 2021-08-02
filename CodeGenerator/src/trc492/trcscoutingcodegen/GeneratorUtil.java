package trc492.trcscoutingcodegen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import trc492.trcscoutingcodegen.data.SessionData;

public class GeneratorUtil
{
    public SessionData sessionData;
    public File sessionFile;
    
    private Scanner sc;
    
    public GeneratorUtil(Scanner sc)
    {
        this.sc = sc;
    }
    
    public boolean fileExists(String fileName)
    {
        return new File(fileName).exists();
    }
    
    public boolean createNewSession(String fileName) throws IOException
    {
        sessionFile = new File(fileName);
        if (!sessionFile.exists())
            sessionFile.createNewFile();
        sessionData = new SessionData();
        writeSessionData();
        return true;
    }
    
    public boolean loadSessionData(String fileName) throws FileNotFoundException
    {
        sessionFile = new File(fileName);
        if (!sessionFile.exists())
            return false;
        sessionData = new SessionData(sessionFile);   
        return true;
    }
    
    public boolean writeSessionData() throws FileNotFoundException
    {
        if (sessionFile == null || sessionData == null)
            return false;
        PrintStream ps = new PrintStream(sessionFile);
        ps.println(sessionData.toJSONString());
        ps.close();
        return true;
    }
    
    public void unloadSession()
    {
        sessionFile = null;
        sessionData = null;
    }
    
    public boolean promptYN(String promptMsg, Object... args)
    {
        if (promptMsg == null)
            return false;
        System.out.printf(promptMsg + "\n", args);
        return sc.nextLine().toLowerCase().contains("y");
    }
    
    public boolean sessionLoaded()
    {
        return sessionData != null;
    }
}
