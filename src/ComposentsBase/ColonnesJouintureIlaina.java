package ComposentsBase;

import Diso.SelectErrors;

import java.util.Arrays;
import java.util.Vector;

public class ColonnesJouintureIlaina {

    String tabName;
    String colonneName;

    public ColonnesJouintureIlaina(String t,String c)
    {
        this.setTabName(t);
        this.setColonneName(c);
    }


    public ColonnesJouintureIlaina(String req) throws SelectErrors {
        Vector<String> liste=new Vector<>(Arrays.asList(req.split(".")));
        if(liste.size()!=2)
        {
            throw new SelectErrors("invalide colonnnes selectionnes");
        }
        if(liste.get(0).equals("t1")==false && liste.get(0).equals("t2")==false)
        {
            throw new SelectErrors("colonnes selectionnes invalides ");
        }
        this.setTabName(liste.get(0));
        this.setColonneName(liste.get(1));
    }


    //get and set
    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getColonneName() {
        return colonneName;
    }

    public void setColonneName(String colonneName) {
        this.colonneName = colonneName;
    }

}
