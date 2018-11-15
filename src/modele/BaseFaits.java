package modele;

import java.util.ArrayList;

public class BaseFaits extends ArrayList<Fait> {

    public void ajouter(Operation o) {
        if (o.isAjouter() && !this.contains(o.getFait()))
            this.add(o.getFait());
        else if (!o.isAjouter())
            this.remove(o.getFait());
    }

    @Override
    public boolean contains(Object obj) {
        Fait f = (Fait) obj;
        for (Fait ff : this)
            if (ff.equals(f))
                return true;
        return false;
    }

    @Override
    public boolean add(Fait f) {
        if (!this.contains(f)) {
            super.add(f);
            return true;
        }
        return false;
    }

}
