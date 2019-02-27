package trc3543.trcscoutingapp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import qrutils.QRCode;

public class QrDataSender extends AppCompatActivity
{
    // send the unique identifier of the phone, and then send the data, serialized in JSON format
    private ImageView qrDisplay;
    private TextView counter;
    private static int page = 0;

    private static final int QR_VIEW_HEIGHT = 240;
    private static final int QR_VIEW_WIDTH = 240;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_data_sender);
        setTitle("Send QR data");

        qrDisplay = (ImageView) findViewById(R.id.qrDisplayImageView);
        counter = (TextView) findViewById(R.id.qrPageCounter);

        Bitmap placeholder = QRCode.encodeMessage("Hello world!", QR_VIEW_WIDTH, QR_VIEW_HEIGHT);

        if (page >= DataStore.matchList.size())
        {
            page = DataStore.matchList.size() - 1;
        }

        if (AddMatches.listEmpty())
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

        if (AddMatches.listEmpty())
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
        if (page < DataStore.matchList.size() - 1)
        {
            page++;
        }

        if (AddMatches.listEmpty())
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
        if (AddMatches.listEmpty())
        {
            counter.setText("No data yet! ÒwÓ ");
        }
        else
        {
            counter.setText(String.format("%d of %d", page + 1, DataStore.matchList.size()));
        }
    }

    private String getJSONSerializedMessage(int id)
    {
        Match match = DataStore.matchList.get(id);
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phoneId", DataStore.firstName + "_" + DataStore.lastName + "_" + DataStore.selfTeamNumber);
            jsonObject.put("csvline", match.getCsvString());
            jsonObject.put("uuid", match.getUUID());
            return jsonObject.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
