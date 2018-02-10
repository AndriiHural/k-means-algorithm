package com.kmeans;

import java.io.*;
import java.util.List;
/**
 * Write and reade file
 * */
class IOWithFile {

    static void writeUsingBufferedWriter(List<String> clusters) {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("Output.txt"))) {
            for (String cluster :
                    clusters) {

                writer.write(cluster);
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
                line=line.trim().replace("   ","\t");
                content += line + "\t";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
