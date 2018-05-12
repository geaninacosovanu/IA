package RNA;

import Utils.Diagnostic;

import java.util.ArrayList;
import java.util.List;

public class RNA {
    private Integer nrInputs;
    private Integer nrOutputs;
    private Integer nrHiddenNeurons;
    private List<ArrayList<Neuron>> layers;
    private Float learningRate;
    private Double[][] X;
    private Diagnostic[] Y;

    public RNA(Integer nrInputs, Integer nrOutputs, Integer nrHiddenNeurons, Float learningRate, Double[][] X, Diagnostic[] Y) {
        this.nrInputs = nrInputs;
        this.nrOutputs = nrOutputs;
        this.nrHiddenNeurons = nrHiddenNeurons;
        this.learningRate = learningRate;
        this.X = X;
        this.Y = Y;
    }

    public void init() {
        layers = new ArrayList<>();
        ArrayList<Neuron> hiddenLayer = new ArrayList();
        ArrayList<Neuron> outputLayer = new ArrayList();
        for (int i = 0; i < nrHiddenNeurons; i++) {
            List<Double> weights = new ArrayList<>();
            for (int j = 0; j <= nrInputs; j++)
                weights.add(Math.random());
            Neuron neuron = new Neuron(weights);
            hiddenLayer.add(neuron);
        }
        layers.add(hiddenLayer);
        for (int i = 0; i < nrOutputs; i++) {
            List<Double> weights = new ArrayList<>();
            for (int j = 0; j <= nrHiddenNeurons; j++)
                weights.add(Math.random());
            Neuron neuron = new Neuron(weights);
            outputLayer.add(neuron);
        }
        layers.add(outputLayer);
    }

    private Double transfer(Double value) {
        return 1.0 / (1.0 + Math.exp(-value));
    }


    //HERE!!
    private Double[] forwardPropagation(Double[] X) {
        List<Double> inputs = new ArrayList<>();
        for (int i = 0; i < X.length; i++)
            inputs.add(X[i]);
        for (ArrayList<Neuron> layer : layers) {
            List<Double> newX = new ArrayList<Double>();
            for (Neuron n : layer) {
                Double[] ret = new Double[inputs.size()];
                for (int i = 0; i < inputs.size(); i++)
                    ret[i] = inputs.get(i);
                Double activation = activate(ret, n.getWeights());
                n.setOutput(transfer(activation));
                newX.add(n.getOutput());
            }
            inputs = newX;
        }
        Double[] ret = new Double[inputs.size()];
        for (int i = 0; i < inputs.size(); i++)
            ret[i] = inputs.get(i);
        return ret;
    }


    private Double activate(Double[] X, List<Double> weights) {
        Double result = 0d;
        for (int i = 0; i < X.length; i++)
            result += X[i] * weights.get(i);
        result += weights.get(weights.size() - 1);
        return result;
    }

    private Double transferInverse(Double value) {
        return value * (1 - value);
    }

    private void backwardPropagation(Double[] expected) {
        for (int i = layers.size() - 1; i > 0; i--) {
            ArrayList<Neuron> crtLayer = layers.get(i);
            List<Double> errors = new ArrayList<>();
            if (i == layers.size() - 1)
                for (int j = 0; j < crtLayer.size(); j++) {
                    Neuron crtNeuron = crtLayer.get(j);

                    //GET ATTENTION HERE
                    errors.add(expected[j] - crtNeuron.getOutput());
                }
            else
                for (int j = 0; j < crtLayer.size(); j++) {
                    Double crtError = 0d;
                    ArrayList<Neuron> nextLayer = layers.get(i + 1);
                    for (Neuron n : nextLayer)
                        crtError += n.getWeights().get(j) * n.getDelta();
                }
            for (int j = 0; j < crtLayer.size(); j++)
                crtLayer.get(j).setDelta(errors.get(j) * transferInverse(crtLayer.get(j).getOutput()));

        }
    }

    private void updateWeights(Double[] example) {

        for (int i = 0; i < layers.size(); i++) {
            List<Double> inputs = new ArrayList<>();
            for (int k = 0; k < example.length; k++)
                inputs.add(example[k]);
            if (i > 0) {
                List<Double> newInputs = new ArrayList<>();
                for (Neuron n : layers.get(i - 1))
                    newInputs.add(n.getOutput());
                inputs = newInputs;
            }
            for (Neuron n : layers.get(i)) {
                for (int j = 0; j < inputs.size(); j++)
                    n.setWeight(n.getWeights().get(i) * learningRate * n.getDelta() * inputs.get(j), i);
                n.setWeight(n.getWeights().get(inputs.size() - 1) * learningRate * n.getDelta(), inputs.size() - 1);
            }
        }
    }

    public Diagnostic evaluate(Double[] example) {
        Double[] computedOutput;
        computedOutput = forwardPropagation(example);
        Double max = computedOutput[0];
        int m = 0;
        for (int k = 1; k < computedOutput.length; k++)
            if (computedOutput[k] > max) {
                max = computedOutput[k];
                m = k;
            }
        if (m == 0)
            return Diagnostic.DH;
        if (m == 1)
            return Diagnostic.SL;
        if (m == 2)
            return Diagnostic.NO;
        return null;
    }

    public void train(Integer n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < X.length; j++) {
                Double[] example = X[j];
                Double[] computedOutput = forwardPropagation(example);
                Double[] expected = new Double[nrOutputs];
                if (Y[j] == Diagnostic.DH) {
                    expected[0] = 1d;
                    expected[1] = 0d;
                    expected[2] = 0d;
                } else if (Y[j] == Diagnostic.SL) {
                    expected[0] = 0d;
                    expected[1] = 1d;
                    expected[2] = 0d;
                } else {
                    expected[0] = 0d;
                    expected[1] = 0d;
                    expected[2] = 1d;
                }
                Double[] computedLabels = new Double[nrOutputs];
                int m = 0;
                Double max = computedOutput[0];
                for (int k = 1; k < computedOutput.length; k++)
                    if (computedOutput[k] > max) {
                        max = computedOutput[k];
                        m = k;
                    }
                computedLabels[m] = 1d;
                computedOutput = computedLabels;
                backwardPropagation(expected);
                updateWeights(example);
            }
        }
    }
}
