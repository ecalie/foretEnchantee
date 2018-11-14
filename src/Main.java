import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // génrer environnement
        Environnement environnement = new Environnement(3);

        //initialiser les regles
        //for chaque case

        // générer agent
        Agent agent = new Agent(environnement.getCase(0,0),
                new ArrayList<>(),
                new Capteur(environnement),
                new Effecteur(environnement));

        environnement.setAgent(agent);

        agent.demarrer();
    }
}