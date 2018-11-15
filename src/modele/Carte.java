package modele;

import java.util.ArrayList;
import java.util.List;

public class Carte {

    private Case[][] lesCases;

    public Carte(Case[][] _cases) {
        this.lesCases = _cases;
    }

    public int getTaille() {
        return this.lesCases.length;
    }

    public Case[][] getLesCases() {
        return lesCases;
    }

    public List<Case> voisines(Case laCase) {
        List<Case> res = new ArrayList<>();
        int l = laCase.getLigne();
        int c = laCase.getColonne();

        if (l != 0)
            res.add(lesCases[l - 1][c]);
        if (c != 0)
            res.add(lesCases[l][c - 1]);
        if (l != lesCases.length - 1)
            res.add(lesCases[l + 1][c]);
        if (c != lesCases[0].length - 1)
            res.add(lesCases[l][c + 1]);

        return res;
    }

    public Case getCase(int ligne, int colonne) {
        return this.lesCases[ligne][colonne];
    }
}
