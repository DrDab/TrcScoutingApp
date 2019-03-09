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
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;

@SuppressWarnings("all")
public class SetMatchInfo extends AppCompatActivity
{
    public static final boolean USE_DEBUG = false;

    private int editingoption = -1;

    private int matchNumber;

    private String matchType;
    private String spectatingTeamFieldPosition;

    private int spectatingTeamNumber;

    // Driver's PoV.
    private String startingPosition;

    // Sandstorm Phase
    private boolean hasAutonomous;
    private boolean offPlatform;
    private boolean crossLine;
    private int sHatchLow;
    private int sHatchMid;
    private int sHatchHigh;
    private int sHatchDropped;
    private int sCargoLow;
    private int sCargoMid;
    private int sCargoHigh;
    private int sCargoDropped;

    // Clear Skies Phase
    private boolean isDefenseRobot;
    private int cHatchLow;
    private int cHatchMid;
    private int cHatchHigh;
    private int cHatchDropped;
    private boolean cHatchesFromLoadingZone;
    private int cCargoLow;
    private int cCargoMid;
    private int cCargoHigh;
    private int cCargoDropped;
    private boolean cCargoFromLoadingZone;

    // Endgame
    private int robotsHelped;
    private String endingLocation = "";

    // scores at the end?
    private boolean hasPenalty;
    private boolean yellowCard;
    private boolean redCard;
    private int redScore;
    private int blueScore;
    private double robotDeadTime;

    // Auxiliary Notes.
    private String autonotes;
    private String telenotes;


    //
    // STOPWATCH CONSTANTS AND VARIABLES - DO NOT TOUCH! (please)
    //

    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    final int REFRESH_RATE = 100;

    private Stopwatch timer = null;
    private Handler stopwatchHandler = null;

    private Button startClock = null;
    private Button resetClock = null;

    private EditText clockDisplay = null;
    private boolean clockState = false; // false = stopped, true = started.
    private boolean clockFirstStart = false;
    private double timeAccumulated = 0.0;

    //
    // END STOPWATCH CONSTANTS
    //

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

        // stopwatch handler code.
        // update title bar and stopwatch display every 100ms after start.
        timer = new Stopwatch();
        clockDisplay = (EditText) findViewById(R.id.stopwatchDisp);
        startClock = (Button) findViewById(R.id.clockStartButton);
        resetClock = (Button) findViewById(R.id.clockResetButton);

        stopwatchHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case MSG_START_TIMER:
                        if (!clockFirstStart)
                        {
                            clockFirstStart = true;
                            timer.start();
                        }
                        else
                        {
                            timer.resume();
                        }
                        stopwatchHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                        break;

                    case MSG_UPDATE_TIMER:
                        clockDisplay.setText((double)timer.getElapsedTime() / 1000.0 + "");
                        stopwatchHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER, REFRESH_RATE);
                        break;
                    case MSG_STOP_TIMER:
                        clockDisplay.setText((double)timer.getElapsedTime() / 1000.0 + "");
                        stopwatchHandler.removeMessages(MSG_UPDATE_TIMER);
                        timer.pause();
                        break;

                    default:
                        break;
                }
            }
        };

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

            // populate the match number.
            EditText mnum = (EditText) findViewById(R.id.matchNum);
            mnum.setText(OwOWhatsThis[2]);
            Log.d("SetMatchInfo", "Match Number Set: " + OwOWhatsThis[2]);

            // populate the match type.
            Spinner mtype =(Spinner) findViewById(R.id.CompType);
            mtype.setSelection(((ArrayAdapter)mtype.getAdapter()).getPosition(OwOWhatsThis[3]));
            Log.d("SetMatchInfo", "Match Type Set: " + OwOWhatsThis[3]);

            // populate the team number.
            EditText tnum = (EditText) findViewById(R.id.teamNum);
            tnum.setText(OwOWhatsThis[4]);
            Log.d("SetMatchInfo", "Team Number Set: " + OwOWhatsThis[4]);

            // populate the spectating team.
            Spinner specteam =(Spinner) findViewById(R.id.SpectatingSpinner);
            specteam.setSelection(((ArrayAdapter)specteam.getAdapter()).getPosition(OwOWhatsThis[5]));
            Log.d("SetMatchInfo", "Spectating Team: " + OwOWhatsThis[5]);

            // populate the starting position.
            Spinner spos =(Spinner) findViewById(R.id.startingPositionForm);
            spos.setSelection(((ArrayAdapter)spos.getAdapter()).getPosition(OwOWhatsThis[6]));
            Log.d("SetMatchInfo", "Starting Position Set: " + OwOWhatsThis[6]);

            // =====================[ BEGIN AUTONOMOUS PHASE ]===================== //

            // populate autonomous notes.
            EditText aunotes = (EditText) findViewById(R.id.autoNotes);
            String rawautonotes = OwOWhatsThis[37];
            rawautonotes = rawautonotes.replaceAll("^\"|\"$", ""); // remove quotation marks
            aunotes.setText(rawautonotes);
            Log.d("SetMatchInfo", "Autonomous Notes Set: \"" + rawautonotes + "\"");

            // sjaoifoisajfioasofjiowsa
            CheckBox haveAutonomousCB = (CheckBox) findViewById(R.id.autonomousCB);
            haveAutonomousCB.setChecked(OwOWhatsThis[7].equals("true"));

            CheckBox offPlatformCB = (CheckBox) findViewById(R.id.getOffPlatformCheckbox);
            offPlatformCB.setChecked(OwOWhatsThis[8].equals("true"));

            CheckBox crosslineCB = (CheckBox) findViewById(R.id.crossLineCheckBox);
            crosslineCB.setChecked(OwOWhatsThis[9].equals("true"));

            int sandstormHatchLow = Integer.parseInt(OwOWhatsThis[10]);
            int sandstormHatchMid = Integer.parseInt(OwOWhatsThis[11]);
            int sandstormHatchHigh = Integer.parseInt(OwOWhatsThis[12]);
            int sandstormHatchDrop = Integer.parseInt(OwOWhatsThis[13]);

            int sandstormCargoLow = Integer.parseInt(OwOWhatsThis[14]);
            int sandstormCargoMid = Integer.parseInt(OwOWhatsThis[15]);
            int sandstormCargoHigh = Integer.parseInt(OwOWhatsThis[16]);
            int sandstormCargoDrop = Integer.parseInt(OwOWhatsThis[17]);

            Spinner shl = (Spinner) findViewById(R.id.sandstormHatchLow);
            shl.setSelection(sandstormHatchLow);

            Spinner shm = (Spinner) findViewById(R.id.sandstormHatchMid2);
            shm.setSelection(sandstormHatchMid);

            Spinner shh = (Spinner) findViewById(R.id.sandstormHatchHigh);
            shh.setSelection(sandstormHatchHigh);

            Spinner shd = (Spinner) findViewById(R.id.sandstormHatchesDropped);
            shd.setSelection(sandstormHatchDrop);

            Spinner scl = (Spinner) findViewById(R.id.sandstormCargoLow);
            scl.setSelection(sandstormCargoLow);

            Spinner scm = (Spinner) findViewById(R.id.sandstormCargoMid2);
            scm.setSelection(sandstormCargoMid);

            Spinner sch = (Spinner) findViewById(R.id.sandstormCargoHigh);
            sch.setSelection(sandstormCargoHigh);

            Spinner scd = (Spinner) findViewById(R.id.sandstormCargoDropped2);
            scd.setSelection(sandstormCargoDrop);

            // =====================[ BEGIN TELEOPERATED PHASE ]===================== //

            CheckBox defenseCB = (CheckBox) findViewById(R.id.defenseRobot);
            defenseCB.setChecked(OwOWhatsThis[18].equals("true"));

            int clearHatchLow = Integer.parseInt(OwOWhatsThis[19]);
            int clearHatchMid = Integer.parseInt(OwOWhatsThis[20]);
            int clearHatchHigh = Integer.parseInt(OwOWhatsThis[21]);
            int clearHatchDrop = Integer.parseInt(OwOWhatsThis[22]);
            boolean clearHatchPickupFromLoadingZone = Boolean.parseBoolean(OwOWhatsThis[23]);

            int clearCargoLow = Integer.parseInt(OwOWhatsThis[24]);
            int clearCargoMid = Integer.parseInt(OwOWhatsThis[25]);
            int clearCargoHigh = Integer.parseInt(OwOWhatsThis[26]);
            int clearCargoDrop = Integer.parseInt(OwOWhatsThis[27]);
            boolean clearCargoPickupFromLoadingZone = Boolean.parseBoolean(OwOWhatsThis[28]);

            Spinner chl = (Spinner) findViewById(R.id.clearHatchLow);
            chl.setSelection(clearHatchLow);

            Spinner chm = (Spinner) findViewById(R.id.clearHatchMid2);
            chm.setSelection(clearHatchMid);

            Spinner chh = (Spinner) findViewById(R.id.clearHatchHigh);
            chh.setSelection(clearHatchHigh);

            Spinner chd = (Spinner) findViewById(R.id.clearHatchesDropped);
            chd.setSelection(clearHatchDrop);

            CheckBox hatchLZCB = (CheckBox) findViewById(R.id.hatchFromLoadingZone);
            hatchLZCB.setChecked(clearHatchPickupFromLoadingZone);

            Spinner ccl = (Spinner) findViewById(R.id.clearCargoLow);
            ccl.setSelection(clearCargoLow);

            Spinner ccm = (Spinner) findViewById(R.id.clearCargoMid2);
            ccm.setSelection(clearCargoMid);

            Spinner cch = (Spinner) findViewById(R.id.clearCargoHigh);
            cch.setSelection(clearCargoHigh);

            Spinner ccd = (Spinner) findViewById(R.id.clearCargoDropped);
            ccd.setSelection(clearCargoDrop);

            CheckBox cargoLZCB = (CheckBox) findViewById(R.id.cargoInLoadingZone);
            cargoLZCB.setChecked(clearCargoPickupFromLoadingZone);

            // =====================[ BEGIN ENDGAME ]===================== //

            Spinner endingLocation =(Spinner) findViewById(R.id.endingLocation);
            endingLocation.setSelection(((ArrayAdapter)endingLocation.getAdapter()).getPosition(OwOWhatsThis[29]));

            Spinner helpedRobotSpinner = (Spinner) findViewById(R.id.climbHelpSpinner);
            helpedRobotSpinner.setSelection(Integer.parseInt(OwOWhatsThis[30]));

            CheckBox penaltyCB = (CheckBox) findViewById(R.id.penaltyCB);
            penaltyCB.setChecked(Boolean.parseBoolean(OwOWhatsThis[31]));

            CheckBox yellowCardCB = (CheckBox) findViewById(R.id.yellowcardCB);
            yellowCardCB.setChecked(Boolean.parseBoolean(OwOWhatsThis[32]));

            CheckBox redcardCB = (CheckBox) findViewById(R.id.redcardCB);
            redcardCB.setChecked(Boolean.parseBoolean(OwOWhatsThis[33]));

            EditText redScore = (EditText) findViewById(R.id.redScore);
            redScore.setText(OwOWhatsThis[34] + "");

            EditText blueScore = (EditText) findViewById(R.id.blueScore);
            blueScore.setText(OwOWhatsThis[35] + "");

            //
            EditText robotDeadTime = (EditText) findViewById(R.id.stopwatchDisp);
            robotDeadTime.setText(OwOWhatsThis[36] + "");


            // populate teleop notes.
            EditText tonotes = (EditText) findViewById(R.id.teleopnotes);
            String rawtonotes = OwOWhatsThis[38];
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
            Log.d("SetMatchInfo","Parsing Match Number.");
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
            Log.d("SetMatchInfo","Parsing Competition Type");
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
            Log.d("SetMatchInfo","Parsing Alliance Number.");
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
            Log.d("SetMatchInfo","Parsing spectating team.");
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
            Log.d("SetMatchInfo","Parsing starting position.");
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
            Log.d("SetMatchInfo","Reading comments.");
            EditText editText = (EditText) findViewById(R.id.autoNotes);
            autonotes = editText.getText().toString();
            EditText editText2 = (EditText) findViewById(R.id.teleopnotes);
            telenotes = editText2.getText().toString();
        }
        if (!breakCond)
        {
            Log.d("SetMatchInfo","Checking match general statistics.");

            CheckBox penaltyCB = (CheckBox) findViewById(R.id.penaltyCB);
            hasPenalty = penaltyCB.isChecked();

            CheckBox yellowCardCB = (CheckBox) findViewById(R.id.yellowcardCB);
            yellowCard = yellowCardCB.isChecked();

            CheckBox redcardCB = (CheckBox) findViewById(R.id.redcardCB);
            redCard = redcardCB.isChecked();

            EditText redScoreForm = (EditText) findViewById(R.id.redScore);
            EditText blueScoreForm = (EditText) findViewById(R.id.blueScore);
            try
            {
                redScore = Integer.parseInt(redScoreForm.getText().toString());
                blueScore = Integer.parseInt(blueScoreForm.getText().toString());
            }
            catch (Exception e)
            {
                Snackbar.make(view, "Issue with score formatting", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }

            EditText deathClockForm = (EditText) findViewById(R.id.stopwatchDisp);
            try
            {
                robotDeadTime = Double.parseDouble(deathClockForm.getText().toString());
            }
            catch (Exception e)
            {
                Snackbar.make(view, "Issue with stopwatch formatting", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                breakCond = true;
            }


        }
        if (!breakCond)
        {
            // read autonomous stuff.
            CheckBox haveAutonomousCB = (CheckBox) findViewById(R.id.autonomousCB);
            hasAutonomous = haveAutonomousCB.isChecked();

            CheckBox offPlatformCB = (CheckBox) findViewById(R.id.getOffPlatformCheckbox);
            offPlatform = offPlatformCB.isChecked();

            CheckBox crossLineCB = (CheckBox) findViewById(R.id.crossLineCheckBox);
            crossLine = crossLineCB.isChecked();

            Spinner shl = (Spinner) findViewById(R.id.sandstormHatchLow);
            sHatchLow = Integer.parseInt(shl.getSelectedItem().toString());

            Spinner shm = (Spinner) findViewById(R.id.sandstormHatchMid2);
            sHatchMid = Integer.parseInt(shm.getSelectedItem().toString());

            Spinner shh = (Spinner) findViewById(R.id.sandstormHatchHigh);
            sHatchHigh = Integer.parseInt(shh.getSelectedItem().toString());

            Spinner shd = (Spinner) findViewById(R.id.sandstormHatchesDropped);
            sHatchDropped = Integer.parseInt(shd.getSelectedItem().toString());

            // lol

            Spinner scl = (Spinner) findViewById(R.id.sandstormCargoLow);
            sCargoLow = Integer.parseInt(scl.getSelectedItem().toString());

            Spinner scm = (Spinner) findViewById(R.id.sandstormCargoMid2);
            sCargoMid = Integer.parseInt(scm.getSelectedItem().toString());

            Spinner sch = (Spinner) findViewById(R.id.sandstormCargoHigh);
            sCargoHigh = Integer.parseInt(sch.getSelectedItem().toString());

            Spinner scd = (Spinner) findViewById(R.id.sandstormCargoDropped2);
            sCargoDropped = Integer.parseInt(scd.getSelectedItem().toString());
        }
        if (!breakCond)
        {
            // read teleop stuff.
            CheckBox defenseCB = (CheckBox) findViewById(R.id.defenseRobot);
            isDefenseRobot = defenseCB.isChecked();

            Spinner chl = (Spinner) findViewById(R.id.clearHatchLow);
            cHatchLow = Integer.parseInt(chl.getSelectedItem().toString());

            Spinner chm = (Spinner) findViewById(R.id.clearHatchMid2);
            cHatchMid = Integer.parseInt(chm.getSelectedItem().toString());

            Spinner chh = (Spinner) findViewById(R.id.clearHatchHigh);
            cHatchHigh = Integer.parseInt(chh.getSelectedItem().toString());

            Spinner chd = (Spinner) findViewById(R.id.clearHatchesDropped);
            cHatchDropped = Integer.parseInt(chd.getSelectedItem().toString());

            CheckBox chflz = (CheckBox) findViewById(R.id.cargoInLoadingZone);
            cHatchesFromLoadingZone = chflz.isChecked();

            Spinner ccl = (Spinner) findViewById(R.id.clearCargoLow);
            cCargoLow = Integer.parseInt(ccl.getSelectedItem().toString());

            Spinner ccm = (Spinner) findViewById(R.id.clearCargoMid2);
            cCargoMid = Integer.parseInt(ccm.getSelectedItem().toString());

            Spinner cch = (Spinner) findViewById(R.id.clearCargoHigh);
            cCargoHigh = Integer.parseInt(cch.getSelectedItem().toString());

            Spinner ccd = (Spinner) findViewById(R.id.clearCargoDropped);
            cCargoDropped = Integer.parseInt(ccd.getSelectedItem().toString());

            CheckBox ccflz = (CheckBox) findViewById(R.id.hatchFromLoadingZone);
            cCargoFromLoadingZone = ccflz.isChecked();

            // read endgame stuff.
            Spinner climbHelpSpinner = (Spinner) findViewById(R.id.climbHelpSpinner);
            String chs = climbHelpSpinner.getSelectedItem().toString();
            if (chs.equals("None"))
            {
                robotsHelped = 0;
            }
            else if (chs.contains("1"))
            {
                robotsHelped = 1;
            }
            else if (chs.contains("2"))
            {
                robotsHelped = 2;
            }

            Spinner endLocationSpinner = (Spinner) findViewById(R.id.endingLocation);
            endingLocation = endLocationSpinner.getSelectedItem().toString();
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
        if (spectatingTeamNumber == DataStore.selfTeamNumber)
        {
            containsOwnTeam = "*";
        }

        String listMsg = String.format("Match # %d (%s) Team: %d", matchNumber, matchType, spectatingTeamNumber);
        String CSVFormat = containsOwnTeam + "," + DataStore.getDateAsString() + "," + matchNumber + "," + matchType + "," + spectatingTeamNumber + "," + spectatingTeamFieldPosition + "," + startingPosition+","+ hasAutonomous +"," + offPlatform + ","+ crossLine + ","+ sHatchLow + "," + sHatchMid + "," + sHatchHigh + "," + sHatchDropped + "," + sCargoLow + "," + sCargoMid + "," + sCargoHigh + "," + sCargoDropped +"," + isDefenseRobot + "," + cHatchLow + "," + cHatchMid + "," + cHatchHigh + "," + cHatchDropped + "," + cHatchesFromLoadingZone + "," + cCargoLow + "," + cCargoMid + "," + cCargoHigh + "," + cCargoDropped + "," + cCargoFromLoadingZone + "," + endingLocation+","+ robotsHelped + "," + hasPenalty +"," + yellowCard + "," + redCard + "," + redScore + "," + blueScore + "," + robotDeadTime + ",\""+autonotes+"\",\""+telenotes+"\"";
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

    public void stopwatchTogglerOnClick(View v)
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
    }

    public void stopwatchResetOnClick(View v)
    {
        timer.reset();
        clockDisplay.setText(timer.getElapsedTime() / 1000.0 + "");
    }

}