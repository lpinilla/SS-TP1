package ar.edu.itba.grupo3.TP;

import org.junit.Test;

import java.io.IOException;
import java.sql.Time;

public class TimeOutputTest {
    TimeOutput t;
    @Test
    public void timeOutputTest() throws IOException {

        TimeOutput.outputGenerator(2,1,10.1,7,11);
        TimeOutput.outputGenerator(2,2,9,8,10);
        TimeOutput.outputGenerator(2,4,7,5,7.7);
        TimeOutput.outputGenerator(2,5,5,4,6);
        TimeOutput.outputGenerator(2,8,2,1.1,2.3);


        TimeOutput.outputGenerator(4,1,20.1,19,21);
        TimeOutput.outputGenerator(4,2,19,18.5,20.4);
        TimeOutput.outputGenerator(4,4,17,16.9,18);
        TimeOutput.outputGenerator(4,5,15,14,16.5);
        TimeOutput.outputGenerator(4,8,12,11.1,12.3);


    }
}
