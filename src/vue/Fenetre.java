package vue;

import controleur.ActionEffectuerCycle;
import modele.Agent;
import modele.Case;
import modele.Environnement;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    private Case[][] lesCases;
    private Agent agent;
    private JPanel centre;
    private JLabel nord1;
    private JLabel nord2;

    public Fenetre(Case[][] lesCases, Agent agent) {
        super("Bienvenue dans la forête enchantée ...");

        this.setLayout(new BorderLayout());
        this.lesCases = lesCases;
        this.agent = agent;

        this.centre = new MapPanel(this.lesCases, this.agent);
        this.add(centre, BorderLayout.CENTER);

        this.nord1 = new JLabel();
        this.nord2 = new JLabel();
        JPanel nord = new JPanel(new BorderLayout());
        nord.add(nord1, BorderLayout.EAST);
        nord.add(nord2, BorderLayout.WEST);
        this.add(nord, BorderLayout.NORTH);

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

    public void mourir() {
        this.nord1.setText("Je suis mort");
    }

    public void majIntention(String intention, Environnement env) {
        this.nord1.setText(intention);
        this.nord2.setText("score : " + env.getScoreAgent());
    }


}
