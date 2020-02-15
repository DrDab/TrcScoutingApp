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

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class About extends AppCompatActivity
{
    private TextView versionIdText;
    private TextView buildDateText;
    private TextView nfcSupportedText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("About This App");
        setTitleColor(Color.parseColor("#ff669900"));

        versionIdText = (TextView) findViewById(R.id.versionIdText);
        versionIdText.setText("Version: " + DataStore.VERSION_NUMBER);

        buildDateText = (TextView) findViewById(R.id.buildDateText);
        Date buildDate = new Date(BuildConfig.TIMESTAMP);
        buildDateText.setText("Build Date: " + buildDate.getTime());

        nfcSupportedText = (TextView) findViewById(R.id.nfcSupportedText);
        nfcSupportedText.setText(DataStore.deviceSupportsNfc ? "NFC Supported" : "NFC Not Supported");
    }
}
