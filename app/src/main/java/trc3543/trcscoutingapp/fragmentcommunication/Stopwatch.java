package trc3543.trcscoutingapp.fragmentcommunication;

public class Stopwatch
{
    private final long start;

    public Stopwatch()
    {
        start = System.currentTimeMillis();
    }

    public double elapsedTime()
    {
        long now = System.currentTimeMillis();
        return(now - start) / 1000.0;
    }

    public double getTime()
    {
        return System.currentTimeMillis()/1000.0;
    }
}