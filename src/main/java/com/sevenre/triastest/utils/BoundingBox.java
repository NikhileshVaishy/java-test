package com.sevenre.triastest.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhilesh on 12/07/17.
 */
public class BoundingBox {
    private static int GRID_WIDTH = 5;
    private static int GRID_HEIGHT = 5;

    private double lat1;
    private double lon1;
    private double lat2;
    private double lon2;

    public List<Coordinates> calculatePoints() {
        double x_cord_distance = this.getLat2() - this.getLat1();
        double y_cord_distance = this.getLon2() - this.getLon1();
        double x_unit = x_cord_distance / GRID_WIDTH;
        double y_unit = y_cord_distance / GRID_HEIGHT;

        ArrayList<Coordinates> grid = new ArrayList<>(GRID_WIDTH*GRID_HEIGHT);
        for (double i = this.getLat1(); i <= this.getLat2(); i += x_cord_distance / (double)(GRID_WIDTH-1)) {
            for (double j = this.getLon1(); j <= this.getLon2(); j += y_cord_distance / (double) (GRID_HEIGHT - 1)) {
                Coordinates coordinates = new Coordinates();
                coordinates.setLat(i);
                coordinates.setLon(j);
                grid.add(coordinates);
            }
        }
        return grid;
    }

    public double getLat1() {
        return lat1;
    }

    public void setLat1(double lat1) {
        this.lat1 = lat1;
    }

    public double getLon1() {
        return lon1;
    }

    public void setLon1(double lon1) {
        this.lon1 = lon1;
    }

    public double getLat2() {
        return lat2;
    }

    public void setLat2(double lat2) {
        this.lat2 = lat2;
    }

    public double getLon2() {
        return lon2;
    }

    public void setLon2(double lon2) {
        this.lon2 = lon2;
    }
}
