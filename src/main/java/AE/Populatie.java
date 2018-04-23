package AE;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Populatie {
    private List<Cromozom> membri;
    private Integer nrIndivizi;
    private Integer nrGene;
    private Integer Yref;
    private Double probabilitateMutatie;

    private List<Double> domeniu;

    Populatie(Integer n, Integer m, Double probabilitateMutatie, List<Double> domeniu, Integer yref) {
        nrIndivizi = n;
        nrGene = m;
        this.probabilitateMutatie = probabilitateMutatie;
        this.Yref=yref;
        this.domeniu = domeniu;

    }

    public void initializeaza() {
        membri = new ArrayList<>(nrIndivizi);
        for (int i = 0; i < nrIndivizi; i++) {
            Cromozom cromozom = new Cromozom(nrGene, probabilitateMutatie, domeniu,Yref);
            List<Double> listGenerare = new ArrayList<>();
            if (nrGene.equals(1)) {
                listGenerare.add(UtilsAE.getIntegerBetweenMinAndMax(domeniu.get(0), domeniu.get(1)));
                cromozom.genereaza(listGenerare);
            } else {
                for (int j = 0; j < nrGene; j++) {
                    listGenerare.add(UtilsAE.getIntegerBetweenMinAndMax(domeniu.get(0), domeniu.get(1)));
                }
                cromozom.genereaza(listGenerare);
            }
            membri.add(cromozom);
        }
    }

    public Cromozom getBest() {
        Double min = membri.get(0).fitness();
        Integer pozitia = 0;
        for (int i = 1; i < membri.size(); i++) {
            if (membri.get(i).fitness() < min) {
                min = membri.get(i).fitness();
                pozitia = i;
            }
        }
        return membri.get(pozitia);
    }

    private void recombinare() {
        for (int i = 0; i < nrIndivizi / 2; i++) {
            Cromozom p1 = this.selecteazaParinte();
            Cromozom p2 = this.selecteazaParinte();
            List<Cromozom> copii = p1.recombinare(p2);
            membri.add(copii.get(0));
            membri.add(copii.get(1));
        }
    }

    private Cromozom selecteazaParinte() {
        Integer random = UtilsAE.getRandomInteger();
        return membri.get(random % membri.size());
    }

    private void mutatie() {
        for (Cromozom membru : membri) {
            Double prob = UtilsAE.getRandomBetween0and1();
            if (prob < probabilitateMutatie) {
                //are loc mutatia pentru individul i
                membru.mutatie();
            }
        }
    }

    private void selectie() {
        List<Cromozom> sorted = membri.stream().sorted((Comparator.comparingDouble(Cromozom::fitness))).collect
                (Collectors.toList());
        List<Cromozom> rez = new ArrayList<>();
        for (int i = 0; i < nrIndivizi; i++) {
            rez.add(sorted.get(i));
        }
        membri = rez;
    }

    public Populatie evolueaza() {
        this.recombinare();
        this.mutatie();
        this.selectie();
        return this;
    }
}
