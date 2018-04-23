package Regression;

import org.ejml.simple.SimpleMatrix;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import AE.AE;
import AE.UtilsAE;
import AE.Cromozom;

public class Service {
    public Service() {
    }

    public SimpleMatrix leastSquare(double[][] x, double[][] y) {
        SimpleMatrix X = new SimpleMatrix(x);
        SimpleMatrix Y = new SimpleMatrix(y);
        SimpleMatrix B = new SimpleMatrix();
        B = X.transpose().mult(X).invert().mult(X.transpose()).mult(Y);
        return B;
    }


    public DataRegresie solveLeastSquare(double[][] x, double[][] y, DataRegresie date) {
        SimpleMatrix B = leastSquare(x, y);
        //System.out.println(B);
        double[][] d = new double[8][1];
        for (int i = 0; i < date.getX().size(); i++)
            d[i][0] = date.getX().get(i);
        //System.out.println(B);
        for (int i = 0; i < date.getX().size(); i++)
            d[i][0] = (date.getX().get(i) - Utils.min) / (Utils.max - Utils.min);
        SimpleMatrix Dx = new SimpleMatrix(d);
        SimpleMatrix R = new SimpleMatrix();
        R = B.transpose().mult(Dx);
        date.getY().add(R.get(0, 0));
        date.getY().add(R.get(1, 0));
        date.getY().add(R.get(2, 0));
        for (int i = 0; i < date.getY().size(); i++)
            date.getY().set(i, date.getY().get(i) * (Utils.max - Utils.min) + Utils.min);

        return date;
    }

    public DataRegresie solveGradientDescendent(double[][] x, double[][] y, DataRegresie dr, float learningRate, Integer nr) {
        SimpleMatrix B = gradientDescendent(x, y, nr, learningRate);

        double[][] d = new double[8][1];
        for (int i = 0; i < dr.getX().size(); i++)
            d[i][0] = (dr.getX().get(i) - Utils.min) / (Utils.max - Utils.min);
        SimpleMatrix Dx = new SimpleMatrix(d);
        SimpleMatrix R = new SimpleMatrix();
        R = B.transpose().mult(Dx);

        for (int i = 0; i < R.numRows(); i++)
            for (int j = 0; j < R.numCols(); j++) {
                double val = R.get(i, j) * (Utils.max - Utils.min) + Utils.min;
                R.set(i, j, val);
            }

        dr.getY().add(R.get(0, 0));
        dr.getY().add(R.get(1, 0));
        dr.getY().add(R.get(2, 0));
        return dr;
    }


    public SimpleMatrix gradientDescendent(double[][] x1, double[][] y, int n, float learning_rate) {
        double[][] coef = new double[y[0].length][x1[0].length];
        for (int m = 0; m < n; m++) {
            for (int i = 0; i < x1.length; i++) {
                for (int p = 0; p < y[0].length; p++) {
                    double computed = Utils.err(x1[i], coef[p]);
                    double err;
                    err = computed - y[i][p];
                    for (int j = 0; j < x1[0].length; j++) {
                        coef[p][j] = coef[p][j] - learning_rate * err * x1[i][j];
                    }
                }
            }
        }

        return new SimpleMatrix(coef).transpose();
  /*      SimpleMatrix X1 = new SimpleMatrix(x1);
        SimpleMatrix Y = new SimpleMatrix(y);
        SimpleMatrix Bant = new SimpleMatrix(X1.numCols(), Y.numCols());


        for (int m = 1; m < n; m++) {
            SimpleMatrix Err = X1.mult(Bant).minus(Y);
            SimpleMatrix newB = new SimpleMatrix(X1.numCols(), Y.numCols());

            //System.out.println(Err.transpose());
            //System.out.println(X1);
            //System.out.println(Err.transpose().mult(X1).scale(learning_rate / X1.numRows()));
            newB = Bant.minus(Err.transpose().mult(X1).scale(learning_rate / X1.numRows()).transpose());
            float medieErrAnt = 0f, medieErrActuala = 0f;
            medieErrAnt = Float.parseFloat(String.valueOf(X1.mult(Bant).minus(Y).elementPower(2).elementSum() / (3 * X1.numRows())));
            medieErrActuala = Float.parseFloat(String.valueOf(X1.mult(newB).minus(Y).elementPower(2).elementSum() / (3 * X1.numRows())));
            //System.out.println(medieErrAnt + " " + medieErrActuala);
            //if(medieErrAnt>medieErrActuala)
                Bant = newB;

        }
        System.out.println(Bant);
        return Bant;*/
    }


    public DataRegresie solveAE(DataRegresie dr, Integer nrIndivizi, int i, Integer nrIteratii, double v, Double
            probabilitateMutatie) {

        List<Double> domeniu = new ArrayList<>();
        domeniu.add(0d);
        domeniu.add(1d);
        AE ae = new AE(nrIndivizi, i, nrIteratii, 0.0, probabilitateMutatie, domeniu, 0);
        Cromozom cromozom1 = ae.run();
        ae = new AE(nrIndivizi, i, nrIteratii, 0.0, probabilitateMutatie, domeniu, 1);
        Cromozom cromozom2 = ae.run();
        ae = new AE(nrIndivizi, i, nrIteratii, 0.0, probabilitateMutatie, domeniu, 2);
        Cromozom cromozom3 = ae.run();
        double[][] b = new double[8][3];
        for (int k = 0; k < 8; k++) {
            b[k][0] = cromozom1.getListaGene().get(k).getValoare();
            b[k][1] = cromozom2.getListaGene().get(k).getValoare();
            b[k][2] = cromozom3.getListaGene().get(k).getValoare();
        }
        SimpleMatrix B = new SimpleMatrix(b);

        /* System.out.println(B);*/

        double[][] d = new double[8][1];
        for (int k = 0; k < dr.getX().size(); k++)
            d[k][0] = (dr.getX().get(k) - Utils.min) / (Utils.max - Utils.min);
        SimpleMatrix Dx = new SimpleMatrix(d);
        SimpleMatrix R = new SimpleMatrix();

        R = B.transpose().mult(Dx);
        for (int m = 0; m < R.numRows(); m++)
            for (int j = 0; j < R.numCols(); j++) {
                double val = R.get(m, j) * (Utils.max - Utils.min) + Utils.min;
                R.set(m, j, val);
            }

        dr.getY().add(R.get(0, 0));
        dr.getY().add(R.get(1, 0));
        dr.getY().add(R.get(2, 0));
        return dr;


    }

    public double[] testLeastSquare(double[][] antx1N, double[][] antyN, double[][] testx1, double[][] testy) {
        double[] accuracy1 = new double[3];
        double[] actual = new double[3];
        double[] expected = new double[3];
        int[] count = new int[3];
        for (int i = 0; i < testx1.length; i++) {
            List<Double> test = new ArrayList<>();
            for (int j = 0; j < testx1[i].length; j++)
                test.add(testx1[i][j]);
            DataRegresie dr = new DataRegresie(test);
            dr=solveLeastSquare(antx1N, antyN, dr);
            for (int j = 0; j < dr.getY().size(); j++) {
                actual[j] += dr.getY().get(j);
                expected[j] += testy[i][j];
            }
        }

        for (int i = 0; i < accuracy1.length; i++)
            accuracy1[i] = 100 - (Math.abs(expected[i] - actual[i]) * 100 / expected[i]);
        return accuracy1;
    }

    public double[] testGradient(double[][] antx1N, double[][] antyN, double[][] testx1, double[][] testy, float lr, int it) {
        double[] accuracy1 = new double[3];
        double[] actual = new double[3];
        double[] expected = new double[3];
        int[] count = new int[3];
        for (int i = 0; i < testx1.length; i++) {
            List<Double> test = new ArrayList<>();
            for (int j = 0; j < testx1[i].length; j++)
                test.add(testx1[i][j]);
            DataRegresie dr = new DataRegresie(test);
            dr=solveGradientDescendent(antx1N, antyN, dr, lr, it);
            for (int j = 0; j < dr.getY().size(); j++) {
                actual[j] += dr.getY().get(j);
                expected[j] += testy[i][j];
            }
        }

        for (int i = 0; i < accuracy1.length; i++)
            accuracy1[i] = 100 - (Math.abs(expected[i] - actual[i]) * 100 / expected[i]);
        return accuracy1;
    }

    public double[] testAE(double[][] antx1N, double[][] antyN, double[][] testx1, double[][] testy, int nrIndivizi, int nrIteratii, double probabilitateMutatie) {
        UtilsAE.X = new SimpleMatrix(antx1N);
        UtilsAE.Y = new SimpleMatrix(antyN);
        double[] accuracy1 = new double[3];
        double[] actual = new double[3];
        double[] expected = new double[3];
        PrintWriter writer = null;
        try {

            writer = new PrintWriter(new FileWriter("C:/Users/cosov/IdeaProjects/IALab3/src/main/resources/rez"), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < testx1.length; i++) {
            List<Double> test = new ArrayList<>();
            for (int j = 0; j < testx1[i].length; j++)
                test.add(testx1[i][j]);
            writer.println();
            writer.println("Data de test " + i + " " + test);
            DataRegresie dr = new DataRegresie(test);
            dr=solveAE(dr, nrIndivizi, 8, nrIteratii, 0.0, probabilitateMutatie);
            for (int j = 0; j < dr.getY().size(); j++) {

                actual[j] += dr.getY().get(j);
                writer.print("Actual " + dr.getY().get(j));
                expected[j] += testy[i][j];
                writer.print(" Expected " + testy[i][j]);
                writer.println();
            }

        }

        writer.println("Accuracy ");
        for (int i = 0; i < accuracy1.length; i++) {
            accuracy1[i] = 100 - (Math.abs(expected[i] - actual[i]) * 100 / expected[i]);
            writer.print(" " + accuracy1[i]);
        }
        writer.close();

        return accuracy1;
    }
}



