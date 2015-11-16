package design.focus;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SplashLoader extends AppCompatActivity implements View.OnClickListener {

    Button loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        loadingBar = (Button) findViewById(R.id.progressBar);
        loadingBar.setOnClickListener(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_loader);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.progressBar)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
