package com.example.dogpound;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity implements OnClickListener
{   //declare start button variable
    private Button button_start;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    //launches start page with components
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start); //sets start page layout

        //function for start button
        button_start = findViewById(R.id.start); //finds start button by ID
        button_start.setOnClickListener(this); //click listener for start button
    }

    @Override
    public void onClick(View start)
    //goes to MainActivity page when start button is clicked
    {
        //declare intent to switch to MainActivity
        Intent MainActivity = new Intent(StartActivity.this, MainActivity.class);

        startActivity(MainActivity); //start MainActivity
    }
}
