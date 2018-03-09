package trc3543.trcscoutingapp;

import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    static String crossedAutoLine;

    static int cubesPlacedOnScale;
    static int cubesAttemptedOnScale;

    static int cubesPlacedOnSwitch;
    static int cubesAttemptedOnSwitch;

    // Teleoperated Phase.
    static int cubesPlacedFarSwitch;
    static int cubesAttemptedFarSwitch;

    static int cubesPlacedNearSwitch;
    static int cubesAttemptedNearSwitch;

    static int cubesPlacedScale;
    static int cubesAttemptedScale;

    static int cubesPlacedExchange;
    static int cubesAttemptedExchange;

    static String cubePickupPortal;
    static String cubePickupGround;

    // Endgame
    static String endgameClimbAttempt;
    static String endgameSuccessfulClimb;
    static String endgameParkedOnPlatform;

    // Robert breakdown
    static String robotBreakdownStandard;

    // Robert penalties
    static String herding;
    static String scalecontact;
    static String pinning;
    static String zonecontact;
    static String other;
    static int penaltyPoints;

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
            Spinner mySpinner = (Spinner) findViewById(R.id.SpectatingSpinner);
            spectatingTeamRawName = mySpinner.getSelectedItem().toString();
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
            // read the starting position.
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
            // read the Autonomous info.
            Spinner autoLineSpinner = (Spinner) findViewById(R.id.crossedTheAutoLine);
            crossedAutoLine = autoLineSpinner.getSelectedItem().toString();

            Spinner cubesPlacedScaleSpinner = (Spinner) findViewById(R.id.cubesPlacedOnScaleAuto);
            cubesPlacedOnScale = Integer.parseInt(cubesPlacedScaleSpinner.getSelectedItem().toString());

            Spinner cubesAttemptedScaleSpinner = (Spinner) findViewById(R.id.cubesAttemptedOnScaleAuto);
            cubesAttemptedOnScale = Integer.parseInt(cubesAttemptedScaleSpinner.getSelectedItem().toString());

            Spinner cubesPlacedSwitchSpinner = (Spinner) findViewById(R.id.cubesPlacedOnSwitchAuto);
            cubesPlacedOnSwitch = Integer.parseInt(cubesPlacedSwitchSpinner.getSelectedItem().toString());

            Spinner cubesAttemptedSwitchSpinner = (Spinner) findViewById(R.id.cubesAttemptedOnSwitchAuto);
            cubesAttemptedOnSwitch = Integer.parseInt(cubesAttemptedSwitchSpinner.getSelectedItem().toString());
        }
        if (!breakCond)
        {
            // read the TeleOp info.
            Spinner cubesPlacedFarSwitchSpinner = (Spinner) findViewById(R.id.cubesPlacedFarSwitchTeleOp);
            cubesPlacedFarSwitch = Integer.parseInt(cubesPlacedFarSwitchSpinner.getSelectedItem().toString());

            Spinner cubesAttemptedFarSwitchSpinner = (Spinner) findViewById(R.id.cubesAttemptedFarSwitchTeleOp);
            cubesAttemptedFarSwitch = Integer.parseInt(cubesAttemptedFarSwitchSpinner.getSelectedItem().toString());

            Spinner cubesPlacedNearSwitchSpinner = (Spinner) findViewById(R.id.cubesPlacedNearSwitchTeleOp);
            cubesPlacedNearSwitch = Integer.parseInt(cubesPlacedNearSwitchSpinner.getSelectedItem().toString());

            Spinner cubesAttemptedNearSwitchSpinner = (Spinner) findViewById(R.id.cubesAttemptedNearSwitchTeleOp);
            cubesAttemptedNearSwitch = Integer.parseInt(cubesAttemptedNearSwitchSpinner.getSelectedItem().toString());

            Spinner cubesPlacedScaleSpinner = (Spinner) findViewById(R.id.cubesPlacedScaleTeleOp);
            cubesPlacedScale = Integer.parseInt(cubesPlacedScaleSpinner.getSelectedItem().toString());

            Spinner cubesAttemptedScaleSpinner = (Spinner) findViewById(R.id.cubesAttemptedScaleTeleOp);
            cubesAttemptedScale = Integer.parseInt(cubesAttemptedScaleSpinner.getSelectedItem().toString());

            Spinner cubesPlacedExchangeSpinner = (Spinner) findViewById(R.id.cubesPlacedExchangeTeleOp);
            cubesPlacedExchange = Integer.parseInt(cubesPlacedExchangeSpinner.getSelectedItem().toString());

            Spinner cubesAttemptedExchangeSpinner = (Spinner) findViewById(R.id.cubesAttemptedScaleTeleOp);
            cubesAttemptedExchange = Integer.parseInt(cubesAttemptedExchangeSpinner.getSelectedItem().toString());

            CheckBox portalCheckBox = (CheckBox) findViewById(R.id.portalChecker);
            if (portalCheckBox.isChecked())
            {
                cubePickupPortal = "Yes";
            }
            else
            {
                cubePickupPortal = "No";
            }

            CheckBox groundCheckBox = (CheckBox) findViewById(R.id.groundChecker);
            if (groundCheckBox.isChecked())
            {
                cubePickupGround = "Yes";
            }
            else
            {
                cubePickupGround = "No";
            }

            // Endgame values.
            CheckBox climbAttemptSpinner = (CheckBox) findViewById(R.id.climbAttemptChecker);
            if (climbAttemptSpinner.isChecked())
            {
                endgameClimbAttempt = "Yes";
            }
            else
            {
                endgameClimbAttempt = "No";
            }

            CheckBox climbSUCCessSpinner = (CheckBox) findViewById(R.id.climbSuccessChecker);
            if (climbSUCCessSpinner.isChecked())
            {
                endgameSuccessfulClimb = "Yes";
            }
            else
            {
                endgameSuccessfulClimb = "No";
            }

            CheckBox platformParkingSpinner = (CheckBox) findViewById(R.id.parkOnPlatformChecker);
            if (platformParkingSpinner.isChecked())
            {
                endgameParkedOnPlatform = "Yes";
            }
            else
            {
                endgameParkedOnPlatform = "No";
            }
        }
        if (!breakCond)
        {
            // Robert breakdown stats.
            Spinner robotBreakdownSpinner = (Spinner) findViewById(R.id.robotBreakdownSpinner);
            robotBreakdownStandard = robotBreakdownSpinner.getSelectedItem().toString();
            // Penalties stats.
            CheckBox herdingSpinner = (CheckBox) findViewById(R.id.penaltyHerdingSpinner);
            if (herdingSpinner.isChecked())
            {
                herding = "Yes";
            }
            else
            {
                herding = "No";
            }

            CheckBox scaleContactSpinner = (CheckBox) findViewById(R.id.penaltyScaleContact);
            if (scaleContactSpinner.isChecked())
            {
                scalecontact = "Yes";
            }
            else
            {
                scalecontact = "No";
            }

            CheckBox pinningSpinner = (CheckBox) findViewById(R.id.penaltyPinning);
            if (pinningSpinner.isChecked())
            {
                pinning = "Yes";
            }
            else
            {
                pinning = "No";
            }

            CheckBox zoneContactSpinner = (CheckBox) findViewById(R.id.penaltyZoneContact);
            if (zoneContactSpinner.isChecked())
            {
                zonecontact = "Yes";
            }
            else
            {
                zonecontact = "No";
            }

            CheckBox otherFidgetSpinner = (CheckBox) findViewById(R.id.penaltyOther);
            if (otherFidgetSpinner.isChecked())
            {
                other = "Yes";
            }
            else
            {
                other = "No";
            }

            try
            {
                EditText editText = (EditText) findViewById(R.id.penaltyPoints);
                penaltyPoints = Integer.parseInt(editText.getText().toString());
            }
            catch(NumberFormatException e)
            {
                Snackbar.make(view, "Issue with penalty points input.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
            catch(NullPointerException e)
            {
                Snackbar.make(view, "Penalty points cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
        }
        if (!breakCond)
        {
            EditText editText = (EditText) findViewById(R.id.autoNotes);
            autonotes = editText.getText().toString();
            EditText editText2 = (EditText) findViewById(R.id.teleopnotes);
            telenotes = editText2.getText().toString();
        }
        if (!breakCond)
        {
            CheckBox matchWonCheckBox = (CheckBox) findViewById(R.id.matchWon);
            matchWon = matchWonCheckBox.isChecked();
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
        String CSVFormat = red_savarin+","+DataStore.getDateAsString() +","+MatchNumber +","+chocolat_gelato+","+spectatingTeamNumber+","+spectatingTeamRawName+","+startingPosition+", ,"+crossedAutoLine+","+cubesPlacedOnScale+","+cubesAttemptedOnScale+","+cubesPlacedOnSwitch+","+cubesAttemptedOnSwitch+", ,"+cubesPlacedFarSwitch+","+cubesAttemptedFarSwitch+","+cubesPlacedNearSwitch+","+cubesAttemptedNearSwitch+","+cubesPlacedScale+","+cubesAttemptedScale+","+cubesPlacedExchange+","+cubesAttemptedExchange+","+cubePickupPortal+","+cubePickupGround+", ,"+endgameClimbAttempt+","+endgameSuccessfulClimb+","+endgameParkedOnPlatform+","+robotBreakdownStandard+", ,"+herding+","+scalecontact+","+pinning+","+zonecontact+","+other+","+penaltyPoints+","+autonotes+","+telenotes;
        if (USE_DEBUG)
        {
            Snackbar.make(view, CSVFormat, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        AddCompetitions.addToList(listMsg);
        DataStore.CsvFormattedContests.add(CSVFormat);

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
