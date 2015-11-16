package design.focus;

import android.media.Image;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{

    private ImageButton lightbulb;
    private ImageButton heart;
    private ImageButton watch;
    private ImageButton speech;

    private boolean lightOn;
    private boolean heartOn;
    private boolean watchOn;
    private boolean speechOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        lightbulb = (ImageButton) findViewById(R.id.light);
        heart = (ImageButton) findViewById(R.id.heart);
        watch = (ImageButton) findViewById(R.id.watch);
        speech = (ImageButton) findViewById(R.id.speech);


        lightbulb.setOnClickListener(this);
        heart.setOnClickListener(this);
        watch.setOnClickListener(this);
        speech.setOnClickListener(this);

        //temp
        lightOn = false;
        heartOn = false;
        watchOn = false;
        speechOn = false;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.light)
        {
            if(!lightOn)
            {
                lightbulb.setImageResource(R.drawable.lightbulb_2temp);
                lightOn=true;
            }
            else
            {
                lightbulb.setImageResource(R.drawable.lightbulb_1temp);
                lightOn=false;
            }
        }
        if(v.getId() == R.id.heart)
        {
            if(!heartOn)
            {
                heart.setImageResource(R.drawable.heart_2temp);
                heartOn=true;
            }
            else
            {
                heart.setImageResource(R.drawable.heart_1temp);
                heartOn=false;
            }
        }
        if(v.getId() == R.id.watch)
        {
            if(!watchOn)
            {
                watch.setImageResource((R.drawable.watch_vibrate_2tmep));
                watchOn=true;
            }
            else
            {
                watch.setImageResource((R.drawable.watch_vibrate_1temp));
                watchOn=false;
            }

        }
        if(v.getId() == R.id.speech)
        {
            if(!speechOn)
            {
                speech.setImageResource(R.drawable.speak_2temp);
                speechOn=true;
            }
            else
            {
                speech.setImageResource(R.drawable.speak_1temp);
                speechOn=false;
            }
        }
    }
}
