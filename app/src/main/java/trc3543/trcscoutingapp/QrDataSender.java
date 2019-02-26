package trc3543.trcscoutingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QrDataSender extends AppCompatActivity
{

    // send the unique identifier of the phone, and then send the data.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_data_sender);
    }
}
