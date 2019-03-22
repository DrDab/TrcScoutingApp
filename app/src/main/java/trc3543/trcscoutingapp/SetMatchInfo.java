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
public class SetMatchInfo extends AppCompatActivity
{
    public static final boolean USE_DEBUG = false;

    private int editingoption = -1;

    private int teamNumber;
    private String teamName;

    private String driveTrain;

    // Game-specific variables.
    private boolean highCargo;
    private boolean highHatch;
    private boolean midCargo;
    private boolean midHatch;
    private boolean lowCargo;
    private boolean lowHatch;
    private boolean hatchesFromGround;
    private String deliveryPreference;

    private String offenseDefense;

    private int maxClimb;
    private String programmingLanguage;

    private String topFeatures;
    private String otherNotes;

    // sandstorm.
    private String opmode;
    private boolean startFromLevel2;

    // End game-specific variables.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_match_info2);
        setTitle("Add Match");
        try
        {
            DataStore.parseUserInfoGeneral();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
        }

        try
        {
            Intent myIntent = getIntent();
            String editoptionstr = myIntent.getStringExtra("EditOption");
            Log.d("SetMatchInfo", "editoptionsstr=\"" + editoptionstr + "\"");
            editingoption = Integer.parseInt(editoptionstr);
            Log.d("SetMatchInfo", "Got edit option: " + editingoption);
        }
        catch (Exception e)
        {
            Log.d("SetMatchInfo","You shouldn't see this message");
            editingoption = -1;
        }

        // populate the boxes if already filled.
        if (editingoption != -1)
        {
            String read = DataStore.matchList.get(editingoption).getCsvString();
            Log.d("SetMatchInfo", editingoption + " " + read);
            String[] OwOWhatsThis = read.split(",");

            for (int i = 0; i < OwOWhatsThis.length; i++)
            {
                Log.d("SetMatchInfo", i + ")" + OwOWhatsThis[i]);
            }

            EditText teamNumberET = (EditText) findViewById(R.id.teamNumber);
            teamNumberET.setText(OwOWhatsThis[0]);

            EditText teamNameET = (EditText) findViewById(R.id.teamName);
            String rawTeamName = OwOWhatsThis[1].replaceAll("^\"|\"$", ""); // remove quotation marks
            teamNameET.setText(DataStore.unescapeFormat(rawTeamName));

            Spinner driveBaseSpinner = (Spinner) findViewById(R.id.driveBase);
            driveBaseSpinner.setSelection(((ArrayAdapter)driveBaseSpinner.getAdapter()).getPosition(OwOWhatsThis[2]));

            CheckBox highCargoCB = (CheckBox) findViewById(R.id.highCargo);
            highCargoCB.setChecked(Boolean.parseBoolean(OwOWhatsThis[3]));

            CheckBox highHatchCB = (CheckBox) findViewById(R.id.highHatch);
            highHatchCB.setChecked(Boolean.parseBoolean(OwOWhatsThis[4]));

            CheckBox midCargoCB = (CheckBox) findViewById(R.id.midCargo);
            midCargoCB.setChecked(Boolean.parseBoolean(OwOWhatsThis[5]));

            CheckBox midHatchCB = (CheckBox) findViewById(R.id.midHatch);
            midHatchCB.setChecked(Boolean.parseBoolean(OwOWhatsThis[6]));

            CheckBox lowCargoCB = (CheckBox) findViewById(R.id.lowCargo);
            lowCargoCB.setChecked(Boolean.parseBoolean(OwOWhatsThis[7]));

            CheckBox lowHatchCB = (CheckBox) findViewById(R.id.lowHatch);
            lowHatchCB.setChecked(Boolean.parseBoolean(OwOWhatsThis[8]));

            CheckBox hatchesFromGroundCheckBox = (CheckBox) findViewById(R.id.hatchesFromGround);
            hatchesFromGroundCheckBox.setChecked(Boolean.parseBoolean(OwOWhatsThis[9]));

            Spinner playStyleSpinner =(Spinner) findViewById(R.id.robotPlaystyle);
            playStyleSpinner.setSelection(((ArrayAdapter)playStyleSpinner.getAdapter()).getPosition(OwOWhatsThis[10]));

            Spinner climbLevel = (Spinner) findViewById(R.id.robotClimbLevel);
            climbLevel.setSelection(Integer.parseInt(OwOWhatsThis[11]));

            Spinner programmingLanguageSpinner = (Spinner) findViewById(R.id.robotProgrammingLanguage);
            programmingLanguageSpinner.setSelection(((ArrayAdapter)programmingLanguageSpinner.getAdapter()).getPosition(OwOWhatsThis[12]));

            Spinner deliveryPreferenceSpinner = (Spinner) findViewById(R.id.robotDeliveryPreference);
            deliveryPreferenceSpinner.setSelection(((ArrayAdapter)deliveryPreferenceSpinner.getAdapter()).getPosition(OwOWhatsThis[13]));

            EditText topFeaturesET = (EditText) findViewById(R.id.topFeatures);
            String rawTopFeatures = DataStore.unescapeFormat(OwOWhatsThis[14].replaceAll("^\"|\"$", "")); // remove quotation marks
            topFeaturesET.setText(rawTopFeatures);

            EditText othernotesET = (EditText) findViewById(R.id.otherNotes);
            String rawOtherNotes = DataStore.unescapeFormat(OwOWhatsThis[15].replaceAll("^\"|\"$", "")); // remove quotation marks
            othernotesET.setText(rawOtherNotes);

            Spinner sandstormOpmodeSpinner =(Spinner) findViewById(R.id.sandstormOpMode);
            sandstormOpmodeSpinner.setSelection(((ArrayAdapter)sandstormOpmodeSpinner.getAdapter()).getPosition(OwOWhatsThis[16]));

            CheckBox lvl2CB = (CheckBox) findViewById(R.id.sandstormStartFromLevel2);
            lvl2CB.setChecked(Boolean.parseBoolean(OwOWhatsThis[17]));
        }
    }

    public void confirmTypes(View view)
    {
        boolean breakCond = false;

        if (!breakCond)
        {
            try
            {
                Log.d("SetMatchInfo","Parsing Team Number.");
                EditText editText = (EditText) findViewById(R.id.teamNumber);
                teamNumber = Integer.parseInt(editText.getText().toString());
            }
            catch(NumberFormatException e)
            {
                Snackbar.make(view, "Issue with Team number Formatting", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
            catch(NullPointerException e)
            {
                Snackbar.make(view, "Team number cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }
        }

        if (!breakCond)
        {
            Log.d("SetMatchInfo","Parsing Team Name.");
            EditText editText = (EditText) findViewById(R.id.teamName);
            teamName = editText.getText().toString();
        }

        if (!breakCond)
        {
            Log.d("SetMatchInfo","Parsing Drivebase Type");
            Spinner mySpinner =(Spinner) findViewById(R.id.driveBase);
            driveTrain = mySpinner.getSelectedItem().toString();
        }

        if (!breakCond)
        {
            CheckBox highCargoCB = (CheckBox) findViewById(R.id.highCargo);
            highCargo = highCargoCB.isChecked();
            CheckBox highHatchCB = (CheckBox) findViewById(R.id.highHatch);
            highHatch = highHatchCB.isChecked();

            CheckBox midCargoCB = (CheckBox) findViewById(R.id.midCargo);
            midCargo = midCargoCB.isChecked();
            CheckBox midHatchCB = (CheckBox) findViewById(R.id.midHatch);
            midHatch = midHatchCB.isChecked();

            CheckBox lowCargoCB = (CheckBox) findViewById(R.id.lowCargo);
            lowCargo = lowCargoCB.isChecked();
            CheckBox lowHatchCB = (CheckBox) findViewById(R.id.lowHatch);
            lowHatch = lowHatchCB.isChecked();

            CheckBox hatchesFromGroundCheckBox = (CheckBox) findViewById(R.id.hatchesFromGround);
            hatchesFromGround = hatchesFromGroundCheckBox.isChecked();

            Spinner playStyleSpinner =(Spinner) findViewById(R.id.robotPlaystyle);
            offenseDefense = playStyleSpinner.getSelectedItem().toString();

            Spinner climbLevelCB = (Spinner) findViewById(R.id.robotClimbLevel);
            maxClimb = Integer.parseInt(climbLevelCB.getSelectedItem().toString());

            Spinner programmingLanguageCB = (Spinner) findViewById(R.id.robotProgrammingLanguage);
            programmingLanguage = programmingLanguageCB.getSelectedItem().toString();

            Spinner deliveryPreferenceCB = (Spinner) findViewById(R.id.robotDeliveryPreference);
            deliveryPreference = deliveryPreferenceCB.getSelectedItem().toString();

            EditText topFeaturesET = (EditText) findViewById(R.id.topFeatures);
            topFeatures = topFeaturesET.getText().toString();

            EditText othernotesET = (EditText) findViewById(R.id.otherNotes);
            otherNotes = othernotesET.getText().toString();

            Spinner sandstormOpmodeSpinner =(Spinner) findViewById(R.id.sandstormOpMode);
            opmode = sandstormOpmodeSpinner.getSelectedItem().toString();

            CheckBox lvl2CB = (CheckBox) findViewById(R.id.sandstormStartFromLevel2);
            startFromLevel2 = lvl2CB.isChecked();
        }

        if (!breakCond)
        {
            // All values are confirmed, move to next screen.
            Log.d("SetMatchInfo","Esketit!");
            moveToNextScreen(view);
        }
    }

    public void moveToNextScreen(View view)
    {
        String containsOwnTeam = "";

        String listMsg = String.format("Team # %d (%s)", teamNumber, teamName);
        String CSVFormat = (teamNumber + ",\"" + DataStore.escapeFormat(teamName) + "\"," + driveTrain + "," + highCargo + "," + highHatch + "," + midCargo + "," + midHatch + "," + lowCargo + "," + lowHatch + "," + hatchesFromGround + "," + offenseDefense + "," + maxClimb + "," + programmingLanguage + "," + deliveryPreference + ",\""  + DataStore.escapeFormat(topFeatures) + "\",\"" + DataStore.escapeFormat(otherNotes) + "\"," + opmode + "," + startFromLevel2);
        Log.d("SetMatchInfo", CSVFormat);

        if (USE_DEBUG)
        {
            Snackbar.make(view, CSVFormat, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        if (editingoption == -1)
        {
            Log.d("SetMatchInfo","Adding new entry to list.");
            AddMatches.addToList(listMsg, CSVFormat);
        }
        else
        {
            Log.d("SetMatchInfo","Resetting list entry: " + editingoption);
            AddMatches.resetListItem(listMsg, CSVFormat, editingoption);
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

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP))
        {
            clockState = !clockState;
            if (clockState)
            {
                startClock.setText("Stop");
                stopwatchHandler.sendEmptyMessage(MSG_START_TIMER);
            }
            else
            {
                startClock.setText("Start");
                stopwatchHandler.sendEmptyMessage(MSG_STOP_TIMER);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    */

}