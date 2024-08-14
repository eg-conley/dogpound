package com.example.dogpound;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity {

    private static final long DELAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent startIntent = new Intent(WelcomeActivity.this, StartActivity.class);
                startActivity(startIntent);
                finish();
            }
        }, DELAY_TIME);
    }
}
