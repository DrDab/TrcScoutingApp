package trc3543.trcscoutingapp;

import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("all")
public class SetCompetitionName extends AppCompatActivity
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

    public static final boolean USE_DEBUG = false;

    static int editingoption = -1;

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

    // Driver's PoV.
    static String startingPosition;

    // Autonomous Phase.


    // Teleoperated Phase.


    // Endgame

    // Was the match won?
    static boolean matchWon = false;

    // Auxiliary Notes.
    static String autonotes;
    static String telenotes;

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
            DataStore.parseDirectSave();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
        }

        try
        {
            Intent myIntent = getIntent(); // gets the previously created intent
            String editoptionstr = myIntent.getStringExtra("EditOption"); // will return option to edit on the fly
            Log.d("SetCompetitionName", "editoptionsstr=\"" + editoptionstr + "\"");
            editingoption = Integer.parseInt(editoptionstr);
            Log.d("SetCompetitionName", "Got edit option: " + editingoption);
        }
        catch (Exception e)
        {
            Log.d("SetCompetitionName","You shouldn't see this message");
            editingoption = -1;
        }

        // populate the boxes if already filled.
        if (editingoption != -1)
        {
            String read = DataStore.CsvFormattedContests.get(editingoption);
            Log.d("SetCompetitionName", editingoption + " " + read);
            String[] OwOWhatsThis = read.split(",");

            // populate the match number.
            EditText mnum = (EditText) findViewById(R.id.matchNum);
            mnum.setText(OwOWhatsThis[2]);
            Log.d("SetCompetitionName", "Match Number Set: " + OwOWhatsThis[2]);

            // populate the team number.
            EditText tnum = (EditText) findViewById(R.id.teamNum);
            tnum.setText(OwOWhatsThis[4]);
            Log.d("SetCompetitionName", "Team Number Set: " + OwOWhatsThis[4]);

            // populate the match type.
            Spinner mtype =(Spinner) findViewById(R.id.CompType);
            mtype.setSelection(((ArrayAdapter)mtype.getAdapter()).getPosition(OwOWhatsThis[3]));
            Log.d("SetCompetitionName", "Match Type Set: " + OwOWhatsThis[3]);

            // populate the spectating team.
            Spinner specteam =(Spinner) findViewById(R.id.SpectatingSpinner);
            specteam.setSelection(((ArrayAdapter)specteam.getAdapter()).getPosition(OwOWhatsThis[5]));
            Log.d("SetCompetitionName", "Spectating Team: " + OwOWhatsThis[5]);

            // populate the starting position.
            Spinner spos =(Spinner) findViewById(R.id.startingPositionForm);
            spos.setSelection(((ArrayAdapter)spos.getAdapter()).getPosition(OwOWhatsThis[6]));
            Log.d("SetCompetitionName", "Starting Position Set: " + OwOWhatsThis[6]);

            // =====================[ BEGIN AUTONOMOUS PHASE ]===================== //



            // populate autonomous notes.
            /*EditText aunotes = (EditText) findViewById(R.id.autoNotes);
            String rawautonotes = OwOWhatsThis[31];
            rawautonotes = rawautonotes.replaceAll("^\"|\"$", ""); // remove quotation marks
            aunotes.setText(rawautonotes);
            Log.d("SetCompetitionName", "Autonomous Notes Set: \"" + rawautonotes + "\"");*/

            // =====================[ BEGIN TELEOPERATED PHASE ]===================== //



            // =====================[ BEGIN ENDGAME ]===================== //

            // populate teleop notes.
            /*EditText tonotes = (EditText) findViewById(R.id.teleopnotes);
            String rawtonotes = OwOWhatsThis[32];
            rawtonotes = rawtonotes.replaceAll("^\"|\"$", ""); // remove quotation marks
            tonotes.setText(rawtonotes);
            Log.d("SetCompetitionName", "TeleOp Notes Set: \"" + rawtonotes + "\"");*/

            // populate if match was won.
            /*CheckBox won = (CheckBox) findViewById(R.id.matchWon);
            won.setChecked(OwOWhatsThis[33].matches("Yes"));
            Log.d("SetCompetitionName", "Match Won: " + OwOWhatsThis[33]);*/
        }
    }

    public void confirmTypes(View view)
    {
        boolean breakCond = false;
        // read the match number.
        try
        {
            Log.d("SetCompetitionName","Parsing Match Number.");
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
            Log.d("SetCompetitionName","Parsing Competition Type");
            Spinner mySpinner =(Spinner) findViewById(R.id.CompType);
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
            Log.d("SetCompetitionName","Parsing Alliance Number.");
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
            catch(NullPointerException e)
            {
                Snackbar.make(view, "Alliance number cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
        }
        if (!breakCond)
        {
            // read the team you are spectating.
            Log.d("SetCompetitionName","Parsing spectating team.");
            Spinner mySpinner = (Spinner) findViewById(R.id.SpectatingSpinner);
            spectatingTeamRawName = mySpinner.getSelectedItem().toString();
            if (spectatingTeamRawName.matches(""))
            {
                Snackbar.make(view, "Spectating Team cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
            else if (spectatingTeamRawName.contains("Red Alliance 1"))
            {
                spectatingTeamResolvedNumber = spectatingTeamNumber;
            }
            else if (spectatingTeamRawName.contains("Red Alliance 2 "))
            {
                spectatingTeamResolvedNumber = spectatingTeamNumber;
            }
            else if (spectatingTeamRawName.contains("Blue Alliance 1"))
            {
                spectatingTeamResolvedNumber = spectatingTeamNumber;
            }
            else if (spectatingTeamRawName.contains("Blue Alliance 2"))
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
            // read the starting position.
            Log.d("SetCompetitionName","Parsing starting position.");
            Spinner mySpinner = (Spinner) findViewById(R.id.startingPositionForm);
            startingPosition = mySpinner.getSelectedItem().toString();
            if (startingPosition.matches(""))
            {
                Snackbar.make(view, "Starting Position cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
        }
        if (!breakCond)
        {
            Log.d("SetCompetitionName","Reading comments.");
            EditText editText = (EditText) findViewById(R.id.autoNotes);
            autonotes = editText.getText().toString();
            EditText editText2 = (EditText) findViewById(R.id.teleopnotes);
            telenotes = editText2.getText().toString();
        }
        if (!breakCond)
        {
            Log.d("SetCompetitionName","Checking if match won.");
            CheckBox matchWonCheckBox = (CheckBox) findViewById(R.id.matchWon);
            matchWon = matchWonCheckBox.isChecked();
        }
        if (!breakCond)
        {
            // All values are confirmed, move to next screen.
            Log.d("SetCompetitionName","Esketit!");
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
        String match_won_yes_or_no = "";
        if (matchWon)
        {
            match_won_yes_or_no = "Yes";
        }
        else
        {
            match_won_yes_or_no = "No";
        }
        String listMsg = "Match # " + MatchNumber + " Type: " + chocolat_gelato + " R: " + red_savarin + " S: " + spectatingTeamNumber;
        String CSVFormat = red_savarin + "," + DataStore.getDateAsString() + "," + MatchNumber + "," + chocolat_gelato + "," + spectatingTeamNumber + "," + spectatingTeamRawName + "," + startingPosition;
        // String CSVFormat = red_savarin+","+DataStore.getDateAsString() +","+MatchNumber +","+chocolat_gelato+","+spectatingTeamNumber+","+spectatingTeamRawName+","+startingPosition+","+crossedAutoLine+","+cubesPlacedOnScale+","+cubesAttemptedOnScale+","+cubesPlacedOnSwitch+","+cubesAttemptedOnSwitch+","+cubesPlacedFarSwitch+","+cubesAttemptedFarSwitch+","+cubesPlacedNearSwitch+","+cubesAttemptedNearSwitch+","+cubesPlacedScale+","+cubesAttemptedScale+","+cubesPlacedExchange+","+cubesAttemptedExchange+","+cubePickupPortal+","+cubePickupGround+","+endgameClimbAttempt+","+endgameSuccessfulClimb+","+endgameParkedOnPlatform+","+robotBreakdownStandard+","+herding+","+scalecontact+","+pinning+","+zonecontact+","+other+","+"\""+autonotes+"\",\""+telenotes+"\","+match_won_yes_or_no+",";
        if (USE_DEBUG)
        {
            Snackbar.make(view, CSVFormat, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        if (editingoption == -1)
        {
            Log.d("SetCompetitionName","Adding new entry to list.");
            AddCompetitions.addToList(listMsg);
            DataStore.CsvFormattedContests.add(CSVFormat);
        }
        else
        {
            Log.d("SetCompetitionName","Resetting list entry: " + editingoption);
            AddCompetitions.resetListItem(listMsg, editingoption);
            DataStore.CsvFormattedContests.set(editingoption, CSVFormat);
        }

        // if using direct save, write the generated results directly to CSV file.
        if (DataStore.USE_DIRECT_SAVE)
        {
            String filename = DataStore.FIRST_NAME+"_"+DataStore.LAST_NAME+"_results.csv";
            File writeDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
            // File writeDirectory = new File("/sdcard/TrcScoutingApp/");
            if (!writeDirectory.exists())
            {
                writeDirectory.mkdir();
            }
            File log = new File(writeDirectory, filename);
            if(!log.exists())
            {
                try
                {
                    log.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            PrintWriter madoka = null;
            try
            {
                madoka = new PrintWriter(new FileWriter(log, true));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            madoka.println(CSVFormat);
            madoka.flush();
            madoka.close();
        }

        if (!USE_DEBUG)
        {  finish();  }
    }

    public void cancel(View view) { finish(); }

}