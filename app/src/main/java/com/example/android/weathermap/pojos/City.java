package com.example.android.weathermap.pojos;

import java.util.ArrayList;

/**
 * Created by HP on 02-Jul-17.
 */

public class City {
    Coord coord;
    ArrayList<Weather> weather;
    Main main;
    int visibility;
    String name;

    public City(Coord coord, ArrayList<Weather> weather, Main main, int visibility, String name) {
        this.coord = coord;
        this.weather = weather;
        this.main = main;
        this.visibility = visibility;
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
