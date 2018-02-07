package com.kmeans;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;

public class Start {
    public static List<double[]> centerCoord = new ArrayList<>();
    public static List<Cluster> clusters = new ArrayList<>();
    public static double[] coordinates;

    public static void main(String[] args) {

        String[] content = readUsingBufferredReader().split(" ");//дані з файлу//TODO static?
        double[] arrContent = Arrays.asList(content).stream().mapToDouble(Double::parseDouble).toArray();
        coordinates = Arrays.copyOfRange(arrContent, 2, arrContent.length);
        int k = (int) arrContent[0];
        choiceCenter(k);//choice start coordinates

        while (isChangeCenter()) {
            //step 2: calculation distance
            //step 3: clustering
            clustering();
            //step 4: choice new Center
            choiceCenter(k);
            //step 5: have the coordinates changed?
        }
        writeUsingBufferredWriter();
    }

    public static void writeUsingBufferredWriter() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Output.txt"))) {
            for (Cluster cluster :
                    clusters) {
                //cluster.newCenter();
                System.out.println(cluster.toString());
                bw.write(cluster.getX() + "\t" + cluster.getY() + "\t" + cluster.getAmount() + "\n");

            }

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readUsingBufferredReader() {
        String content = "";
        try (BufferedReader br = new BufferedReader(new FileReader("Test-case-2.txt"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                line.trim();
                content += line + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * Сhoice start coordinates
     * k-number of cluster
     */
    public static void choiceCenter(int k) {//TODO Переробити, бо тут можна зразу створювати об Кластера
        if (clusters.isEmpty()) {
            System.out.println("Yes List Center is Empty");
            int x = 0;//start coordinates x
            int y = 1;//start coordinates y
            for (int i = 0; i < k; i++) {
                clusters.add(new Cluster(coordinates[x], coordinates[y]));
                System.out.println("Choice center [" + x + " , " + y + "]");
                x += 2;
                y += 2;
            }
        } else {
            System.out.println("NO");
            for (Cluster cluster :
                    clusters) {
                cluster.newCenter();
                System.out.println("\n" + cluster.toString());
                cluster.removeAllPoins();//clean cluster
            }
        }
    }

    /**
     * which point to which cluster is closer
     */
    public static void clustering() {
        for (int i = 0; i < coordinates.length; i += 2) {
            //minDistance
            int numOfMin = 0;//number cluster with min distance
            double min = 9999;  //min distance
            for (int j = 0; j < clusters.size(); j++) {
                Cluster cluster = clusters.get(j);
                double distance = distanceCenterToAllPoint(cluster.getX(), cluster.getY(), coordinates[i], coordinates[i + 1]);
                if (distance < min) {
                    min = distance;
                    numOfMin = j;
                } else if (distance == min) System.out.println("===");
            }

            System.out.println("X= " + coordinates[i] + " Y= " + coordinates[i + 1] + " Center = " + numOfMin);
            double[] point = {coordinates[i], coordinates[i + 1]};
            clusters.get(numOfMin).addPoint(point);
        }
    }

    /**
     * Відстань від центра до точоки
     */
    public static double distanceCenterToAllPoint(double x1, double y1, double x2, double y2) {
        List<Double> distances = new ArrayList<>();
        Double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        distances.add(distance);
        System.out.println("Distance between X1= " + x1 + " Y1 " + y1 + " X2= " + x2 + " Y2 " + y2 + " ->" + distance);

        return distance;
    }

    /**
     * Check the optimal choice the points
     */
    public static boolean isChangeCenter() {//TODo наркутив з змінними, тяжко для розуміння
        for (Cluster cluster :
                clusters) {
            if (!cluster.isOptimal()) return true;
        }
        return false;
    }
}
