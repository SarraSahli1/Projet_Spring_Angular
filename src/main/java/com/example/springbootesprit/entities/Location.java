package com.example.springbootesprit.entities;

import java.awt.*;

public class Location {

    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean contains(Point point) {
        // TODO: Implement this method
        return false;
    }
}

