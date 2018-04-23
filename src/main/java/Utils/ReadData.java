package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReadData {
    public static Double[][] X;
    public static Double[][] Y;

    public static Double[][] readX() {
        File file = new File("C:/Users/cosov/IdeaProjects/IALab3/src/main/resources/antrenare");
        BufferedReader br = null;
        Double[][] x = null;
        try {
            br = new BufferedReader(new FileReader(file));
            List<String> lines = br.lines().collect(Collectors.toList());
            int nr = lines.size();
            x = new Double[nr][6];
            int k = 0;
            for (String line : lines) {
                String[] all = line.split(" ");
                for (int i = 0; i < 6; i++)
                    x[k][i] = Double.parseDouble(all[i]);
                k++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return x;
    }

    public static Diagnostic[] readY() {
        File file = new File("C:/Users/cosov/IdeaProjects/IALab3/src/main/resources/antrenare");

        BufferedReader br = null;

        Diagnostic[] y = null;

        try {
            br = new BufferedReader(new FileReader(file));
            List<String> lines = br.lines().collect(Collectors.toList());
            int nr = lines.size();
            y = new Diagnostic[nr];
            int k = 0;
            for (String line : lines) {
                String[] all = line.split(" ");
                y[k] = Diagnostic.valueOf(all[6]);
                k++;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return y;

    }
}
