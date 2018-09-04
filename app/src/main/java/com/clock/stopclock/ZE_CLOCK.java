package com.clock.stopclock;

        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Color;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.ParcelFileDescriptor;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import java.util.Calendar;
        import java.text.SimpleDateFormat;

        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.Date;

public class ZE_CLOCK extends Activity {

    private ImageView units1, units2, units3, units4, units5, units6, units7, units8;
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonLap;
    private Button buttonPause;
    private Button buttonClear;
    private TextView lapString;
    private EditText logTxt;
    private Handler handler = new Handler();
    private int d1, d2, d3, d4, d5, d6, d7, d8;
    private int c, e;
    private static final int CREATE_REQUEST_CODE = 40, OPEN_REQUEST_CODE = 41, SAVE_REQUEST_CODE = 42;
    public static final String DATE_FORMAT_NOW = "dd-MM-yyy HH:mm:ss";

    Integer[] LedDigit1 = {R.drawable.led0, R.drawable.led1,
            R.drawable.led2, R.drawable.led3,
            R.drawable.led4, R.drawable.led5,
            R.drawable.led6, R.drawable.led7,
            R.drawable.led8, R.drawable.led9};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ze__clock);

        //this hides the keyboard on the initial load of the class, because it kept getting in the way.
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Here my Relative Layout variable is defined so that i can set a specified layout or background colour, etc. to the Design every time it initially loads up.
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativeLayout);
        rl.setBackgroundColor(Color.BLACK);

        //These set of variables are the last step to the LedDigit1 Integer[]'s phase. These variables 'units' are defined to the corresponding digit from right to left.
        //Here i am declaring that units1 = digit00; where digit00 is the actual ID of each ImageView where the numbers of the Stopwatch are displayed.
        units1 = (ImageView) findViewById(R.id.digit00);
        units2 = (ImageView) findViewById(R.id.digit0);
        units3 = (ImageView) findViewById(R.id.digit1);
        units4 = (ImageView) findViewById(R.id.digit2);
        units5 = (ImageView) findViewById(R.id.digit3);
        units6 = (ImageView) findViewById(R.id.digit4);
        units7 = (ImageView) findViewById(R.id.digit5);
        units8 = (ImageView) findViewById(R.id.digit6);

        //This is the Start button. it starts the Stopwatch activity.
        buttonStart = (Button) findViewById(R.id.startbtn);

        //This is the Pause button. it pauses the Stopwatch activity.
        buttonPause = (Button) findViewById(R.id.pausebtn);

        // This is the Stop button. it stops the Stopwatch activity.
        buttonStop = (Button) findViewById(R.id.stopbtn);

        //This is the Lap button. it uses strings to print values to text such as d1, d2, d3 etc in the right order to print a lap. e.g. "Lap 1: 1:00:35:59"
        buttonLap = (Button) findViewById(R.id.lapbtn);

        //This is the Clear Lap button. it clears the laps Text-field and resets the lap back to 0 (the c value)
        buttonClear = (Button) findViewById(R.id.clearbtn);

        //This is the Lap text-field. it displays the laps by appending text every time the Lap button is pressed.
        lapString = (TextView) findViewById(R.id.lapText);

        logTxt = (EditText) findViewById(R.id.logText);

        //these set of variables are pointing to the correct digit corresponding to its number from right to left via the LedDigit Integer[].
        d1 = 0;
        d2 = 0;
        d3 = 0;
        d4 = 0;
        d5 = 0;
        d6 = 0;
        d7 = 0;
        d8 = 0;

        //This variable is used to count the number of laps every time you press the 'Lap' button
        c = 0;
        e = 0;

        //This is where my start button's code is.
        buttonStart.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                //here i am saying to start the counter
                //it's needed here because the stop and/or pause button stops the handler from running altogether
                handler.postDelayed(timeCounter, 0);
                //I set the buttonStart Onclick to false after it is pressed so that it doesn't speed it up faster than it's supposed to be.
                buttonStart.setClickable(false);
                //stop button is enabled so that you can stop it after you press start
                buttonStop.setClickable(true);
                //and the same for the pause button.
                buttonPause.setClickable(true);
            }

        });

        //This is where my stop button's code is.
        buttonStop.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                //removeCallbacks removes all the processes from the running queue as it is a runnable.
                handler.removeCallbacks(timeCounter);
                //d1, etc. is defined here as '0' so that when the stop button is pressed it is also resetting the timer back to 0.
                d1 = 0;
                d2 = 0;
                d3 = 0;
                d4 = 0;
                d5 = 0;
                d6 = 0;
                d7 = 0;
                d8 = 0;
                //after the stop button is pressed, the start button is now allowed to be pressed again.
                buttonStart.setClickable(true);
                //this isn't super important. it just doesn't let you press stop again until it is set to allowed again.
                buttonStop.setClickable(false);
                //the pause button is disabled because you have stopped the Stopwatch.
                buttonPause.setClickable(false);
                //i have declared my units here because i used them above to set the digits back to 0 on button press.
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

        buttonPause.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handler.removeCallbacks(timeCounter);
                buttonStop.setClickable(true);
                buttonStart.setClickable(true);
                buttonPause.setClickable(false);
            }
        });

        buttonLap.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (buttonLap.isPressed()) {
                    c++;
                    lapString.append("Lap " + c + ": " + d8 + d7 + ":" + d6 + d5 + ":" + d4 + d3 + ":" + d2 + d1 +
                            " | duration: " + e + "\n");

                    units1.setImageResource(LedDigit1[d1]);
                    units2.setImageResource(LedDigit1[d2]);
                    units3.setImageResource(LedDigit1[d3]);
                    units4.setImageResource(LedDigit1[d4]);
                    units5.setImageResource(LedDigit1[d5]);
                    units6.setImageResource(LedDigit1[d6]);
                    units7.setImageResource(LedDigit1[d7]);
                    units8.setImageResource(LedDigit1[d8]);
                }

            }
        });

        buttonClear.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                lapString.setText("");
                c = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clock_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //where's my splash screen?

    //the *Help* page in the main menu will contain basic instructions to help with navigating through the app and refer to the about menu for more help.

    //the *About* menu will bring up an overlay (not full screen but nearly) on top of whatever you are doing and can be exited by the back button.
    //--maybe by clicking outside it, too.

    private Runnable timeCounter = new Runnable() {

        public void run() {
            d1++;

            if (d1 == 10) {
                d1 = 0;
                d2++;
            }

            if (d2 == 6) {
                d2 = 0;
                d3++;
            }

            if (d3 == 10) {
                d3 = 0;
                d4++;
            }

            if (d4 == 6) {
                d4 = 0;
                d5++;
            }

            if (d5 == 10) {
                d5 = 0;
                d6++;
            }

            if (d6 == 6) {
                d6 = 0;
                d7++;
            }

            if (d7 == 10) {
                d7 = 0;
                d8++;
            }

            if (d8 == 6) {
                d1 = 0;
                d2 = 0;
                d3 = 0;
                d4 = 0;
                d5 = 0;
                d6 = 0;
                d7 = 0;
                d8 = 0;
            }

            if (d1 == 0 && d2 == 0 && d3 == 0 && d4 == 0 && d5 == 0 && d6 == 0 && d7 == 0 && d8 == 0) {
                buttonStart.setEnabled(true);
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

    public void newFile(View view)
    {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "newfile.txt");

        startActivityForResult(intent, CREATE_REQUEST_CODE);
    }

    private void writeFileContent(Uri uri) {
        try {
            ParcelFileDescriptor pfd =
                    this.getContentResolver().
                            openFileDescriptor(uri, "w");

            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());

            String textContent =
                    new Date().toString()
                            + "\n"
                            + logTxt.getText().toString()
                            + "\n"
                            + "-------------------------------------"
                            + "\n"
                            + lapString.getText().toString()
                            + "-------------------------------------"
                            + "\n"
                            + "Log file created by StopClock";

            fileOutputStream.write(textContent.getBytes());

            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        Uri currentUri = null;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CREATE_REQUEST_CODE) {

                if (resultData != null) {
                    currentUri = resultData.getData();
                    writeFileContent(currentUri);
                }


            }

        }
    }
}

//storage

/**
 package com.zamzit.scorpio.storagedemo;

 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.BufferedReader;
 import java.io.InputStream;
 import java.io.InputStreamReader;

 import android.app.Activity;
 import android.os.Bundle;
 import android.view.Menu;
 import android.view.MenuItem;
 import android.widget.EditText;
 import android.content.Intent;
 import android.view.View;
 import android.net.Uri;
 import android.os.ParcelFileDescriptor;


 public class StorageDemoActivity extends AppCompatActivity {

 private static EditText textView;

 private static final int CREATE_REQUEST_CODE = 40;
 private static final int OPEN_REQUEST_CODE = 41;
 private static final int SAVE_REQUEST_CODE = 42;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_storage_demo);

 textView = (EditText) findViewById(R.id.fileText);
 }

 public void newFile(View view)
 {
 Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

 intent.addCategory(Intent.CATEGORY_OPENABLE);
 intent.setType("text/plain");
 intent.putExtra(Intent.EXTRA_TITLE, "newfile.txt");

 startActivityForResult(intent, CREATE_REQUEST_CODE);
 }

 public void saveFile(View view)
 {
 Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
 intent.addCategory(Intent.CATEGORY_OPENABLE);
 intent.setType("text/plain");

 startActivityForResult(intent, SAVE_REQUEST_CODE);
 }

 public void openFile(View view)
 {
 Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
 intent.addCategory(Intent.CATEGORY_OPENABLE);
 intent.setType("text/plain");
 startActivityForResult(intent, OPEN_REQUEST_CODE);
 }

 private void writeFileContent(Uri uri)
 {
 try{
 ParcelFileDescriptor pfd =
 this.getContentResolver().
 openFileDescriptor(uri, "w");

 FileOutputStream fileOutputStream =
 new FileOutputStream(pfd.getFileDescriptor());

 String textContent =
 textView.getText().toString();

 fileOutputStream.write(textContent.getBytes());

 fileOutputStream.close();
 pfd.close();
 } catch (FileNotFoundException e) {
 e.printStackTrace();
 } catch (IOException e) {
 e.printStackTrace();
 }
 }

 private String readFileContent(Uri uri) throws IOException {

 InputStream inputStream =
 getContentResolver().openInputStream(uri);
 BufferedReader reader =
 new BufferedReader(new InputStreamReader(
 inputStream));
 StringBuilder stringBuilder = new StringBuilder();
 String currentline;
 while ((currentline = reader.readLine()) != null) {
 stringBuilder.append(currentline + "\n");
 }
 inputStream.close();
 return stringBuilder.toString();
 }

 public void onActivityResult(int requestCode, int resultCode,
 Intent resultData) {

 Uri currentUri = null;

 if (resultCode == Activity.RESULT_OK)
 {
 if (requestCode == CREATE_REQUEST_CODE)
 {
 if (resultData != null) {
 textView.setText("");
 }
 } else if (requestCode == SAVE_REQUEST_CODE) {

 if (resultData != null) {
 currentUri = resultData.getData();
 writeFileContent(currentUri);
 }
 } else if (requestCode == OPEN_REQUEST_CODE) {

 if (resultData != null) {
 currentUri = resultData.getData();

 try {
 String content =
 readFileContent(currentUri);
 textView.setText(content);
 } catch (IOException e) {

 }
 }
 }


 }
 }



 }

 */