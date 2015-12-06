package design.focus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{

    private ImageButton lightbulb;
    private ImageButton watch;
    private ImageButton speech;
    private ImageButton play;
    private ImageButton pause;
    private ImageButton stop;

    private static final int RESOLUTION = 200;
    private static final int RESOLUTION2 = 100;

    private boolean lightOn;
    private boolean watchOn;
    private boolean speechOn;
    private boolean playOn;
    private boolean pauseOn;
    private boolean stopOn;

    private Bitmap lightBMOn;
    private Bitmap speakBMOn;
    private Bitmap watchBMOn;
    private Bitmap playBMOn;
    private Bitmap pauseBMOn;
    private Bitmap stopBMOn;
    private Bitmap lightBMOff;
    private Bitmap speakBMOff;
    private Bitmap watchBMOff;
    private Bitmap playBMOff;
    private Bitmap pauseBMOff;
    private Bitmap stopBMOff;

    private static DrawerLayout drawer = null;

    private SpeechRecognizer sr;

    private float auto;

    private AlarmManagerBroadcastReceiver alarm;

    private TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Focus");

        alarm = new AlarmManagerBroadcastReceiver();

        lightbulb = (ImageButton) findViewById(R.id.light);
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

        lightbulb.setImageBitmap(lightBMOff);
        watch.setImageBitmap(watchBMOff);
        speech.setImageBitmap(speakBMOff);
        play.setImageBitmap(playBMOff);
        pause.setImageBitmap(pauseBMOff);
        stop.setImageBitmap(stopBMOff);

        lightbulb.setOnClickListener(this);
        watch.setOnClickListener(this);
        speech.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

        //temp
        lightOn = false;
        watchOn = false;
        speechOn = false;
        playOn = false;
        pauseOn = false;
        stopOn = false;

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

    @Override
    public void onBackPressed()
    {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.phone_settings)
        {

        }
        else if (id == R.id.wearable_settings)
        {

        }
        else if (id == R.id.speech_Settings)
        {

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.light)
        {
            if(!lightOn)
            {
                lightbulb.setImageBitmap(lightBMOn);
                lightOn=true;

                drawer.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(this, SplashLoader.class);
                intent.putExtra("from main", true);
                startActivity(intent);
            }
            else
            {
                lightbulb.setImageBitmap(
                        lightBMOff);
                lightOn=false;

                WindowManager.LayoutParams layout = getWindow().getAttributes();
                layout.screenBrightness = auto;
                getWindow().setAttributes(layout);

            }
        }

        if(v.getId() == R.id.watch)
        {
            if(!watchOn)
            {
                watch.setImageBitmap(
                        watchBMOn);
                watchOn=true;

                long time = System.currentTimeMillis();
                Date resultdate = new Date(time);
                Toast toast = Toast.makeText(getApplicationContext(), resultdate.toString(), Toast.LENGTH_LONG);
                toast.show();

                Context context = this.getApplicationContext();
                if(alarm != null){
                    alarm.setOnetimeTimer(context);
                }else{
                    Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                watch.setImageBitmap(
                        watchBMOff);
                watchOn=false;
            }

        }

        if(v.getId() == R.id.speech)
        {
            if(!speechOn)
            {
                speech.setImageBitmap(
                        speakBMOn);
                speechOn=true;

                sr = SpeechRecognizer.createSpeechRecognizer(this);
                sr.setRecognitionListener(new listener());

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                sr.startListening(intent);


            }
            else
            {
                speech.setImageBitmap(
                       speakBMOff);
                speechOn=false;
                sr.cancel();
                sr.destroy();
            }
        }

        if(v.getId() == R.id.play)
        {
            if(!playOn)
            {
                play.setImageBitmap(
                        playBMOn);
                playOn=true;

            }
            else
            {
                play.setImageBitmap(
                        playBMOff);
                playOn=false;
            }
        }

        if(v.getId() == R.id.pause)
        {
            if(!pauseOn)
            {
                pause.setImageBitmap(
                        pauseBMOn);
                pauseOn=true;

            }
            else
            {
                pause.setImageBitmap(
                        pauseBMOff);
                pauseOn=false;
            }
        }

        if(v.getId() == R.id.stop)
        {
            if(!stopOn)
            {
                stop.setImageBitmap(
                        stopBMOn);
                stopOn=true;

            }
            else
            {
                stop.setImageBitmap(
                        stopBMOff);
                stopOn=false;
            }
        }
    }

    // inner listener class

    private class listener implements RecognitionListener
    {

        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results)
        {
            String str = new String();
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            System.out.println(data);
            for (int i = 0; i < data.size(); i++)
            {
                str += data.get(i);
            }

            Toast toast = Toast.makeText(getApplicationContext(),data.get(0).toString(), Toast.LENGTH_LONG);
            toast.show();

            t1.speak(data.get(0).toString(), TextToSpeech.QUEUE_FLUSH, null);

        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }



}

