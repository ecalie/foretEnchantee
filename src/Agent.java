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

    public Agent(Case _positionInitiale, List<RegleDeduction> _regles, Capteur _capteur, Effecteur _effecteur) {
        this.position = _positionInitiale;
        this.capteur = _capteur;
        this.effecteur = _effecteur;
        this.intentions = new ArrayList<>();
        this.desir = Objet.Lumiere;
        this.croyances = new BaseFaits();
        this.moteur = new MoteurInference(this.croyances, _regles);
    }

    public Case getPosition() {
        return position;
    }

    public void setPosition(Case position) {
        this.position = position;
    }

    public void setCroyances(BaseFaits croyances) {
        this.croyances = croyances;
    }

    public Action choixAction() {
        // Trouver une action sans risque
        //      - chercher une case non visitée sans risque

        //      - chercher un chemin

        // Sinon tirer une roche

        // Sinon prendre un risque dans une crevasse

        return null;
    }

    public void demarrer() {
        while (true) {
            //Appeler le capteur -> ajouter faits

            // Execéute les règles applicables et mise à jour des croyances
            this.moteur.appliquerRegles();

            // Choix d'une action
            Action a = this.choixAction();

            // Excéution de l'action
            effecteur.executerAction(a);
        }
    }
}
