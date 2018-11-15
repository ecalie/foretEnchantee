package vue;

import controleur.ActionEffectuerCycle;
import modele.Agent;
import modele.Case;
import modele.Objet;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    private Case[][] lesCases;
    private Agent agent;

    public Fenetre(Case[][] lesCases, Agent agent) {
        super("Bienvenue dans la forête enchantée ...");

        this.setLayout(new BorderLayout());
        this.lesCases = lesCases;
        this.agent = agent;

        this.add(new MapPanel(this.lesCases, this.agent), BorderLayout.CENTER);

        JButton btn = new JButton("Effectuer un cycle");
        btn.addActionListener(new ActionEffectuerCycle(agent, this));
        this.add(btn, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.setVisible(true);
    }

    public void majPositionAgent() {
        this.add(new MapPanel(this.lesCases, this.agent), BorderLayout.CENTER);

        this.validate();
    }

}
