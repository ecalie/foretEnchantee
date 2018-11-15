package modele;

import java.util.ArrayList;
import java.util.List;

public class MoteurInference {

    private BaseFaits baseDeFait;
    private List<RegleDeduction> regles;

    public MoteurInference(Carte map) {
        this.baseDeFait = new BaseFaits();
        this.regles = new ArrayList<>();
        genererRegles(map);
    }

    public BaseFaits getBaseDeFait() {
        return baseDeFait;
    }

    public void genererRegles(Carte map){
        for (Case[] ligne : map.getLesCases()) {
            for (Case laCase : ligne) {
                List<Case> voisines = map.voisines(laCase);
                genererReglesSurCase(laCase, voisines);
            }
        }
    }

    public void genererReglesNouvelleCarte(Carte map){
        int taille = map.getTaille();
        for (int t=0; t<taille; t++){
            Case laCase = map.getCase(t,taille);
            genererReglesSurCase(laCase, map.voisines(laCase));
            if(t!=taille-1)
                laCase = map.getCase(taille,t);
                genererReglesSurCase(laCase, map.voisines(laCase));
        }
    }

    public void genererReglesSurCase(Case laCase, List<Case> voisines){
        List<Fait> declencheurs = new ArrayList<Fait>();
        List<Operation> corps = new ArrayList<Operation>();

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
