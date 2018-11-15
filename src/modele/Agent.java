package modele;

import java.util.ArrayList;
import java.util.List;

public class Agent {

    private Case position;
    private List<Action> intentions;
    private String but;
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

    public List<Case> getMemoire() {
        return memoire;
    }

    public void resetCroyances() {
        this.croyances.clear();
    }

    public String getBut() {
        return but;
    }

    public void majIntention() {
        for (Fait f : this.croyances) {
            if (f.getType() == TypeFait.Lumiere) {
                this.intentions.add(new Action(TypeAction.Sortir, null));
                this.but = "Sortir";
                return;
            }
        }
        for (Fait f : this.croyances) {
            // Si la case est sans danger et que l'agent ne l'a pas déjà explorée
            if (f.getType() == TypeFait.SansDanger && !this.memoire.contains(f.getEmplacement())) {
                this.intentions = determinationDeplacements(f.getEmplacement());
                this.but = "se déplacer en "+ f.getEmplacement();
                return;
            }
        }
        for (Fait f : this.croyances) {
            if (f.getType() == TypeFait.Monstre) {
                this.intentions = determinationDeplacements(f.getCause());
                this.intentions.add(new Action(TypeAction.Tirer, determinationDirection(f.getCause(), f.getEmplacement())));
                this.croyances.remove(new Fait(f.getEmplacement(), null, false,TypeFait.Monstre));
                this.croyances.remove(new Fait(f.getEmplacement(), null, true,TypeFait.Monstre));
                this.intentions.add(new Action(TypeAction.Deplacer, determinationDirection(f.getCause(), f.getEmplacement())));
                this.but = "tirer et se déplacer en " + f.getEmplacement();
                if (!memoire.contains( f.getEmplacement()))
                    this.memoire.add( f.getEmplacement());
                return;
            }
        }
        for (Fait f : this.croyances) {
            if (f.getType() == TypeFait.Crevasse && !f.isCertitude()) {
                this.intentions = determinationDeplacements(f.getEmplacement());
                this.but = "se déplacer en " + f.getEmplacement();
                return;
            }
        }
        System.out.print("Je suis mort");
    }

    public ArrayList<Action> determinationDeplacements(Case cible) {
        ArrayList<Action> _intention = new ArrayList<>();
        ArrayList<Case> chemin;
        if (!memoire.contains(cible))
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
            for (Case c : memoire) {
                if (distance(_position, c) == 1 && !_chemin.contains(c)) {
                    _chemin.add(c);
                    ArrayList<Case> res = exploration(c, _cible, _chemin);
                    if (res == null)
                        _chemin.remove(c);
                    else
                        return res;
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

        if (objets.size() == 0)
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
    }

    public void ajoutFait(Fait fait) {
        this.croyances.add(fait);
    }

    public void resetMemoire() {
        this.memoire.clear();
        this.memoire.add(this.position);
    }
}
