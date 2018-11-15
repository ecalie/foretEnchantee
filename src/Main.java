import modele.*;
import vue.Fenetre;

public class Main {

    public static void main(String[] args) {
        // générer environnement
        Environnement environnement = new Environnement(3);

        //initialiser les regles
        MoteurInference moteur = new MoteurInference(environnement.getMap(), null);

        // générer agent
        Agent agent = new Agent(environnement.getCase(0,0),
                moteur,
                new Capteur(environnement),
                new Effecteur(environnement));

        environnement.setAgent(agent);

        Fenetre fenetre = new Fenetre(environnement.getMap().getLesCases(), agent);
       environnement.setFenetre(fenetre);
    }
}