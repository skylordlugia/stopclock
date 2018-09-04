package com.clock.stopclock;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Locky Klavins on 2/11/16.
 */

public class timer extends Activity {

    private ImageView units1, units2, units3, units4, units5, units6, units7, units8;
    private Button buttonStart;
    private Button buttonStop;
    private ImageView incr1, incr2, incr3, incr4, incr5, incr6, incr7, incr8;
    private ImageView decr1, decr2, decr3, decr4, decr5, decr6, decr7, decr8;
    private Handler handler = new Handler();
    private int d1, d2, d3, d4, d5, d6, d7, d8;
    private MediaPlayer mp;
    private EditText txtPhoneNo;
    private EditText txtMessage;

    Integer[] LedDigit1 = {R.drawable.led0, R.drawable.led1,
            R.drawable.led2, R.drawable.led3,
            R.drawable.led4, R.drawable.led5,
            R.drawable.led6, R.drawable.led7,
            R.drawable.led8, R.drawable.led9};

    Integer[] LedDigit2 = {R.drawable.led0, R.drawable.led1,
            R.drawable.led2, R.drawable.led3,
            R.drawable.led4, R.drawable.led5};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelLayout);
        rl.setBackgroundColor(Color.BLACK);

        mp = MediaPlayer.create(timer.this, R.raw.sound);

        units1 = (ImageView) findViewById(R.id.digit00);
        units2 = (ImageView) findViewById(R.id.digit0);
        units3 = (ImageView) findViewById(R.id.digit1);
        units4 = (ImageView) findViewById(R.id.digit2);
        units5 = (ImageView) findViewById(R.id.digit3);
        units6 = (ImageView) findViewById(R.id.digit4);
        units7 = (ImageView) findViewById(R.id.digit5);
        units8 = (ImageView) findViewById(R.id.digit6);

        buttonStart = (Button) findViewById(R.id.startbtn);
        buttonStop = (Button) findViewById(R.id.stopbtn);

        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);

        d1 = 0;
        d2 = 0;
        d3 = 0;
        d4 = 0;
        d5 = 0;
        d6 = 0;
        d7 = 0;
        d8 = 0;

        // Here my Timer's up and Down values are declared. The code is long and is quite dirty. i'm 100% sure it could be written better but i don't have the skills to.
        // each timer has its own set of resources pointing to Units which are declared above so that each number on the timer can increment from 0-5 or 0-9 based on position.
        // i didn't know how to set a set of image resources globally so that i didn't have to add the code in each time.
        incr1 = (ImageView) findViewById(R.id.up1);
        incr1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d1++;
                if(d1 == 10)
                    d1 = 0;
                units1.setImageResource(LedDigit1[d1]);
            }
        });
        incr2 = (ImageView) findViewById(R.id.up2);
        incr2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d2++;
                if(d2 == 6)
                    d2 = 0;
                units2.setImageResource(LedDigit2[d2]);
            }
        });
        incr3 = (ImageView) findViewById(R.id.up3);
        incr3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d3++;
                if(d3 == 10)
                    d3 = 0;
                units3.setImageResource(LedDigit1[d3]);
            }
        });
        incr4 = (ImageView) findViewById(R.id.up4);
        incr4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d4++;
                if(d4 == 6)
                    d4 = 0;
                units4.setImageResource(LedDigit2[d4]);
            }
        });
        incr5 = (ImageView) findViewById(R.id.up5);
        incr5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d5++;
                if(d5 == 10)
                    d5 = 0;
                units5.setImageResource(LedDigit1[d5]);
            }
        });
        incr6 = (ImageView) findViewById(R.id.up6);
        incr6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d6++;
                if(d6 == 6)
                    d6 = 0;
                units6.setImageResource(LedDigit2[d6]);
            }
        });
        incr7 = (ImageView) findViewById(R.id.up7);
        incr7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d7++;
                if(d7 == 10)
                    d7 = 0;
                units7.setImageResource(LedDigit1[d7]);
            }
        });
        incr8 = (ImageView) findViewById(R.id.up8);
        incr8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d8++;
                if(d8 == 6)
                    d8 = 0;
                units8.setImageResource(LedDigit2[d8]);
            }
        });
        decr1 = (ImageView) findViewById(R.id.down1);
        decr1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d1--;
                if(d1 == -1)
                    d1 = 9;
                units1.setImageResource(LedDigit1[d1]);
            }
        });
        decr2 = (ImageView) findViewById(R.id.down2);
        decr2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d2--;
                if(d2 == -1)
                    d2 = 5;
                units2.setImageResource(LedDigit2[d2]);
            }
        });
        decr3 = (ImageView) findViewById(R.id.down3);
        decr3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d3--;
                if(d3 == -1)
                    d3 = 9;
                units3.setImageResource(LedDigit1[d3]);
            }
        });
        decr4 = (ImageView) findViewById(R.id.down4);
        decr4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d4--;
                if(d4 == -1)
                    d4 = 5;
                units4.setImageResource(LedDigit2[d4]);
            }
        });
        decr5 = (ImageView) findViewById(R.id.down5);
        decr5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d5--;
                if(d5 == -1)
                    d5 = 9;
                units5.setImageResource(LedDigit1[d5]);
            }
        });
        decr6 = (ImageView) findViewById(R.id.down6);
        decr6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d6--;
                if(d6 == -1)
                    d6 = 5;
                units6.setImageResource(LedDigit2[d6]);
            }
        });
        decr7 = (ImageView) findViewById(R.id.down7);
        decr7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d7--;
                if(d7 == -1)
                    d7 = 9;
                units7.setImageResource(LedDigit1[d7]);
            }
        });
        decr8 = (ImageView) findViewById(R.id.down8);
        decr8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d8++;
                if(d8 == 10)
                    d8 = 0;
                units8.setImageResource(LedDigit2[d8]);
            }
        });

        buttonStart.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                buttonStart.setClickable(false);
                buttonStop.setClickable(true);
                handler.postDelayed(timeCounter, 0);
            }

        });

        buttonStop.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handler.removeCallbacks(timeCounter);
                buttonStart.setClickable(true);
                buttonStop.setClickable(false);
                mp = null;
                //media player callbacks
                if(mp != null) {
                    mp.stop();
                    mp.reset();
                    mp.release();
                }
                //d values reset
                d1 = 0;
                d2 = 0;
                d3 = 0;
                d4 = 0;
                d5 = 0;
                d6 = 0;
                d7 = 0;
                d8 = 0;
                //image resources
                units1.setImageResource(LedDigit1[d1]);
                units2.setImageResource(LedDigit1[d2]);
                units3.setImageResource(LedDigit1[d3]);
                units4.setImageResource(LedDigit1[d4]);
                units5.setImageResource(LedDigit1[d5]);
                units6.setImageResource(LedDigit1[d6]);
                units7.setImageResource(LedDigit1[d7]);
                units8.setImageResource(LedDigit1[d8]);
            }
        });
    }

    private void sendSMS(String phoneNumber, String message) {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, timer.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }

    private Runnable timeCounter = new Runnable() {

        public void run() {
            d1--;

            if (d1 == -1) {
                d1 = 9;
                d2 = d2 - 1;
            }

            if (d2 == -1) {
                d2 = 5;
                d3 = d3 - 1;
            }

            if (d3 == -1) {
                d3 = 9;
                d4 = d4 - 1;
            }

            if (d4 == -1) {
                d4 = 5;
                d5--;
            }

            if (d5 == -1) {
                d5 = 9;
                d6 = d6 - 1;
            }

            if (d6 == -1) {
                d6 = 5;
                d7--;
            }

            if (d7 == -1) {
                d7 = 9;
                d8--;
            }
            if (d8 == -1) {
                d1 = 0;
                d2 = 0;
                d3 = 0;
                d4 = 0;
                d5 = 0;
                d6 = 0;
                d7 = 0;
                d8 = 0;
            }

            if (d8 == 0 && d7 == 0 && d6 == 0 && d5 == 0 && d4 == 0 && d3 == 0 && d2 == 0 && d1 == 0) {
                mp.start();
                handler.removeCallbacks(timeCounter);
                String phoneNo = txtPhoneNo.getText().toString();
                String message = "Timer is finished. " + txtMessage.getText().toString();

                if (phoneNo.length() > 0 && message.length() > 0) {
                    sendSMS(phoneNo, message);
                } else {
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
                }

            }

            units1.setImageResource(LedDigit1[d1]);
            units2.setImageResource(LedDigit1[d2]);
            units3.setImageResource(LedDigit1[d3]);
            units4.setImageResource(LedDigit1[d4]);
            units5.setImageResource(LedDigit1[d5]);
            units6.setImageResource(LedDigit1[d6]);
            units7.setImageResource(LedDigit1[d7]);
            units8.setImageResource(LedDigit1[d8]);

            handler.postDelayed(timeCounter, 0);
            }
        };
    }

/*
    public void sendSMS(String phoneNumber, String message) {
        PendingIntent piSent = PendingIntent.getBroadcast(getApplicationContext(),
                0, new Intent(SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(getApplicationContext(),
                0, new Intent(DELIVERED), 0);
        SmsManager smsManager = SmsManager.getDefault();
        int length = message.length();

        if (length > MAX_SMS_MESSAGE_LENGTH) {
            ArrayList<String> messagelist = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(phoneNumber, null,
                    messagelist, null, null);
        }   else {
            smsManager.sendTextMessage(phoneNumber, null, message,
                    piSent, piDelivered);
        }


    }
    */