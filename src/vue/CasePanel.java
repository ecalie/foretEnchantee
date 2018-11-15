package vue;

import javax.swing.*;
import java.awt.*;

public class CasePanel extends JPanel {

    private Img[][] composantes;

    public CasePanel(boolean agent, boolean crevasse, boolean monstre, boolean vent, boolean caca, boolean portail) {
        this.setLayout(new GridLayout(2,3));

        this.composantes = new Img[2][3];
        composantes[0][0] = new Img(new ImageIcon("images/herbe.png").getImage());
        composantes[0][1] = new Img(new ImageIcon("images/herbe.png").getImage());
        composantes[0][2] = new Img(new ImageIcon("images/herbe.png").getImage());
        composantes[1][0] = new Img(new ImageIcon("images/herbe.png").getImage());
        composantes[1][1] = new Img(new ImageIcon("images/herbe.png").getImage());
        composantes[1][2] = new Img(new ImageIcon("images/herbe.png").getImage());

        if (vent)
            composantes[0][0] = new Img(new ImageIcon("images/vent.png").getImage());

        if (crevasse)
            composantes[0][1] = new Img(new ImageIcon("images/crevasse.png").getImage());

        if (portail)
            composantes[0][2] = new Img(new ImageIcon("images/portail.png").getImage());

        if (monstre)
            composantes[1][0] = new Img(new ImageIcon("images/monstre.png").getImage());

        if (agent)
            composantes[1][1] = new Img(new ImageIcon("images/agent.png").getImage());

        if (caca)
            composantes[1][2] = new Img(new ImageIcon("images/caca.png").getImage());


        this.add(composantes[0][0]);
        this.add(composantes[0][1]);
        this.add(composantes[0][2]);
        this.add(composantes[1][0]);
        this.add(composantes[1][1]);
        this.add(composantes[1][2]);

    }

    public void setComposante(int l, int c, String image) {
        this.composantes[l][c].setIcon(new ImageIcon(image));
    }


}
