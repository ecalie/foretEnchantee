package modele;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

        this.regles.clear();

        for (int ligne = 0 ; ligne < map.getTaille() ; ligne++) {
            for (int colonne = 0; colonne < map.getTaille(); colonne++) {

                Case emplacement = map.getCase(ligne, colonne);
                List<Case> voisines = map.voisines(emplacement);

                ////////////////////////////////////////////////////////////////////////
                // Règle  : SI odeur                                                  //
                //             ALORS potentiellement monstre sur les cases voisines   //
                ////////////////////////////////////////////////////////////////////////
                Fait fait = new Fait(emplacement, null, true, TypeFait.Odeur);
                List<Fait> faits = new ArrayList<>();
                faits.add(fait);
                List<Operation> operations = new ArrayList<>();
                for (Case c : voisines) {
                    Fait f = new Fait(c, emplacement, false, TypeFait.Monstre);
                    operations.add(new Operation(f, true));
                }
                RegleDeduction regle = new RegleDeduction(faits, operations);
                this.regles.add(regle);

                ////////////////////////////////////////////////////////////////////////
                // Règle  : SI vent                                                   //
                //             ALORS potentiellement crevasse sur les cases voisines  //
                ////////////////////////////////////////////////////////////////////////
                fait = new Fait(emplacement, null, true, TypeFait.Vent);
                faits = new ArrayList<>();
                faits.add(fait);
                operations = new ArrayList<>();
                for (Case c : voisines) {
                    Fait f = new Fait(c, emplacement, false, TypeFait.Crevasse);
                    operations.add(new Operation(f, true));
                }
                regle = new RegleDeduction(faits, operations);
                this.regles.add(regle);

                ////////////////////////////////////////////////////////////////////////
                // Règle  : SI vide                                                   //
                //             ALORS les cases voisines sans danger                   //
                ////////////////////////////////////////////////////////////////////////
                fait = new Fait(emplacement, null, true, TypeFait.Vide);
                faits = new ArrayList<>();
                faits.add(fait);
                operations = new ArrayList<>();
                for (Case c : voisines) {
                    Fait f = new Fait(c, emplacement, true, TypeFait.SansDanger);
                    operations.add(new Operation(f, true));
                }
                regle = new RegleDeduction(faits, operations);
                this.regles.add(regle);

                ///////////////////////////////////////////////////////////////////////////////
                // Règle  : SI sans (danger ou monstre ou crevasse potentiels) ET sansDanger //
                //             ALORS on supprime  (danger ou monstre ou crevasse potentiels) //
                ///////////////////////////////////////////////////////////////////////////////
                for (TypeFait type : Arrays.asList(TypeFait.Monstre, TypeFait.Crevasse, TypeFait.SansDanger)) {
                    faits = new ArrayList<>();
                    faits.add(new Fait(emplacement, null, true, TypeFait.Exploree));
                    faits.add(new Fait(emplacement, null, type == TypeFait.SansDanger?true:false, type));
                    operations = new ArrayList<>();
                    Fait f = new Fait(emplacement, null, type == TypeFait.SansDanger?true:false, type);
                    operations.add(new Operation(f, false));
                    regle = new RegleDeduction(faits, operations);
                    this.regles.add(regle);
                }

                ////////////////////////////////////////////////////////////////////////
                // Règle  : SI fait incertain ET fait certain                         //
                //             ALORS fait certain                                     //
                ////////////////////////////////////////////////////////////////////////
                for (TypeFait typeFaitIncertain : Arrays.asList(TypeFait.Crevasse, TypeFait.Monstre)) {
                    Fait faitIncertain = new Fait(emplacement, null, false, typeFaitIncertain);
                    for (TypeFait typeFaitCertain : Arrays.asList(TypeFait.Crevasse, TypeFait.Monstre, TypeFait.SansDanger, TypeFait.Vide)) {
                        Fait faitCertain = new Fait(emplacement, null, true, typeFaitCertain);
                        faits = new ArrayList<>();
                        faits.add(faitIncertain);
                        faits.add(faitCertain);
                        operations = new ArrayList<>();
                        operations.add(new Operation(faitIncertain, false));
                        regle = new RegleDeduction(faits, operations);
                        this.regles.add(regle);
                    }
                }
            }
        }
    }

    public void appliquerRegles() {
        // démarquer toutes les règles
        for (RegleDeduction r : this.regles)
            r.Demarquer();
        boolean onBoucle=true;
        while (onBoucle) {
            onBoucle = false;
            for (RegleDeduction r : regles) {
                if (!r.isMarquee() && r.estApplicable(this.baseDeFait)) {
                    r.marquer();
                    onBoucle = true;
                    for (Operation o : r.getCorps())
                            baseDeFait.ajouter(o);
                }
            }
        }
    }

}
