package modele;

import java.util.List;

public class Capteur {

    private Environnement environnement;

    public Capteur(Environnement _environnement) {
        this.environnement = _environnement;
    }

    public List<Objet> getObjetCase(Case _case) {
        return _case.getObjet();
    }
}
