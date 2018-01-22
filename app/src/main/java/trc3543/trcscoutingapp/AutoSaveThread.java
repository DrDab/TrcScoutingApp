package trc3543.trcscoutingapp;

import android.util.Log;

import java.io.IOException;

public class AutoSaveThread
{
    /**
     *
     *  Copyright (c) 2017 Titan Robotics Club, _c0da_ (Victor Du)
     *
     *	Permission is hereby granted, free of charge, to any person obtaining a copy
     *	of this software and associated documentation files (the "Software"), to deal
     *	in the Software without restriction, including without limitation the rights
     *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     *	copies of the Software, and to permit persons to whom the Software is
     *	furnished to do so, subject to the following conditions:
     *
     *	The above copyright notice and this permission notice shall be included in all
     *	copies or substantial portions of the Software.
     *
     *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
     *	SOFTWARE.
     */

    public AutoSaveThread()
    {
        Log.d("AutoSave", "Initialized autosave thread.");
    }

    public void run()
    {
        // save the entries in RAM every x minutes.
        for(;;)
        {
            double time = System.currentTimeMillis() / 1000.0;
            if (time % DataStore.AUTOSAVE_SECONDS == 0 && DataStore.USE_AUTOSAVE) {
                Log.d("AutoSave", "Auto saving at time=" + time);
                String filename = DataStore.FIRST_NAME + "_" + DataStore.LAST_NAME + "_results.csv";
                try {
                    DataStore.writeContestsToCsv(filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
