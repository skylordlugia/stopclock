package com.clock.stopclock;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relLayout1);
        rl.setBackgroundColor(Color.BLACK);
    }
    private TextView Help;




}
