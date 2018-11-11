import java.util.ArrayList;

public class BaseFaits extends ArrayList<Fait> {

    public void ajouter(Operation o ) {
        if (o.isAjouter())
            this.add(o.getFait());
        else
            this.remove(o.getFait());
    }

}
