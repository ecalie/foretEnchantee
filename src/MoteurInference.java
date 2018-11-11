import java.util.ArrayList;
import java.util.List;

public class MoteurInference {

    private BaseFaits baseDeFait;
    private List<RegleDeduction> regles;

    public MoteurInference(BaseFaits _base, List<RegleDeduction> _regles) {
        this.baseDeFait = _base;
        this.regles = _regles;
    }

    public void appliquerRegles() {
        for (RegleDeduction r : regles)
            if (r.estApplicable(this.baseDeFait))
                for (Operation o : r.getCorps())
                    baseDeFait.ajouter(o);
    }

}
