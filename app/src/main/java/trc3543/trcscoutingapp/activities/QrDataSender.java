package trc3543.trcscoutingapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import qrutils.QRCode;
import trc3543.trcscoutingapp.data.AppSettings;
import trc3543.trcscoutingapp.data.GsonUtil;
import trc3543.trcscoutingapp.data.MatchInfo;
import trc3543.trcscoutingapp.R;

public class QrDataSender extends AppCompatActivity
{
    // send the unique identifier of the phone, and then send the data, serialized in JSON format
    private ImageView qrDisplay;
    private TextView counter;
    private Button prevButton;
    private Button nextButton;

    private int page = 0;

    private static final int QR_VIEW_HEIGHT = 240;
    private static final int QR_VIEW_WIDTH = 240;

    private List<MatchInfo> matchList;
    private AppSettings appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_data_sender);
        setTitle("Send QR data");

        Intent intent = getIntent();
        matchList = (ArrayList) intent.getSerializableExtra("matchList");
        appSettings = (AppSettings) intent.getSerializableExtra("appSettings");

        qrDisplay = (ImageView) findViewById(R.id.qrDisplayImageView);
        counter = (TextView) findViewById(R.id.qrPageCounter);
        prevButton = (Button) findViewById(R.id.qrPrevButton);
        nextButton = (Button) findViewById(R.id.qrNextButton);

        Bitmap placeholder = QRCode.encodeMessage("Hello world!", QR_VIEW_WIDTH, QR_VIEW_HEIGHT);

        if (page >= matchList.size())
        {
            page = matchList.size() - 1;
        }

        if (matchList.size() == 0)
        {
            qrDisplay.setImageResource(0);
        }
        else
        {
            qrDisplay.setImageBitmap(QRCode.encodeMessage(getJSONSerializedMessage(page), QR_VIEW_WIDTH, QR_VIEW_HEIGHT));
        }

        updateCounter();
    }

    public void onPrevClicked(View vue)
    {
        if (page > 0)
        {
            page--;
        }

        if (matchList.size() == 0)
        {
            qrDisplay.setImageResource(0);
        }
        else
        {
            qrDisplay.setImageBitmap(QRCode.encodeMessage(getJSONSerializedMessage(page), QR_VIEW_WIDTH, QR_VIEW_HEIGHT));
        }
        updateCounter();
    }

    public void onNextClicked(View vue)
    {
        if (page < matchList.size() - 1)
        {
            page++;
        }

        if (matchList.size() == 0)
        {
            qrDisplay.setImageResource(0);
        }
        else
        {
            qrDisplay.setImageBitmap(QRCode.encodeMessage(getJSONSerializedMessage(page), QR_VIEW_WIDTH, QR_VIEW_HEIGHT));
        }

        updateCounter();
    }

    private void updateCounter()
    {
        if (page >= matchList.size() - 1)
        {
            nextButton.setEnabled(false);
        }
        else
        {
            nextButton.setEnabled(true);
        }

        if (page <= 0)
        {
            prevButton.setEnabled(false);
        }
        else
        {
            prevButton.setEnabled(true);
        }

        if (matchList.size() == 0)
        {
            counter.setText("No data yet!");
        }
        else
        {
            counter.setText(String.format("%d of %d", page + 1, matchList.size()));
        }
    }

    private String getJSONSerializedMessage(int id)
    {
        MatchInfo match = matchList.get(id);
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phoneId", appSettings.firstName + "_" + appSettings.lastName + "_" + appSettings.selfTeamNumber);
            jsonObject.put("match", GsonUtil.MatchInfoToJSONObject(match));
            return jsonObject.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
