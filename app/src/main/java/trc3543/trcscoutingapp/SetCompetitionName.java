package trc3543.trcscoutingapp;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;

@SuppressWarnings("all")
public class SetCompetitionName extends AppCompatActivity
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

    public static final boolean USE_DEBUG = false;

    static int MatchNumber;
    static String competitionName;
    
    /**
     * Competition Type
     * 1 = Practice
     * 2 = Qualification
     * 3 = Semi-Final
     * 4 = Final
     *
     * @author Victor Du
     */
    static int competitionType;
    
    static String competitionTypeRawName;

    static String spectatingTeamRawName;
    static int spectatingTeamResolvedNumber;

    static int spectatingTeamNumber;    // the team # for teh team you are spectating

    static boolean sampleCond1 = false;
    static boolean sampleCond2 = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_competition_name2);
        try
        {
            DataStore.parseTeamNum();
            DataStore.parseFirstName();
            DataStore.parseLastName();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
        }

    }

    public void confirmTypes(View view)
    {
        boolean breakCond = false;
        // read the match number.
        try
        {
            EditText editText = (EditText) findViewById(R.id.matchNum);
            MatchNumber = Integer.parseInt(editText.getText().toString());
        }
        catch(NumberFormatException e)
        {
            Snackbar.make(view, "Issue with Match number Formatting", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            breakCond = true;
        }
        catch(NullPointerException e)
        {
            Snackbar.make(view, "Match number cannot be empty.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            breakCond = true;
        }
        if (!breakCond)
        {
            // read the competition type.
            Spinner mySpinner=(Spinner) findViewById(R.id.CompType);
            competitionTypeRawName = mySpinner.getSelectedItem().toString();
            if (competitionTypeRawName.matches(""))
            {
                Snackbar.make(view, "Competition Type cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
            else if (competitionTypeRawName.matches("Practice"))
            {
                competitionType = 1;
            }
            else if (competitionTypeRawName.matches("Qualification"))
            {
                competitionType = 2;
            }
            else if (competitionTypeRawName.matches("Semi-Final"))
            {
                competitionType = 3;
            }
            else if (competitionTypeRawName.matches("Final"))
            {
                competitionType = 4;
            }
            else
            {
                // We have an impossible scenario, where no competition type is selected. [Practice should be selected by default] (Nom d'un chien! - Red Savarin)
                breakCond = true;
            }
        }
        if (!breakCond)
        {
            // read the alliance teams.
            try
            {
                EditText editText = (EditText) findViewById(R.id.teamNum);
                spectatingTeamNumber = Integer.parseInt(editText.getText().toString());
            }
            catch(NumberFormatException e)
            {
                Snackbar.make(view, "Issue with alliance number Formatting", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
            catch(NullPointerException e) {
                Snackbar.make(view, "Alliance number cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
        }
        if (!breakCond)
        {
            // read the team you are spectating.
            Spinner mySpinner=(Spinner) findViewById(R.id.SpectatingSpinner);
            spectatingTeamRawName= mySpinner.getSelectedItem().toString();
            if (spectatingTeamRawName.matches(""))
            {
                Snackbar.make(view, "Spectating Team cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
            else if (spectatingTeamRawName.matches("Red Alliance 1"))
            {
                spectatingTeamResolvedNumber = spectatingTeamNumber;
            }
            else if (spectatingTeamRawName.matches("Red Alliance 2"))
            {
                spectatingTeamResolvedNumber = spectatingTeamNumber;
            }
            else if (spectatingTeamRawName.matches("Red Alliance 3"))
            {
                spectatingTeamResolvedNumber = spectatingTeamNumber;
            }
            else if (spectatingTeamRawName.matches("Blue Alliance 1"))
            {
                spectatingTeamResolvedNumber = spectatingTeamNumber;
            }
            else if (spectatingTeamRawName.matches("Blue Alliance 2"))
            {
                spectatingTeamResolvedNumber = spectatingTeamNumber;
            }
            else if (spectatingTeamRawName.matches("Blue Alliance 3"))
            {
                spectatingTeamResolvedNumber = spectatingTeamNumber;
            }
            else
            {
                // We have an impossible scenario, where no spectating Team is selected. [Red Alliance 1 should be selected by default] (Nom d'un chien! - Red Savarin)
                breakCond = true;
            }
        }
        if (!breakCond)
        {
            // check the objectives won.
            CheckBox cb1 = (CheckBox) findViewById(R.id.conditionI);
            CheckBox cb2 = (CheckBox) findViewById(R.id.conditionII);
            sampleCond1 = cb1.isChecked();
            sampleCond2 = cb2.isChecked();
        }
        if (!breakCond)
        {
           // All values are confirmed, move to next screen.
            moveToNextScreen(view);
        }
    }

    public void moveToNextScreen(View view)
    {
        String red_savarin = "";
        if (spectatingTeamNumber == DataStore.SELF_TEAM_NUMBER)
        {
            red_savarin = "*";
        }
        String chocolat_gelato;
        if (competitionType == 1)
        {
            chocolat_gelato = "Practice";
        }
        else if (competitionType == 2)
        {
            chocolat_gelato = "Qualif.";
        }
        else if (competitionType == 3)
        {
            chocolat_gelato = "SemFinal";
        }
        else
        {
            chocolat_gelato = "Final";
        }
        String listMsg = "Match # " + MatchNumber + " Type: " + chocolat_gelato + " R: " + red_savarin + " S: " + spectatingTeamNumber;
        String CSVFormat = red_savarin+","+DataStore.getDateAsString() +","+MatchNumber +","+chocolat_gelato+","+spectatingTeamNumber+","+spectatingTeamRawName+","+sampleCond1+","+sampleCond2;
        if (USE_DEBUG)
        {
            Snackbar.make(view, CSVFormat, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        AddCompetitions.addToList(listMsg);
        DataStore.CsvFormattedContests.add(CSVFormat);
        if (!USE_DEBUG)
        {  finish();  }
    }

    public void cancel(View view) { finish(); }

}
