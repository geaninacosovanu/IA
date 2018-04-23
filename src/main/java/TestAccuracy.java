import AE.UtilsAE;
import Regression.Service;
import Regression.Utils;
import org.ejml.simple.SimpleMatrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TestAccuracy {

    private double[][] antyN;
    private double[][] antx1N;
    private double[][] testX1;
    private double[][] testY;
    Service service;

    public TestAccuracy() {
        service =new Service();

    }

    private void init(){
        String url = "C:/Users/cosov/IdeaProjects/IALab3/src/main/resources/antrenare";
        double[][] x1 = Utils.readX1(url);
        double[][] y = Utils.readY(url);
        double[][] yN = Utils.readYNormalizat(url);
        double[][] x1N = Utils.readX1Normalizat(url);
        double[] lse = new double[3];
        //for (int it = 0; it < 100; it++) {

        List<Integer> all = initIndexes(x1.length);
        int s = (int) (all.size() * 0.8);
        double[][] antx = new double[s][8];
        double[][] testx = new double[all.size() - s][8];
        double[][] anty = new double[s][3];
        double[][] testy = new double[all.size() - s][3];
        for (int i = 0; i < all.size(); i++) {
            if (i < s) {
                antx[i] = x1N[all.get(i)];
                anty[i] = yN[all.get(i)];
            } else {
                testx[i - s] = x1[all.get(i)];
                testy[i - s] = y[all.get(i)];
            }
        }
        antx1N = antx;
        antyN = anty;
        testX1 = testx;
        testY = testy;
    }
    private List<Integer> initIndexes(Integer nr) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < nr; i++)
            indexes.add(i);
        Collections.shuffle(indexes);
        return indexes;
    }



    public double[] testLeastSquare() {
        double[] accuracyLS=new double[3];
        int n=100;
        for (int i = 0; i < n; i++) {
            init();
            double[] testLSE=service.testLeastSquare(antx1N,antyN,testX1,testY);
            for(int j=0;j<testLSE.length;j++)
                accuracyLS[j]+=testLSE[j];
        }
        for(int i=0;i<accuracyLS.length;i++){
            accuracyLS[i]=accuracyLS[i]/n;
        }
        return accuracyLS;

    }

    public double[] testGradient() {
        double[] accuracyGr=new double[3];
        int n=100;
        for (int i = 0; i < n; i++) {
            init();
            double[] testGr=service.testGradient(antx1N,antyN,testX1,testY,0.05f,20);
            for(int j=0;j<testGr.length;j++)
                accuracyGr[j]+=testGr[j];
        }
        for(int i=0;i<accuracyGr.length;i++){
            accuracyGr[i]=accuracyGr[i]/n;
        }
        return accuracyGr;
    }

    public double[] testAE() {
        double[] accuracyAe=new double[3];
        int n=100;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter("C:/Users/cosov/IdeaProjects/IALab3/src/main/resources/rez",true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < n; i++) {
            init();
            writer.println("Initializat date pt iteratia " + i);
            writer.close();
            double[] testAe=service.testAE(antx1N,antyN,testX1,testY,100,10,0.5);
            try {
                writer = new PrintWriter(new FileWriter("C:/Users/cosov/IdeaProjects/IALab3/src/main/resources/rez"),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer.println("Obtinut rezultate pt iteratia "+i);
            for(int j=0;j<testAe.length;j++)
                accuracyAe[j]+=testAe[j];
        }
        for(int i=0;i<accuracyAe.length;i++){
            accuracyAe[i]=accuracyAe[i]/n;
        }

        return accuracyAe;
    }
}
