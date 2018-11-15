package modele;

import java.util.ArrayList;
import java.util.List;

public class MoteurInference {

    private BaseFaits baseDeFait;
    private List<RegleDeduction> regles;

    public MoteurInference(Carte map, BaseFaits base) {
        this.baseDeFait = base;
        this.regles = new ArrayList<>();
        genererRegles(map);
    }

    public BaseFaits getBaseDeFait() {
        return baseDeFait;
    }

    public void setBaseDeFait(BaseFaits baseDeFait) {
        this.baseDeFait = baseDeFait;
    }

    public void genererRegles(Carte map){
        //      - Si une case est vide, alors les cases voisines non ni crevasse ni monstre
        for (int ligne = 0 ; ligne < 3 ; ligne++) {
            for (int colonne = 0 ; colonne < 3 ; colonne++) {
                Case emplacement = map.getCase(ligne, colonne);
                Fait fait = new Fait(emplacement, null,  true,TypeFait.Vide);
                List<Fait> faits = new ArrayList<>();
                faits.add(fait);
                List<Operation> operations = new ArrayList<>();
                for (Case c : map.voisines(emplacement)) {
                    Fait f = new Fait(c, emplacement,  true, TypeFait.SansDanger);
                    operations.add(new Operation(f, true));
                }
                RegleDeduction regle = new RegleDeduction(faits, operations);
                this.regles.add(regle);
            }
        }

        //      - Si une case contient du vent, il peut y a voir une crevasse dans les cases voisines
        for (int ligne = 0 ; ligne < 3 ; ligne++) {
            for (int colonne = 0 ; colonne < 3 ; colonne++) {
                Case emplacement = map.getCase(ligne, colonne);
                Fait fait = new Fait(emplacement, null,  true, TypeFait.Vent);
                List<Fait> faits = new ArrayList<>();
                faits.add(fait);
                List<Operation> operations = new ArrayList<>();
                for (Case c : map.voisines(emplacement)) {
                    Fait f = new Fait(c, emplacement,  false, TypeFait.Crevasse);
                    operations.add(new Operation(f, true));
                }
                RegleDeduction regle = new RegleDeduction(faits, operations);
                this.regles.add(regle);
            }
        }

        //      - Si une case contient du caca, il peut y a voir un monstre dans les cases voisines
        for (int ligne = 0 ; ligne < 3 ; ligne++) {
            for (int colonne = 0; colonne < 3; colonne++) {
                Case emplacement = map.getCase(ligne, colonne);
                Fait fait = new Fait(emplacement, null,  true, TypeFait.Odeur);
                List<Fait> faits = new ArrayList<>();
                faits.add(fait);
                List<Operation> operations = new ArrayList<>();
                for (Case c : map.voisines(emplacement)) {
                    Fait f = new Fait(c, emplacement,  false, TypeFait.Monstre);
                    operations.add(new Operation(f, true));
                }
                RegleDeduction regle = new RegleDeduction(faits, operations);
                this.regles.add(regle);
            }
        }

        // Une case explorée n'est pas sans danger
        for (int ligne = 0 ; ligne < 3 ; ligne++) {
            for (int colonne = 0; colonne < 3; colonne++) {
                Case emplacement = map.getCase(ligne, colonne);
                Fait fait = new Fait(emplacement, null,  true, TypeFait.Exploree);
                List<Fait> faits = new ArrayList<>();
                faits.add(fait);
                List<Operation> operations = new ArrayList<>();
                    Fait f = new Fait(emplacement, null,  true, TypeFait.SansDanger);
                    operations.add(new Operation(f, false));
                RegleDeduction regle = new RegleDeduction(faits, operations);
                this.regles.add(regle);
            }
        }
    }

    public void genererReglesNouvelleCarte(Carte map){
        int taille = map.getTaille();
        for (int t=0; t<taille; t++){
            Case laCase = map.getCase(t,taille-1);
            genererReglesSurCase(laCase, map.voisines(laCase));
            if(t!=taille-1)
                laCase = map.getCase(taille-1,t);
                genererReglesSurCase(laCase, map.voisines(laCase));
        }
    }

    public void genererReglesSurCase(Case laCase, List<Case> voisines){
        List<Fait> declencheurs = new ArrayList<>();
        List<Operation> corps = new ArrayList<>();

        for (TypeFait typeDeclencheur : TypeFait.values() ){

            //Identification du fait déclencheur
            Fait faitDeclencheur = new Fait(
                    laCase,
                    laCase,
                    true,
                    typeDeclencheur
                    );

            declencheurs.add(faitDeclencheur);

            //Identification des faits causés par le déclencheur
            TypeFait typeOperation;
            boolean certitude;

            switch(typeDeclencheur){
                case Odeur:
                    typeOperation = TypeFait.Monstre; //si j'ai une odeur, j'ai potentiellement des monstres
                    certitude = false;
                    break;
                case Vent:
                    typeOperation = TypeFait.Crevasse; //si j'ai du vent j'ai potentiellement des crevasses
                    certitude = false;
                    break;
                case Vide:
                default:
                    typeOperation = TypeFait.SansDanger; //si je n'ai rien, je n'ai rien à côté
                    certitude = true;
                    break;
            }

            //On crée les faits causés pour chaque case voisine
            for (Case voisine : voisines) {

                Fait faitOperation = new Fait(
                        voisine,
                        laCase,
                        certitude,
                        typeOperation
                        );

                Operation operation = new Operation(
                        faitOperation,
                        true
                );

                corps.add(operation);
                RegleDeduction regle = new RegleDeduction(declencheurs, corps);
                this.regles.add(regle);
            }
        }

    }

    public void appliquerRegles() {
        for (RegleDeduction r : regles)
            if (!r.isMarquee() && r.estApplicable(this.baseDeFait)) {
                r.marquer();
                for (Operation o : r.getCorps())
                    baseDeFait.ajouter(o);
            }
    }

}
