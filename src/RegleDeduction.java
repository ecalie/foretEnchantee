import java.util.List;

public class RegleDeduction {

    private List<Fait> declencheurs;
    private List<Operation> corps;
    private boolean marquee;

    public List<Fait> getDeclencheurs() {
        return declencheurs;
    }

    public List<Operation> getCorps() {
        return corps;
    }

    public boolean estApplicable(BaseFaits base) {
        // Si la règle est marquée, est n'est pas applicable
        if (this.marquee)
            return false;

        // Est-ce qu'on a tous les faits déclencheurs dans le base de faits
        for (Fait f : this.declencheurs)
            if (!base.contains(f))
                return false;

        return true;
    }

    public boolean isMarquee() {
        return marquee;
    }

    public void marquer() {
        marquee = true;
    }

}
