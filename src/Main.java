import modele.*;
import vue.Fenetre;
//import vue.Fenetre;

public class Main {

    public static void main(String[] args) {
        // génrer environnement
        Environnement environnement = new Environnement(3);

        //initialiser les regles
        MoteurInference moteur = new MoteurInference(environnement.getMap());

        System.out.println("regles");
        // générer agent
        Agent agent = new Agent(environnement.getCase(0,0),
                moteur,
                new Capteur(environnement),
                new Effecteur(environnement));

        environnement.setAgent(agent);

        Fenetre f = new Fenetre(environnement.getMap().getLesCases(), agent);
        agent.demarrer();
    }
}