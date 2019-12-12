package com.idw.project.cataloguemovie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.idw.project.cataloguemovie.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    TextView splash;

    long Delay = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splash = findViewById(R.id.tv_splash);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransation);
        splash.startAnimation(myanim);

        Timer RunSplash = new Timer();

        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {

                finish();

                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };

        RunSplash.schedule(ShowSplash, Delay);
    }
}
