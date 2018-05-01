package Utils;

public class Util {
    private static Double medie(Double[] values){
        Double s=0d;
        for(int i=0;i<values.length;i++)
            s=s+values[i];
        return s/values.length;
    }
    private static Double deviatie(Double[] values,Double medie){
        Double s=0d;
        for(int i=0;i<values.length;i++)
            s+=Math.pow((values[i]-medie),2);
        return Math.sqrt(s/(values.length-1));
    }


    public static void normalizare(Double[] values){
        Double medie=medie(values);
        Double deviatie=deviatie(values,medie);
        for(int i=0;i<values.length;i++)
           values[i]=(values[i]-medie)/deviatie;
    }
    public static Double computedValue(Double[] data, Double[] coef) {
        Double s = coef[0];
        for (int i = 0; i < data.length; i++)
            s += coef[i+1] * data[i];
        return s;
    }
    public static Double sigmoid(Double x){
        return 1/(1+Math.exp(-x));
    }
    public static void normData(Double[][] X, Double[] data){
        Double[] temp = new Double[X.length];
        for (int j = 0; j < X[0].length; j++) {
            for (int i = 0; i < X.length; i++) {
                temp[i] = X[i][j];
            }
            Double medie = medie(temp);
            Double deviatie = deviatie(temp,medie);
            data[j]=(data[j]-medie)/deviatie;
        }
    }
}
