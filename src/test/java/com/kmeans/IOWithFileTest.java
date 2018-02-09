package com.kmeans;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

public class IOWithFileTest {
    private final String INPUT_FIlE="Test-case-1.txt";
    private final String OUTPUT_FIlE="Output.txt";
    @Test
    public void writeUsingBufferedWriterFileExist() throws Exception {
        assertTrue((new File(OUTPUT_FIlE)).exists());
    }

    @Test
    public void writeUsingBuferredWriterNotEmptyFile() throws Exception {
        String contentOutputFile = IOWithFile.readUsingBufferedReader(OUTPUT_FIlE);
        assertNotEquals(contentOutputFile, "");
    }

    @Test
    public void writeUsingBufferedWriterNumberOfRowEqualsNumberCluster() throws Exception {
        String[] contentInputFile = IOWithFile.readUsingBufferedReader(INPUT_FIlE)
                .split("\t");
        String[] contentOutputFile = IOWithFile.readUsingBufferedReader(OUTPUT_FIlE)
                .replace(",", ".")
                .split("\t");
        double[] arrContent = Arrays.asList(contentOutputFile)
                .stream()
                .mapToDouble(Double::parseDouble)
                .toArray();
        int numberOfPoints = arrContent.length / 3;
        assertEquals(Integer.parseInt(contentInputFile[0]), numberOfPoints);
    }

    @Test
    public void readUsingBufferedReaderReturnNotEmptyString() throws Exception {
        String contentInputFile = IOWithFile.readUsingBufferedReader(INPUT_FIlE);
        assertNotEquals(contentInputFile, "");
    }

    @Test
    public void readUsingBufferedReaderCheckNumberOfPoint() throws Exception {
        String[] contentInputFile = IOWithFile.readUsingBufferedReader(INPUT_FIlE)
                //.replace("\t"," ")
                .split("\t");
        double[] arrContent = Arrays.asList(contentInputFile)
                .stream()
                .mapToDouble(Double::parseDouble)
                .toArray();
        int numberOfPoints = (arrContent.length - 2) / 2;
        assertEquals(arrContent[1], numberOfPoints,0);
    }
}