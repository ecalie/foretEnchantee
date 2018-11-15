package modele;

import vue.Fenetre;

import java.util.ArrayList;

public class Environnement {

    private Carte map;
    private Agent agent;
    private Fenetre fenetre;

    public Environnement(int taille) {
        this.genererMap(taille);
    }

    ////////////////////////
    // GETTERS ET SETTERS //
    ////////////////////////

    public Agent getAgent() {
        return agent;
    }

    public void setFenetre(Fenetre fenetre) {
        this.fenetre = fenetre;
    }

    public Fenetre getFenetre() {
        return fenetre;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Case getCase(int ligne, int colonne) {
        return this.map.getCase(ligne, colonne);
    }

    public Carte getMap() {
        return map;
    }

    /**
     * Générer une carte d'une certaine taille.
     */
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

        // supprimer les éléments dangeureux de la case (0,0)
        cases[0][0].getObjet().clear();

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


        // Générer le portail
        boolean portail = false;
        while (!portail) {
            int ligne = (int) (Math.random() * taille);
            int colonne = (int) (Math.random() * taille);

            if (!cases[ligne][colonne].getObjet().contains(Objet.Crevasse) && !cases[ligne][colonne].getObjet().contains(Objet.Monstre)) {
                portail = true;
                cases[ligne][colonne].getObjet().add(Objet.Lumiere);
            }
        }
    }

    /**
     * Faire sortir l'agent de la carte et le placer dans la suivante.
     */
    public void sortirAgent() {
        // Calculer le score
        // TODO

        // Générer une map plus grande
        this.genererMap(this.map.getTaille() + 1);

        // Placer l'agent sur la map
        this.agent.setPosition(this.map.getCase(0, 0));

        // Réinitialiser les croyances et la mémoire de l'agent
        this.agent.resetCroyances();
        this.agent.resetMemoire();

        //Créer les règles des nouvelles cases
        this.agent.getMoteur().genererRegles(map);

        // Afficher la map
        this.fenetre.hide();
        this.fenetre = new Fenetre(this.map.getLesCases(), this.agent);
        this.fenetre.validate();
    }

    /**
     * Faire déplacer l'agent sur la carte.
     */
    public void deplacerAgent(Direction direction) {
        // Mettre à jour position agent
        this.agent.setPosition(this.voisine(this.agent.getPosition(), direction));
        if(agentEstMortCeSoir()) {
            this.fenetre.mourir();
            this.agent.ajoutFait(new Fait(this.agent.getPosition(), null, true, TypeFait.Crevasse));
            this.agent.getMemoire().remove(this.agent.getPosition());
            this.agent.setPosition(this.map.getCase(0, 0));
        }
    }

    /**
     * Vrai si l'agent est sur une crevasse (il ne peut pas rencontrer de monstre car tire toujours avant).
     */
    private boolean agentEstMortCeSoir () {
        if ( this.map.getCase(this.agent.getPosition().getLigne(), this.agent.getPosition().getColonne()).getObjet().contains(Objet.Crevasse))
            return true;
        else
            return false;
    }

    /**
     * Faire tirer l'agent sur une case voisine de sa position.
     */
    public void tirer(Direction direction) {
        // La case sur la quelle l'agent tire
        Case visee = this.voisine(this.agent.getPosition(), direction);

        // Modifier l'état de la case concernée
        if (visee.getObjet().contains(Objet.Monstre))
            visee.getObjet().remove(Objet.Monstre);

        // Modifier score agent
        // TODO
    }

    /**
     * Récupérer la case voisine d'une case selon une direction.
     */
    public Case voisine(Case laCase, Direction direction) {
        int l = laCase.getLigne();
        int c = laCase.getColonne();

        switch (direction) {
            case Haut:
                return this.getCase(l - 1, c);
            case Bas:
                return this.getCase(l + 1, c);
            case Gauche:
                return this.getCase(l, c - 1);
            case Droite:
                return this.getCase(l, c + 1);
            default:
                return null;
        }
    }

}
