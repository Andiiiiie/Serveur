package ComposentsBase;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class Changement implements Serializable {

    String colonne;
    String donnee;
    public Changement(String c)
    {
        String[]  separate= c.split("=");
        Vector<String> separated = new Vector<>(List.of(separate));
        this.setColonne(separated.get(0));
        this.setDonnee(separated.get(1));
    }

    //get and set
    public String getColonne() {
        return colonne;
    }

    public void setColonne(String colonne) {
        this.colonne = colonne;
    }

    public String getDonnee() {
        return donnee;
    }

    public void setDonnee(String donnee) {
        this.donnee = donnee;
    }

}
