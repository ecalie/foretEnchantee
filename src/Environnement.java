public class Environnement {

    private Carte map;
    private Agent agent;

    public Environnement(int taille) {
        this.genererMap(taille);
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Case getCase(int ligne, int colonne) {
        return this.map.getCase(ligne, colonne);
    }

    public void genererMap(int taille) {
        Case[][] cases = new Case[taille][taille];
        for (int ligne = 0; ligne < taille; ligne++) {
            for (int colonne = 0; colonne < taille; colonne++) {
                cases[ligne][colonne] = new Case(ligne, colonne);

                // générer obstacle
                double nbAleatoire = Math.random();
                if (nbAleatoire < 0.1)
                    cases[ligne][colonne].getObjet().add(Objet.Crevasse);
                else if (nbAleatoire < 0.2)
                    cases[ligne][colonne].getObjet().add(Objet.Monstre);
            }
        }

        this.map = new Carte(cases);

        // générer vent et odeur
        for (int ligne = 0; ligne < taille; ligne++)
            for (int colonne = 0; colonne < taille; colonne++)
                if (cases[ligne][colonne].getObjet().contains(Objet.Crevasse))
                    for (Case c : map.voisines(cases[ligne][colonne]))
                        c.getObjet().add(Objet.Vent);
                else if (cases[ligne][colonne].getObjet().contains(Objet.Monstre))
                    for (Case c : map.voisines(cases[ligne][colonne]))
                        c.getObjet().add(Objet.Odeur);
    }

    public void sortirAgent() {
        // Calculer le score

        // Générer une map plus grande

        // Placer l'agent sur la map

        // Réinitialiser les croyances de l'agent
    }

    public void deplacerAgent(Direction direction) {
        // Mettre à jour position agent
    }

    public void tirer(Direction direction) {
        // Modifier l'état de la case concernée

        // Modifier score agent
    }

}
