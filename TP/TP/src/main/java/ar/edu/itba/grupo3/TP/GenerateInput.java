package ar.edu.itba.grupo3.TP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInput {

    public static void inputGenerator(int N,int L) throws IOException {
        generateInputs(N,L);
    }


    //N = #particles
    //L = lenght board
    private static void generateInputs(int N,int L) throws IOException{
        Random r = new Random();
        //particles radious between 0 and 1;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("TP/TP/resources/RandomStaticInput.txt")));
            writer.write(Integer.toString(N));
            writer.newLine();
            writer.write(Integer.toString(L));
            writer.newLine();

            //creating ratios of particles
            for (int i = 0; i < N; i++) {
                for(int j=0;j<2;j++){
                    double rad = r.nextDouble();
                    if(j!=1){
                        writer.write(rad+"    ");
                    }
                    else{
                        //write another property, 1 in this case because its an example
                        writer.write("1");//
                    }
                }
                writer.newLine();
            }
            writer.flush();
            writer.close();
        }catch (IOException e) {
            System.out.println("Error creating Static Input");
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("TP/TP/resources/RandomDynamicInput.txt")));

            writer.write("0"); //time of dynamic particles.
            writer.newLine();

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < 2; j++) {
                    double ran= r.nextDouble() * L;
                    if(j!=1){
                        writer.write(ran + "    ");
                    }
                    else {
                        writer.write(Double.toString(ran));
                    }
                }
                writer.newLine();
            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error creating Dynamic Input");
        }

    }
}
