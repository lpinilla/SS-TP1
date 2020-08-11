package ar.edu.itba.grupo3.TP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TimeOutput {

    public static void outputGenerator(int N, int M,double tprom,double tmin,double tmax) throws IOException {
        generateTimeOutput(N,M,tprom,tmin,tmax);
    }


    private static void generateTimeOutput(int N, int M,double tprom,double tmin,double tmax) throws IOException {
        try {
            File f=new File("resources/TimeOutput"+N+".txt");
            if(!f.exists()){
                BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                writer.write(Integer.toString(N));
                writer.newLine();
                writer.write(Integer.toString(M) + " "+Double.toString(tprom)+" "+Double.toString(tprom-tmin)+" "+Double.toString(tmax-tprom));
                //necesito todos los tiempos?
//                for (int i = 0; i < time.length; i++) {
//                    writer.write(Double.toString(time[i])+" ");
//                }
                writer.newLine();
                writer.flush();
                writer.close();
            }
            else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(f,true));
                writer.write(Integer.toString(M) + " "+Double.toString(tprom)+" "+Double.toString(tprom-tmin)+" "+Double.toString(tmax-tprom));
//                for (int i = 0; i < time.length; i++) {
//                   writer.write(Double.toString(time[i])+" ");
//                }
                writer.newLine();
                writer.flush();
                writer.close();
            }

        } catch (IOException e) {
            System.out.println("Error creating Time Output data");
        }
    }
}
