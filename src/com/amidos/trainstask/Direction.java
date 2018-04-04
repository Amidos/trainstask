package com.amidos.trainstask;

public class Direction {

    private Town from;

    private Town to;

    private Direction canGoTo;

    private int distance;

    public Town getFrom() {
        return from;
    }

    public void setFrom(Town from) {
        this.from = from;
    }

    public Town getTo() {
        return to;
    }

    public void setTo(Town to) {
        this.to = to;
    }

    public Direction getCanGoTo() {
        return canGoTo;
    }

    public Direction setCanGoTo(Direction canGoTo) {
        this.canGoTo = canGoTo;
        return this;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Direction(Town from, Town to, int distance)
    {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }
}
