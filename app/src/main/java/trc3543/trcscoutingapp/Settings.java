package trc3543.trcscoutingapp;

import android.graphics.Color;
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
        File writeDirectory = new File("/sdcard/TrcScoutingApp/");
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
        finish();
    }
}