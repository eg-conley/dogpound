package com.example.dogpound;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends Activity implements OnClickListener
{   //declare restart button and score variables
    private static int highScore=0;
    private Button button_restart;
    private TextView textView_highScore, textView_score, textView_congrats;
    private MediaPlayer congrats;
    private int frame= 0;
    private int congratsDogs[] = {R.drawable.congratsdog1,R.drawable.congratsdog2};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    //launches result page with components
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result); //sets result page layout

        //retrieve score and high score from MainActivity
        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("SCORE");

        if(score > highScore)
        {
            congrats = MediaPlayer.create(this, R.raw.congrats); //background music
            congrats.start();
            highScore = score;
            textView_congrats = findViewById(R.id.newhighscore);
            textView_congrats.setText("New High Score!");

            //animate congrats dog
            Handler congratsHandler = new Handler();
            ImageView congratsDog = findViewById(R.id.saddog);
            congratsDog.setImageResource(R.drawable.congratsdog1);
            congratsHandler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    congratsDog.setImageResource(congratsDogs[frame]);
                    congratsHandler.postDelayed(this,500);
                    frame++;
                    if (frame > 1)
                        frame = 0;
                }
            }, 500);
        }

        //display score and high score
        textView_score = findViewById(R.id.score);
        textView_score.setText("Score: " + score); //displays score
        textView_highScore = findViewById(R.id.highscore);
        textView_highScore.setText("High Score: " + highScore); //displays high score

        //set up restart button function
        button_restart = findViewById(R.id.restart);
        button_restart.setOnClickListener(this);
    }

    @Override
    public void onClick(View again)
    {   //go to StartActivity page when restart button is clicked
        Intent StartActivity = new Intent(ResultActivity.this, StartActivity.class);
        startActivity(StartActivity); //start StartActivity
    }
}
