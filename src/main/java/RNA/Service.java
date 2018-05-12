package RNA;

import Utils.Diagnostic;
import Utils.Util;

public class Service {
    RNA rna;

    public Service() {
    }

    private void normalizareX(Double[][] Xn) {
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

    public void train(Double[][] X, Diagnostic[] Y, Float learningRate, Integer nrOut, Integer n) {
        normalizareX(X);
        rna = new RNA(X[0].length, 3, 2, learningRate, X, Y);
        rna.init();
        rna.train(n);
    }


    public Double testRNA(Double[][] X, Diagnostic[] Y, Double[][] testX, Diagnostic[] testY, Float learningRate, Integer nrOut, Integer n) {

        Double[][] Xn = new Double[X.length][X[0].length];
        for (int i = 0; i < X.length; i++)
            for (int j = 0; j < Xn[0].length; j++) {
                Xn[i][j] = X[i][j];
            }
        normalizareX(Xn);
        rna = new RNA(X[0].length, 3, 2, learningRate, Xn, Y);
        rna.init();
        rna.train(n);
        Integer nr = 0;
        for (int i = 0; i < testX.length; i++) {
            Double[] date = new Double[X[i].length];
            for (int j = 0; j < Xn[i].length; j++)
                date[j] = testX[i][j];
            Util.normData(X, date);
            Diagnostic d = rna.evaluate(date);
            if (d.equals(testY[i]))
                nr++;
        }
        return (nr * 1.0) / (testX.length * 1.0);
    }
}
