package ComposentsBase;

import java.io.Serializable;
import java.util.Vector;

public class Tableau implements Serializable {
    String name;
    Vector<String> attribus;
    Vector<Ligne> donnees;

    public Tableau()
    {

    }

    public Tableau(String n,Vector<String> att)
    {
        this.setName(n);
        this.setAttribus(att);
        this.setDonnees(new Vector<Ligne>());
    }

    public String toString()
    {
        String reponse=this.getName()+"\n";
        for (int i=0;i<this.getAttribus().size();i++)
        {
            reponse=reponse+this.getAttribus().get(i)+"\t";
        }
        reponse=reponse+"\n";
        for (int j=0;j<this.getDonnees().size();j++)
        {
            for (int k=0;k<this.getAttribus().size();k++)
            {
               reponse=reponse+this.getDonnees().get(j).getDonnees().get(k)+"\t";
            }
            reponse=reponse+"\n";
        }
        reponse=reponse+"nombre de ligne:"+this.getDonnees().size()+"\n";
        return reponse;
    }
    public void addDonnee(Ligne ligne)
    {
        this.getDonnees().add(ligne);
    }

    public boolean isColNamesOK(Vector<String> strings)
    {
        for(int i=0;i<strings.size();i++)
        {
            if(this.getAttribus().indexOf(strings.get(i))==-1)
            {
                return false;
            }
        }
        return true;
    }

    public boolean isIntheRow(Ligne l1,Ligne l2)
    {
        for(int i=0;i<l1.getDonnees().size();i++)
        {
            if(l1.getDonnees().size()==l2.getDonnees().size())
            {
                if(l1.getDonnees().get(i).equals(l2.getDonnees().get(i)))
                {

                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }

        }
        return true;
    }

    public Boolean isInTable(Vector<Ligne> lignes,Ligne ligne)
    {
        for(int i=0;i<lignes.size();i++)
        {
            if(this.isIntheRow(lignes.get(i),ligne)==true)
            {
                return  true;
            }
        }
        return false;
    }


    public Tableau union(Tableau relation2)
    {
        Tableau relation=new Tableau("union",this.getAttribus());
        for(int i=0;i<this.getDonnees().size();i++)
        {
            if(this.isInTable(relation.getDonnees(),this.getDonnees().get(i))==false)
            {
                relation.addDonnee(this.getDonnees().get(i));
            }
        }
        for (int j=0;j<relation2.getDonnees().size();j++)
        {
            if(this.isInTable(relation.getDonnees(),relation2.getDonnees().get(j))==false)
            {
                relation.addDonnee(relation2.getDonnees().get(j));
            }
        }
        return relation;
    }

    public Tableau difference(Tableau relation2)
    {
        Tableau relation=new Tableau("union",this.getAttribus());
        for(int i=0;i<this.getDonnees().size();i++) {
            if (this.isInTable(relation2.getDonnees(), this.getDonnees().get(i))==false) {
                if (this.isInTable(relation.getDonnees(), this.getDonnees().get(i)) == false) {
                    relation.addDonnee(this.getDonnees().get(i));
                }
            }
        }
        return relation;
    }

    public Tableau projection(Vector<String> collones)
    {
        Tableau tableau=new Tableau("projection",collones);
        Ligne temp;
        int indice;
        for(int i=0;i<this.getDonnees().size();i++)
        {
            temp=new Ligne(collones);
            for(int j=0;j<collones.size();j++)
            {
                indice=this.getAttribus().indexOf(collones.get(j));
                temp.getDonnees().add(this.getDonnees().get(i).getDonnees().get(indice));
            }
            tableau.getDonnees().add(temp);
        }
        return  tableau;
    }




    public Tableau intersection(Tableau relation2)
    {
        Tableau relation=new Tableau("intersection",this.getAttribus());
        for(int i=0;i<this.getDonnees().size();i++) {
            if (this.isInTable(relation2.getDonnees(), this.getDonnees().get(i))) {
                if (this.isInTable(relation.getDonnees(), this.getDonnees().get(i)) == false) {
                    relation.addDonnee(this.getDonnees().get(i));
                }
            }
        }
        return relation;
    }

    public Vector<String> combinerAttributs(Tableau tab2)
    {
        Vector<String> champs=new Vector<String>();
        for(int i=0;i<this.getAttribus().size();i++)
        {
            champs.add(this.getAttribus().get(i));
        }
        for(int j=0;j<tab2.getAttribus().size();j++)
        {
            champs.add(tab2.getAttribus().get(j));
        }
        return  champs;
    }

    public Tableau produit(Tableau tab2)
    {
        Tableau relation=new Tableau("produit",this.combinerAttributs(tab2));
        for (int i=0;i<this.getDonnees().size();i++)
        {
            for (int j=0;j<tab2.getDonnees().size();j++)
            {
                relation.addDonnee(this.getDonnees().get(i).fusionnerLigne(tab2.getDonnees().get(j)));
            }
        }
        return relation;
    }

    public String getColByName( String name,int indice)
    {
        for(int i=0;i<this.getAttribus().size();i++)
        {
            if(this.getAttribus().get(i).equals(name))
            {
                return this.getDonnees().get(indice).getDonnees().get(i);
            }
        }
        return null;
    }


    public Tableau produitJouinture(Tableau tab2,String col1,String col2)
    {
        Tableau relation=new Tableau("produit",this.combinerAttributs(tab2));
        for (int i=0;i<this.getDonnees().size();i++)
        {
            for (int j=0;j<tab2.getDonnees().size();j++)
            {
                if(this.getColByName(col1,i).equals(tab2.getColByName(col2,j)))
                {
                    relation.addDonnee(this.getDonnees().get(i).fusionnerLigne(tab2.getDonnees().get(j)));
                }
            }
        }
        return relation;
    }


    public Vector<String> difColonne(Vector<String> A,Vector<String> B)
    {
        Vector<String> dif=new Vector<>();
        for(int i=0;i<A.size();i++)
        {
            if(B.indexOf(A.get(i))==-1)
            {
                dif.add(A.get(i));
            }
        }
        return  dif;
    }

    public Tableau division(Tableau tab2)
    {
        Vector<String> colR=this.getAttribus();
        Vector<String> colS=tab2.getAttribus();
        Tableau relation1=this.projection(this.difColonne(colR,colS));
        Tableau relation2=relation1.produit(tab2);
        Tableau relation3=relation2.difference(this);
        Tableau relation4=relation3.projection(this.difColonne(colR,colS));
        Tableau relation5=relation1.difference(relation4);
        return relation5;
    }




    //get and set
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<String> getAttribus() {
        return attribus;
    }

    public void setAttribus(Vector<String> attribus) {
        this.attribus = attribus;
    }

    public Vector<Ligne> getDonnees() {
        return donnees;
    }

    public void setDonnees(Vector<Ligne> donnees) {
        this.donnees = donnees;
    }
}
