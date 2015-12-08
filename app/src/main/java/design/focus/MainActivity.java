package design.focus;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks {

    private ImageButton lightBulb, watch, speech, play, pause, stop;
    private Bitmap lightBMOn, speakBMOn, watchBMOn, playBMOn, pauseBMOn, stopBMOn,
            lightBMOff, speakBMOff, watchBMOff, playBMOff, pauseBMOff, stopBMOff;
    private boolean lightOn, watchOn, speechOn, playOn, pauseOn, stopOn;
    private static DrawerLayout drawer = null;
    private static final int RESOLUTION = 200;
    private float auto;
    private TextToSpeech t1;
    private SpeechRecognizer sr;
    private Date startTime;
    private ArrayList<Date> StartTimes, PauseTimes;
    private int[] globalArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Focus");

        lightBulb = (ImageButton) findViewById(R.id.light);
        watch = (ImageButton) findViewById(R.id.watch);
        speech = (ImageButton) findViewById(R.id.speech);
        play = (ImageButton) findViewById(R.id.play);
        pause = (ImageButton) findViewById(R.id.pause);
        stop = (ImageButton) findViewById(R.id.stop);

        lightBMOff = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.lightbulb_onetemp, RESOLUTION, RESOLUTION);
        watchBMOff = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.watch_onetemp, RESOLUTION, RESOLUTION);
        speakBMOff = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.speak_onetemp, RESOLUTION, RESOLUTION);
        playBMOff = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.play_off, RESOLUTION, RESOLUTION);
        pauseBMOff = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.pause_off, RESOLUTION, RESOLUTION);
        stopBMOff = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.finish_off, RESOLUTION, RESOLUTION);

        lightBMOn = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.lightbulb_twotemp, RESOLUTION, RESOLUTION);
        watchBMOn = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.watch_vibrate_twotemp, RESOLUTION, RESOLUTION);
        speakBMOn = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.speak_twotemp, RESOLUTION, RESOLUTION);
        playBMOn = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.play_on, RESOLUTION, RESOLUTION);
        pauseBMOn = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.pause_on, RESOLUTION, RESOLUTION);
        stopBMOn = BitmapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.finish_on, RESOLUTION, RESOLUTION);

        lightBulb.setImageBitmap(lightBMOff);
        watch.setImageBitmap(watchBMOff);
        speech.setImageBitmap(speakBMOff);
        play.setImageBitmap(playBMOff);
        pause.setImageBitmap(pauseBMOff);
        stop.setImageBitmap(stopBMOff);

        clickListeners();

        //temp
        lightOn = false;
        watchOn = false;
        speechOn = false;
        playOn = false;
        pauseOn = false;
        stopOn = false;

        StartTimes = new ArrayList<>();
        PauseTimes = new ArrayList<>();

        auto = getWindow().getAttributes().screenBrightness;

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });
    }

    private void clickListeners() {

        lightBulb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lightOn) {
                    lightBulb.setImageBitmap(lightBMOn);
                    lightOn = true;
                    drawer.setVisibility(View.INVISIBLE);
                    setContentView(R.layout.blue);
                    Intent intent = new Intent(getApplicationContext(), SplashLoader.class);
                    intent.putExtra("from main", true);
                    startActivity(intent);
                }
                else {
                    lightBulb.setImageBitmap(lightBMOff);
                    lightOn = false;
                    drawer.setVisibility(View.VISIBLE);
                    WindowManager.LayoutParams layout = getWindow().getAttributes();
                    layout.screenBrightness = auto;
                    getWindow().setAttributes(layout);

                }
        }});

        watch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!watchOn) {
                    watch.setImageBitmap(watchBMOn);
                    watchOn=true;
                    long time = System.currentTimeMillis();
                    Date resultdate = new Date(time);
                    //Toast toast = Toast.makeText(getApplicationContext(), resultdate.toString(), Toast.LENGTH_LONG);
                    //toast.show();
                    watchClick();
                }
                else {
                    watch.setImageBitmap(watchBMOff);
                    watchOn=false;
                }
            }
        });

        speech.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!speechOn) {
                    speech.setImageBitmap(speakBMOn);
                    speechOn=true;
                    globalArray = newMathQuestion();
                    t1.speak(("What is " +globalArray[0] + "+" + globalArray[1] + "?"),TextToSpeech.QUEUE_FLUSH, null);

                    //noinspection StatementWithEmptyBody
                    while(t1.isSpeaking()) {} //Does Nothing

                    sr = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
                    sr.setRecognitionListener(new listener());

                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                    sr.startListening(intent);
                }
                else {
                    speech.setImageBitmap(speakBMOff);
                    speechOn=false;
                    sr.destroy();
                }
            }});

        play.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!playOn) {
                    play.setImageBitmap(playBMOn);
                    playOn=true;
                    stop.setImageBitmap(stopBMOn);
                    stopOn=true;
                    long time = System.currentTimeMillis();
                    startTime = new Date(time);
                    PauseTimes.clear();
                    StartTimes.clear();
                    StartTimes.add(startTime);
                }
                if(playOn && pauseOn) {
                    pause.setImageBitmap(pauseBMOff);
                    pauseOn = false;
                    stop.setImageBitmap(stopBMOn);
                    stopOn=true;
                    long time = System.currentTimeMillis();
                    Date pauseEnd = new Date(time);
                    PauseTimes.add(pauseEnd);
                }
            }});

        pause.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pauseOn && playOn) {
                    pause.setImageBitmap(pauseBMOn);
                    pauseOn=true;
                    stop.setImageBitmap(stopBMOff);
                    stopOn=false;
                    long time = System.currentTimeMillis();
                    Date pauseStart = new Date(time);
                    PauseTimes.add(pauseStart);
                }
                else {
                    pause.setImageBitmap(pauseBMOff);
                    pauseOn = false;
                    stop.setImageBitmap(stopBMOn);
                    stopOn=true;
                    long time = System.currentTimeMillis();
                    Date pauseEnd = new Date(time);
                    PauseTimes.add(pauseEnd);
                }
            }});

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stopOn && !pauseOn) {

                    play.setImageBitmap(playBMOff);
                    playOn = false;

                    long time = System.currentTimeMillis();
                    Date endTime = new Date(time);
                    long temp = 0, total, result;

                    for (int i = 0; i < PauseTimes.size(); i += 2) {
                        temp += PauseTimes.get(i + 1).getTime() - PauseTimes.get(i).getTime();
                    }

                    total = endTime.getTime() - startTime.getTime();
                    result = total - temp;

                    System.out.println("Total Break Time ~" + temp / 1000 + " seconds");
                    System.out.println("Total Trip Time ~" + total / 1000 + " seconds");
                    System.out.println("Total Driving Time ~" + result / 1000 + " seconds");
                     Toast toast = Toast.makeText(getApplicationContext(), "Total Break Time ~" + temp / 1000 + " seconds\n" + "Total Trip Time ~" + total / 1000 + " seconds\n" + "Total Driving Time ~" + result / 1000 + " seconds", Toast.LENGTH_LONG);
                    toast.show();

                    stop.setImageBitmap(stopBMOff);
                    stopOn = false;

                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putLong(getString(R.string.trips), result);
                    editor.apply();

                    long item = sharedPref.getLong(getString(R.string.trips), result);

                    System.out.println(item);
                }
            }
        });
    }

    private void watchClick() {
        android.support.v7.app.NotificationCompat.WearableExtender wearableExtender =
                new android.support.v7.app.NotificationCompat.WearableExtender()
                        .setHintShowBackgroundOnly(true);

        Notification notification =
                new android.support.v7.app.NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Hello Truck Driver")
                        .setContentText("Please be careful!")
                        .extend(wearableExtender)
                        .setVibrate(new long[]{1000,1000})
                        .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notification);
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.stats) {
            Intent intent = new Intent(this, Statistics.class);
            startActivity(intent);
        }
        else if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    public void onConnected(Bundle bundle) {}

    public void onConnectionSuspended(int i) {}

    // inner listener class
    private class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {}

        public void onBeginningOfSpeech() {}

        public void onRmsChanged(float thing) {}

        public void onBufferReceived(byte[] buffer) {}

        public void onEndOfSpeech() {}

        public void onError(int error) {}

        public void onPartialResults(Bundle partialResults) {}

        public void onEvent(int eventType, Bundle params) {}

        @Override
        public void onResults(Bundle results) {
            String str = "";
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            System.out.println(data);
            for (int i = 0; i < data.size(); i++) {
                str += data.get(i);
            }

            Toast toast = Toast.makeText(getApplicationContext(),data.get(0).toString(), Toast.LENGTH_LONG);
            toast.show();

            int i = globalArray[0]+globalArray[1];

            if (data.get(0).toString().contains(i+"")) {
                t1.speak("correct", TextToSpeech.QUEUE_FLUSH, null);
            }
            else {
                t1.speak("wrong wrong wrong", TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    public String newQuestion() {
        Random rand = new Random();
        int i = rand.nextInt(10);
        System.out.println(i);

        switch (i) {
            case 0:
                return "How many eggs in a dozen?";
            case 1:
                return "How many feathers in a dozen?";
            case 2:
                return "How many rocks in a dozen?";
            case 3:
                return "How many cars in a dozen?";
            case 4:
                return "How many hairs in a dozen?";
            case 5:
                return "How many dogs in a dozen?";
            case 6:
                return "How many cats in a dozen?";
            case 7:
                return "How many fish in a dozen?";
            case 8:
                return "How many apples in a dozen?";
            case 9:
                return "How many shoes in a dozen?";
            default:
                return "hello?";
        }
    }

    public int[] newMathQuestion() {
        Random rand = new Random();
        int x = rand.nextInt(10) + 1, y = rand.nextInt(10) + 1;
        int[] a = new int[2];
        a[0]=x;
        a[1]=y;
        return a;
    }
}