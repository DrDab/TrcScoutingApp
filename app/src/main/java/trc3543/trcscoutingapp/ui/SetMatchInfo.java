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
    private EditText matchNumEditText;
    private EditText teamNumEditText;
    private Spinner allianceSpinner;
    private Spinner matchTypeSpinner;
    private EditText notesEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_match_info2);
        setTitle("Add Match");

        this.matchNumEditText = (EditText) findViewById(R.id.matchNum);
        this.teamNumEditText = (EditText) findViewById(R.id.teamNum);
        this.allianceSpinner = (Spinner) findViewById(R.id.SpectatingSpinner);
        this.matchTypeSpinner = (Spinner) findViewById(R.id.matchTypeSpinner);
        this.notesEditText = (EditText) findViewById(R.id.notes);

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
            setEditTextValue(matchNumEditText, matchInfo.matchNumber);
            setEditTextValue(teamNumEditText, matchInfo.teamNumber);
            setSpinnerByTextValue(allianceSpinner, matchInfo.alliance);
            setSpinnerByTextValue(matchTypeSpinner, matchInfo.matchType);
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
                matchInfo.matchNumber = Integer.parseInt(matchNumEditText.getText().toString());
                matchInfo.teamNumber = Integer.parseInt(teamNumEditText.getText().toString());
                matchInfo.alliance = allianceSpinner.getSelectedItem().toString();
                matchInfo.matchType = matchTypeSpinner.getSelectedItem().toString();
                matchInfo.notes = notesEditText.getText().toString();
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
            Log.d("SetMatchInfo","Esketit!");
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

    private void setEditTextValue(EditText editText, Object toSet)
    {
        editText.setText(toSet.toString() + "");
    }
}