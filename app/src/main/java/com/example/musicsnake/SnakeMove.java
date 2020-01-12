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

    private List<Coordinates> walls = new ArrayList<>();

    public SnakeMove()
    {

    }

    public void initGame()
    {


        AddWAlls();
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

        for (Coordinates wall: walls)
        {
            map[wall.getX()][wall.getY()] = EnumTileType.Wall;
        }

        return map;
    }

    private void AddWAlls()
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
