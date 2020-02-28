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

package trc3543.trcscoutingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import trc3543.trcscoutingapp.data.DataStore;
import trc3543.trcscoutingapp.data.MatchInfo;
import trc3543.trcscoutingapp.R;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;

import java.io.IOException;

@SuppressWarnings("all")
public class SetMatchInfo extends AppCompatActivity
{
    public static final boolean USE_DEBUG = false;

    private int editingoption = -1;

    private MatchInfo matchInfo;

    // elements
    private EditText teamNumEditText;
    private Spinner driveTrainSpinner;
    private Spinner langSpinner;
    private Spinner shootingAccuracySpinner;
    private EditText powerCellsChamberCapacity;
    private EditText cycleTimeEditText;
    private EditText cyclesPerMatchEditText;
    private CheckBox startNearAudience;
    private CheckBox startMidPos;
    private CheckBox startAwayFromAudience;
    private CheckBox canCrossInitLine;
    private CheckBox autoShootingLow;
    private CheckBox autoShootingHigh;
    private CheckBox autoShootingInner;
    private EditText autoShootingNumber;
    private CheckBox controlPanelRotation;
    private CheckBox controlPanelPosition;
    private CheckBox shootingFromNearField;
    private CheckBox shootingFromMidField;
    private CheckBox shootingFromFarField;
    private CheckBox teleopShootingLow;
    private CheckBox teleopShootingHigh;
    private CheckBox teleopShootingInner;
    private CheckBox pickupGround;
    private CheckBox pickupFeederStation;
    private Spinner pickupType;
    private Spinner strategyType;
    private CheckBox driveUnderTrench;
    private CheckBox climbing;
    private CheckBox balancing;
    private EditText notesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_match_info2);
        setTitle("Add Team");

        this.teamNumEditText = findViewById(R.id.teamNum);
        this.notesEditText = findViewById(R.id.notes);
        this.driveTrainSpinner = findViewById(R.id.driveTrainSpinner);
        this.langSpinner = findViewById(R.id.langSpinner);
        this.shootingAccuracySpinner = findViewById(R.id.shootingAccuracySpinner);
        this.powerCellsChamberCapacity = findViewById(R.id.cellsInChamberET);
        this.cycleTimeEditText = findViewById(R.id.cycleTimeET);
        this.cyclesPerMatchEditText = findViewById(R.id.cyclesPerMatchET);
        this.startNearAudience = findViewById(R.id.startingPosNearCB);
        this.startMidPos = findViewById(R.id.startingPosMidCB);
        this.startAwayFromAudience = findViewById(R.id.startingPosFarCB);
        this.canCrossInitLine = findViewById(R.id.crossInitLineCB);
        this.autoShootingLow = findViewById(R.id.autoShootingLowCB);
        this.autoShootingHigh = findViewById(R.id.autoShootingHighCB);
        this.autoShootingInner = findViewById(R.id.autoShootingInnerCB);
        this.autoShootingNumber = findViewById(R.id.autoCellsScoreET);
        this.controlPanelPosition = findViewById(R.id.controlPanelPositioningCB);
        this.controlPanelRotation = findViewById(R.id.controlPanelRotationCB);
        this.shootingFromNearField = findViewById(R.id.teleopShootingLocNearCB);
        this.shootingFromMidField = findViewById(R.id.teleopShootingLocMidCB);
        this.shootingFromFarField = findViewById(R.id.teleopShootingLocFarCB);
        this.teleopShootingLow = findViewById(R.id.teleopShootingLow);
        this.teleopShootingHigh = findViewById(R.id.teleopShootingHigh);
        this.teleopShootingInner = findViewById(R.id.teleopShootingInner);
        this.pickupGround = findViewById(R.id.pickuplocationGround);
        this.pickupFeederStation = findViewById(R.id.pickuplocationFeederStation);
        this.pickupType = findViewById(R.id.pickupTypeSpinner);
        this.strategyType = findViewById(R.id.generalStrategySpinner);
        this.driveUnderTrench = findViewById(R.id.driveUnderTrenchCB);
        this.climbing = findViewById(R.id.climbingCB);
        this.balancing = findViewById(R.id.balancingCB);

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
            matchInfo = DataStore.matchList.get(editingoption);
            String read = matchInfo.getCsvString();
            Log.d("SetMatchInfo", editingoption + " " + read);
            setEditTextValue(teamNumEditText, matchInfo.teamNumber);
            setSpinnerByTextValue(driveTrainSpinner, matchInfo.driveTrain);
            setSpinnerByTextValue(langSpinner, matchInfo.programmingLanguage);
            setSpinnerByTextValue(shootingAccuracySpinner, matchInfo.shootingAccuracy);
            setEditTextValue(powerCellsChamberCapacity, matchInfo.powerCellsChamberCapacity);
            setEditTextValue(cycleTimeEditText, matchInfo.cycleTime);
            setEditTextValue(cyclesPerMatchEditText, matchInfo.cyclesPerMatch);
            setCheckBox(startNearAudience, matchInfo.startNearAudience);
            setCheckBox(startMidPos, matchInfo.startMidPos);
            setCheckBox(startAwayFromAudience, matchInfo.startAwayFromAudience);
            setCheckBox(canCrossInitLine, matchInfo.canCrossInitLine);
            setCheckBox(autoShootingLow, matchInfo.autoShootingLow);
            setCheckBox(autoShootingHigh, matchInfo.autoShootingHigh);
            setCheckBox(autoShootingInner, matchInfo.autoShootingInner);
            setEditTextValue(autoShootingNumber, matchInfo.autoShootingNumber);
            setCheckBox(controlPanelRotation, matchInfo.controlPanelRotation);
            setCheckBox(controlPanelPosition, matchInfo.controlPanelPosition);
            setCheckBox(shootingFromNearField, matchInfo.shootingFromNearField);
            setCheckBox(shootingFromMidField, matchInfo.shootingFromMidField);
            setCheckBox(shootingFromFarField, matchInfo.shootingFromFarField);
            setCheckBox(teleopShootingLow, matchInfo.teleopShootingLow);
            setCheckBox(teleopShootingHigh, matchInfo.teleopShootingHigh);
            setCheckBox(teleopShootingInner, matchInfo.teleopShootingInner);
            setCheckBox(pickupGround, matchInfo.pickupGround);
            setCheckBox(pickupFeederStation, matchInfo.pickupFeederStation);
            setSpinnerByTextValue(pickupType, matchInfo.pickupType);
            setSpinnerByTextValue(strategyType, matchInfo.strategyType);
            setCheckBox(driveUnderTrench, matchInfo.driveUnderTrench);
            setCheckBox(climbing, matchInfo.climbing);
            setCheckBox(balancing, matchInfo.balancing);
            setEditTextValue(notesEditText, matchInfo.notes);
        }
        else
        {
            matchInfo = new MatchInfo();
        }
    }

    public void confirmTypes(View view) throws JSONException
    {
        boolean breakCond = false;

        if (!breakCond)
        {
            try
            {
                matchInfo.teamNumber = getEditTextNumericValue(teamNumEditText);
                matchInfo.driveTrain = getSpinnerTextValue(driveTrainSpinner);
                matchInfo.programmingLanguage = getSpinnerTextValue(langSpinner);
                matchInfo.shootingAccuracy = getSpinnerTextValue(shootingAccuracySpinner);
                matchInfo.powerCellsChamberCapacity = getEditTextNumericValue(powerCellsChamberCapacity);
                matchInfo.cycleTime = getEditTextNumericValue(cycleTimeEditText);
                matchInfo.cyclesPerMatch = getEditTextNumericValue(cyclesPerMatchEditText);
                matchInfo.startNearAudience = getCheckBox(startNearAudience);
                matchInfo.startMidPos = getCheckBox(startMidPos);
                matchInfo.startAwayFromAudience = getCheckBox(startAwayFromAudience);
                matchInfo.canCrossInitLine = getCheckBox(canCrossInitLine);
                matchInfo.autoShootingLow = getCheckBox(autoShootingLow);
                matchInfo.autoShootingHigh = getCheckBox(autoShootingHigh);
                matchInfo.autoShootingInner = getCheckBox(autoShootingInner);
                matchInfo.autoShootingNumber = getEditTextNumericValue(autoShootingNumber);
                matchInfo.controlPanelRotation = getCheckBox(controlPanelRotation);
                matchInfo.controlPanelPosition = getCheckBox(controlPanelPosition);
                matchInfo.shootingFromNearField = getCheckBox(shootingFromNearField);
                matchInfo.shootingFromMidField = getCheckBox(shootingFromMidField);
                matchInfo.shootingFromFarField = getCheckBox(shootingFromFarField);
                matchInfo.teleopShootingLow = getCheckBox(teleopShootingLow);
                matchInfo.teleopShootingHigh = getCheckBox(teleopShootingHigh);
                matchInfo.teleopShootingInner = getCheckBox(teleopShootingInner);
                matchInfo.pickupGround = getCheckBox(pickupGround);
                matchInfo.pickupFeederStation = getCheckBox(pickupFeederStation);
                matchInfo.pickupType = getSpinnerTextValue(pickupType);
                matchInfo.strategyType = getSpinnerTextValue(strategyType);
                matchInfo.driveUnderTrench = getCheckBox(driveUnderTrench);
                matchInfo.climbing = getCheckBox(climbing);
                matchInfo.balancing = getCheckBox(balancing);
                matchInfo.notes = getEditTextValue(notesEditText);
            }
            catch (Exception e)
            {
                breakCond = true;
            }
        }

        breakCond = breakCond && !matchInfo.allFieldsPopulated();

        if (!breakCond)
        {
            // All values are confirmed, move to next screen.
            Log.d("SetMatchInfo","Moving to next screen");
            Log.d("owo", matchInfo.getCsvString());
            moveToNextScreen(view);
        }
        else
        {
            Snackbar.make(view, "Please make sure all required fields are populated.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void moveToNextScreen(View view)
    {
        if (editingoption == -1)
        {
            Log.d("SetMatchInfo","Adding new entry to list.");
            AddMatches.addToList(matchInfo);
        }
        else
        {
            Log.d("SetMatchInfo","Resetting list entry: " + editingoption);
            AddMatches.resetListItem(matchInfo, editingoption);
        }

        try
        {
            DataStore.writeArraylistsToJSON();
        }
        catch (IOException | JSONException e)
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

    private void setSpinnerByTextValue(Spinner spinner, String toSelect)
    {
        for (int i = 0; i < spinner.getCount(); i++)
        {
            if (spinner.getItemAtPosition(i).equals(toSelect))
            {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public String getSpinnerTextValue(Spinner spinner)
    {
        return spinner.getSelectedItem().toString();
    }

    private void setEditTextValue(EditText editText, Object toSet)
    {
        editText.setText(toSet.toString() + "");
    }

    public String getEditTextValue(EditText editText)
    {
        return editText.getText().toString();
    }

    public Integer getEditTextNumericValue(EditText editText)
    {
        return Integer.parseInt(editText.getText().toString());
    }

    private void setCheckBox(CheckBox checkBox, Boolean toSet)
    {
        checkBox.setChecked(toSet);
    }

    public Boolean getCheckBox(CheckBox checkBox)
    {
        return checkBox.isChecked();
    }
}