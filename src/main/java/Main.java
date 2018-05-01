import Clasificare.Service;
import Utils.Diagnostic;
import Utils.ReadData;
import Utils.Util;

import java.util.List;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) {
//
//        Service service = new Service(ReadData.readX(), ReadData.readY());
//        Double[] date = new Double[6];
//        date[0] = 68.61;
//        date[1] = 15.08;
//        date[2] = 63.01;
//        date[3] = 53.53;
//        date[4] = 123.43;
//        date[5] = 39.5;
//        date[0] = 33.84;
//        date[1] = 5.05;
//        date[2] = 36.64;
//        date[3] = 28.77;
//        date[4] = 123.95;
//        date[5] = -0.2;
//        Diagnostic d = service.solveGradientDescendent(50, 0.005f, date);
//        Diagnostic d1 = service.solveAE(date, 20, 10, 0.1d);

        test();
        run();

    }

    private static void run() {
        while (true) {
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("Problema clasificarii unei boli.");
            System.out.println("1.Metoda gradientului descendent");
            System.out.println("2.Algoritmi evolutivi");

            System.out.print("Alegeti algoritmul: ");
            Scanner scanner = new Scanner(System.in);
            Integer cmd = scanner.nextInt();
            if (cmd < 1 || cmd > 3)
                System.out.println("Ati intorodus un nr. gresit!");
            else {
                System.out.println("Introduceti valorile:");
                Double[] x = new Double[6];
                for (int i = 0; i < 6; i++)
                    x[i] = scanner.nextDouble();
                switch (cmd) {
                    case 1: {
                        System.out.print("Nr iteratii: ");
                        int nr = scanner.nextInt();
                        System.out.print("Learning rate: ");
                        float lr = scanner.nextFloat();
                        gradientDescendent(lr, nr, x);
                        break;
                    }
                    case 2:
                        Integer nrIndivizi;
                        Integer nrIteratii;
                        Double probabilitateMutatie;
                        System.out.print("Introduceti numarul de indivizi din populatie: ");
                        nrIndivizi = scanner.nextInt();
                        System.out.print("Introduceti numarul de iteratii: ");
                        nrIteratii = scanner.nextInt();
                        System.out.print("Introduceti probabililatea de mutatie: ");
                        probabilitateMutatie = scanner.nextDouble();
                        if (probabilitateMutatie >= 1 || probabilitateMutatie <= 0) {
                            System.out.println("Probabilitatea trebuie sa fie intre 0 si 1!");
                        }
                        ae(x, nrIndivizi, nrIteratii, probabilitateMutatie);
                        break;

                }


            }
        }
    }

    private static void ae(Double[] dr, Integer nrIndivizi, Integer nrIteratii, Double probabilitateMutatie) {
        Service service = new Service(ReadData.readX(), ReadData.readY());
        Diagnostic d = service.solveAE(dr, nrIndivizi,  nrIteratii, probabilitateMutatie);
        System.out.println(d.toString());
    }

    private static void gradientDescendent(float learningRate, Integer nr, Double[] dr) {

        Service service = new Service(ReadData.readX(), ReadData.readY());
        Diagnostic d = service.solveGradientDescendent(nr, learningRate, dr);
        System.out.println(d.toString());
    }


    private static void test() {
        TestAccuracy test = new TestAccuracy(ReadData.readX(), ReadData.readY());
        System.out.println("Acuratetea pentru:");
        System.out.println("1. metoda gradientului descendent:");
        Double[] testGr = test.testGradient();
        for (int i = 0; i < testGr.length; i++)
            System.out.print(testGr[i] + "% ");
        System.out.println();
        System.out.println("2. algoritm evolutiv");
        Double[] testAE = test.testAE();
        for (int i = 0; i < testAE.length; i++)
            System.out.print(testAE[i] + "% ");
    }


}
