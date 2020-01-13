package com.example.musicsnake;

import com.example.musicsnake.EnumTileType;
import com.example.musicsnake.SnakeMove;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

public class SnakeView extends View
{
    private Paint mapPaint = new Paint();
    private EnumTileType snakeViewMap[][];
    private List<Interval> intervals;

    public void setSnakeViewMap(EnumTileType[][] map)
    {
        this.snakeViewMap = map;
    }
    public void setIntervals(List<Interval> intervals)
    {
        this.intervals = intervals;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if(snakeViewMap != null)
        {
            float tileSizeX = canvas.getWidth() / snakeViewMap.length;
            float tileSizeY =  canvas.getHeight() / snakeViewMap[0].length;
            float circleSize = Math.min(tileSizeX, tileSizeY) / 2;
            mapPaint.setTextSize(40);

            for (int x = 0; x < snakeViewMap.length; x++)
            {
                for (int y = 0; y < snakeViewMap[x].length; y++)
                {
                    switch (snakeViewMap[x][y])
                    {
                        case Nothing:
                            mapPaint.setColor(Color.WHITE);
                            break;
                        case Wall:
                            mapPaint.setColor(Color.LTGRAY);
                            break;
                        case SnakeHead:
                            mapPaint.setColor(Color.BLACK);
                            break;
                        case SnakeTail:
                            mapPaint.setColor(Color.DKGRAY);
                            break;
                        case IntervalShort:
                            mapPaint.setColor(Color.RED);
                            break;
                    }

                    float cx = x * tileSizeX + tileSizeX/2f + circleSize/2;
                    float cy = y * tileSizeY + tileSizeY / 2f + circleSize/2;

                    canvas.drawCircle(cx, cy, circleSize, mapPaint);
                }
            }
            for (Interval i: intervals)
            {
                Coordinates c = i.getCoordinates();

                float cx = c.getX() * tileSizeX + tileSizeX/2f + circleSize/2;
                float cy = c.getY() * tileSizeY + tileSizeY / 2f + circleSize/2;

                mapPaint.setColor(Color.BLUE);
                canvas.drawText(i.toString(), cx, cy, mapPaint);

            }
        }
    }


    public SnakeView(Context context, AttributeSet attrset)
    {
        super(context, attrset);
    }
}
