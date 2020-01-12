package com.example.musicsnake;

import com.example.musicsnake.Coordinates;
import com.example.musicsnake.EnumTileType;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class SnakeMove
{
    public static final int GameWidth = 28;
    public static final int GameHeight = 42;

    private EnumDirection currentDirection = EnumDirection.East;

    private EnumGameState currentGameState = EnumGameState.Running;

    private List<Coordinates> walls = new ArrayList<>();
    private List<Coordinates> snake = new ArrayList<>();

    public SnakeMove()
    {

    }

    public void initGame()
    {
        addSnake();
        addWAlls();
    }

    public void updateDirection(EnumDirection  newDirection) {
        if (Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1)
        {
            currentDirection = newDirection;
        }
    }

    public void update()
    {
        switch (currentDirection)
        {
            case North:
                updateSnake(0,-1);
                break;
            case East:
                updateSnake(1, 0);
                break;
            case South:
                updateSnake(0,1);
                break;
            case West:
                updateSnake(-1,0);
                break;
        }

        for (Coordinates w: walls)
        {
            if( snake.get(0).equals((w)))
            {
                currentGameState = EnumGameState.Lost;
            }
        }
    }

    public EnumTileType[][] getMap()
    {
        EnumTileType[][] map = new EnumTileType[GameWidth][GameHeight];

        for (int x = 0; x < GameWidth; x++)
        {
            for (int y = 0; y < GameHeight; y++)
            {
                map[x][y] = EnumTileType.Nothing;
            }
        }

        for (Coordinates s: snake)
        {
            map[s.getX()][s.getY()] = EnumTileType.SnakeHead;
        }
        map[snake.get(0).getX()][snake.get(0).getY()] = EnumTileType.SnakeHead;

        for (Coordinates wall: walls)
        {
            map[wall.getX()][wall.getY()] = EnumTileType.Wall;
        }

        return map;
    }

    public EnumGameState getCurrentGameState()
    {
        return currentGameState;
    }

    private void updateSnake(int x, int y)
    {
        for (int i = snake.size()-1; i > 0 ; i--)
        {
            snake.get(i).setX(snake.get(i-1).getX());
            snake.get(i).setY(snake.get(i-1).getY());
        }

        snake.get(0).setX(snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);
    }

    private void addSnake()
    {
        snake.clear();

        snake.add(new Coordinates(7,7));
        snake.add(new Coordinates(6,7));
        snake.add(new Coordinates(5,7));
        snake.add(new Coordinates(4,7));
        snake.add(new Coordinates(3,7));
        snake.add(new Coordinates(2,7));

    }

    private void addWAlls()
    {

        //górna i dolna ściana
        for (int x = 0; x < GameWidth; x++)
        {
            walls.add(new Coordinates(x, 0));
            walls.add(new Coordinates(x,GameHeight-1));
        }

        //prawa i lewa ściana
        for (int y = 0; y < GameHeight; y++)
        {
            walls.add(new Coordinates(0, y));
            walls.add(new Coordinates(GameWidth-1, y));
        }
    }


}
