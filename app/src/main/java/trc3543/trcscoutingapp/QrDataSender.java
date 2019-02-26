package trc3543.trcscoutingapp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import qrutils.QRCode;

public class QrDataSender extends AppCompatActivity
{
    // send the unique identifier of the phone, and then send the data, serialized in JSON format
    private ImageView qrDisplay;
    private TextView counter;
    private int page = 0;

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

        Bitmap bitmap = QRCode.encodeMessage("Hello world!", QR_VIEW_WIDTH, QR_VIEW_HEIGHT);
        qrDisplay.setImageBitmap(bitmap);

        updateCounter();
    }

    private void onPrevClicked(View vue)
    {

    }

    private void onNextClicked(View vue)
    {

    }

    private void updateCounter()
    {
        counter.setText(String.format("%d of %d", page + 1, DataStore.matchList.size()));
    }
}
