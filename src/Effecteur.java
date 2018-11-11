public class Effecteur {

    private Environnement environnement;

    public Effecteur(Environnement _environnement) {
        this.environnement = _environnement;
    }

    public void executerAction(Action action) {
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
