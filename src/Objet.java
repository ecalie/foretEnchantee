public enum Objet {
    Crevasse,
    Monstre,
    Portail,
    Odeur,
    Lumiere,
    Vent;

    public TypeFait getTypeFait() {
        switch (this) {
            case Crevasse:
                return TypeFait.Crevasse;
            case Monstre:
                return TypeFait.Monstre;
            case Odeur:
                return TypeFait.Odeur;
            case Lumiere:
                return TypeFait.Lumiere;
            case Vent:
                return TypeFait.Vent;
        }
        return null;
    }
}
