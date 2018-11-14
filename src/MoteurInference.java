import java.util.ArrayList;
import java.util.List;

public class MoteurInference {

    private BaseFaits baseDeFait;
    private List<RegleDeduction> regles;

    public MoteurInference(Carte map) {
        this.baseDeFait = new BaseFaits();
        genererRegles(map);
    }

    public BaseFaits getBaseDeFait() {
        return baseDeFait;
    }

    public void genererRegles(Carte map){
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
