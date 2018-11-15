package vue;

import controleur.ActionEffectuerCycle;
import modele.Agent;
import modele.Case;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    private Case[][] lesCases;
    private Agent agent;
    private JPanel centre;

    public Fenetre(Case[][] lesCases, Agent agent) {
        super("Bienvenue dans la forête enchantée ...");

        this.setLayout(new BorderLayout());
        this.lesCases = lesCases;
        this.agent = agent;

        this.centre = new MapPanel(this.lesCases, this.agent);
        this.add(centre, BorderLayout.CENTER);

        JButton btn = new JButton("Effectuer un cycle");
        btn.addActionListener(new ActionEffectuerCycle(agent, this));
        this.add(btn, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.setVisible(true);
    }

    public void majPositionAgent() {
        this.remove(this.centre);
        this.centre = new MapPanel(this.lesCases, this.agent);
        this.add(this.centre, BorderLayout.CENTER);
        this.validate();
    }

}
