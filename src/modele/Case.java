package modele;

import java.util.*;

public class Case {

    private int ligne;
    private int colonne;
    private List<Objet> objet;

    public Case(int _ligne, int _colonne) {
        this.ligne = _ligne;
        this.colonne = _colonne;
        this.objet = new ArrayList<>();
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public List<Objet> getObjet() {
        return objet;
    }

    @Override
    public boolean equals(Object obj) {
        Case c2 = (Case) obj;
        return (c2.ligne == this.ligne && c2.colonne == this.colonne);
    }

    @Override
    public String toString() {
        return "(" + this.ligne + ", " + this.colonne + ")";
    }
}
