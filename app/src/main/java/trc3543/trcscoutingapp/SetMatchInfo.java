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
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;

import trc3543.gamespecific.PagerAdapter;

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
    private int sCargoShipHatches;
    private int sCargoShipCargo;

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
    private int cCargoShipHatches;
    private int cCargoShipCargo;

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

        // initialize tabbed layout.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Sandstorm"));
        tabLayout.addTab(tabLayout.newTab().setText("Clear Skies"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // done initializing tabbed layout.

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


            // =====================[ END ENDGAME + TELEOPERATED PHASE ]===================== //
        }
    }

    public void confirmTypes(View view)
    {
        boolean breakCond = false;



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
        String CSVFormat = containsOwnTeam + "," + DataStore.getDateAsString() + "," + matchNumber + "," + matchType + "," + spectatingTeamNumber + "," + spectatingTeamFieldPosition + "," + startingPosition+","+ hasAutonomous +"," + offPlatform + ","+ crossLine + ","+ sHatchLow + "," + sHatchMid + "," + sHatchHigh + "," + sHatchDropped + "," + sCargoLow + "," + sCargoMid + "," + sCargoHigh + "," + sCargoDropped +"," + sCargoShipHatches + "," + sCargoShipCargo + "," +  isDefenseRobot + "," + cHatchLow + "," + cHatchMid + "," + cHatchHigh + "," + cHatchDropped + "," + cHatchesFromLoadingZone + "," + cCargoLow + "," + cCargoMid + "," + cCargoHigh + "," + cCargoDropped + "," + cCargoFromLoadingZone + "," + cCargoShipHatches + "," + cCargoShipCargo + "," +  endingLocation+","+ robotsHelped + "," + hasPenalty +"," + yellowCard + "," + redCard + "," + redScore + "," + blueScore + "," + robotDeadTime + ",\""+DataStore.escapeFormat(autonotes)+"\",\""+DataStore.escapeFormat(telenotes)+"\"";
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
            // your code here
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    */

}