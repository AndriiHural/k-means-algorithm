package com.kmeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contain center-points and other points this cluster.
 * <p>
 * xCoordinateOfCenter, yCoordinateOfCenter are coordinates center of the cluster.
 * points are a set of points belonging to the cluster .
 * isOptimal cluster is optimal if during two steps, center of cluster doesn't change
 * amount is number of point in cluster
 */
public class Cluster {
    private double xCoordinateOfCenter;
    private double yCoordinateOfCenter;
    private List<double[]> points = new ArrayList<>();
    private boolean isOptimal = false;
    private int amount;
    private double inClusterAvrgDistance;

    public double getInClusterAvrgDistance() {
        return inClusterAvrgDistance;
    }

    public void setInClusterAvrgDistance(double inClusterAvrgDistance) {
        this.inClusterAvrgDistance = inClusterAvrgDistance;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount() {
        this.amount = points.size();
    }

    public Cluster(double xCoordinateOfCenter, double yCoordinateOfCenter) {
        this.xCoordinateOfCenter = xCoordinateOfCenter;
        this.yCoordinateOfCenter = yCoordinateOfCenter;
    }

    public double getxCoordinateOfCenter() {
        return xCoordinateOfCenter;
    }

    public void setxCoordinateOfCenter(double xCoordinateOfCenter) {
        this.xCoordinateOfCenter = xCoordinateOfCenter;
    }

    public double getyCoordinateOfCenter() {
        return yCoordinateOfCenter;
    }

    public void setyCoordinateOfCenter(double yCoordinateOfCenter) {
        this.yCoordinateOfCenter = yCoordinateOfCenter;
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
                "xCoordinateOfCenter=" + xCoordinateOfCenter +
                ", yCoordinateOfCenter=" + yCoordinateOfCenter +
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

        double sumX = 0, sumY = 0;
        for (double[] point :
                points) {
            sumX += point[0];
            sumY += point[1];
        }
        double newX = sumX / points.size();
        double newY = sumY / points.size();
        if (xCoordinateOfCenter == newX && yCoordinateOfCenter == newY) {
            isOptimal = true;
        } else {
            isOptimal = false;
            xCoordinateOfCenter = newX;
            yCoordinateOfCenter = newY;
        }
    }

    public void removeAllPoints() {
        points.clear();
    }

    public void setPoints(List<double[]> points) {
        this.points = points;
    }
}
