package trc3543.trcscoutingapp;

import androidx.appcompat.app.AppCompatActivity;
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

        if (DataStore.deviceSupportsNfc)
        {
            nfcMainStatus.setText("NFC Ready to Pair! :>");
        }
        else
        {

        }
    }
}
