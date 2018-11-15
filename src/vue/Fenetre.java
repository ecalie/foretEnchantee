package vue;

import modele.Agent;
import modele.Case;
import modele.Objet;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    private JPanel[][] cases;
    private Agent agent;

    public Fenetre(Case[][] lesCases, Agent agent) {
        super("Bienvenue dans la forête enchantée ...");

        this.setLayout(new BorderLayout());

        cases = new JPanel[lesCases.length][lesCases[0].length];

        JPanel grille = new JPanel(new GridLayout(lesCases.length, lesCases[0].length));
        for (int l = 0; l < lesCases.length; l++) {
            for (int c = 0; c < lesCases[0].length; c++) {
                cases[l][c] = new JPanel();
                cases[l][c].setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

                cases[l][c] = new CasePanel(
                        agent.getPosition() == lesCases[l][c],
                        lesCases[l][c].getObjet().contains(Objet.Crevasse),
                        lesCases[l][c].getObjet().contains(Objet.Monstre),
                        lesCases[l][c].getObjet().contains(Objet.Vent),
                        lesCases[l][c].getObjet().contains(Objet.Odeur),
                        lesCases[l][c].getObjet().contains(Objet.Lumiere));

                grille.add(cases[l][c]);
            }
        }

        this.add(grille, BorderLayout.CENTER);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.setVisible(true);


    }

}
