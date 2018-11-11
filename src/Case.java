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
}
