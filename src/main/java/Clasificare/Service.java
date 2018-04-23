package Clasificare;

import Utils.Diagnostic;
import Utils.ReadData;
import Utils.Util;
import org.ejml.simple.SimpleMatrix;

public class Service {
    private Double[][] X;
    private Double[] Y;

    public Service() {
        X = ReadData.readX();
        normalizare(ReadData.readY());
    }

    private void normalizare(Diagnostic[] y) {
        Double[] temp = new Double[X.length];
        for (int j = 0; j < X[0].length; j++) {
            for (int i = 0; i < X.length; i++) {
                temp[i] = X[i][j];
            }
            Util.normalizare(temp);
            for (int i = 0; i < X.length; i++) {
                X[i][j] = temp[i];
            }
        }

        Y = new Double[X.length];
        for (int i = 0; i < X.length; i++) {
            if (y[i] == Diagnostic.DH)
                Y[i] = 0d;
            if (y[i] == Diagnostic.SL)
                Y[i] = 0.5;
            if (y[i] == Diagnostic.NO)
                Y[i] = 1d;
        }
        //Normalicare valori Y


    }

    public Double[] gradientDescendent(int n, float learning_rate) {
        Double[] coef = new Double[X[0].length + 1];
        for (int i = 0; i < coef.length; i++)
            coef[i] = 0d;
        for (int m = 0; m < n; m++) {
            Double[] predictedValues = new Double[X.length];
            Double[] realValues = new Double[X.length];
            for (int i = 0; i < X.length; i++) {
                predictedValues[i] = Util.sigmoid(Util.err(X[i], coef));
                realValues[i] = Y[i];
            }

            Double gradient = 0d;

            gradient = 0d;
            for (int j = 0; j < X.length; j++) {
                gradient = gradient + (predictedValues[j] - realValues[j]);
            }
            coef[0] = coef[0] - gradient * learning_rate;

            //vezi cazul cu primul coef =1
            for (int i = 1; i < coef.length; i++) {
                gradient = 0d;
                for (int j = 0; j < X.length; j++) {
                    gradient = gradient + X[j][i - 1] * (predictedValues[j] - realValues[j]);
                }
                coef[i] = coef[i] - gradient * learning_rate;
            }

        }
        return coef;
    }
  /*  public DataRegresie solveGradientDescendent(double[][] x, double[][] y, DataRegresie dr, float learningRate, Integer nr) {
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
    }*/
}
