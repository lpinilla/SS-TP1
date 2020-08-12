package ar.edu.itba.grupo3.TP;

//para ubicar las particulas hacemos
//(x/m)+(m*y/m)

// i-m
// i-m+1
// i+1
// i+1+m

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

//       GenerateInput.inputGenerator();
        int N=100; //cantidad de particulas
        int L = 100; //largo
        int M=10; //cuadrados
        double rc=6; //radio de interaccion
        double r=1; //radio de particulas
        boolean periodic=false; //condicion periodica de contorno
        boolean defaultV=true; //usar condiciones default
        //reading from user input
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Formato Default? (y/n)");

        try {
            if(reader.next().charAt(0)=='n') {
                defaultV=false;
                System.out.println("Ingrese N, cantidad de particulas ");
                N = reader.nextInt();
                System.out.println("Ingrese L, largo del tablero");
                L = reader.nextInt();
                System.out.println("Ingrese M, cuadrados por lado");
                M = reader.nextInt();
                System.out.println("Ingrese rc, radio de interaccion");
                rc = reader.nextDouble();
                System.out.println("Ingrese r, radio de particulas");
                r = reader.nextDouble();
                System.out.println("Condicion periodica de contorno? (y/n)");
                periodic = reader.next().charAt(0) == 'y';
            }
        } catch (InputMismatchException e) {
            System.out.println("Parametro invalido");
        }
        reader.close();
        System.out.println("Parametros ingresados:" + N + L + M + rc + r + periodic);
        //creating CIM
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("prueba.txt")));

        CIM cim;
        //cim=new CIM(N,L,(float)rc,M,periodic,false);
        //cim = new CIM(10, (float) 6.0, false, false, "/home/mimi/Documents/SS/TP/SS-TP1/TP/TP/resources/Static100.txt");

       // cim.loadStaticFile("resources/Static100.txt");
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
                    NeighboursOutput.neighboursGenerator(cim);
                    long endTime = System.currentTimeMillis();
                    long duration = (endTime - startTime);
                    tsum += duration;
                    if (duration > tmax) {
                        tmax = duration;
                    }
                    if (duration < tmin) {
                        tmin = duration;
                    }
                }
                TimeOutput.outputGenerator(k, i, tsum / cantT, tmin, tmax);
                tsum = 0;
                tmax = 0;
                tmin = Double.MAX_VALUE;
            }
        }




    }
}





