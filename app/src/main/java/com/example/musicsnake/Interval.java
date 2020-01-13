package com.example.musicsnake;

import androidx.annotation.NonNull;

public class Interval
{
    private Coordinates coordinates;
    private int semitones;

    public Interval(Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }

    public void setSemitones(int semitones)
    {
        this.semitones = semitones;
    }

    public int getSemitones()
    {
        return semitones;
    }

    @NonNull
    @Override
    public String toString()
    {
            switch (semitones)
        {
            case 0:
                return "P1";
            case 1:
                return "m2";
            case 2:
                return "M2";
            case 3:
                return "m3";
            case 4:
                return "M3";
            case 5:
                return "P4";
            case 6:
                return "TT";
            case 7:
                return "P5";
            case 8:
                return "m6";
            case 9:
                return "M6";
            case 10:
                return "m7";
            case 11:
                return "M7";
            case 12:
                return "P8";
        }
        return "??";
    }
}
