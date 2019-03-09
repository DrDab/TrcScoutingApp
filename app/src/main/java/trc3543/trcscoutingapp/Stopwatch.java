package trc3543.trcscoutingapp;

public class Stopwatch
{
    private long startTime = 0L;
    private long currentTime = 0L;
    private boolean isRunning = false;

    public void start()
    {
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
    }

    public void stop()
    {
        this.isRunning = false;
    }

    public void pause()
    {
        this.isRunning = false;
        currentTime = System.currentTimeMillis() - startTime;
    }

    public void resume()
    {
        this.isRunning = true;
        this.startTime = System.currentTimeMillis() - currentTime;
    }

    public void reset()
    {
        this.isRunning = false;
        this.startTime = 0L;
        this.currentTime = 0L;
    }

    public void setElapsedTime(long curElapsedTime)
    {
        this.currentTime = curElapsedTime;
        //this.startTime = System.currentTimeMillis() - curElapsedTime;
    }

    public long getElapsedTime()
    {
        long elapsed = 0L;
        if (isRunning)
        {
            elapsed = System.currentTimeMillis() - startTime;
        }
        else
        {
            elapsed = currentTime - startTime;
        }
        return elapsed;
    }

}