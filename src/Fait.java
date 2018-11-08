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
    private Objet objet;
}
