package ar.edu.itba.grupo3.TP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        CIM cim;
        //cim=new CIM(N,L,(float)rc,M,periodic,false);
        //cim = new CIM(10, (float) 6.0, false, false, "/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/Static100.txt");
        //cim.loadStaticFile("resources/Static100.txt");
        //cim.loadDynamicFile("/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/Dynamic100.txt"); //n = 100, l = 100

        //time
        //datos fijos: L=100;rc=6
        //N=250,500,750,1000
        //lo hacemos para 10 M por N
        //calculamos el promedio de 50 tiempo
        double tsum=0;
        double tmax=0;
        double tmin=Double.MAX_VALUE;
        int cantT=50;
        int cantM=10;
        for(int k=250;k<=1000;k+=250) {
            for (int i = 1; i <= cantM; i++) {
                for (int j = 0; j < cantT; j++) {
                    long startTime = System.currentTimeMillis();
                    cim = new CIM(i, (float) 6.0, false, false, "/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/Static100.txt");
                    cim.loadDynamicFile("/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/Dynamic100.txt");
                    cim.calculateNeighbors();
                    cim.saveNeighborsToFile("/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/neighboursOutput.txt");
                    long endTime = System.currentTimeMillis();
                    long duration = (endTime - startTime);
                    tsum += duration;
                    if (duration > tmax) {
                        tmax = duration;
                    }
                    if (duration < tmin) {
                        tmin = duration;
                    }
                    cim.saveTimeToFile("Times/", k, i, tsum / cantT, tmin, tmax);
                }
                //cim.saveTimeToFile("Times/", k, i, tsum / cantT, tmin, tmax);
                tsum = 0;
                tmax = 0;
                tmin = Double.MAX_VALUE;
            }
        }
    }


    private static ProgramInput readInput(){
        //reading from user input
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        ProgramInput input = new ProgramInput(); //cargando valores default
        System.out.println("Formato Default? (y/n)"); //se toma en cuenta que solo se ingresa y o n
        try {
            if(reader.next().charAt(0)=='n') {
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
            }
        } catch (InputMismatchException e) {
            System.out.println("Parametro invalido");
        }
        reader.close();
        System.out.println("Parametros ingresados:" + input);
        return input;
    }

    private static class ProgramInput{
        int N; //cantidad de particulas
        int L; //largo
        int M; //cuadrados
        double rc; //radio de interaccion
        double r; //radio de particulas
        boolean periodic; //condicion periodica de contorno

        ProgramInput(){
            this.N = 100;
            this.L = 100;
            this.M = 10;
            this.rc = 6;
            this.r = 1;
            this.periodic = false;
        }

        public String toString(){
            return N + " " + L + " " + M + " " + rc + " " + r + " " +  periodic + " ";
        }
    }
}





