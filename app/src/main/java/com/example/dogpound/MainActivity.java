package com.example.dogpound;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends Activity
{   //declare animal location, holes, score, and music variables
    private int doglocation, birdlocation;
    private int[] holes = {R.id.hole1, R.id.hole2, R.id.hole3,
            R.id.hole4, R.id.hole5, R.id.hole6,
            R.id.hole7, R.id.hole8, R.id.hole9};
    private int score;
    private TextView textView_time, textView_score ;
    private MediaPlayer song, pop, buzz;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    //launches main page with components
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //sets main page layout

        //initialize background music and sound effects
        song = MediaPlayer.create(this, R.raw.music); //background music
        pop = MediaPlayer.create(this, R.raw.pop); //correctly clicks terrier
        buzz = MediaPlayer.create(this, R.raw.buzz); //incorrectly clicks bird
        song.start(); //start song with game

        textView_time = findViewById(R.id.timedisplay); //calls text ID to display time
        long time = 15000; //initializes player's game time
        textView_score = findViewById(R.id.currentscore); //calls text ID to display score

        //functions to operate game
        startTimer(time); //starts timer for game
        generateAnimals(time); //generates moles during game
        setClickListener(); //animal buttons' click listeners
    }

    private void startTimer(long time)
    //starts timer for game
    {
        //starts countdown for game, time is in milliseconds
        new CountDownTimer(time, 1000) {
            public void onTick(long time_left)
            //displays remaining game time
            {
                textView_time.setText("Timer: " + String.valueOf((time_left / 1000) + 1));
            }
            public void onFinish()
            //determines high score and goes to ResultActivity page when game is finished
            {
                //declare intent to switch to StartActivity
                Intent ResultActivity = new Intent(MainActivity.this, ResultActivity.class);
                ResultActivity.putExtra("SCORE", score); //push score to results
                song.pause(); //stop background music

                startActivity(ResultActivity); //start ResultActivity
                finish();
            }
        }.start();
    }

    //declare handler for moles for generation timing
    Handler moleHandler = new Handler();
    private void generateAnimals(long time)
    //generates moles during game
    {
        new CountDownTimer(time, 700) {
            public void onTick(long time_left)
            //randomly generates a dog in hole
            {
                doglocation = randNum();
                //displays dog in hole
                ImageButton randDog = findViewById(holes[doglocation]);
                randDog.setImageResource(R.drawable.dog_resize);
                randDog.setClickable(true);
                moleHandler.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        randDog.setImageResource(R.drawable.hole);
                    }
                }, 500);

                //randomly generates a bird in different hole from dog
                birdlocation = randNum();
                while (birdlocation == doglocation)
                //keeps generating location for bird until it is different from dog location
                {
                    birdlocation = randNum();
                }
                //displays bird in hole
                ImageButton randBird = findViewById(holes[birdlocation]);
                randBird.setImageResource(R.drawable.bird_resize);
                randBird.setClickable(true);
                moleHandler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        randBird.setImageResource(R.drawable.hole);
                    }
                    }, 500);
            }
            public void onFinish()
            {
            }
        }.start();
    }

    private void setClickListener()
    //click listeners for mole buttons
    {
        for (int i = 0; i < holes.length; i++)
        {
            final int index = i;
            ImageButton dogButton = findViewById(holes[index]);
            dogButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (index == doglocation)
                    {
                        onDogClicked(index); //pop sound for dog
                    }
                    if (index == birdlocation)
                    {
                        onBirdClicked(index); //buzz sound for bird
                    }
                }
            });
        }
    }

    private void onDogClicked(int index)
    {
        //increases score by 1 when dog is clicked
        pop.start();
        ImageButton clickedMole = findViewById(holes[index]);
        clickedMole.setImageResource(R.drawable.hole);
        clickedMole.setClickable(false);
        score++;
        textView_score.setText("Score: " + score); //displays score
    }

    private void onBirdClicked(int index)
    {
        //decreases score by 1 when bird is clicked
        buzz.start();
        ImageButton clickedBird = findViewById(holes[index]);
        clickedBird.setImageResource(R.drawable.hole);
        clickedBird.setClickable(false);

        //keeps score at 0 if technically negative
        if (score == 0)
            score = 0;
        else
            score = score - 1;

        textView_score.setText("Score: " + score); //displays score
    }

    public int randNum()
    //generates new random number for dog location
    {
        Random random = new Random();
        return random.nextInt(9);
    }
}
