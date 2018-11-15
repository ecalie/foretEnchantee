package vue;

import modele.Agent;
import modele.Case;
import modele.Objet;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {


    private CasePanel[][] casesPanels;

    public MapPanel(Case[][] lesCases, Agent agent) {
        this.setLayout(new GridLayout(lesCases.length, lesCases[0].length));

        casesPanels = new CasePanel[lesCases.length][lesCases[0].length];
        for (int l = 0; l < lesCases.length; l++) {
            for (int c = 0; c < lesCases[0].length; c++) {
                casesPanels[l][c] = new CasePanel(
                        agent.getPosition() == lesCases[l][c],
                        lesCases[l][c].getObjet().contains(Objet.Crevasse),
                        lesCases[l][c].getObjet().contains(Objet.Monstre),
                        lesCases[l][c].getObjet().contains(Objet.Vent),
                        lesCases[l][c].getObjet().contains(Objet.Odeur),
                        lesCases[l][c].getObjet().contains(Objet.Lumiere));

                this.add(casesPanels[l][c]);
            }
        }
    }
}
