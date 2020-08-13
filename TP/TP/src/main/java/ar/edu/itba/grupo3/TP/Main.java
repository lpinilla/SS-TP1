package ar.edu.itba.grupo3.TP;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

//  ________________________________________
//< Don't mind me, just a good luck totem   >
//  ----------------------------------------
//      \
//       \
//         .--.
//        |o_o |
//        |:_/ |
//       //   \ \
//      (|     | )
//     /'\_   _/`\
//     \___)=(___/
//


public class Main {

    public static void main(String[] args) {
        ProgramInput input = readInput();
        //creating CIM
        CIM cim = null;
        try {
            //si no era default,creo archivos static y dynamic random
            if (!input.def) {
                if (input.timeAnalyzer) {

                    timeAnalyzer(input);

                } else {
                    createRandomInputs(input);
                    input.pathDynamic = "/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/RandomDynamicInput.txt";
                    input.pathStatic = "/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/RandomStaticInput.txt";
                    neighbourAnalizer(input);
                }
            } else {
                neighbourAnalizer(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createRandomInputs(ProgramInput input) throws IOException {
        GenerateInput.inputGenerator(input.N, input.L);
    }

    private static void timeAnalyzer(ProgramInput input) throws IOException {
        //Analiza 100 tiempos por M
        //Analiza 10 M del input a +1
        //Analiza 5 N desde el input hasta +100
        CIM cim = null;
        int cantT = 100;
        long[] times = new long[cantT];
        int cantM = 5;
        int auxM = input.M;
        int auxN = input.N;
        int q = 1; //auxiliar para archivos de octave ignorar
        for (int k = input.N; k < auxN + 500; k += 100, input.N += 100) {
            System.out.println("Analizando N: " + input.N);
            for (int i = input.M; i < auxM + cantM; i++) {
                System.out.println("Analizando M: " + input.M);

                for (int j = 0; j < cantT; j++) {
                    createRandomInputs(input);
                    input.pathDynamic = "/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/RandomDynamicInput.txt";
                    input.pathStatic = "/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/RandomStaticInput.txt";
                    cim = neighbourAnalizer(input);
                    times[j] = cim.getDuration();
                }
                cim.saveTimeToFile("", k, i, times, q);
                Arrays.fill(times, 0);
                input.M++;
            }
            input.M = auxM;
            q++;
        }
    }

    private static CIM neighbourAnalizer(ProgramInput input) {
        CIM cim = new CIM(input.N, input.L, (float) input.rc, input.M, input.periodic, false);
        cim.loadStaticFile(input.pathStatic);
        cim.loadDynamicFile(input.pathDynamic);
        long startTime = System.nanoTime();
        cim.calculateNeighbors();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        cim.setDuration(duration);
        cim.saveNeighborsToFile("/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/neighboursOutput.txt");
        return cim;
    }


    private static ProgramInput readInput() {
        //reading from user input
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        ProgramInput input = new ProgramInput(); //cargando valores default
        System.out.println("Formato Default? N=100, L=100, M=10, Rc=6, R=1, Infinite=true (y/n)"); //se toma en cuenta que solo se ingresa y o n
        try {
            if (reader.next().charAt(0) == 'n') {
                input.def = false;
                System.out.println("Ingrese N, cantidad de particulas ");
                input.N = reader.nextInt();
                System.out.println("Ingrese L, largo del tablero");
                input.L = reader.nextInt();
                System.out.println("Ingrese M, cuadrados por lado");
                input.M = reader.nextInt();
                System.out.println("Ingrese rc, radio de interaccion");
                input.rc = reader.nextDouble();
                System.out.println("Ingrese r, radio de particulas");
                input.r = reader.nextDouble();
                System.out.println("Condicion periodica de contorno? (y/n)");
                input.periodic = reader.next().charAt(0) == 'y';
                System.out.println("Analizar tiempo? (y/n)");
                input.timeAnalyzer = reader.next().charAt(0) == 'y';

            }
        } catch (InputMismatchException e) {
            System.out.println("Parametro invalido");
        }
        reader.close();
        System.out.println("Parametros ingresados:" + input);
        return input;
    }

    private static class ProgramInput {
        int N; //cantidad de particulas
        int L; //largo
        int M; //cuadrados
        double rc; //radio de interaccion
        double r; //radio de particulas
        boolean periodic; //condicion periodica de contorno
        boolean def; //default?
        boolean timeAnalyzer;
        String pathStatic;
        String pathDynamic;


        ProgramInput() {
            this.N = 100;
            this.L = 100;
            this.M = 10;
            this.rc = 6;
            this.r = 1;
            this.periodic = true;
            this.def = true;
            this.timeAnalyzer = false;
            this.pathStatic = "/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/Static100.txt";
            this.pathDynamic = "/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/Dynamic100.txt";

        }

        public String toString() {
            return N + " " + L + " " + M + " " + rc + " " + r + " " + periodic + " ";
        }
    }
}





