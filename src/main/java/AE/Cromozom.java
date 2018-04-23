package AE;

import java.util.ArrayList;
import java.util.List;

public class Cromozom {
    //private final Double minimGlobal;
    private List<Double> domeniuValori;
    private List<Gena> listaGene;
    private Integer nrGene;
    private Integer Yref;
    private Double probabilitateMutatie;


    /**
     * M numarul de gene din cromozom
     *
     * @param m
     * @param yref
     */
    Cromozom(Integer m, Double probabilitateMutatie, List<Double>
            domeniuValori, Integer yref) {
        listaGene = new ArrayList<>(m);
        nrGene = m;
        this.probabilitateMutatie = probabilitateMutatie;
        //this.minimGlobal = minimGlobal;
        this.Yref=yref;
        this.domeniuValori = domeniuValori;
    }

    public List<Gena> getListaGene() {
        return listaGene;
    }

    /***
     * Resultaul intre (0,1)
     * @return
     */
    public Double fitness() {

        return UtilsAE.LSE(this,Yref);
    }

    public void genereaza(List<Double> r) {
        for (int i = 0; i < nrGene; i++) {
            Gena gena = new Gena(r.get(i) , domeniuValori);
            listaGene.add(gena);
        }
    }

    public List<Cromozom> recombinare(Cromozom other) {
        List<Cromozom> copii = new ArrayList<>(2);
        Cromozom copil1 = new Cromozom(nrGene, probabilitateMutatie, domeniuValori, Yref);
        Cromozom copil2 = new Cromozom(nrGene, probabilitateMutatie,  domeniuValori, Yref);
        if (nrGene==1) {
            List<Double> genaParinte=new ArrayList<>();
            genaParinte.add(other.listaGene.get(0).getValoare());
            copil2.genereaza(genaParinte);
            genaParinte.clear();
            genaParinte.add(this.listaGene.get(0).getValoare());
            copil1.genereaza(genaParinte);
        }
        else{
            List<Double> genaParinte=new ArrayList<>();
            for(int i=0;i<nrGene;i++) {
                if (i%2==0)
                    genaParinte.add(other.getListaGene().get(i).getValoare());
                else
                    genaParinte.add(this.getListaGene().get(i).getValoare());
            }
            copil1.genereaza(genaParinte);
            genaParinte.clear();
            for(int i=0;i<nrGene;i++) {
                if (i%2==0)
                    genaParinte.add(this.getListaGene().get(i).getValoare());
                else
                    genaParinte.add(other.getListaGene().get(i).getValoare());
            }
            copil2.genereaza(genaParinte);

        }
        copii.add(copil1);
        copii.add(copil2);
        return copii;
    }

    public void mutatie() {
        for (int i = 0; i < nrGene; i++) {
            Double random = UtilsAE.getRandomBetween0and1();
            if (random < probabilitateMutatie) {
                double delta = UtilsAE.getIntegerBetweenMinAndMax(domeniuValori.get(0),domeniuValori.get(1));
                Gena nouaGena = listaGene.get(i).modifica(delta);
                listaGene.set(i, nouaGena);
            }
        }
    }


}
