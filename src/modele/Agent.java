package modele;

import java.util.ArrayList;
import java.util.List;

public class Agent {

    private Case position;
    private List<Action> intentions;
    private Objet desir;
    private BaseFaits croyances;
    private Capteur capteur;
    private Effecteur effecteur;
    private MoteurInference moteur;
    private List<Case> memoire;

    public Agent(Case _positionInitiale, MoteurInference _moteur, Capteur _capteur, Effecteur _effecteur) {
        this.position = _positionInitiale;
        this.capteur = _capteur;
        this.effecteur = _effecteur;
        this.intentions = new ArrayList<>();
        this.desir = Objet.Lumiere;
        this.croyances = new BaseFaits();
        this.moteur = _moteur;
        this.moteur.setBaseDeFait(this.croyances);
        this.memoire = new ArrayList<>();
        this.memoire.add(this.position);
    }

    public MoteurInference getMoteur() {
        return moteur;
    }

    public Case getPosition() {
        return position;
    }

    public void setPosition(Case position) {
        this.position = position;
    }

    public BaseFaits getCroyances() {
        return croyances;
    }

    public void resetCroyances() {
        this.croyances = new BaseFaits();
    }

    public void majIntention() {
        for (Fait f : this.croyances) {
            if (f.getType() == TypeFait.Lumiere) {
                this.intentions.add(new Action(TypeAction.Sortir, null));
                return;
            }
        }
        for (Fait f : this.croyances) {
            if (f.getType() == TypeFait.SansDanger) {
                this.intentions = determinationDeplacements(f.getEmplacement());
                return;
            }
        }
        for (Fait f : this.croyances) {
            if (f.getType() == TypeFait.Monstre) {
                this.intentions = determinationDeplacements(f.getCause());
                this.intentions.add(new Action(TypeAction.Tirer, determinationDirection(f.getCause(), f.getEmplacement())));
                this.intentions.add(new Action(TypeAction.Deplacer, determinationDirection(f.getCause(), f.getEmplacement())));
                return;
            }
        }
        for (Fait f : this.croyances) {
            if (f.getType() == TypeFait.Crevasse && !f.isCertitude()) {
                this.intentions = determinationDeplacements(f.getEmplacement());
                return;
            }
        }
        System.out.print("Je suis mort");
    }

    public ArrayList<Action> determinationDeplacements(Case cible) {
        ArrayList<Action> _intention = new ArrayList<>();
        ArrayList<Case> chemin;

        this.memoire.add(cible);
        ArrayList<Case> cheminInitial = new ArrayList<>();
        cheminInitial.add(this.position);
        chemin = exploration(this.position, cible, cheminInitial);
        for (int i = 0; i < chemin.size() - 1; i++)
            _intention.add(new Action(TypeAction.Deplacer, determinationDirection(chemin.get(i), chemin.get(i + 1))));

        return _intention;
    }

    private ArrayList<Case> exploration(Case _position, Case _cible, ArrayList<Case> _chemin) {
        if (_position.getLigne() == _cible.getLigne() && _position.getColonne() == _cible.getColonne())
            return _chemin;
        else {
            for (int i = 0; i < this.memoire.size(); i++) {
                if (distance(_position, this.memoire.get(i)) == 1 && !_chemin.contains(new Case(this.memoire.get(i).getLigne(), this.memoire.get(i).getColonne()))) {
                    _chemin.add(this.memoire.get(i));
                    return exploration(this.memoire.get(i), _cible, _chemin);
                }
            }
        }
        return null;
    }

    private int distance(Case c1, Case c2) {
        return Math.abs(c1.getLigne() - c2.getLigne()) + Math.abs(c1.getColonne() - c2.getColonne());
    }

    private Direction determinationDirection(Case c1, Case c2) {
        if (c1.getLigne() - c2.getLigne() < 0)
            return Direction.Bas;
        else if (c1.getLigne() - c2.getLigne() > 0)
            return Direction.Haut;
        else if (c1.getColonne() - c2.getColonne() < 0)
            return Direction.Droite;
        else if (c1.getColonne() - c2.getColonne() > 0)
            return Direction.Gauche;
        return null;
    }

    public void observer() {
        List<Objet> objets = this.capteur.getObjetCase(this.position);
        for (Objet o : objets)
            this.croyances.add(new Fait(this.position, null, true, o.getTypeFait()));

        if (!objets.contains(Objet.Monstre) && !objets.contains(Objet.Crevasse))
            this.croyances.add(new Fait(this.position, null, true, TypeFait.Vide));

        this.croyances.add(new Fait(this.position, null, true, TypeFait.Exploree));

    }

    public void bouger() {
        if (this.intentions.isEmpty()) {
            // Appeler le capteur -> ajouter faits
            this.observer();

            // Execéute les règles applicables et mise à jour des croyances
            this.moteur.appliquerRegles();

            // Choix d'une action
            majIntention();

            // Excéution de l'action
            effecteur.executerAction(this.intentions.remove(0));
        } else {
            effecteur.executerAction(this.intentions.remove(0));
        }

        System.out.println(this.position);

    }
}
