package com.kmeans;

import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class ClusterTest {
    private Cluster cluster;
    @Before
    public void initTest(){
        cluster=new Cluster(0,0);
        double[] point1={1,0};
        double[] point2={2,0};
        cluster.addPoint(point1);
        cluster.addPoint(point2);
    }
    @After
    public void afterTest(){
        cluster=null;
    }

    @Test
    public void getX() throws Exception {
        assertEquals(0,cluster.getxCoordinateOfCenter(),0);
    }

    @Test
    public void getY() throws Exception {
        assertEquals(0,cluster.getyCoordinateOfCenter(),0);
    }

    @Test
    public void newCenterX() throws Exception {
        cluster.newCenter();
        assertEquals(1.5,cluster.getxCoordinateOfCenter(),0);
    }
    @Test
    public void newCenterY() throws Exception {
        cluster.newCenter();
        assertEquals(0.0,cluster.getyCoordinateOfCenter(),0);
    }
    @Test
    public void removeAllPoints() throws Exception {
        cluster.removeAllPoints();
        List<double[]> points =cluster.getPoints();
        assertEquals(0,points.size());
    }

}