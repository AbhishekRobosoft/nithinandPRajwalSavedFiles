package com.robosoft.lorem.routeResponse;

import java.util.ArrayList;

public class Properties {

    private ArrayList<Segment> segments;
    private Summary summary;
    private ArrayList<Integer> way_points;


    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public void setSegments(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public ArrayList<Integer> getWay_points() {
        return way_points;
    }

    public void setWay_points(ArrayList<Integer> way_points) {
        this.way_points = way_points;
    }
}
