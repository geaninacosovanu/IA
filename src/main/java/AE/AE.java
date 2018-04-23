package AE;


import java.util.ArrayList;
import java.util.List;

public class AE {
    private Populatie populatie;
    private Integer nrIndivizi;
    private Integer nrGene;
    private Integer nrIteratii;
    private Double pragFitness;
    private Double probabilitateMutatie;
    private Integer generatieCurenta;
    private Integer Yref;
    private List<Double> dimensiuneDomeniu;



    public AE(Integer nrIndivizi, Integer nrGene, Integer nrIteratii,
              Double
                      pragFitness,
              Double
                      probabilitateMutatie, List<Double> dimensiuneDomeniu,Integer nr) {

        this.nrIndivizi = nrIndivizi;
        this.nrGene = nrGene;
        this.nrIteratii = nrIteratii;
        this.pragFitness = pragFitness;
        this.probabilitateMutatie = probabilitateMutatie;
        this.Yref=nr;
        this.dimensiuneDomeniu = dimensiuneDomeniu;

    }


    private boolean valid() {
        return populatie.getBest().fitness() > this.pragFitness && generatieCurenta < nrIteratii;
    }

    public Cromozom run() {
        populatie = new Populatie(nrIndivizi, nrGene, probabilitateMutatie, dimensiuneDomeniu,Yref);
        populatie.initializeaza();

        this.generatieCurenta = 0;
        while (valid()) {
            populatie = populatie.evolueaza();
            generatieCurenta++;
        }
        return populatie.getBest();
    }
}
