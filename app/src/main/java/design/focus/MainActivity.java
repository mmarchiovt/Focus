package design.focus;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{

    private ImageButton lightbulb;
    private ImageButton heart;
    private ImageButton watch;
    private ImageButton speech;

    private static final int RESOLUTION = 200;

    private boolean lightOn;
    private boolean heartOn;
    private boolean watchOn;
    private boolean speechOn;
    private Bitmap lightBMOn;
    private Bitmap heartBMOn;
    private Bitmap speakBMOn;
    private Bitmap watchBMOn;
    private Bitmap lightBMOff;
    private Bitmap heartBMOff;
    private Bitmap speakBMOff;
    private Bitmap watchBMOff;

    private SpeechRecognizer sr;

    private float auto;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Focus");

        lightbulb = (ImageButton) findViewById(R.id.light);
        heart = (ImageButton) findViewById(R.id.heart);
        watch = (ImageButton) findViewById(R.id.watch);
        speech = (ImageButton) findViewById(R.id.speech);

        lightbulb.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.lightbulb_onetemp, RESOLUTION, RESOLUTION));
        heart.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.heart_onetemp, RESOLUTION, RESOLUTION));
        watch.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.watch_onetemp, RESOLUTION, RESOLUTION));
        speech.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.speak_onetemp, RESOLUTION, RESOLUTION));

        lightBMOff = decodeSampledBitmapFromResource(getResources(), R.drawable.lightbulb_onetemp, RESOLUTION, RESOLUTION);
        heartBMOff = decodeSampledBitmapFromResource(getResources(), R.drawable.heart_onetemp, RESOLUTION, RESOLUTION);
        watchBMOff = decodeSampledBitmapFromResource(getResources(), R.drawable.watch_onetemp, RESOLUTION, RESOLUTION);
        speakBMOff = decodeSampledBitmapFromResource(getResources(), R.drawable.speak_onetemp, RESOLUTION, RESOLUTION);

        lightBMOn = decodeSampledBitmapFromResource(getResources(), R.drawable.lightbulb_twotemp, RESOLUTION, RESOLUTION);
        heartBMOn = decodeSampledBitmapFromResource(getResources(), R.drawable.heart_twotemp, RESOLUTION, RESOLUTION);
        watchBMOn = decodeSampledBitmapFromResource(getResources(), R.drawable.watch_vibrate_twotemp, RESOLUTION, RESOLUTION);
        speakBMOn = decodeSampledBitmapFromResource(getResources(), R.drawable.speak_twotemp, RESOLUTION, RESOLUTION);

        lightbulb.setOnClickListener(this);
        heart.setOnClickListener(this);
        watch.setOnClickListener(this);
        speech.setOnClickListener(this);

        //temp
        lightOn = false;
        heartOn = false;
        watchOn = false;
        speechOn = false;

        auto = getWindow().getAttributes().screenBrightness;

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            // Handle the camera action
        }
        else if (id == R.id.wearable_settings)
        {

        }
        else if (id == R.id.speech_Settings)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        if(v.getId() == R.id.heart)
        {
            if(!heartOn)
            {
                heart.setImageBitmap(
                       heartBMOn);
                heartOn=true;
            }
            else
            {
                heart.setImageBitmap(
                        heartBMOff);
                heartOn=false;
            }
        }

        if(v.getId() == R.id.watch)
        {
            if(!watchOn)
            {
                watch.setImageBitmap(
                        watchBMOn);
                watchOn=true;
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

                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
                sr.startListening(intent);


            }
            else
            {
                speech.setImageBitmap(
                       speakBMOff);
                speechOn=false;
                sr.cancel();
                //sr.stopListening();
                sr.destroy();
            }
        }
    }


    // Helpers

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight)
    {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
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



        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }

}

