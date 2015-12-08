package design.focus;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationManagerCompat;
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
import android.support.v4.app.NotificationCompat;

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

    private TextToSpeech t1;
    private Date startTime;

    private ArrayList<Date> StartTimes;
    private ArrayList<Date> PauseTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //blue = (LinearLayout) findViewById(R.id.blue);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Focus");

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

        StartTimes = new ArrayList<>();
        PauseTimes = new ArrayList<>();

        auto = getWindow().getAttributes().screenBrightness;

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status)
            {
                if(status != TextToSpeech.ERROR)
                {
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

        if (id == R.id.stats)
        {
            Intent intent = new Intent(this, Statistics.class);
            startActivity(intent);
        }
        else if (id == R.id.settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        else {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
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

                setContentView(R.layout.blue);

                Intent intent = new Intent(this, SplashLoader.class);
                intent.putExtra("from main", true);
                startActivity(intent);
            }
            else
            {
                lightbulb.setImageBitmap(lightBMOff);
                lightOn=false;

                drawer.setVisibility(View.VISIBLE);


                WindowManager.LayoutParams layout = getWindow().getAttributes();
                layout.screenBrightness = auto;
                getWindow().setAttributes(layout);

            }
        }

        if(v.getId() == R.id.watch)
        {
            if(!watchOn)
            {
                watch.setImageBitmap(watchBMOn);
                watchOn=true;
                Notification notification = new NotificationCompat.Builder(getApplication())
                        .setSmallIcon(R.drawable.icon)
                        .extend(new NotificationCompat.WearableExtender())
                        .build();
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());

                int notificationId = 1;
                notificationManager.notify(notificationId, notification);

                long time = System.currentTimeMillis();
                Date resultdate = new Date(time);
                Toast toast = Toast.makeText(getApplicationContext(), resultdate.toString(), Toast.LENGTH_LONG);
                toast.show();

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

                t1.speak("Question:",TextToSpeech.QUEUE_FLUSH, null);

                //noinspection StatementWithEmptyBody
                while(t1.isSpeaking())
                {
                    //Does Nothing
                }

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

                stop.setImageBitmap(stopBMOn);
                stopOn=true;

                long time = System.currentTimeMillis();
                startTime = new Date(time);
                //System.out.println(startTime.getHours() + ":" + startTime.getMinutes());

                PauseTimes.clear();
                StartTimes.clear();

                StartTimes.add(startTime);
            }
            if(playOn && pauseOn)
            {
                pause.setImageBitmap(
                        pauseBMOff);
                pauseOn = false;

                stop.setImageBitmap(stopBMOn);
                stopOn=true;

                long time = System.currentTimeMillis();
                Date pauseEnd = new Date(time);

                PauseTimes.add(pauseEnd);
            }

        }

        if(v.getId() == R.id.pause)
        {
            if(!pauseOn && playOn)
            {
                pause.setImageBitmap(
                        pauseBMOn);
                pauseOn=true;

                stop.setImageBitmap(stopBMOff);
                stopOn=false;

                long time = System.currentTimeMillis();
                Date pauseStart = new Date(time);
                PauseTimes.add(pauseStart);
            }
            else {
                pause.setImageBitmap(
                        pauseBMOff);
                pauseOn = false;

                stop.setImageBitmap(stopBMOn);
                stopOn=true;

                long time = System.currentTimeMillis();
                Date pauseEnd = new Date(time);

                PauseTimes.add(pauseEnd);
            }
        }

        if(v.getId() == R.id.stop)
        {
            if(stopOn && !pauseOn) {

                play.setImageBitmap(
                        playBMOff);
                playOn = false;

                long time = System.currentTimeMillis();
                Date endTime = new Date(time);
                long temp = 0;
                long total;
                long result;


                for (int i = 0; i < PauseTimes.size(); i+=2)
                {
                    temp += PauseTimes.get(i+1).getTime() - PauseTimes.get(i).getTime();
                }


                total = endTime.getTime() - startTime.getTime();
                result =  total - temp;

                System.out.println("Pause ~" + temp/1000 + " seconds");

                System.out.println("Total Trip Time ~" + total/1000 + " seconds");

                System.out.println("Total Driving Time ~" + result/1000 + " seconds");
               // Toast toast = Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG);
               // toast.show();

                stop.setImageBitmap(
                        stopBMOff);
                stopOn=false;

                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong(getString(R.string.trips), result);
                editor.apply();

                long item = sharedPref.getLong(getString(R.string.trips), result);

                System.out.println(item);

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
        public void onError(int error)
        {
            t1.speak("fine", TextToSpeech.QUEUE_FLUSH, null);
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

            t1.speak("No", TextToSpeech.QUEUE_FLUSH, null);

        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }

    }

}

