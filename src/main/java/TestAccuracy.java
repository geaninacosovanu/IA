import Clasificare.Service;
import Utils.Diagnostic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestAccuracy {

    private Diagnostic[] Y;
    private Double[][] X;
    private Diagnostic[] antY;
    private Double[][] antX;
    private Double[][] testX;
    private Diagnostic[] testY;
    Service service;

    public TestAccuracy(Double[][] X, Diagnostic[] Y) {
        this.X=X;
        this.Y=Y;
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
        service = new Service(this.antX, this.antY);


    }

    private List<Integer> initIndexes(Integer nr) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < nr; i++)
            indexes.add(i);
        Collections.shuffle(indexes);
        return indexes;
    }


    public Double[] testGradient() {
        Double[] accuracyGr = new Double[3];
        for (int i = 0; i < accuracyGr.length; i++)  accuracyGr[i]=0d;
        int n = 10;
        for (int i = 0; i < n; i++) {
            init();
            Double[] testGr = service.testGradient(testX, testY, 0.05f, 20);
            for (int j = 0; j < testGr.length; j++)
                accuracyGr[j] += testGr[j];
        }
        for (int i = 0; i < accuracyGr.length; i++) {
            accuracyGr[i] = accuracyGr[i] / n;
        }
        return accuracyGr;
    }

    public Double[] testAE(){
        Double[] accuracyAE = new Double[3];
        for (int i = 0; i < accuracyAE.length; i++)  accuracyAE[i]=0d;
        int n = 5;
        for (int i = 0; i < n; i++) {
            init();
            Double[] testGr = service.testAE(testX, testY,20,10,0.5);
            for (int j = 0; j < testGr.length; j++)
                accuracyAE[j] += testGr[j];
        }
        for (int i = 0; i < accuracyAE.length; i++) {
            accuracyAE[i] = accuracyAE[i] / n;
        }
        return accuracyAE;
    }

}
