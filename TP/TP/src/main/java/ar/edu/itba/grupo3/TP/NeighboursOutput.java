package ar.edu.itba.grupo3.TP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class NeighboursOutput {

    public static void neighboursGenerator(CIM cim) throws IOException {
        generateNeighbours(cim);
    }

    private static void generateNeighbours(CIM cim) {
        List<Particle> all=cim.getAllParticles();
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("resources/neighboursOutput.txt")));
            for(int i=0;i<cim.getN();i++){
                writer.write(Integer.toString(i)+",");
                StringBuilder builder = new StringBuilder();
                for (Particle p : all.get(i).getNeighbours()) {
                    builder.append(p);
                    builder.append(',');
                }
                String id = builder.toString();
                writer.write(id);
                writer.newLine();

            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
