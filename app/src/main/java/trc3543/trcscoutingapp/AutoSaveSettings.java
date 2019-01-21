/*
 * Copyright (c) 2017-2019 Titan Robotics Club
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package trc3543.trcscoutingapp;

import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("all")
public class AutoSaveSettings extends AppCompatActivity
{
    static boolean autoSave;
    static int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_save_settings);
        setTitle("AutoSave Config");
        setTitleColor(Color.parseColor("#ff669900"));

        int minutes = DataStore.autosaveSeconds / 60;

        CheckBox cb1 = (CheckBox) findViewById(R.id.toggle_autosave);
        Spinner mySpinner = (Spinner) findViewById(R.id.timerchoice);
        cb1.setChecked(DataStore.useAutosave);
        mySpinner.setSelection(((ArrayAdapter)mySpinner.getAdapter()).getPosition(minutes + " minutes"));
    }

    public void parseSettings(View view)
    {
        // read in whether auto saving is enabled.
        CheckBox cb1 = (CheckBox) findViewById(R.id.toggle_autosave);
        autoSave = cb1.isChecked();
        // read in the frequency of auto-saving.
        Spinner mySpinner = (Spinner) findViewById(R.id.timerchoice);
        String timerChoiceRawText = mySpinner.getSelectedItem().toString();
        if (timerChoiceRawText.matches("2 minutes"))
        {
            seconds = 120;
        }
        if (timerChoiceRawText.matches("5 minutes"))
        {
            seconds = 300;
        }
        if (timerChoiceRawText.matches("10 minutes"))
        {
            seconds = 600;
        }
        if (timerChoiceRawText.matches("20 minutes"))
        {
            seconds = 1200;
        }
        try {
            writeSettingsToFile(autoSave, seconds);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }

    public void writeSettingsToFile(boolean autoSave, int seconds) throws IOException
    {
        File writeDirectory = new File(Environment.getExternalStorageDirectory(), DataStore.DATA_FOLDER_NAME);
        if (!writeDirectory.exists())
        {
            writeDirectory.mkdir();
        }
        else
        {
            File log = new File(writeDirectory, "autosave.coda");
            if(!log.exists())
            {
                log.createNewFile();
            }
            PrintWriter waffleryebread = new PrintWriter(new FileWriter(log));
            if (autoSave)
            {
                waffleryebread.println("y");
            }
            else
            {
                waffleryebread.println("n");
            }
            waffleryebread.println(seconds);
            waffleryebread.println("\n");
            waffleryebread.flush();
            waffleryebread.close();
        }
        DataStore.parseAutoSaveBoolean();
        DataStore.parseAutoSaveTime();
    }
}