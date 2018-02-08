package com.kmeans;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

public class IOWithFileTest {

    @Test
    public void writeUsingBufferredWriterFileExist() throws Exception {
        assertTrue((new File("Output.txt")).exists());
    }

    @Test
    public void writeUsingBufferredWriterNotEmptyFile() throws Exception {
        String content = IOWithFile.readUsingBufferredReader("Output.txt");
        assertNotEquals(content,"");
    }

    @Test
    public void writeUsingBufferredWriterNumberOfRowEqualsNumberCluster() throws Exception {
        String[] contentIn = IOWithFile.readUsingBufferredReader("Test-case-1.txt").split(" ");
        String[] contentOut = IOWithFile.readUsingBufferredReader("Output.txt").replace(",",".").split(" ");
        double[] arrContent = Arrays.asList(contentOut).stream().mapToDouble(Double::parseDouble).toArray();
        int numberPoint=arrContent.length/3;
        assertEquals(Integer.parseInt(contentIn[0]),numberPoint);
    }

    @Test
    public void readUsingBufferredReaderReturnNotEmptyString() throws Exception {
        String content =IOWithFile.readUsingBufferredReader("Test-case-1.txt");
        assertNotEquals(content,"");
    }
    @Test
    public void readUsingBufferredReaderCheckNumberOfPoint() throws Exception {
        String[] content = IOWithFile.readUsingBufferredReader("Test-case-1.txt").split(" ");
        double[] arrContent = Arrays.asList(content).stream().mapToDouble(Double::parseDouble).toArray();
        int numberPoint=(arrContent.length-2)/2;
        assertEquals(4,numberPoint);
    }
}