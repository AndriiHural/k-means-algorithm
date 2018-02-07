package com.kmeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contain center-points and other points this cluster*/
public class Cluster {
    private double x;
    private double y;
    private List<double[]> points =new ArrayList<>();

    public Cluster(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double[] getPoint(int i) {
        return points.get(i);
    }

    public List<double[]> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        String contain="";
        for (double[] point:points) {
            contain+= Arrays.toString(point)+" ";
        }
        return "Cluster{" +
                "x=" + x +
                ", y=" + y +
                ", points=" + contain +
                '}';
    }

    public void addPoint(double[]point) {
        this.points.add(point);
    }

    public void newCenter(){
        double sumX=0,sumY=0;
        for (double[] point :
                points) {
            sumX += point[0];
            sumY += point[1];
        }
        x=sumX/points.size();
        y=sumY/points.size();
    }
    public void removeAll(){
        points.clear();
    }
}
