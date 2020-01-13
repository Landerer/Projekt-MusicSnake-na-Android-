package com.example.musicsnake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private Button rozpocznijGre;
    private Button wyswietlZasady;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rozpocznijGre = findViewById(R.id.rozpocznijGre);
        rozpocznijGre.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SnakeActivity.class);
                startActivity(intent);
            }
        });

        wyswietlZasady = findViewById(R.id.wyswietlZasady);
        wyswietlZasady.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, RulesActivity.class);
                startActivity(intent);
            }
        });



    }
}
