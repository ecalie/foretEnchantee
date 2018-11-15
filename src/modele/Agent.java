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
        this.memoire = new ArrayList<>();
    }

    public Case getPosition() {
        return position;
    }

    public void setPosition(Case position) {
        this.position = position;
    }

    public void resetCroyances() {
        this.croyances = new BaseFaits();
    }

    public void majIntention() {
        for (Fait f : this.croyances) {
            if (f.getType() == TypeFait.SansDanger) {
                this.intentions = determinationIntentions(f.getEmplacement());
            }
        }
    }

    public ArrayList<Action> determinationIntentions( Case cible ) {
        ArrayList<Action> _intention = new ArrayList<>();
        ArrayList<Case> chemin;
        chemin=exploration(this.position, cible, new ArrayList<>());
        for (int i=0; i < chemin.size()-1; i++)
            _intention.add(determinationAction(chemin.get(i), chemin.get(i+1)));
        return _intention;
    }

    private ArrayList<Case> exploration (Case _position, Case _cible, ArrayList<Case> _chemin) {
        if (_position.getLigne() == _cible.getLigne() && _position.getColonne() == _cible.getColonne())
            return _chemin;
        else {
            for (int i=0; i < this.memoire.size(); i++) {
                if (distance (_position, this.memoire.get(i)) == 1 && !_chemin.contains(new Case(this.memoire.get(i).getLigne(), this.memoire.get(i).getColonne()))) {
                    _chemin.add(this.memoire.get(i));
                    exploration(this.memoire.get(i), _cible, _chemin);
                }
            }
        }
        return null;
    }

    private int distance (Case c1, Case c2) {
        return Math.abs(c1.getLigne() - c2.getLigne()) + Math.abs(c1.getColonne() - c2.getColonne());
    }

    private Action determinationAction(Case c1, Case c2) {
        if (c1.getLigne() - c2.getLigne() > 0)
            return new Action(Type.Deplacer, Direction.Bas);
        else if (c1.getLigne() - c2.getLigne() < 0)
            return new Action(Type.Deplacer, Direction.Haut);
        else if (c1.getColonne() - c2.getColonne() > 0)
            return new Action(Type.Deplacer, Direction.Droite);
        else if (c1.getColonne() - c2.getColonne() < 0)
            return new Action(Type.Deplacer, Direction.Gauche);
        return null;
    }

    public void observer() {
        List<Objet> objets = this.capteur.getObjetCase(this.position);
        for (Objet o : objets)
            this.croyances.add(new Fait(this.position, null, false, true, o.getTypeFait()));
    }

    public void demarrer() {
        while (true) {
            // Appeler le capteur -> ajouter faits
            this.observer();

            // Execéute les règles applicables et mise à jour des croyances
            this.moteur.appliquerRegles();

            // Choix d'une action
            majIntention();

            // Excéution de l'action
            while(!this.intentions.isEmpty())
                effecteur.executerAction(this.intentions.get(0));
        }
    }

    public void ajouterRegles(Carte map) {
        this.moteur.genererReglesNouvelleCarte(map);
    }
}
