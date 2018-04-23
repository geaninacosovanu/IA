package Regression;

import org.ejml.simple.SimpleMatrix;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static double minX;
    public static double maxX;
    public static double maxY;
    public static double minY;
    public static double min;
    public static double max;

    public static void init() {
        min = Math.min(minX, minY);
        max = Math.max(maxX, maxY);
    }

    public static double[][] readX1Normalizat(String url) {
        Double[][] x = readx1(url);
        Double[][] y = ready(url);
        init();
        double[][] X = new double[x.length][8];

        for (int i = 0; i < x.length; i++)
            for (int j = 0; j < x[0].length; j++)
                X[i][j] = (x[i][j] - min) / (max - min);
        return X;
    }

    public static double[][] readX1(String url) {
        Double[][] x = readx1(url);

        double[][] X = new double[x.length][8];

        for (int i = 0; i < x.length; i++)
            for (int j = 0; j < x[0].length; j++)
                X[i][j] = x[i][j];
        return X;
    }

    public static Double[][] readx1(String url) {
        File file = new File(url);

        BufferedReader br = null;
        Double[][] x;


        try {
            br = new BufferedReader(new FileReader(file));
            List<String> lines = br.lines().collect(Collectors.toList());
            int nr = lines.size();
            x = new Double[nr][8];

            for (String line : lines) {
                String[] all = line.split(",");
                x[Integer.parseInt(all[0]) - 1][0] = 1d;
                for (int i = 1; i < 8; i++)
                    x[Integer.parseInt(all[0]) - 1][i] = Double.parseDouble(all[i]);

            }
            List<Double> list = new ArrayList<Double>();
            for (Double[] array : x)
                list.addAll(Arrays.asList(array));
            maxX = list.stream().max(Double::compareTo).get().intValue();
            minX = list.stream().min(Comparator.naturalOrder()).get().intValue();
            return x;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double[][] readYNormalizat(String url) {
        Double[][] x = readx1(url);
        Double[][] y = ready(url);
        init();
        double[][] Y = new double[y.length][3];

        for (int i = 0; i < y.length; i++)
            for (int j = 0; j < y[0].length; j++)
                Y[i][j] = (y[i][j] - min) / (max - min);
        return Y;
    }

    public static double[][] readY(String url) {

        Double[][] y = ready(url);

        double[][] Y = new double[y.length][3];

        for (int i = 0; i < y.length; i++)
            for (int j = 0; j < y[0].length; j++)
                Y[i][j] = (y[i][j]);
        return Y;
    }

    public static Double[][] ready(String url) {
        File file = new File(url);

        BufferedReader br = null;

        Double[][] y;

        try {
            br = new BufferedReader(new FileReader(file));
            List<String> lines = br.lines().collect(Collectors.toList());
            int nr = lines.size();
            y = new Double[nr][3];

            for (String line : lines) {
                String[] all = line.split(",");
                for (int i = 7; i < 10; i++)
                    y[Integer.parseInt(all[0]) - 1][i - 7] = Double.parseDouble(all[i + 1]);

            }
            List<Double> list = new ArrayList<Double>();
            for (Double[] array : y)
                list.addAll(Arrays.asList(array));
            maxY = list.stream().max((p1, p2) -> p1.compareTo(p2)).get().intValue();
            minY = list.stream().min((p1, p2) -> p1.compareTo(p2)).get().intValue();
            return y;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double err(double[] data, double[] coef) {
        double s = 0;
        for (int i = 0; i < data.length; i++)
            s += coef[i] * data[i];
        return s;
    }




}
