package ComposentsBase;

import java.io.Serializable;
import java.util.Vector;

public class Ligne implements Serializable {
    Vector<String> Donnees;
    Vector<String> nomColonne;

    public Ligne()
    {
        this.setDonnees(new Vector<String>());
        this.setNomColonne(new Vector<String>());
    }

    public Ligne(Vector<String> d,Vector<String> t)
    {
        this.setDonnees(d);
        this.setNomColonne(t);
    }

    public Ligne(Vector<String> c)
    {
        this.setNomColonne(c);
        this.setDonnees(new Vector<String>());
    }

    public Ligne fusionnerLigne (Ligne ligne2)
    {
        Ligne reps=new Ligne();
        for(int i=0;i<this.getNomColonne().size();i++)
        {
            reps.getNomColonne().add(this.getNomColonne().get(i));
            reps.getDonnees().add(this.getDonnees().get(i));
        }
        for(int j=0;j<ligne2.getNomColonne().size();j++)
        {
            reps.getNomColonne().add(ligne2.getNomColonne().get(j));
            reps.getDonnees().add(ligne2.getDonnees().get(j));
        }
        return reps;
    }



    //get and set
    public Vector<String> getDonnees() {
        return Donnees;
    }

    public void setDonnees(Vector<String> donnees) {
        Donnees = donnees;
    }

    public Vector<String> getNomColonne() {
        return nomColonne;
    }

    public void setNomColonne(Vector<String> nomColonne) {
        this.nomColonne = nomColonne;
    }
}
