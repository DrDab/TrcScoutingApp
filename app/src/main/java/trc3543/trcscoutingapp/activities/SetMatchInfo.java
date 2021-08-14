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

package trc3543.trcscoutingapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import trc3543.trcscoutingapp.data.MatchInfo;
import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.fragutil.FragmentsDataViewModel;
import trc3543.trcscoutingapp.fragutil.CustomFragmentPagerAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class SetMatchInfo extends AppCompatActivity
{
    public static final String MODULE_NAME = "SetMatchInfo";

    private int editingoption = -1;
    private MatchInfo matchInfo;
    private FragmentPagerAdapter fpa;
    private FragmentsDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_match_info2);
        setTitle("Add Match");

        Intent myIntent = getIntent();
        try
        {
            Log.d(MODULE_NAME, "Getting edit option index from passed Intent...");
            editingoption = myIntent.getIntExtra("EditOption", -1);
            Log.d(MODULE_NAME, "Got edit option index: " + editingoption);
        }
        catch (Exception e)
        {
            Log.d(MODULE_NAME, "Failed to get edit option index from passed Intent. Defaulting to" +
                    " -1.");
            e.printStackTrace();
            editingoption = -1;
        }

        // populate the boxes if already filled.
        Log.d(MODULE_NAME, "Fetching match JSON information from edit option index...");
        matchInfo = (MatchInfo) myIntent.getSerializableExtra("matchInfo");
        String matchInfoJsonObjStr = null;

        try
        {
            matchInfoJsonObjStr = matchInfo == null ? null : matchInfo.toJSONObject().toString();
        }
        catch (JSONException jsonException)
        {
            jsonException.printStackTrace();
        }

        if (matchInfoJsonObjStr == null)
        {
            try
            {
                JSONObject prePopObj = new JSONObject();
                if (myIntent.hasExtra("PrevMatch"))
                    prePopObj.put("matchNumber", 1 + myIntent.getIntExtra("PrevMatch", -1));
                if (myIntent.hasExtra("PrevAlliance"))
                    prePopObj.put("alliance", myIntent.getStringExtra("PrevAlliance"));
                if (myIntent.hasExtra("PrevMatchType"))
                    prePopObj.put("matchType", myIntent.getStringExtra("PrevMatchType"));
                matchInfoJsonObjStr = prePopObj.toString();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        Log.d(MODULE_NAME, matchInfoJsonObjStr == null ? "No match JSON info to fetch." :
                "Fetched match JSON info: " + matchInfoJsonObjStr);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fpa = new CustomFragmentPagerAdapter(fragmentManager, matchInfoJsonObjStr);
        viewPager.setAdapter(fpa);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabStrip.setViewPager(viewPager);

        // initialize all fragments at start of activity.
        viewPager.setOffscreenPageLimit(fpa.getCount());

        Log.d(MODULE_NAME, "Initializing " + FragmentsDataViewModel.MODULE_NAME + "...");
        viewModel = new ViewModelProvider(this).get(FragmentsDataViewModel.class);
    }

    public void confirmTypes(View view) throws JSONException
    {
        Log.d(MODULE_NAME, "Confirm button pressed. Retrieving information from pages...");
        JSONObject[] pageData = new JSONObject[viewModel.getNumRegistered()];

        for (int i = 0; i < fpa.getCount(); i++)
        {
            pageData[i] = viewModel.getInfoFromPage(i);
            if (pageData[i] == null)
            {
                Log.d(MODULE_NAME, "Page " + i + " returned null data! Notifying user.");
                spawnUserMistakeSnackbar(view);
                return;
            }
        }

        matchInfo = MatchInfo.fromMultipleJSONObjects(pageData);

        Log.d(MODULE_NAME, "Checking if all necessary fields are populated.");

        if (matchInfo.allNeededFieldsPopulated())
        {
            // All values are confirmed, move to next screen.
            Log.d(MODULE_NAME, "All fields populated. Moving to next screen.");
            moveToNextScreen(view);
        }
        else
        {
            Log.d(MODULE_NAME, "Necessary fields are missing. Notifying user.");
            spawnUserMistakeSnackbar(view);
        }
    }

    public void moveToNextScreen(View view)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("editExisting", editingoption != -1);
        returnIntent.putExtra("existingEntryIndex", editingoption);
        returnIntent.putExtra("matchInfo", matchInfo);

        Log.d(MODULE_NAME, "Finishing activity with result Intent info: " + returnIntent.getExtras().toString());

        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void spawnUserMistakeSnackbar(View view)
    {
        Snackbar.make(view, "Please make sure all required fields are populated.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**
     * by: ZMan, source: StackOverflow
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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}