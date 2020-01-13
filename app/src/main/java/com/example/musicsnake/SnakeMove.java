package com.example.musicsnake;

import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeMove
{
    public static final int GameWidth = 28;
    public static final int GameHeight = 42;

    private EnumDirection currentDirection = EnumDirection.East;

    private EnumGameState currentGameState = EnumGameState.Running;

    private List<Coordinates> walls = new ArrayList<>();
    private List<Coordinates> snake = new ArrayList<>();
    private List<Interval> intervals = new ArrayList<>();

    private Random random = new Random();
    private boolean increaseTail = false;

    private boolean newInterval = true;

    private Coordinates getSnakeHead()
    {
        return snake.get(0);
    }

    public boolean playInterval()
    {
        return newInterval;
    }

    public SnakeMove()
    {

    }

    public void initGame()
    {
        addSnake();
        addWAlls();
        addIntervals();
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

        checkCollisions();
        checkIntervals();
    }

    private void checkCollisions()
    {
        //sprawdzanie kolizji ze ścianą
        for (Coordinates w: walls)
        {
            if( snake.get(0).equals((w)))
            {
                currentGameState = EnumGameState.Lost;
            }
        }

        //sprawdzanei kolizji ze sobą
        for (int i = 1 ; i < snake.size(); i++)
        {
            if (getSnakeHead().equals(snake.get(i)))
            {
                currentGameState = EnumGameState.Lost;
            }
        }
    }

    private void checkIntervals()
    {
        //usuwanie zjedzonych interwalow
        Interval intervalToRemove = null;
        for (Interval i: intervals)
        {
            if( getSnakeHead().equals(i.getCoordinates()))
            {
                if (i == intervals.get(0))
                {
                    intervalToRemove = i;
                    increaseTail = true;
                }
                else
                {
                    currentGameState = EnumGameState.Lost;
                }
            }
        }
        if (intervalToRemove != null)
        {
            intervals.remove(intervalToRemove);
            addIntervals();
        }
        else
        {
            newInterval = false;
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

        for (Coordinates w: walls)
        {
            map[w.getX()][w.getY()] = EnumTileType.Wall;
        }

        for (Coordinates s: snake)
        {
            map[s.getX()][s.getY()] = EnumTileType.SnakeTail;
        }
        map[snake.get(0).getX()][snake.get(0).getY()] = EnumTileType.SnakeHead;

        for (Interval i: intervals)
        {
            Coordinates c = i.getCoordinates();
            map[c.getX()][c.getY()] = EnumTileType.IntervalShort;
        }

        return map;
    }

    public List<Interval> getIntervals()
    {
        return intervals;
    }

    public EnumGameState getCurrentGameState()
    {
        return currentGameState;
    }

    private void updateSnake(int x, int y)
    {
        int newX = snake.get(snake.size() -1).getX();
        int newY = snake.get(snake.size() -1).getY();

        for (int i = snake.size()-1; i > 0 ; i--)
        {
            snake.get(i).setX(snake.get(i-1).getX());
            snake.get(i).setY(snake.get(i-1).getY());
        }

        if (increaseTail)
        {
            snake.add(new Coordinates(newX,newY));
            increaseTail = false;
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

    //dodawanie interwału i sprawdzanie kolizji
    private void addIntervals() {
        Coordinates coordinates = null;
        int addedCount = 0;

        intervals.clear();
        while (addedCount < 3)
        {
            coordinates = findFreeCoordinates();
            Interval interval = new Interval(coordinates);
            interval.setSemitones(findFreeInterval());
            intervals.add(interval);
            addedCount++;
        }
        newInterval = true;
    }

    private int findFreeInterval()
    {
        boolean found = false;
        int semitones = 0;
        while(!found)
        {
            boolean collision = false;
            semitones = random.nextInt(13);
            for (Interval i: intervals)
            {
                if (i.getSemitones() == semitones)
                {
                    collision = true;
                    break;
                }
            }
            if (!collision)
            {
                found = true;
            }
        }
        return semitones;
    }

    private Coordinates findFreeCoordinates()
    {
        Coordinates coordinates = null;
        boolean found = false;
        while (!found) {
            int x = 1 + random.nextInt(GameWidth - 2);
            int y = 1 + random.nextInt(GameHeight - 2);

            coordinates = new Coordinates(x, y);
            boolean collision = false;
            for (Coordinates s : snake) {
                if (s.equals(coordinates)) {
                    collision = true;
                    break;
                }
            }
            for (Interval i : intervals) {
                if (i.getCoordinates().equals(coordinates)) {
                    collision = true;
                    break;
                }
            }
            if (collision == true) {
                continue;
            }
            for (Interval i : intervals) {
                if (i.getCoordinates().equals(coordinates)) {
                    collision = true;
                    break;
                }
            }
            found = !collision;
        }
        return coordinates;
    }



}
