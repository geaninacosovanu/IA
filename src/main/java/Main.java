import Clasificare.Service;
import Utils.Util;


public class Main {


    public static void main(String[] args) {

        Service service = new Service();
        Double[] coef = service.gradientDescendent(4, 0.001f);
        Double[] date = new Double[6];
        date[0]=68.61;
        date[1]=15.08;
        date[2]=63.01;
        date[3]=53.53;
        date[4]=123.43;
        date[5]=39.5;
        Double predicted=Util.sigmoid(Util.err(date,coef));
        System.out.println(predicted);


        //test();
        run();

    }

    private static void run() {
  /*      while (true) {
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("Problema aproximării calităţii betonului pe baza ingredientelor folosite la prepararea lui.");
            System.out.println("1.Metoda celor mai mici patrate");
            System.out.println("2.Metoda gradientului descendent");
            System.out.println("3.Algoritmi evolutivi");

            System.out.print("Alegeti algoritmul: ");
            Scanner scanner = new Scanner(System.in);
            Integer cmd = scanner.nextInt();
            if (cmd < 1 || cmd > 3)
                System.out.println("Ati intorodus un nr. gresit!");
            else {
                System.out.println("Introduceti valorile:");
                List<Double> x = new ArrayList<>();
                x.add(1d);
                for (int i = 1; i < 8; i++)
                    x.add(scanner.nextDouble());
                DataRegresie dr = new DataRegresie(x);
                switch (cmd) {
                    case 1:
                        leastSquare(dr);
                        break;
                    case 2: {
                        System.out.print("Nr iteratii: ");
                        int nr = scanner.nextInt();
                        System.out.print("Learning rate: ");
                        float lr = scanner.nextFloat();
                        gradientDescendent(lr, nr, dr);
                        break;
                    }
                    case 3:

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
                        ae(dr, nrIndivizi, 8, nrIteratii, probabilitateMutatie);
                        break;

                }


            }
        }*/
    }

   /* private static void ae(DataRegresie dr, Integer nrIndivizi, int nrGene, Integer nrIteratii, Double probabilitateMutatie) {

        Service service = new Service();
        dr = service.solveAE(dr, nrIndivizi, 8, nrIteratii, 0.0, probabilitateMutatie);
        write(dr);
    }

    private static void write(DataRegresie dr) {
        for (double y : dr.getY()) {
            System.out.print(y);
            System.out.print("  ");
        }
        System.out.println();
    }

    private static void gradientDescendent(float learningRate, Integer nr, DataRegresie dr) {

        Service service = new Service();
        dr = service.solveGradientDescendent(x1N, yN, dr, learningRate, nr);
        write(dr);
    }

    private static void leastSquare(DataRegresie dr) {
        Service service = new Service();
        dr = service.solveLeastSquare(x1N, yN, dr);
        write(dr);

    }


    private static void test() {
        TestAccuracy test = new TestAccuracy();
        System.out.println("Acuratetea pentru:");
        System.out.println("1. metoda celor mai mici patrate:");
        double[] testLS = test.testLeastSquare();
        for (int i = 0; i < testLS.length; i++)
            System.out.print(testLS[i] + "% ");

        System.out.println();
        System.out.println("2. metoda gradientului cu learning rate 0.005 si nr iteratii=20 :");
        double[] testGr = test.testGradient();
        for (int i = 0; i < testGr.length; i++)
            System.out.print(testGr[i] + "% ");

        System.out.println();
        System.out.println("3. algoritm evolutiv cu 100 de indivizi, 100 iteratii si 0.5 prob de mutatie");
        *//*double[] testAE = test.testAE();
        for (int i = 0; i < testAE.length; i++)
            System.out.print(testAE[i] + "% ");*//*
    }*/


}
