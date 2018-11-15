package modele;

public class Fait {

    private Case emplacement;
    private Case cause;

    /**
     * Vrai la présence / absence est une certitude, faux si c'est une hypothèse.
     */
    private boolean certitude;

    private TypeFait type;

    public Fait(Case _emplacement, Case _cause, boolean _certitude, TypeFait _type) {
        this.emplacement = _emplacement;
        this.cause = _cause;
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

    public boolean isCertitude() {
        return certitude;
    }

    public TypeFait getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        Fait f2 = (Fait) obj;
        return (f2.emplacement.equals(this.emplacement) && f2.certitude == this.certitude && f2.type == this.type);
    }

    @Override
    public String toString() {
        return (this.certitude?"" : "!") + this.emplacement + " " + this.type;
    }


}
