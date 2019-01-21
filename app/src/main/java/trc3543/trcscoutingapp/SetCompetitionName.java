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

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;

@SuppressWarnings("all")
public class SetCompetitionName extends AppCompatActivity
{
    public static final boolean USE_DEBUG = false;

    static int editingoption = -1;

    static int matchNumber;
    static String competitionName;

    static String matchType;

    static String spectatingTeamFieldPosition;

    static int spectatingTeamNumber;

    // Driver's PoV.
    static String startingPosition;

    // Autonomous Phase.


    // Teleoperated Phase.


    // Endgame
    static String endingLocation = "";

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
        setTitle("Add Match");
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

        try
        {
            Intent myIntent = getIntent();
            String editoptionstr = myIntent.getStringExtra("EditOption");
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
            String read = DataStore.matchList.get(editingoption).getCsvString();
            Log.d("SetCompetitionName", editingoption + " " + read);
            String[] OwOWhatsThis = read.split(",");

            // populate the match number.
            EditText mnum = (EditText) findViewById(R.id.matchNum);
            mnum.setText(OwOWhatsThis[2]);
            Log.d("SetCompetitionName", "Match Number Set: " + OwOWhatsThis[2]);

            // populate the match type.
            Spinner mtype =(Spinner) findViewById(R.id.CompType);
            mtype.setSelection(((ArrayAdapter)mtype.getAdapter()).getPosition(OwOWhatsThis[3]));
            Log.d("SetCompetitionName", "Match Type Set: " + OwOWhatsThis[3]);

            // populate the team number.
            EditText tnum = (EditText) findViewById(R.id.teamNum);
            tnum.setText(OwOWhatsThis[4]);
            Log.d("SetCompetitionName", "Team Number Set: " + OwOWhatsThis[4]);

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
            EditText aunotes = (EditText) findViewById(R.id.autoNotes);
            String rawautonotes = OwOWhatsThis[9];
            rawautonotes = rawautonotes.replaceAll("^\"|\"$", ""); // remove quotation marks
            aunotes.setText(rawautonotes);
            Log.d("SetCompetitionName", "Autonomous Notes Set: \"" + rawautonotes + "\"");

            // =====================[ BEGIN TELEOPERATED PHASE ]===================== //



            // =====================[ BEGIN ENDGAME ]===================== //

            Spinner endingLocation =(Spinner) findViewById(R.id.endingLocation);
            endingLocation.setSelection(((ArrayAdapter)endingLocation.getAdapter()).getPosition(OwOWhatsThis[7]));

            // populate if match was won.
            CheckBox matchWon = (CheckBox) findViewById(R.id.matchWon);
            matchWon.setChecked(OwOWhatsThis[8].contains("Yes"));

            // populate teleop notes.
            EditText tonotes = (EditText) findViewById(R.id.teleopnotes);
            String rawtonotes = OwOWhatsThis[10];
            rawtonotes = rawtonotes.replaceAll("^\"|\"$", ""); // remove quotation marks
            tonotes.setText(rawtonotes);

            // =====================[ END ENDGAME + TELEOPERATED PHASE ]===================== //
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
            matchNumber = Integer.parseInt(editText.getText().toString());
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
            matchType = mySpinner.getSelectedItem().toString();
            if (matchType.matches(""))
            {
                Snackbar.make(view, "Match type cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
            spectatingTeamFieldPosition = mySpinner.getSelectedItem().toString();
            if (spectatingTeamFieldPosition.matches(""))
            {
                Snackbar.make(view, "Spectating Team cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
            // read autonomous stuff.

        }
        if (!breakCond)
        {
            // read teleop stuff.


            // read endgame stuff.
            Spinner endLocationSpinner = (Spinner) findViewById(R.id.endingLocation);
            endingLocation = endLocationSpinner.getSelectedItem().toString();
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
        String containsOwnTeam = "";
        if (spectatingTeamNumber == DataStore.selfTeamNumber)
        {
            containsOwnTeam = "*";
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

        String listMsg = String.format("Match # %d (%s) Team: %d", matchNumber, matchType, spectatingTeamNumber);
        String CSVFormat = containsOwnTeam + "," + DataStore.getDateAsString() + "," + matchNumber + "," + matchType + "," + spectatingTeamNumber + "," + spectatingTeamFieldPosition + "," + startingPosition+","+endingLocation+","+match_won_yes_or_no+",\""+autonotes+"\",\""+telenotes+"\"";

        if (USE_DEBUG)
        {
            Snackbar.make(view, CSVFormat, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        if (editingoption == -1)
        {
            Log.d("SetCompetitionName","Adding new entry to list.");
            AddCompetitions.addToList(listMsg, CSVFormat);
        }
        else
        {
            Log.d("SetCompetitionName","Resetting list entry: " + editingoption);
            AddCompetitions.resetListItem(listMsg, CSVFormat, editingoption);
        }

        try
        {
            DataStore.writeArraylistsToJSON();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (!USE_DEBUG)
        {
            finish();
        }
    }

    public void cancel(View view)
    {
        finish();
    }

    /**
     * This code snippet written by ZMan; may great honor be laid upon this act of chivalry:
     *
     * Answer: https://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside
     * User: https://stackoverflow.com/users/1591623/zman
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            View v = getCurrentFocus();
            if (v instanceof EditText)
            {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY()))
                {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}