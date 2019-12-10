package com.example.musicsnake;

import android.content.Context;

public class SnakeMove
{
    Context context;
    Thread thread = null;
    //Kierunek ruchu weza
    enum ruch {UP, RIGHT, DOWN, LEFT}
    //Dlugosc Weza w blokach
    int dlugoscWeza;
    //Współrzęne poprawnej odpowiedzi
    int poprX;
    int poprY;
    //Współrzęne ekranu gry
    int ekranX;
    int ekranY;
    //Liczba punktów
    int wynik;
    //Współrzędne wszystkich segmentów węża
    int[] wazX;
    int[] wazY;
    //Czy gra jest uruchomiona
    boolean czyGra;
    //Rozmiar panelu grywalnego w blokach
    final int szerokoscBloku = 40;
    int wysokoscBloku;
    //Rozmiar bloku w pikselach
    int blok;

    //Inicjalizacja poczatkowego ruchu weza w prawo
    ruch ruchWeza = ruch.RIGHT;


    void newGame()
    {
        wynik = 0;
        dlugoscWeza = 1;
        wazX[0] = szerokoscBloku / 2;
        wazY[0] = wysokoscBloku / 2;
    }

    public SnakeMove (Context context)
    {
        context = context;
    }
}
