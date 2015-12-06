package com.example.watch;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Vibrator;
import android.content.Context;

public class MainActivity extends Activity {

    private TextView mTextView;
    private Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ImageButton lightbulb = (ImageButton) findViewById(R.id.light);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        lightbulb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                v.vibrate(2000);
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }

//    public void vibrate(){
//        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        v.vibrate();
//    }

}
