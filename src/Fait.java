public class Fait {

    private Case emplacement;
    private Case cause;

    /**
     * Vrai si le fait est la présence l'objet, faux si le fait est l'absence de l'objet.
     */
    private boolean present;

    /**
     * Vrai la présence / absence est une certitude, faux si c'est une hypothèse.
     */
    private boolean certitude;

    /**
     * Monstre  ou crevasse ou lumière.
     */
    private TypeFait type;

    public Fait(Case emplacement, Case cause, boolean present, boolean certitude, TypeFait type) {
        this.emplacement = emplacement;
        this.cause = cause;
        this.present = present;
        this.certitude = certitude;
        this.type = type;
    }


    /////////////
    // GETTERS //
    /////////////

    public Case getEmplacement() {
        return emplacement;
    }

    public Case getCause() {
        return cause;
    }

    public boolean isPresent() {
        return present;
    }

    public boolean isCertitude() {
        return certitude;
    }

    public TypeFait getType() {
        return type;
    }
}
