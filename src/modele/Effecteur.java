package modele;

public class Effecteur {

    private Environnement environnement;

    public Effecteur(Environnement _environnement) {
        this.environnement = _environnement;
    }

    public void executerAction(Action action) {
        this.environnement.getFenetre().majIntention(this.environnement.getAgent().getBut(), this.environnement);
        switch (action.getType()) {
            case Sortir:
                environnement.sortirAgent();
                break;
            case Tirer:
                environnement.tirer(action.getDirection());
                break;
            case Deplacer:
                environnement.deplacerAgent(action.getDirection());
                break;
        }
    }
}
