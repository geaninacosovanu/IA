package AE;

import Regression.Utils;
import org.ejml.simple.SimpleMatrix;

import java.util.List;
import java.util.Random;

public class UtilsAE {
    public static SimpleMatrix X= new SimpleMatrix(Utils.readX1Normalizat("C:/Users/cosov/IdeaProjects/IAL3/src/main/resources/antrenare"));
    public static SimpleMatrix Y= new SimpleMatrix(Utils.readYNormalizat("C:/Users/cosov/IdeaProjects/IAL3/src/main/resources/antrenare"));
    public static SimpleMatrix getMatrix(Cromozom c){
        double[][] b = new double[1][8];
        List<Gena> gene =c.getListaGene();
        for(int i=0;i<gene.size();i++){
            b[0][i]=gene.get(i).getValoare();
        }
        return new SimpleMatrix(b);
    }
    public static  double LSE(Cromozom c, Integer yref){
        SimpleMatrix B=getMatrix(c);

        double se= B.mult(X.transpose()).transpose().minus(Y.cols(yref,yref+1)).elementPower(2).elementSum();
        return se;

    }
    public static Double getRandomBetween0and1() {
        Double min=0.0;
        Double max=1.0;
        Random r=new Random();

        return min+(max-min)*r.nextDouble();
    }

    public static double getIntegerBetweenMinAndMax(Double min, Double max){
        Random r=new Random();
//        return min+(max-min)*r.nextInt();
        return min + (max-min) * r.nextDouble();
    }

    public static Integer getRandomInteger(){

        Random r=new Random();
        Integer numar=r.nextInt();
        while(numar<0){
            numar=r.nextInt();
        }
        return numar;
    }
}
