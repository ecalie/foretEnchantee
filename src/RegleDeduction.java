import java.util.List;

public class RegleDeduction {

    private List<Fait> declencheurs;
    private List<Operation> corps;
    private boolean marquee;

    public RegleDeduction(List<Fait> _declencheurs, List<Operation> _corps) {
        this.declencheurs = _declencheurs;
        this.corps = _corps;
        this.marquee = false;
    }


    public List<Fait> getDeclencheurs() {
        return declencheurs;
    }

    public List<Operation> getCorps() {
        return this.corps;
    }

    public boolean estApplicable(BaseFaits base) {
        //Est-ce qu'on a tous les faits déclencheurs dans le base de fait
        return false;
    }

    public boolean isMarquee() {
        return this.marquee;
    }

    public void marquer() {
        this.marquee = true;
    }
}
