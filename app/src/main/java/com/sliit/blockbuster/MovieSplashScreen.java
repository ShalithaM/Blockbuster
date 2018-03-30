package com.sliit.blockbuster;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MovieSplashScreen extends AppCompatActivity {

    private static final int SPLASH_SHOW_TIME = 4800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_movie_splash_screen);

        new Handler().postDelayed( new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(MovieSplashScreen.this,MovieMainActivity.class);
                MovieSplashScreen.this.startActivity(mainIntent);
                MovieSplashScreen.this.finish();
            }
        }, SPLASH_SHOW_TIME);
    }

}
