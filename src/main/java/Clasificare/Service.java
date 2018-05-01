package Clasificare;

import AE.AE;
import AE.Cromozom;
import AE.UtilsAE;
import Utils.Diagnostic;
import Utils.Util;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private Double[][] Xn;
    private Diagnostic[] Y;
    private Double[][] X;

    public Service(Double[][] X, Diagnostic[] Y) {
        this.X = new Double[X.length][X[0].length];
        for (int i = 0; i < X.length; i++)
            for (int j = 0; j < X[0].length; j++)
                this.X[i][j] = X[i][j];
        this.Xn = X;
        this.Y = Y;
        normalizareX();
    }

    private void normalizareX() {
        Double[] temp = new Double[Xn.length];
        for (int j = 0; j < Xn[0].length; j++) {
            for (int i = 0; i < Xn.length; i++) {
                temp[i] = Xn[i][j];
            }
            Util.normalizare(temp);
            for (int i = 0; i < Xn.length; i++) {
                Xn[i][j] = temp[i];
            }
        }
    }


    public Double[] clasificatorDH(int n, float learning_rate) {
        Double[] Yn = new Double[Xn.length];
        for (int i = 0; i < Xn.length; i++) {
            if (Y[i] == Diagnostic.DH)
                Yn[i] = 1d;
            if (Y[i] == Diagnostic.SL)
                Yn[i] = 0d;
            if (Y[i] == Diagnostic.NO)
                Yn[i] = 0d;
        }
        return gradientDescendent(n, learning_rate, Yn);
    }

    public Double[] clasificatorSL(int n, float learning_rate) {
        Double[] Yn = new Double[Xn.length];
        for (int i = 0; i < Xn.length; i++) {
            if (Y[i] == Diagnostic.DH)
                Yn[i] = 0d;
            if (Y[i] == Diagnostic.SL)
                Yn[i] = 1d;
            if (Y[i] == Diagnostic.NO)
                Yn[i] = 0d;
        }
        return gradientDescendent(n, learning_rate, Yn);
    }

    public Double[] clasificatorNO(int n, float learning_rate) {
        Double[] Yn = new Double[Xn.length];
        for (int i = 0; i < Xn.length; i++) {
            if (Y[i] == Diagnostic.DH)
                Yn[i] = 0d;
            if (Y[i] == Diagnostic.SL)
                Yn[i] = 0d;
            if (Y[i] == Diagnostic.NO)
                Yn[i] = 1d;
        }
        return gradientDescendent(n, learning_rate, Yn);

    }

    public Diagnostic solveGradientDescendent(int n, float learning_rate, Double[] date) {
        Double[] coefDH = clasificatorDH(n, learning_rate);
        Double[] coefSL = clasificatorSL(n, learning_rate);
        Double[] coefNO = clasificatorNO(n, learning_rate);
        Util.normData(this.X, date);
        Double predictedDH = Util.sigmoid(Util.computedValue(date, coefDH));
        Double predictedSL = Util.sigmoid(Util.computedValue(date, coefSL));
        Double predictedNO = Util.sigmoid(Util.computedValue(date, coefNO));
        Double max = Math.max(Math.max(predictedDH, predictedSL), predictedNO);

        Diagnostic diagnostic = null;
        if (max.equals(predictedSL))
            diagnostic = Diagnostic.SL;
        else if (max.equals(predictedDH))
            diagnostic = Diagnostic.DH;
        else if (max.equals(predictedNO))
            diagnostic = Diagnostic.NO;
        return diagnostic;
    }

    public Double[] gradientDescendent(int n, float learning_rate, Double[] Yn) {
        Double[] coef = new Double[Xn[0].length + 1];
        for (int i = 0; i < coef.length; i++)
            coef[i] = 0d;
        for (int m = 0; m < n; m++) {
            Double[] predictedValues = new Double[Xn.length];
            Double[] realValues = new Double[Xn.length];
            for (int i = 0; i < Xn.length; i++) {
                predictedValues[i] = Util.sigmoid(Util.computedValue(Xn[i], coef));
                realValues[i] = Yn[i];
            }
            Double gradient = 0d;
            for (int j = 0; j < Xn.length; j++) {
                gradient = gradient + (predictedValues[j] - realValues[j]);
            }
            coef[0] = coef[0] - gradient * learning_rate;
            for (int i = 1; i < coef.length; i++) {
                gradient = 0d;
                for (int j = 0; j < Xn.length; j++) {
                    gradient = gradient + Xn[j][i - 1] * (predictedValues[j] - realValues[j]);
                }
                coef[i] = coef[i] - gradient * learning_rate;
            }
        }
        return coef;
    }

    private void initAE() {
        double[][] x = new double[Xn.length][7];
        for (int j = 0; j < Xn.length; j++)
            x[j][0] = 1;
        for (int j = 0; j < Xn.length; j++)
            for (int k = 0; k < 5; k++)
                x[j][k + 1] = Xn[j][k];
        UtilsAE.X = x;

        double[][] y = new double[Y.length][3];
        for (int i = 0; i < Y.length; i++) {
            if (Y[i] == Diagnostic.DH) {
                y[i][0] = 1d;
                y[i][1] = 0d;
                y[i][2] = 0d;
            }
            if (Y[i] == Diagnostic.SL) {
                y[i][0] = 0d;
                y[i][1] = 1d;
                y[i][2] = 0d;
            }
            if (Y[i] == Diagnostic.NO) {
                y[i][0] = 0d;
                y[i][1] = 0d;
                y[i][2] = 1d;
            }
        }

        UtilsAE.Y = y;


    }

    public Diagnostic solveAE(Double[] dr, Integer nrIndivizi, Integer nrIteratii, Double probabilitateMutatie) {
        initAE();
        List<Double> domeniu = new ArrayList<>();
        domeniu.add(0d);
        domeniu.add(1d);
        AE ae = new AE(nrIndivizi, 7, nrIteratii, 0.0, probabilitateMutatie, domeniu, 0);
        Cromozom cromozom1 = ae.run();
        ae = new AE(nrIndivizi, 7, nrIteratii, 0.0, probabilitateMutatie, domeniu, 1);
        Cromozom cromozom2 = ae.run();
        ae = new AE(nrIndivizi, 7, nrIteratii, 0.0, probabilitateMutatie, domeniu, 2);
        Cromozom cromozom3 = ae.run();
        Double[][] b = new Double[3][7];

        for (int k = 0; k < 7; k++) {
            b[0][k] = cromozom1.getListaGene().get(k).getValoare();
            b[1][k] = cromozom2.getListaGene().get(k).getValoare();
            b[2][k] = cromozom3.getListaGene().get(k).getValoare();
        }

        Util.normData(this.X, dr);
        Double predictedDH = Util.sigmoid(Util.computedValue(dr, b[0]));
        Double predictedSL = Util.sigmoid(Util.computedValue(dr, b[1]));
        Double predictedNO = Util.sigmoid(Util.computedValue(dr, b[2]));
        Double max = Math.max(Math.max(predictedDH, predictedSL), predictedNO);

        Diagnostic diagnostic = null;
        if (max.equals(predictedSL))
            diagnostic = Diagnostic.SL;
        else if (max.equals(predictedDH))
            diagnostic = Diagnostic.DH;
        else if (max.equals(predictedNO))
            diagnostic = Diagnostic.NO;
        return diagnostic;
    }

    public Double[] testAE(Double[][] testX, Diagnostic[] testY, Integer nrIndivizi, Integer nrIteratii, Double probabilitateMutatie) {
        Double[] pred = new Double[3];
        Double[] real = new Double[3];
        Double[] accuracy = new Double[3];
        for (int i = 0; i < pred.length; i++) {
            pred[i] = 0d;
            real[i] = 0d;
            accuracy[i] = 0d;
        }
        for (int i = 0; i < testX.length; i++) {
            Diagnostic d = solveAE(testX[i], nrIndivizi, nrIteratii, probabilitateMutatie);
            if (testY[i] == Diagnostic.DH) {
                if (d == testY[i]) pred[0]++;
                real[0]++;
            }
            if (testY[i] == Diagnostic.SL) {
                if (d == testY[i]) pred[1]++;
                real[1]++;
            }
            if (testY[i] == Diagnostic.NO) {
                if (d == testY[i]) pred[2]++;
                real[2]++;
            }

        }

        for (int i = 0; i < pred.length; i++)
            accuracy[i] = pred[i] * 100 / real[i];
        return accuracy;
    }

    public Double[] testGradient(Double[][] testX, Diagnostic[] testY, float lr, int n) {
        Double[] pred = new Double[3];
        Double[] real = new Double[3];
        Double[] accuracy = new Double[3];
        for (int i = 0; i < pred.length; i++) {
            pred[i] = 0d;
            real[i] = 0d;
            accuracy[i] = 0d;
        }
        for (int i = 0; i < testX.length; i++) {
            Diagnostic d = solveGradientDescendent(n, lr, testX[i]);
            if (testY[i] == Diagnostic.DH) {
                if (d == testY[i]) pred[0]++;
                real[0]++;
            }
            if (testY[i] == Diagnostic.SL) {
                if (d == testY[i]) pred[1]++;
                real[1]++;
            }
            if (testY[i] == Diagnostic.NO) {
                if (d == testY[i]) pred[2]++;
                real[2]++;
            }

        }

        for (int i = 0; i < pred.length; i++)
            accuracy[i] = pred[i] * 100 / real[i];
        return accuracy;
    }

}
