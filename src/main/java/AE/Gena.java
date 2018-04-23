package AE;


import java.util.List;

public class Gena {
    private Double valoare;
    private List<Double> maxDomeniu;

    public Gena(Double valoare, List<Double> maxDomeniu) {
        this.valoare = valoare;
        this.maxDomeniu = maxDomeniu;
    }

    public Gena modifica(double delta) {
        if (valoare+delta>maxDomeniu.get(0) && valoare+delta<maxDomeniu.get(1))
            return new Gena(valoare+delta,maxDomeniu);
        else
        if (valoare-delta>maxDomeniu.get(0) && valoare-delta<maxDomeniu.get(1))
            return new Gena(valoare-delta,maxDomeniu);
        else{
            return new Gena(valoare,maxDomeniu);
        }

    }

    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }
}
