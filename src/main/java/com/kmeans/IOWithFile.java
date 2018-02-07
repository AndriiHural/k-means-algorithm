package com.kmeans;

import java.io.*;
import java.util.List;

public class IOWithFile {

    public static void writeUsingBufferredWriter(List<Cluster> clusters) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Output.txt"))) {
            for (Cluster cluster :
                    clusters) {
                //cluster.newCenter();
                System.out.println(cluster.toString());
                String x = String.format("%.2f", cluster.getX());
                String y = String.format("%.2f", cluster.getY());
                bw.write(x + "\t" + y + "\t" + cluster.getAmount() + "\n");
            }

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readUsingBufferredReader(String file) {
        String content = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line.trim();
                content += line + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
