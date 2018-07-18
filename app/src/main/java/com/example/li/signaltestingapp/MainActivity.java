package com.example.li.signaltestingapp;

import android.content.Context;
import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TelephonyManager tm  = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        SignalListener pl= new SignalListener();
        tm.listen(pl,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
                                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                TextView textView=(TextView)findViewById(R.id.textView3);
                                // textView is the TextView view that should display it
                                textView.setText(currentDateTimeString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }
    private class SignalListener extends PhoneStateListener {
        @Override
        public void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength) {

            // get the signal strength (a value between 0 and 31)
            String s=signalStrength.toString();
            String[] parts=s.split(" ");
            TextView signal=(TextView)findViewById(R.id.textView5);
            signal.setText(parts[9]);
            super.onSignalStrengthsChanged(signalStrength);
        }
    }


}
