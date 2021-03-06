package design.focus;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashLoader extends AppCompatActivity {

    protected boolean first;

    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        LinearLayout layout;
        ImageView icon;

        if(extras != null && extras.getBoolean("from main")) {
            setContentView(R.layout.blue);
            layout = (LinearLayout) findViewById(R.id.blue);
        }
        else {
            setContentView(R.layout.activity_splash_loader);
            icon = (ImageView) findViewById(R.id.icon);
            icon.setImageBitmap(BitmapHelper.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.icon, 100, 100));
            layout = (LinearLayout) findViewById(R.id.splash);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMain();
            }
        });

        if (extras == null) {
            first =  false;
            timerToMain();
        }
        else {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.screenBrightness = 1F;
            getWindow().setAttributes(layoutParams);
        }
    }

    private void timerToMain() {
        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {}
            public void onFinish()
            {
               switchToMain();
            }
        }.start();
    }

    private void switchToMain() {
        first = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}