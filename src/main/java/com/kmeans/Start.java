package com.kmeans;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Start {
    private static List<Cluster> clusters = new ArrayList<>();
    private static double[] coordinates;
    private static final String INPUT_FILE = "Test-case-1.txt";

    public static void main(String[] args) {
        new Start();
    }

    private Start() {

        String[] contentInputFile = IOWithFile.readUsingBufferedReader(INPUT_FILE)
                .split(" ");
        double[] arrContent = Arrays.asList(contentInputFile)
                .stream()
                .mapToDouble(Double::parseDouble)
                .toArray();
        coordinates = Arrays.copyOfRange(arrContent, 2, arrContent.length);
        int numberOfCluster = (int) arrContent[0];
        choiceCenter(numberOfCluster);//choice start coordinates

        while (isChangeCenter()) {
            //step 2: calculation distance
            //step 3: clustering
            clustering();
            //step 4: choice new Center
            choiceCenter(numberOfCluster);
            //step 5: have the coordinates changed?
        }
        IOWithFile.writeUsingBufferedWriter(clusters);
    }

    /**
     * Calculate coordinates of center or choice coordinate of start
     *
     * @param numberOfCluster The number of cluster need to choice, how many start coordinates we need.
     */
    private static void choiceCenter(int numberOfCluster) {

        if (clusters.isEmpty()) {
            int x = 0;//start coordinates x
            int y = 1;//start coordinates y
            for (int i = 0; i < numberOfCluster; i++) {
                clusters.add(new Cluster(coordinates[x], coordinates[y]));
                x += 2;
                y += 2;
            }
        } else {
            for (Cluster cluster :
                    clusters) {
                cluster.newCenter();
                cluster.removeAllPoints();//clean cluster
            }
        }
    }

    /**
     * To join point to proper cluster we need to find the closest cluster to point and combine them into new cluster
     */
    private static void clustering() {

        for (int i = 0; i < coordinates.length; i += 2) {
            int numCloserCluster = 0;//number cluster with min distance
            double min = 9999;  //min distance
            for (int j = 0; j < clusters.size(); j++) {
                Cluster cluster = clusters.get(j);
                double distance = distanceCenterToAllPoint(cluster.getxCoordinateOfCenter(), cluster.getyCoordinateOfCenter(),
                        coordinates[i], coordinates[i + 1]);
                if (distance < min) {
                    min = distance;
                    numCloserCluster = j;
                }
            }
            double[] point = {coordinates[i], coordinates[i + 1]};
            clusters.get(numCloserCluster)
                    .addPoint(point);
        }
    }

    /**
     * Return distance from center to point
     *
     * @param x1,y2 coordinate of center
     * @param x2,x2 coordinate of other point
     */
    private static double distanceCenterToAllPoint(double x1, double y1, double x2, double y2) {

        return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
    }

    /**
     * Check the optimal choice the points. If center point doesn't change, after calculation new center, this point
     * is optimal. If center point to each cluster is optimal, we need finish algorithm.
     */
    private static boolean isChangeCenter() {

        for (Cluster cluster :
                clusters) {
            if (!cluster.isOptimal()) return true;
        }
        return false;
    }
}
