package com.example.musicsnake;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class SnakeActivity extends AppCompatActivity implements View.OnTouchListener{

    private SnakeMove snakeMove;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay = 240;
    private float prevX, prevY;
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        snakeMove = new SnakeMove();
        snakeMove.initGame();

        snakeView = (SnakeView)findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);
        snakeView.setIntervals(snakeMove.getIntervals());
        playInterval(snakeMove.getIntervals().get(0));
        startUpdateHandler();
    }

    private void startUpdateHandler()
    {
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run ()
            {
                snakeMove.update();

                if (snakeMove.getCurrentGameState() == EnumGameState.Running) {
                    handler.postDelayed(this, updateDelay);
                }

                if( snakeMove.getCurrentGameState() == EnumGameState.Lost)
                {
                    onGameLost();
                }

                if(snakeMove.playInterval())
                {
                    playInterval(snakeMove.getIntervals().get(0));
                }
                snakeView.setSnakeViewMap(snakeMove.getMap());
                snakeView.invalidate();
            }
        }, updateDelay);
    }

    private void onGameLost()
    {
        Toast.makeText(this, "You lost.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float newX = event.getX();
                float newY = event.getY();

                if(Math.abs(newX - prevX) > Math.abs(newY - prevY))//przesunięcie poziome
                {
                    if(newX > prevX)//przesunięcie w prawo
                    {
                        snakeMove.updateDirection(EnumDirection.East);
                    }
                    else//przesuniecie w lewo
                    {
                        snakeMove.updateDirection(EnumDirection.West);
                    }
                }
                else//przesunięcie pionowe
                {
                    if(newY > prevY)//przesuniecie w dół
                    {
                        snakeMove.updateDirection(EnumDirection.South);
                    }
                    else//przesunięcie w góre
                    {
                        snakeMove.updateDirection(EnumDirection.North);
                    }
                }

                break;
        }
        return true;
    }

    public void playInterval(Interval interval)
    {
        int[] sounds = {
                R.raw.unison_on_c,
                R.raw.minor_second_on_c,
                R.raw.major_second_on_c,
                R.raw.minor_third_on_c,
                R.raw.major_third_on_c,
                R.raw.perfect_fourth_on_c,
                R.raw.tritone_on_c,
                R.raw.perfect_fifth_on_c,
                R.raw.minor_sixth_on_c,
                R.raw.major_sixth_on_c,
                R.raw.minor_seventh_on_c,
                R.raw.major_seventh_on_c,
                R.raw.perfect_octave_on_c
                };

        mp = MediaPlayer.create(this, sounds[interval.getSemitones()]);
        mp.start();
    }
}
