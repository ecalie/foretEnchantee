import java.util.List;

public class RegleDeduction {

    private List<Fait> declencheurs;
    private List<Operation> corps;

    public List<Fait> getDeclencheurs() {
        return declencheurs;
    }

    public List<Operation> getCorps() {
        return corps;
    }

    public boolean estApplicable(BaseFaits base) {
        return false;
    }
}
