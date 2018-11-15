package vue;

import modele.Agent;
import modele.Case;
import modele.Objet;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {


    private JLabel[][] casesPanels;

    public MapPanel(Case[][] lesCases, Agent agent) {
        this.setLayout(new GridLayout(lesCases.length, lesCases[0].length));
        casesPanels = new JLabel[lesCases.length][lesCases[0].length];
        for (int l = 0; l < lesCases.length; l++) {
            for (int c = 0; c < lesCases[0].length; c++) {
                casesPanels[l][c] = new JLabel();
                casesPanels[l][c].setBorder(BorderFactory.createLineBorder(Color.BLACK));


                String nomImage = "images/";

                if (lesCases[l][c].getObjet().contains(Objet.Vent))
                    nomImage += "nuage";
                if (lesCases[l][c].getObjet().contains(Objet.Monstre))
                    nomImage += "monstre";
                if (lesCases[l][c].getObjet().contains(Objet.Odeur))
                    nomImage += "caca";
                if (lesCases[l][c].getObjet().contains(Objet.Crevasse))
                    nomImage += "crevasse";
                if (lesCases[l][c].getObjet().contains(Objet.Lumiere))
                    nomImage += "portail";
                if (agent.getPosition() == lesCases[l][c])
                    nomImage += "agent";

                if (nomImage.equals("images/"))
                    nomImage = "images/herbe.png";
                else
                    nomImage += ".png";

                casesPanels[l][c] = new Img(new ImageIcon(nomImage).getImage());
                this.add(casesPanels[l][c]);

            }
        }
    }
}
