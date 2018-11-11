public class Fait {

    private Case emplacement;
    private Case cause;

    /**
     * Vrai si le fait est l'absence de l'objet, faux si le fait est la présence de l'objet.
     */
    private boolean negation;

    /**
     * Vrai la présence / absence est une certitude, faux si c'est une hypothèse.
     */
    private boolean certitude;

    /**
     * Monstre  ou crevasse.
     */
    private Objet objet;

    /////////////
    // GETTERS //
    /////////////

    public Case getEmplacement() {
        return emplacement;
    }

    public Case getCause() {
        return cause;
    }

    public boolean isNegation() {
        return negation;
    }

    public boolean isCertitude() {
        return certitude;
    }

    public Objet getObjet() {
        return objet;
    }
}
