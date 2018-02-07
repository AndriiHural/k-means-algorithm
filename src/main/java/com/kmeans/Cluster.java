package com.kmeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contain center-points and other points this cluster
 */
public class Cluster {
    private double x;
    private double y;
    private List<double[]> points = new ArrayList<>();
    private boolean isOptimal = false;
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

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
        String contain = "";
        for (double[] point : points) {
            contain += Arrays.toString(point) + " ";
        }
        return "Cluster{" +
                "x=" + x +
                ", y=" + y +
                ", points=" + contain +
                '}';
    }

    public void addPoint(double[] point) {
        this.points.add(point);
    }

    public boolean isOptimal() {
        return isOptimal;
    }

    public void setOptimal(boolean optimal) {
        isOptimal = optimal;
    }

    public void newCenter() {
        amount = points.size();
        double sumX = 0, sumY = 0;
        for (double[] point :
                points) {
            sumX += point[0];
            sumY += point[1];
        }
        double newX = sumX / points.size();
        double newY = sumY / points.size();
        if (x == newX && y == newY) {
            isOptimal = true;
            System.out.println("\t Oll center is [" + x + " , " + y + "]");
        } else {
            isOptimal = false;
            x = newX;

            y = newY;
            System.out.println("\t New center is [" + x + " , " + y + "]");
        }
    }

    public void removeAllPoins() {
        points.clear();
    }
}
