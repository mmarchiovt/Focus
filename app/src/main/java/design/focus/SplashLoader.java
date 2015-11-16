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

    private Button loadingBar;


    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_loader);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loadingBar = (Button) findViewById(R.id.progressBar);
        loadingBar.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.progressBar)
        {
            switchToMain();
        }
    }

    private void switchToMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
