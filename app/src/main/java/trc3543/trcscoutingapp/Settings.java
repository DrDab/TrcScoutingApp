package trc3543.trcscoutingapp;

import android.graphics.Color;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("all")
public class Settings extends AppCompatActivity
{
    /**
     *
     *  Copyright (c) 2018 Titan Robotics Club, _c0da_ (Victor Du)
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

    static String first_name;
    static String last_name;
    static int teamNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        setTitleColor(Color.parseColor("#ff669900"));

        EditText teamNumForm = (EditText) findViewById(R.id.teamNumForm);
        EditText firstNameForm = (EditText) findViewById(R.id.firstNameForm);
        EditText lastNameForm = (EditText) findViewById(R.id.lastNameForm);
        CheckBox saveDirectlyCheckBox = (CheckBox) findViewById(R.id.saveDirectlyCheckBox);

        teamNumForm.setText(DataStore.selfTeamNumber + "");
        firstNameForm.setText(DataStore.firstName);
        lastNameForm.setText(DataStore.lastName);
        saveDirectlyCheckBox.setChecked(DataStore.USE_DIRECT_SAVE);

        // AddCompetitions.verifyStoragePermissions(this);
    }

    public void parseSettings(View v)
    {
        // read in name and team number from onClick in activity_settings.xml
        boolean breakCond = false;
        boolean breakCond2 = false;
        boolean breakCond3 = false;
        // read the team number.
        try
        {
            EditText editText = (EditText) findViewById(R.id.teamNumForm);
            teamNumber = Integer.parseInt(editText.getText().toString());
        }
        catch(NumberFormatException e)
        {
            Snackbar.make(v, "Issue with Team number Formatting", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            breakCond = true;
        }
        catch(NullPointerException e)
        {
            Snackbar.make(v, "Team number cannot be empty.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            breakCond = true;
        }
        if (!breakCond)
        {
            // read the first name.
            try
            {
                EditText editText = (EditText) findViewById(R.id.firstNameForm);
                first_name = editText.getText().toString();
                if (first_name.length() == 0)
                {
                    Snackbar.make(v, "First name cannot be empty.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    breakCond2 = true;
                }
            }
            catch(NullPointerException e)
            {
                Snackbar.make(v, "First name cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond2 = true;
            }
        }
        if (!breakCond2)
        {
            // read the last name.
            try
            {
                EditText editText = (EditText) findViewById(R.id.lastNameForm);
                last_name = editText.getText().toString();
                if (last_name.length() == 0)
                {
                    Snackbar.make(v, "Last name cannot be empty.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    breakCond3 = true;
                }
            }
            catch(NullPointerException e)
            {
                Snackbar.make(v, "Last name cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond3 = true;
            }
        }
        if (!breakCond && !breakCond2 && !breakCond3)
        {
            // read whether to save directly to file upon confirming Game results
            CheckBox cb1 = (CheckBox) findViewById(R.id.saveDirectlyCheckBox);
            boolean saveDirectly = cb1.isChecked();
            // export the settings to a file.
            try
            {
                writeSettingsToFile(first_name, last_name, teamNumber, saveDirectly);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    public void writeSettingsToFile(String firstname, String lastname, int teamNum, boolean saveDirectly) throws IOException
    {
        File writeDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
        if (!writeDirectory.exists())
        {
            writeDirectory.mkdir();
        }
        else
        {
            String saveReading = saveDirectly ? "y" : "n";
            File log = new File(writeDirectory, "settings.coda");
            if(!log.exists())
            {
                log.createNewFile();
            }
            PrintWriter waffleryebread = new PrintWriter(new FileWriter(log));
            waffleryebread.println(teamNum);
            waffleryebread.println(firstname);
            waffleryebread.println(lastname);
            waffleryebread.println(saveReading);
            waffleryebread.println("\n");
            waffleryebread.flush();
            waffleryebread.close();
        }
        DataStore.parseTeamNum();
        DataStore.parseFirstName();
        DataStore.parseLastName();
        DataStore.parseDirectSave();
        finish();
    }
}
