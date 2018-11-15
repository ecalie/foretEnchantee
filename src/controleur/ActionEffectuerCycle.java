package controleur;

import modele.Agent;
import modele.Case;
import vue.Fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionEffectuerCycle implements ActionListener {

    private Agent agent;
    private Fenetre fenetre;

    public ActionEffectuerCycle(Agent agent, Fenetre fenetre) {
        this.agent = agent;
        this.fenetre = fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //Case avant = this.agent.getPosition();
        this.agent.bouger();

        //Case apres = this.agent.getPosition();
        this.fenetre.majPositionAgent();
    }
}
