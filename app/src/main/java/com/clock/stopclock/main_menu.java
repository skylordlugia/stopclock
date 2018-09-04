package com.clock.stopclock;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class main_menu extends Activity {

    private Button StopWatch;
    private Button HelpMenu;
    private Button Quit;
    private Button TimerMenu;
    private Button About;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativeLayout2);
        rl.setBackgroundColor(Color.BLACK);
        //getActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.SEND_SMS}, 1);
            requestPermissions(new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        StopWatch = (Button) findViewById(R.id.stopwatchbtn);
        HelpMenu = (Button) findViewById(R.id.helpbtn);
        Quit = (Button) findViewById(R.id.quitbtn);
        About = (Button) findViewById(R.id.aboutbtn);
        TimerMenu = (Button) findViewById(R.id.timerbtn);


    }

    public void StopWatch(View view)
    {
        Intent intent = new Intent(main_menu.this, ZE_CLOCK.class);
        startActivity(intent);
    }

    public void TimerMenu(View view)
    {
        Intent intent = new Intent(main_menu.this, timer.class);
        startActivity(intent);

    }

    public void HelpMenu(View view)
    {
        Intent intent = new Intent(main_menu.this, help.class);
        startActivity(intent);
    }

    public void Quit(View view)
    {
        // TODO Auto-generated method stub
        finish();
        System.exit(0);
    }
}