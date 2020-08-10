package ar.edu.itba.grupo3.TP;

import org.junit.Test;

public class CIMTest {

    @Test()
    public void readStaticFileTest(){
        CIM cim = new CIM(10, (float) 1.0, false, "resources/Static100.txt");
        cim.loadDynamicFile("resources/Dynamic100.txt");
    }
}
