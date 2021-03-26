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
import androidx.viewpager.widget.ViewPager;
import trc3543.trcscoutingapp.data.DataStore;
import trc3543.trcscoutingapp.data.MatchInfo;
import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.fragmentcommunication.CollectorServer;
import trc3543.trcscoutingapp.fragmentcommunication.CollectorTransaction;
import trc3543.trcscoutingapp.fragmentcommunication.Stopwatch;

import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings("all")
public class SetMatchInfo extends AppCompatActivity
{
    public static final boolean USE_DEBUG = false;

    private int editingoption = -1;

    private MatchInfo matchInfo;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_match_info2);
        setTitle("Add Match");

        WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("file:///sdcard/TrcScoutingApp/Furry.html");

        try
        {
            DataStore.parseUserInfoGeneral();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

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
        JSONObject autonomousData = new JSONObject();
        JSONObject teleopData = new JSONObject();
        JSONObject endgameData = new JSONObject();
        if (editingoption != -1)
        {
            matchInfo = DataStore.matchList.get(editingoption);
           // load the match info
        }
        else
        {
            matchInfo = new MatchInfo();
            int matchNumberAutoPop = myIntent.getIntExtra("PrevMatch", -1);
            String matchAllianceAutoPop = myIntent.getStringExtra("PrevAlliance");
            String matchTypeAutoPop = myIntent.getStringExtra("PrevMatchType");
            // new match. populate match number, and previously saved alliance/match type.

        }
    }

    public void confirmTypes(View view) throws JSONException
    {
        boolean breakCond = false;

        if (!breakCond)
        {
            try
            {
               // confirm types here.
            }
            catch (Exception e)
            {
                e.printStackTrace();
                breakCond = true;
            }
        }

        breakCond = breakCond || !matchInfo.allFieldsPopulated();

        if (!breakCond)
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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }

    private class PhaseClassifier
    {
        private JSONObject autoFields;
        private JSONObject teleopFields;
        private JSONObject endFields;

        public PhaseClassifier(JSONObject matchInfoJsonObj, String[] autoCat, String[] telCat, String[] endCat)
                throws JSONException
        {
            autoFields = new JSONObject();
            teleopFields = new JSONObject();
            endFields = new JSONObject();
            HashMap<String, Boolean> autoMap = new HashMap<>();
            for (String s : autoCat)
            {
                autoMap.put(s, true);
            }
            HashMap<String, Boolean> teleMap = new HashMap<>();
            for (String s : telCat)
            {
                teleMap.put(s, true);
            }
            HashMap<String, Boolean> endgMap = new HashMap<>();
            for (String s : endCat)
            {
                endgMap.put(s, true);
            }
            Iterator<String> keyIterator = matchInfoJsonObj.keys();
            while (keyIterator.hasNext())
            {
                String key = keyIterator.next();
                if (autoMap.containsKey(key))
                {
                    autoFields.put(key, matchInfoJsonObj.get(key));
                }
                if (teleMap.containsKey(key))
                {
                    teleopFields.put(key, matchInfoJsonObj.get(key));
                }
                if (endgMap.containsKey(key))
                {
                    endFields.put(key, matchInfoJsonObj.get(key));
                }
            }
        }

        public JSONObject getAutoFields()
        {
            return this.autoFields;
        }

        public JSONObject getTeleopFields()
        {
            return this.teleopFields;
        }

        public JSONObject getEndFields()
        {
            return this.endFields;
        }
    }
}