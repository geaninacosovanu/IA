package RNA;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    private List<Double> weights;
    private Double delta;
    private Double output;

    public Neuron(List<Double> weights) {
        this.weights=weights;
        delta=0d;
        output=0d;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public void setWeight(Double weight,Integer poz) {
        this.weights.set(poz,weight);
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }

    public Double getOutput() {
        return output;
    }

    public void setOutput(Double output) {
        this.output = output;
    }
}
