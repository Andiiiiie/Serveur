package ComposentsBase;

import Diso.SelectErrors;

import java.io.Serializable;

public class Condition implements Serializable {
    String collumnName;
    String Donnee;
    String liaison;
    public Condition(String c, String d, String l)
    {
        this.setDonnee(d);
        this.setCollumnName(c);
        this.setLiaison(l);
    }
    public boolean isOkey(Ligne ligne,int numeroColonne) throws SelectErrors {
        if(this.getLiaison().equals("=") )
        {
            if(ligne.getDonnees().get(numeroColonne).equals(this.getDonnee()))
            {
                return true;
            }

        }
         else
        {
            try
            {
                Integer.parseInt(ligne.getDonnees().get(numeroColonne));
                Integer.parseInt(this.getDonnee());
                if(this.getLiaison().equals(">") && Integer.parseInt(ligne.getDonnees().get(numeroColonne))>Integer.parseInt(this.getDonnee()))
                {
                        //System.out.println("nety ny tato");
                        return true;
                }
                else if(this.getLiaison().equals("<") && Integer.parseInt(ligne.getDonnees().get(numeroColonne))<Integer.parseInt(this.getDonnee()))
                {
                    return true;
                }
                else if(this.getLiaison().equals(">=") && Integer.parseInt(ligne.getDonnees().get(numeroColonne))>=Integer.parseInt(this.getDonnee()))
                {
                    return true;
                }
                else if(this.getLiaison().equals("<=") && Integer.parseInt(ligne.getDonnees().get(numeroColonne))<=Integer.parseInt(this.getDonnee()))
                {
                    return true;
                }
            } catch (Exception e)
            {
                throw new SelectErrors("contions type error");
            }

        }

        return false;
    }


    //get and set
    public String getCollumnName() {
        return collumnName;
    }

    public void setCollumnName(String collumnName) {
        this.collumnName = collumnName;
    }

    public String getDonnee() {
        return Donnee;
    }

    public void setDonnee(String donnee) {

        String[] testTab=donnee.split("'");
        if(testTab.length>1)
        {
            Donnee = testTab[1];
        }
        else
        {
            Donnee = testTab[0];
        }

    }
    public String getLiaison() {
        return liaison;
    }

    public void setLiaison(String liaison) {
        this.liaison = liaison;
    }
}
