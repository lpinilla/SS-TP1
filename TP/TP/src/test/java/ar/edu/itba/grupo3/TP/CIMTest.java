package ar.edu.itba.grupo3.TP;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class CIMTest {

    private CIM cim;

    @Before
    public void createCIM(){
        //m = 10 -> cell_size = 10
        cim = new CIM(10, (float) 8, false, false, "resources/Static100.txt");
        cim.loadDynamicFile("resources/Dynamic100.txt"); //n = 100, l = 100
    }

    @Test
    public void correctParticleReadTest(){
        Particle p0 = cim.getAllParticles().get(0);
        Assert.assertNotNull(p0);
        Assert.assertEquals(Double.valueOf(8.4615324e+00), p0.getX());
        Assert.assertEquals(Double.valueOf(4.5584588e+01), p0.getY());
        Assert.assertEquals(Double.valueOf(0.3700), p0.getRadious());
        Assert.assertEquals(Double.valueOf(1.0000), p0.getProperty());
    }

    @Test
    public void correctParticleReadTest42(){
        Particle p42 = cim.getAllParticles().get(41);
        Assert.assertNotNull(p42);
        Assert.assertEquals(Double.valueOf(3.3112866e+01), p42.getX());
        Assert.assertEquals(Double.valueOf(6.5821450e+01), p42.getY());
        Assert.assertEquals(Double.valueOf(0.3700), p42.getRadious());
        Assert.assertEquals(Double.valueOf(1.0000), p42.getProperty());
    }

    @Test
    public void getParticleCurrentCellTest(){
        cim.getParticleCurrentCell(cim.getAllParticles().get(0));
        Assert.assertEquals(40, cim.getParticleCurrentCell(cim.getAllParticles().get(0)));
    }

    @Test
    public void getParticleCurrentCellTest2(){
        Assert.assertEquals(63, cim.getParticleCurrentCell(cim.getAllParticles().get(41)));
    }

    @Test
    public void getParticleCurrentCellTest3(){
        cim.getParticleCurrentCell(cim.getAllParticles().get(40));
        Assert.assertEquals(54, cim.getParticleCurrentCell(cim.getAllParticles().get(40)));
    }

    @Test
    public void getNeighborCellTest(){
        //particle 0 -> cell 40 -> neighbor cells [40, 50, 51, 41, 31]
        List<Integer> lCells = Arrays.asList(40, 50, 51, 41, 31);
        Assert.assertArrayEquals(
                lCells.stream().map(i -> cim.getHeads().get(i)).filter(Objects::nonNull).map(Particle::getId).toArray(),
                cim.getLShapeHeaders(40).stream().map(Particle::getId).toArray());
    }

    @Test
    public void getNeighborCellTest2(){
        //particle ? -> cell 0 -> neighbor cells [0, 10, 11, 1, -11]
        List<Integer> lCells = Arrays.asList(0, 10, 11, 1, -11);
        Assert.assertArrayEquals(
                lCells.stream().map(i -> cim.getHeads().get(i)).filter(Objects::nonNull).map(Particle::getId).toArray(),
                cim.getLShapeHeaders(0).stream().map(Particle::getId).toArray());
    }


    @Test
    public void getNeighborCellTest3(){
        //particle ? -> cell 97 -> neighbor cells [97, 107, 108, 98, 88]
        List<Integer> lCells = Arrays.asList(97, 107, 108, 98, 88);
        Assert.assertArrayEquals(
                lCells.stream().map(i -> cim.getHeads().get(i)).filter(Objects::nonNull).map(Particle::getId).toArray(),
                cim.getLShapeHeaders(97).stream().map(Particle::getId).toArray());
    }

    @Test
    public void getNeighborCellTest4(){
        //particle ? -> cell 30 -> neighbor cells [30, 40, 41, 31, 21]
        List<Integer> lCells = Arrays.asList(30, 40, 41, 31, 21);
        Assert.assertArrayEquals(
                lCells.stream().map(i -> cim.getHeads().get(i)).filter(Objects::nonNull).map(Particle::getId).toArray(),
                cim.getLShapeHeaders(30).stream().map(Particle::getId).toArray());
    }

    @Test
    public void getNeighborCellTest5(){
        cim.setPeriodicEnvironment(true);
        List<Integer> lCells = Arrays.asList(19, 0, 20, 29, 10);
        Assert.assertArrayEquals(
                lCells.stream().map(i -> cim.getHeads().get(i)).filter(Objects::nonNull).map(Particle::getId).toArray(),
                cim.getLShapeHeaders(19).stream().map(Particle::getId).toArray());
    }

    @Test
    public void getNeighborCellTest6(){
        cim.setPeriodicEnvironment(true);
        List<Integer> lCells = Arrays.asList(9, 19, 10, 0, 90);
        Assert.assertArrayEquals(
                lCells.stream().map(i -> cim.getHeads().get(i)).filter(Objects::nonNull).map(Particle::getId).toArray(),
                cim.getLShapeHeaders(9).stream().map(Particle::getId).toArray());
    }

    @Test
    public void getNeighborCellTest7(){
        cim.setPeriodicEnvironment(true);
        List<Integer> lCells = Arrays.asList(69, 79, 70, 60, 50);
        Assert.assertArrayEquals(
                lCells.stream().map(i -> cim.getHeads().get(i)).filter(Objects::nonNull).map(Particle::getId).toArray(),
                cim.getLShapeHeaders(69).stream().map(Particle::getId).toArray());
    }

    @Test
    public void getNeighborCellTest8(){
        cim.setPeriodicEnvironment(true);
        List<Integer> lCells = Arrays.asList(95, 5, 6, 96, 86);
        Assert.assertArrayEquals(
                lCells.stream().map(i -> cim.getHeads().get(i)).filter(Objects::nonNull).map(Particle::getId).toArray(),
                cim.getLShapeHeaders(95).stream().map(Particle::getId).toArray());
    }

    @Test
    public void calculateNeighborsTest(){
        cim.calculateNeighbors();
        List<Integer> expected = Arrays.asList(37, 57);
        Assert.assertArrayEquals(
                expected.toArray(),
                cim.getAllParticles().get(0).getNeighbours().stream().map(Particle::getId).toArray());
    }

    @Test
    public void calculateNeighborsTest2(){
        cim.calculateNeighbors();
        List<Integer> expected = Arrays.asList(21, 40, 83);
        Assert.assertArrayEquals(
                expected.toArray(),
                cim.getAllParticles().get(90).getNeighbours().stream().map(Particle::getId).toArray());
    }

    @Test
    public void timeOutputTest() {
        cim.saveTimeToFile("Times/", 2, 1, 10.1, 7, 11);
        cim.saveTimeToFile("Times/", 2, 2, 9, 8, 10);
        cim.saveTimeToFile("Times/", 2, 4, 7, 5, 7.7);
        cim.saveTimeToFile("Times/", 2, 5, 5, 4, 6);
        cim.saveTimeToFile("Times/", 2, 8, 2, 1.1, 2.3);
        cim.saveTimeToFile("Times/", 4, 1, 20.1, 19, 21);
        cim.saveTimeToFile("Times/", 4, 2, 19, 18.5, 20.4);
        cim.saveTimeToFile("Times/", 4, 4, 17, 16.9, 18);
        cim.saveTimeToFile("Times/", 4, 5, 15, 14, 16.5);
        cim.saveTimeToFile("Times/", 4, 8, 12, 11.1, 12.3);
    }
}
