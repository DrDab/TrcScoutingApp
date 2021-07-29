package trc3543.trcscoutingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import trc3543.trcscoutingapp.data.IOUtils;
import trc3543.trcscoutingapp.R;

import android.os.Bundle;
import android.widget.TextView;

public class NfcDataSender extends AppCompatActivity
{

    private TextView nfcMainStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_data_sender);

        nfcMainStatus = (TextView) findViewById(R.id.nfcMainStatus);

        setTitle("Send CSV");

        boolean nfcSupported = getIntent().getBooleanExtra("deviceSupportsNfc", false);
        nfcMainStatus.setText(nfcSupported ? "NFC Ready to Pair" : "NFC not supported or disabled");
    }
}
