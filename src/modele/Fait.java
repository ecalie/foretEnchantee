package modele;

public class Fait {

    private Case emplacement;
    private Case cause;

    /**
     * Vrai si le fait est la présence l'objet, faux si le fait est l'absence de l'objet.
     */
    private boolean negation;

    /**
     * Vrai la présence / absence est une certitude, faux si c'est une hypothèse.
     */
    private boolean certitude;

    private TypeFait type;

    public Fait(Case _emplacement, Case _cause, boolean _negation, boolean _certitude, TypeFait _type) {
        this.emplacement = _emplacement;
        this.cause = _cause;
        this.negation = _negation;
        this.certitude = _certitude;
        this.type = _type;
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

    public boolean isNegation() {
        return negation;
    }

    public boolean isCertitude() {
        return certitude;
    }

    public TypeFait getType() {
        return type;
    }
}
