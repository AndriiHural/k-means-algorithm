package com.kmeans;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

public class IOWithFileTest {

    @Test
    public void writeUsingBufferedWriterFileExist() throws Exception {
        assertTrue((new File("Output.txt")).exists());
    }

    @Test
    public void writeUsingBuferredWriterNotEmptyFile() throws Exception {
        String contentOutputFile = IOWithFile.readUsingBufferedReader("Output.txt");
        assertNotEquals(contentOutputFile, "");
    }

    @Test
    public void writeUsingBufferedWriterNumberOfRowEqualsNumberCluster() throws Exception {
        String[] contentInputFile = IOWithFile.readUsingBufferedReader("Test-case-1.txt")
                .split(" ");
        String[] contentOutputFile = IOWithFile.readUsingBufferedReader("Output.txt")
                .replace(",", ".")
                .split(" ");
        double[] arrContent = Arrays.asList(contentOutputFile)
                .stream()
                .mapToDouble(Double::parseDouble)
                .toArray();
        int numberOfPoints = arrContent.length / 3;
        assertEquals(Integer.parseInt(contentInputFile[0]), numberOfPoints);
    }

    @Test
    public void readUsingBufferedReaderReturnNotEmptyString() throws Exception {
        String contentInputFile = IOWithFile.readUsingBufferedReader("Test-case-1.txt");
        assertNotEquals(contentInputFile, "");
    }

    @Test
    public void readUsingBufferedReaderCheckNumberOfPoint() throws Exception {
        String[] contentInputFile = IOWithFile.readUsingBufferedReader("Test-case-1.txt")
                .split(" ");
        double[] arrContent = Arrays.asList(contentInputFile)
                .stream()
                .mapToDouble(Double::parseDouble)
                .toArray();
        int numberOfPoints = (arrContent.length - 2) / 2;
        assertEquals(4, numberOfPoints);
    }
}