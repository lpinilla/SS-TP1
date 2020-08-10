package ar.edu.itba.grupo3.TP;

import org.junit.Test;

import java.util.List;

public class CIMTest {

    @Test()
    public void readStaticFileTest(){
        CIM cim = new CIM(10, (float) 1.0, false, false, "resources/Static100.txt");
        cim.loadDynamicFile("resources/Dynamic100.txt");
        List<Particle> neighbors = cim.getParticleNeighbors(cim.getAllParticles().get(0));
        System.out.println(neighbors);
    }
}
