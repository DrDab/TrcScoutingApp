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

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import trc3543.trcscoutingapp.data.DataStore;
import trc3543.trcscoutingapp.data.MatchInfo;
import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.fragments.FragmentsDataViewModel;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

@SuppressWarnings("all")
public class SetMatchInfo extends AppCompatActivity
{
    public static final boolean USE_DEBUG = false;

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
            editingoption = myIntent.getIntExtra("EditOption", -1);
            Log.d("SetMatchInfo", "Got edit option index: " + editingoption);
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
        }
        else
        {
            matchInfo = new MatchInfo();
            int matchNumberAutoPop = myIntent.getIntExtra("PrevMatch", -1);
            String matchAllianceAutoPop = myIntent.getStringExtra("PrevAlliance");
            String matchTypeAutoPop = myIntent.getStringExtra("PrevMatchType");

        }

        JSONObject initMatchInfo = null;
        try {
            initMatchInfo = matchInfo.toJSONObject();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fpa = new CustomFragmentPagerAdapter(fragmentManager, initMatchInfo == null ? null : initMatchInfo.toString());
        viewPager.setAdapter(fpa);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabStrip.setViewPager(viewPager);

        // initialize all fragments at start of activity.
        viewPager.setOffscreenPageLimit(fpa.getCount());

        try
        {
            DataStore.parseUserInfoGeneral();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        viewModel = new ViewModelProvider(this).get(FragmentsDataViewModel.class);
    }

    public void confirmTypes(View view) throws JSONException
    {
        JSONObject[] pageData = new JSONObject[3];
        pageData[0] = viewModel.getInfoFromPage(0);
        pageData[1] = viewModel.getInfoFromPage(1);
        pageData[2] = viewModel.getInfoFromPage(2);
        matchInfo = MatchInfo.fromMultipleJSONObjects(pageData[0], pageData[1], pageData[2]);

        if (matchInfo.allNeededFieldsPopulated())
        {
            // All values are confirmed, move to next screen.
            Log.d("SetMatchInfo", "Moving to next screen.");
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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }
}