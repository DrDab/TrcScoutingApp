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
import android.content.Intent;
import android.graphics.Color;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import trc3543.trcscoutingapp.data.AppSettings;
import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.data.IOUtils;
import trc3543.trcscoutingapp.uiutil.UIUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

@SuppressWarnings("all")
public class SettingsActivity extends AppCompatActivity
{
    private EditText teamNumForm;
    private EditText firstNameForm;
    private EditText lastNameForm;
    private CheckBox settingsAutosaveCB;
    private EditText autosaveTimeForm;

    private AppSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        setTitleColor(Color.parseColor("#ff669900"));

        settings = (AppSettings) getIntent().getSerializableExtra("appSettings");

        teamNumForm = (EditText) findViewById(R.id.settingsTeamNumForm);
        firstNameForm = (EditText) findViewById(R.id.settingsFirstNameET);
        lastNameForm = (EditText) findViewById(R.id.settingsLastNameET);
        settingsAutosaveCB = (CheckBox) findViewById(R.id.settingsAutosaveCB);
        autosaveTimeForm = (EditText) findViewById(R.id.settingsCSVExportIntervalET);

        if (settings == null)
        {
            settings = new AppSettings();
            UIUtils.setEditTextValue(autosaveTimeForm, 3600);
        }
        else
        {
            UIUtils.setEditTextValue(teamNumForm, settings.selfTeamNumber);
            UIUtils.setEditTextValue(firstNameForm, settings.firstName);
            UIUtils.setEditTextValue(lastNameForm, settings.lastName);
            settingsAutosaveCB.setChecked(settings.useAutosave);
            UIUtils.setEditTextValue(autosaveTimeForm, settings.autosaveSeconds);
        }
    }

    public void confirmSettings(View v)
    {
        if (UIUtils.isEditTextEmpty(teamNumForm) || UIUtils.isEditTextEmpty(firstNameForm)
        || UIUtils.isEditTextEmpty(lastNameForm) || UIUtils.isEditTextEmpty(autosaveTimeForm))
        {
            openUserErrorSnackbar(v);
            return;
        }

        try
        {
            settings.selfTeamNumber = Integer.parseInt(UIUtils.getEditTextValue(teamNumForm));
            settings.firstName = UIUtils.getEditTextValue(firstNameForm);
            settings.lastName = UIUtils.getEditTextValue(lastNameForm);
            settings.useAutosave = settingsAutosaveCB.isChecked();
            settings.autosaveSeconds = Integer.parseInt(UIUtils.getEditTextValue(autosaveTimeForm));

            Intent returnIntent = new Intent();
            returnIntent.putExtra("appSettings", settings);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            openUserErrorSnackbar(v);
        }
    }

    private void openUserErrorSnackbar(View v)
    {
        Snackbar.make(v, "Please make sure all info is entered.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
