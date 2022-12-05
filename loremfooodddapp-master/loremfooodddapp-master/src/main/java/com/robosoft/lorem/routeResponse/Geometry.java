package com.robosoft.lorem.routeResponse;

import java.util.ArrayList;

public class Geometry {

    private ArrayList<ArrayList<Double>> coordinates;
    private String type;


    public ArrayList<ArrayList<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
