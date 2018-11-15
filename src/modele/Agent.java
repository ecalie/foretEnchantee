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

    // Constructeur
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

    ////////////////////////
    // GETTERS ET SETTERS //
    ////////////////////////
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

    /**
     * Ajouter un fait dans la croyance.
     */
    public void ajoutFait(Fait fait) {
        this.croyances.add(fait);
    }

    /**
     * Réinitialiser la mémoire (lorsque l'agent à trouvé la sortie et qu'il change de carte).
     */
    public void resetMemoire() {
        this.memoire.clear();
        this.memoire.add(this.position);
    }

    /**
     * Mettre à jour les intentions de l'agent en fonction de la base de faits
     */
    public void majIntention() {
        // S'il peut sortir
        for (Fait f : this.croyances) {
            if (f.getType() == TypeFait.Lumiere) {
                this.intentions.add(new Action(TypeAction.Sortir, null));
                this.but = "Sortir";
                return;
            }
        }

        // Sinon il cherche une case sans risque
        for (Fait f : this.croyances) {
            // Si la case est sans danger et que l'agent ne l'a pas déjà explorée
            if (f.getType() == TypeFait.SansDanger && !this.memoire.contains(f.getEmplacement())) {
                this.intentions = determinationDeplacements(f.getEmplacement());
                this.but = "se déplacer en "+ f.getEmplacement();
                return;
            }
        }

        // Sinon il cherche un monstre pour lui tirer une pierre
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

        // Sinon il prend un risque dans une crevasse
        for (Fait f : this.croyances) {
            if (f.getType() == TypeFait.Crevasse && !f.isCertitude()) {
                this.intentions = determinationDeplacements(f.getEmplacement());
                this.but = "se déplacer en " + f.getEmplacement();
                return;
            }
        }
    }

    /**
     * Déterminer le chemin entre la postion de l'agent et une case cible.
     */
    public ArrayList<Action> determinationDeplacements(Case cible) {
        ArrayList<Action> _intention = new ArrayList<>();
        ArrayList<Case> chemin;
        // La case va devenir explorée, pour simplifier on l'ajoute directement
        if (!memoire.contains(cible))
            this.memoire.add(cible);
        // On cherche le chemin
        ArrayList<Case> cheminInitial = new ArrayList<>();
        cheminInitial.add(this.position);
        chemin = exploration(this.position, cible, cheminInitial);
        // On ajoute les étapes du chemin une par une
        for (int i = 0; i < chemin.size() - 1; i++)
            _intention.add(new Action(TypeAction.Deplacer, determinationDirection(chemin.get(i), chemin.get(i + 1))));
        return _intention;
    }

    /**
     * Explorer pour trouver le chemin entre deux cases sans passer deux fois par les mêmes cases.
     */
    private ArrayList<Case> exploration(Case _position, Case _cible, ArrayList<Case> _chemin) {
        // Si on a trouvé la case d'arrivée
        if (_position.getLigne() == _cible.getLigne() && _position.getColonne() == _cible.getColonne())
            return _chemin;
        else {
            // sinon on cherche une case voisine dans la mémoire (donc sans risque)
            for (Case c : memoire) {
                if (distance(_position, c) == 1 && !_chemin.contains(c)) {
                    _chemin.add(c);
                    // On explore depuis cette nouvelle case
                    ArrayList<Case> res = exploration(c, _cible, _chemin);

                    // si on a pas trouvé de chemin on retire le chemin et essaye une autre case voisine
                    if (res == null)
                        _chemin.remove(c);
                    else
                        return res;
                }
            }
        }
        return null;
    }

    /**
     * Calculer la distance entre deux cases.
     */
    private int distance(Case c1, Case c2) {
        return Math.abs(c1.getLigne() - c2.getLigne()) + Math.abs(c1.getColonne() - c2.getColonne());
    }

    /**
     * Déterminer la direction pour aller de la case de départ à la case d'arrivée.
     */
    private Direction determinationDirection(Case depart, Case arrivee) {
        if (depart.getLigne() == arrivee.getLigne() -1)
            return Direction.Bas;
        else if (depart.getLigne() == arrivee.getLigne() + 1)
            return Direction.Haut;
        else if (depart.getColonne() == arrivee.getColonne() - 1)
            return Direction.Droite;
        else if (depart.getColonne() == arrivee.getColonne() + 1)
            return Direction.Gauche;
        return null;
    }

    /**
     * Utiliser le capteurs pour observer l'environnement et mette à jour les croyances.
     */
    public void observer() {
        List<Objet> objets = this.capteur.getObjetCase(this.position);
        for (Objet o : objets)
            this.croyances.add(new Fait(this.position, null, true, o.getTypeFait()));

        if (objets.size() == 0)
            this.croyances.add(new Fait(this.position, null, true, TypeFait.Vide));

        this.croyances.add(new Fait(this.position, null, true, TypeFait.Exploree));

    }

    /**
     * Effectuer un déplacement ou un tir.
     */
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
}
