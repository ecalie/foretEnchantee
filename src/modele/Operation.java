package modele;

public class Operation {

    private Fait fait;
    /**
     * Vrai si l'opération est d'ajouter le fait, faux si l'opération est de supprimer le fait.
     */
    private boolean ajouter;

    public Operation(Fait fait, boolean ajouter) {
        this.fait = fait;
        this.ajouter = ajouter;
    }

    public Fait getFait() {
        return fait;
    }

    public boolean isAjouter() {
        return ajouter;
    }
}
