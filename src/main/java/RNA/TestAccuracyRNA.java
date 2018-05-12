package RNA;

import Utils.Diagnostic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestAccuracyRNA {

    Service service;
    private Diagnostic[] Y;
    private Double[][] X;
    private Diagnostic[] antY;
    private Double[][] antX;
    private Double[][] testX;
    private Diagnostic[] testY;

    public TestAccuracyRNA(Double[][] X, Diagnostic[] Y) {
        this.X = X;
        this.Y = Y;
        service = new Service();
    }

    private void init() {
        List<Integer> all = initIndexes(X.length);
        int s = (int) (all.size() * 0.8);
        this.antX = new Double[s][8];
        this.testX = new Double[all.size() - s][8];
        this.antY = new Diagnostic[s];
        this.testY = new Diagnostic[all.size() - s];
        for (int i = 0; i < all.size(); i++) {
            if (i < s) {
                this.antX[i] = X[all.get(i)];
                this.antY[i] = Y[all.get(i)];
            } else {
                this.testX[i - s] = X[all.get(i)];
                this.testY[i - s] = Y[all.get(i)];
            }
        }
//        Service service = new Service();
//        service.train(ReadData.readX(), ReadData.readY(), 0.001f, 3, 200);


    }

    private List<Integer> initIndexes(Integer nr) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < nr; i++)
            indexes.add(i);
        Collections.shuffle(indexes);
        return indexes;
    }


    public Double test() {
        Double accuracy = 0d;
        int n = 10;
        for (int i = 0; i < n; i++) {
            init();
            Double test = service.testRNA(X, Y, testX, testY, 0.05f, 3, 10);
            accuracy += test;
        }

        accuracy = (accuracy * 1.0) / (1.0 * n);
        return accuracy;
    }


}
