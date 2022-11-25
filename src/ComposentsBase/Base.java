package ComposentsBase;

import java.io.Serializable;
import java.util.Vector;

public class Base implements Serializable {
    String name;
    Vector<Tableau> tabs;
    public Base(String name) {
        this.setName(name);
        this.setTabs(new Vector<Tableau>());
    }

    public void addNewTab(Tableau tableau)
    {
        this.getTabs().add(tableau);
    }

    public Tableau getTabByName(String name)
    {
        Tableau tableau=null;
        int indice=this.isInBase(name);
        if(indice!=-1)
        {
            tableau=this.getTabs().get(indice);
        }
        return tableau;
    }

    public int isInBase(String name)
    {
        for(int i=0;i<this.getTabs().size();i++)
        {
            if(this.getTabs().get(i).getName().equals(name))
            {
                return i;
            }
        }
        return -1;
    }


    //get and set
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<Tableau> getTabs() {
        return tabs;
    }

    public void setTabs(Vector<Tableau> tabs) {
        this.tabs = tabs;
    }
}
