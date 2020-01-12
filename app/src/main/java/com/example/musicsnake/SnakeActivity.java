package com.example.musicsnake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SnakeActivity extends AppCompatActivity {

    private SnakeMove snakeMove;
    private SnakeView snakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        snakeMove = new SnakeMove();
        snakeMove.initGame();

        snakeView = (SnakeView)findViewById(R.id.snakeView);
        snakeView.setSnakeViewMap(snakeMove.getMap());
        snakeView.invalidate();
    }
}
