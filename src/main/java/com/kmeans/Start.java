package com.kmeans;

import java.util.*;

import static java.lang.Math.*;

public class Start {
    private static List<Cluster> clusters = new ArrayList<>();
    private static List<String> clustersOld = new ArrayList<>();
    private static List<Integer> randomCenter;
    private static double qualityOld;
    private static int count = 0;
    private static double[] coordinates;
    private static final String INPUT_FILE = "Test-case-2.txt";
    private static int numberOfCluster;

    public static void main(String[] args) {
        new Start();
    }

    private Start() {
        dataPreparation();

        while (count < 5) {
            randomCenter = randomPoint();
            startAlgorithm();
            qualityOfClustering();
        }
        IOWithFile.writeUsingBufferedWriter(clustersOld);
    }

    private static void startAlgorithm() {

        choiceCenter(numberOfCluster);//choice start coordinates
        while (isChangeCenter()) {
            //step 2: calculation distance
            //step 3: clustering
            clustering();
            //step 4: choice new Center
            choiceCenter(numberOfCluster);
            //step 5: have the coordinates changed?
        }
    }

    /**
     * read file and divide to parameter, coordinates
     */
    private static void dataPreparation() {

        String[] contentInputFile = IOWithFile.readUsingBufferedReader(INPUT_FILE)
                .split("\t");
        double[] arrContent = Arrays.asList(contentInputFile)
                .stream()
                .mapToDouble(Double::parseDouble)
                .toArray();
        coordinates = Arrays.copyOfRange(arrContent, 2, arrContent.length);
        numberOfCluster = (int) arrContent[0];
    }

    /**
     * Calculate coordinates of center or choice coordinate of start
     *
     * @param numberOfCluster The number of cluster need to choice, how many start coordinates we need.
     */
    private static void choiceCenter(int numberOfCluster) {

        if (clusters.isEmpty()) {
            for (int i = 0; i < numberOfCluster; i++) {
                clusters.add(new Cluster(coordinates[randomCenter.get(i)], coordinates[randomCenter.get(i) + 1]));
            }
        } else {
            for (Cluster cluster :
                    clusters) {
                cluster.newCenter();
                cluster.setInClusterAvrgDistance(inClusterAverageDistance(cluster));
                cluster.setAmount();
                cluster.removeAllPoints();//clean cluster
            }
        }
    }

    /**
     * Random start center points
     */
    private static List<Integer> randomPoint() {
        Random r = new Random();
        List<Integer> list = new ArrayList<>();
        list.add(0);
        while (list.size() < numberOfCluster) {
            int rundNumber = 0;
            for (int i = 0; i < list.size(); i++) {
                rundNumber = r.nextInt(coordinates.length / 2) * 2;
                for (int j = 0; j < list.size(); j++) {
                    if ((list.get(j) == rundNumber) || rundNumber >= (coordinates.length / 2)) {
                        i--;
                        break;
                    }
                }
            }
            list.add(rundNumber);
            Collections.shuffle(list);
        }
        return list;
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
    private static double distanceCenterToAllPoint(double x1, double y1,
                                                   double x2, double y2) {

        return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
    }

    /**
     * calculate the mean intra-cluster distance in one cluster
     *
     * @param cluster cluster in which to calculate
     */
    private static double inClusterAverageDistance(Cluster cluster) {

        double sumDistance = 0;
        for (double[] point :
                cluster.getPoints()) {
            sumDistance += Start.distanceCenterToAllPoint(cluster.getxCoordinateOfCenter(), cluster.getyCoordinateOfCenter(),
                    point[0], point[1]);
        }
        cluster.setInClusterAvrgDistance(sumDistance / cluster.getAmount());
        return cluster.getInClusterAvrgDistance();
    }

    /**
     * calculate the mean intra-cluster distance
     */
    private static double averageDistance() {

        double sum = 0;
        for (Cluster cluster :
                clusters) {
            sum += cluster.getInClusterAvrgDistance();
        }
        return sum / numberOfCluster;
    }

    /**
     * calculate the average intercluster distance
     */
    private static double outClusterAverageDistance() {

        List<double[]> pointsCenterCluster = new ArrayList<>();
        Cluster pseudoCluster = new Cluster(0, 0);
        for (Cluster cluster :
                clusters) {
            pointsCenterCluster.add(new double[]{cluster.getxCoordinateOfCenter(), cluster.getyCoordinateOfCenter()});
        }
        pseudoCluster.setPoints(pointsCenterCluster);
        pseudoCluster.newCenter();
        pseudoCluster.setAmount();
        return inClusterAverageDistance(pseudoCluster);
    }

    /**
     * function to assess the quality of the clusterer.
     * To get the best result, you can run the clustering algorithm several times, then select the result with the smallest value.
     */
    private static double clusterizationEvaluation(double inClusterAverageDistance, double OutClusterAverageDistance) {

        return numberOfCluster * (inClusterAverageDistance / OutClusterAverageDistance);
    }

    /**
     * To get the best result, you can run the clustering algorithm several times, then select the result with the smallest value.
     */
    private static void qualityOfClustering() {

        double quality = clusterizationEvaluation(averageDistance(), outClusterAverageDistance());
        if (qualityOld > 0) {
            if (quality < qualityOld) {
                clustersToString();
                count = 0;
            } else count++;
        } else {
            count++;
            qualityOld = quality;
            clustersToString();
        }
        clusters.clear();
    }

    private static void clustersToString() {
        clustersOld.clear();
        for (Cluster cluster :
                clusters) {
            String xCoordinateOfCenter = String.format("%.2f", cluster.getxCoordinateOfCenter());
            String yCoordinateOfCenter = String.format("%.2f", cluster.getyCoordinateOfCenter());
            String amountPointsInCluster = Integer.toString(cluster.getAmount());
            clustersOld.add(xCoordinateOfCenter + "\t" + yCoordinateOfCenter + "\t" + amountPointsInCluster.trim());
        }
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
