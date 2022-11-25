package ComposentsBase;

import Diso.GrammarError;
import Fichier.Dossier;

import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

public class MyDataBase implements Serializable {
    Vector<Base> bases;
    Dossier dossier;

    public MyDataBase() throws IOException, ClassNotFoundException {
        this.setBases(new Vector<Base>());
        this.setDossier(new Dossier("archives",this));
    }

    public void addNewBase(Base base)
    {
        this.getBases().add(base);
    }

    public Base getBaseByName(String name)
    {
        for(int i=0;i<this.getBases().size();i++)
        {
            if(this.getBases().get(i).getName().equals(name))
            {
                //System.out.println(this.getBases().get(i).getName());
                return this.getBases().get(i);
            }
        }

        return null;
    }

    public void afficherBases() throws GrammarError {
        if(this.getBases().size()==0)
        {
            throw new GrammarError("aucune base cree");
        }
        System.out.println("Bases:");
        for(int i=0;i<this.getBases().size();i++)
        {
            System.out.println(this.getBases().get(i).getName());
        }
    }


    //get and set
    public Vector<Base> getBases() {
        return bases;
    }

    public void setBases(Vector<Base> bases) {
        this.bases = bases;
    }
    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }


}
