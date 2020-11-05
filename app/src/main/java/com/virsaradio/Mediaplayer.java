package com.virsaradio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mediaplayer extends AppCompatActivity{
    Button buttonStart, buttonStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);

//        buttonStart.setOnClickListener(this);
//        buttonStop.setOnClickListener(this);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), MyService.class));
            }
        });

    }

//    @Override
//    public void onClick(View view) {
//        if(view==buttonStart)
//        {
//            startService(new Intent(this, MyService.class));
//        }
//        if(view==buttonStop)
//        {
//            stopService(new Intent(this, MyService.class));
//        }
//
//
//    }
}
