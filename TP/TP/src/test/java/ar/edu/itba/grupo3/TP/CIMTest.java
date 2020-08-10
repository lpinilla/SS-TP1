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
        cim = new CIM(10, (float) 1.0, false, false, "resources/Static100.txt");
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
        Assert.assertEquals(40, cim.getParticleCurrentCell(cim.getAllParticles().get(0)));
    }

    @Test
    public void getParticleCurrentCellTest2(){
        Assert.assertEquals(63, cim.getParticleCurrentCell(cim.getAllParticles().get(41)));
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
}
