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

    public Action choixAction() {
        // Trouver une action sans risque
        //      - chercher une case non visitée sans risque

        //      - chercher un chemin

        // Sinon tirer une roche

        // Sinon prendre un risque dans une crevasse

        return null;
    }

    public void observer() {
        List<Objet> objets = this.capteur.getObjetCase(this.position);
        for (Objet o : objets)
            this.croyances.add(new Fait(this.position, null, false, o.getTypeFait()));
    }

    public void bouger() {
            // Appeler le capteur -> ajouter faits
            this.observer();

            // Execéute les règles applicables et mise à jour des croyances
            this.moteur.appliquerRegles();

            // Choix d'une action
            Action a = this.choixAction();

            // Excéution de l'action
            effecteur.executerAction(a);
    }

    public void ajouterRegles(Carte map) {
        this.moteur.genererReglesNouvelleCarte(map);
    }
}
