package ar.edu.itba.grupo3.TP;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class NeighbourOutputTest {
    private CIM cim;

    @Before
    public void setCIM(){
        cim = new CIM(10, (float) 9.9, false, false, "resources/Static100.txt");
        cim.loadDynamicFile("resources/Dynamic100.txt"); //n = 100, l = 100
        cim.calculateNeighbors();

    }

    @Test
    public void testOutputTxt() throws IOException {
        NeighboursOutput.neighboursGenerator(cim);
    }

}
