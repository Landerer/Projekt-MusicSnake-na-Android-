package com.example.musicsnake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SnakeActivity extends AppCompatActivity implements View.OnTouchListener{

    private SnakeMove snakeMove;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay = 125;
    private float prevX, prevY;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        snakeMove = new SnakeMove();
        snakeMove.initGame();

        snakeView = (SnakeView)findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);

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
}
