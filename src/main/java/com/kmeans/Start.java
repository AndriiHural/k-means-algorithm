package com.kmeans;

import java.util.*;

import static java.lang.Math.*;

public class Start {
    private static List<Cluster> clusters = new ArrayList<>();
    private static List<String> clustersOld = new ArrayList<>();
    private static double quality;
    private static double qualityOld;
    private static int count=0;
    private static double[] coordinates;
    private static final String INPUT_FILE = "Test-case-3.txt";
    private static int numberOfCluster;

    public static void main(String[] args) {
        new Start();
    }

    private Start() {
        dataPreparation();

        while (count<5){
            startAlgorithm();
            qualityOfClustering();
            System.out.println("Step");
        }
        //IOWithFile.writeUsingBufferedWriter(clustersOld);
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
                List<Integer> randomCenter=randomPoint();
                clusters.add(new Cluster(coordinates[randomCenter.get(i)], coordinates[randomCenter.get(i)+1]));
                System.out.println("x=  "+clusters.get(i).getxCoordinateOfCenter()+" y= "+clusters.get(i).getyCoordinateOfCenter());

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

    static List<Integer> randomPoint(){
        Random r = new Random();
        List<Integer> list=new ArrayList<>();
        list.add(0);
        while (list.size()<numberOfCluster){
            int rundNumber=r.nextInt(coordinates.length/2)*2;
            for (int i :
                    list) {
                if((i!=rundNumber)&&(rundNumber<=coordinates.length/2)){
                    list.add(rundNumber);
                    break;
                }
            }
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
    static double distanceCenterToAllPoint(double x1, double y1,
                                           double x2, double y2) {

        return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
    }

    static double inClusterAverageDistance(Cluster cluster) {

        double sumDistance = 0;
        for (double[] point :
                cluster.getPoints()) {
            sumDistance += Start.distanceCenterToAllPoint(cluster.getxCoordinateOfCenter(), cluster.getyCoordinateOfCenter(),
                    point[0], point[1]);
        }
        cluster.setInClusterAvrgDistance(sumDistance / cluster.getAmount());
        return cluster.getInClusterAvrgDistance();
    }

    static double averageDistance() {

        double sum = 0;
        for (Cluster cluster :
                clusters) {
            sum += cluster.getInClusterAvrgDistance();
        }
        return sum / numberOfCluster;
    }

    static double outClusterAverageDistance() {

        List<double[]> pointsCenterCluster = new ArrayList<>();
        Cluster pseudoCluster = new Cluster(0, 0);
        for (Cluster cluster :
                clusters) {
            pointsCenterCluster.add(new double[]{cluster.getxCoordinateOfCenter(),cluster.getyCoordinateOfCenter()});
        }
        pseudoCluster.setPoints(pointsCenterCluster);
        pseudoCluster.newCenter();
        pseudoCluster.setAmount();
        return inClusterAverageDistance(pseudoCluster);
    }

    static double clusterizationEvaluation(double inClusterAverageDistance, double OutClusterAverageDistance) {

        return numberOfCluster * (inClusterAverageDistance / OutClusterAverageDistance);
    }

    static void qualityOfClustering(){

        quality = clusterizationEvaluation(averageDistance(), outClusterAverageDistance());
        if (qualityOld>0){
            System.out.println("Yes");
            if (quality<qualityOld){
               // clustersToString();
                count=0;
            }else count++;
        }else {
            System.out.println("No");
            count++;
            qualityOld = quality;
            //clustersToString();
        }
        System.out.println("qualityOld "+qualityOld);
        clusters.clear();
    }
    static void clustersToString(){
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
