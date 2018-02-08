package com.kmeans;

import java.io.*;
import java.util.List;
/**
 * Write and reade file
 * */
class IOWithFile {

    static void writeUsingBufferedWriter(List<Cluster> clusters) {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("Output.txt"))) {
            for (Cluster cluster :
                    clusters) {
                //cluster.newCenter();
                String xCoordinateOfCenter = String.format("%.2f", cluster.getxCoordinateOfCenter());
                String yCoordinateOfCenter = String.format("%.2f", cluster.getyCoordinateOfCenter());
                String amountPointsInCluster = Integer.toString(cluster.getAmount());
                writer.write(xCoordinateOfCenter + " " + yCoordinateOfCenter + " " + amountPointsInCluster.trim());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readUsingBufferedReader(String nameOfInputFile) {

        String content = "";
        try (BufferedReader br = new BufferedReader(
                new FileReader(nameOfInputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line=line.trim();
                content += line + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
