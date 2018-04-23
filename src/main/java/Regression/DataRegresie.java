package Regression;

import java.util.ArrayList;
import java.util.List;

public class DataRegresie {
    List<Double> x;
    List<Double> y;

    public DataRegresie(List<Double> x) {
        this.x = x;
        y=new ArrayList<>();
    }

    public List<Double> getX() {
        return x;
    }

    public void setX(List<Double> x) {
        this.x = x;
    }

    public List<Double> getY() {
        return y;
    }

    public void setY(List<Double> y) {
        this.y = y;
    }
}
