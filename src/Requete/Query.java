package Requete;

import ComposentsBase.*;
import Diso.GrammarError;
import Diso.InsertErrors;
import Diso.SelectErrors;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Query implements Serializable {
    MyDataBase myDataBase;
    Base base;

    public Query(MyDataBase base) {
        this.setMyDataBase(base);
    }


    public void executeQuery(String requete) throws InsertErrors, GrammarError, SelectErrors, IOException {
        String[] tab = requete.split(" ");
        Vector<String> req = new Vector<>(List.of(tab));
        int indice ;

            if (requete.endsWith(";") == false) {
                throw new InsertErrors("; oubliee");
            }
        if (req.indexOf("create") != -1) {
            indice = req.indexOf("create") + 1;
            String[] nomBase = req.get(indice).split(";");
            this.getMyDataBase().addNewBase(new Base(nomBase[0]));
            if (this.getBase() == null) {
                this.setBase(this.getMyDataBase().getBases().get(0));
            }
        } else if (req.indexOf("use") != -1) {
            indice = req.indexOf("use") + 1;
            String[] nomBase = req.get(indice).split(";");
            if (this.getMyDataBase().getBaseByName(nomBase[0]) == null) {
                throw new GrammarError("Base innexistant dans la base de donnee");
            } else {
                this.setBase(this.getMyDataBase().getBaseByName(nomBase[0]));
            }
        }
        else if(req.indexOf("afficher")!=-1)
        {
            indice=req.indexOf("afficher")+1;
            String[] splitiavo=req.get(indice).split(";");
            if (splitiavo[0].equals("bases"))
            {
                this.getMyDataBase().afficherBases();
            }
            else if(splitiavo[0].equals("base"))
            {
                System.out.println(this.getBase().getName());
            }
        }
        else if(req.indexOf("select")!=-1)
        {
            this.select(req);
        }
        else if(req.indexOf("show")!=-1)
        {
            if(this.getBase()==null)
            {
                throw new SelectErrors("no base selected");
            }
            if(this.getBase().getTabs().size()==0)
            {
                throw new SelectErrors("no tables in base "+this.getBase().getName());
            }
            for(int i=0;i<this.getBase().getTabs().size();i++)
            {
                System.out.println((i+1)+"-"+this.getBase().getTabs().get(i).getName());
            }
        }
        else if(req.indexOf("createBase")!=-1)
        {
            String nameBase=req.get(req.indexOf("createBase")+1);
            this.getMyDataBase().addNewBase(new Base(nameBase));
        }
        else if(req.indexOf("add")!=-1)
        {
            if(this.getBase()==null)
            {
                throw new GrammarError("no Base selected");
            }
            String tabName=req.get((req.indexOf("add")+1));
            if(this.getBase().isInBase(tabName)!=-1)
            {
                throw new GrammarError("nom Table invalide");
            }

            if(req.size()<4)
            {
                throw new GrammarError("table pas creee manque colonnne");
            }

            String cols=req.get((req.indexOf("add")+2));
            Vector<String> vectCol=getNewColNames(cols);
            this.getBase().addNewTab(new Tableau(tabName,vectCol));
        }
        else if(req.indexOf("insert")!=-1)
        {
            this.insert(req);
        }
        else if(req.indexOf("update")!=-1)
        {

        }
        else if(req.indexOf("save")!=-1)
        {
            this.getMyDataBase().getDossier().miseAjour(this.getMyDataBase());
            System.out.println("donnees enregistres");
        }
//        else if(req.indexOf("delete")!=-1)
//        {
//            String tabName=req.get((req.indexOf("delete")+1));
//            if(req.indexOf("delete")!=-1)
//            {}
//        }

        this.getMyDataBase().getDossier().miseAjour(this.getMyDataBase());
    }


    public void update(Vector<String> req) throws GrammarError {
        String tabName=req.get(1);
        if(this.getBase().isInBase(tabName)==-1)
        {
            throw new GrammarError("Tableau inexistant");
        }
        if(req.indexOf("where")==-1)
        {
             
        }

    }




    public Vector<String> getNewColNames(String req)
    {
        return new Vector<>(Arrays.asList(req.split(",")));
    }

    public Condition getCondition(String req,Tableau tableau) throws SelectErrors {
        String[] sub=req.split("'");
        if(tableau.getAttribus().indexOf(sub[0])==-1)
        {
            throw new SelectErrors(sub[0]+" non existant dans le tableau");
        }
        Condition condition=new Condition(sub[0],sub[2],sub[1]);
        return condition;
    }


    public void insert(Vector<String> req) throws GrammarError, InsertErrors {
        String tabName=req.get(1);
        int indiceTab=this.getBase().isInBase(tabName);
        Vector<String> donneees=getNewColNames(req.get(2));
        if(donneees.size()>this.getBase().getTabs().get(indiceTab).getAttribus().size() || donneees.size()<this.getBase().getTabs().get(indiceTab).getAttribus().size())
        {
            throw new GrammarError("Donnes invalides");
        }
        Ligne temp=new Ligne(donneees,this.getBase().getTabs().get(indiceTab).getAttribus());
        if(this.getBase().getTabs().get(indiceTab).isInTable(this.getBase().getTabs().get(indiceTab).getDonnees(),temp))
        {
            throw new InsertErrors("donnees invalides");
        }
        this.getBase().getTabs().get(indiceTab).addDonnee(temp);
        System.out.println("Donnees inserees");
    }



    public Tableau selection(Condition conditions,Tableau tab) throws SelectErrors {
        Tableau relation=new Tableau("selection",tab.getAttribus());
        int numColonne=tab.getAttribus().indexOf(conditions.getCollumnName());
        for(int i=0;i<tab.getDonnees().size();i++)
        {
            if(conditions.isOkey(tab.getDonnees().get(i),numColonne))
            {
                relation.addDonnee(tab.getDonnees().get(i));
                System.out.println("nisy iray ");
            }
        }
        return relation;
    }


    public Tableau selections(Vector<Condition> conditions,Tableau tab) throws SelectErrors {
        Tableau relation= new Tableau("selctions",tab.getAttribus());
        Vector<Tableau> liste=new Vector<>();
        Tableau temp;
        for(int i=0;i<conditions.size();i++)
        {
            temp=this.selection(conditions.get(i),tab);
            liste.add(temp);
        }
        relation=liste.get(0);
        //System.out.println(relation.getDonnees().size()+"  lignes");
        for(int j=1;j<liste.size();j++) {
            relation = relation.intersection(liste.get(j));
        }
        return relation;
    }

    public boolean verificationColonne(Vector<String> colonnes,String tabName1,String tabName2) throws SelectErrors {
        int indice1=this.getBase().isInBase(tabName1);
        int indice2=this.getBase().isInBase(tabName2);
        //getFusionColonnes 2 tableau t1.dew,t2.fef
        Vector<ColonnesJouintureIlaina> listeIlaina=new Vector<>();
        for(int a=0;a<colonnes.size();a++)
        {
            listeIlaina.add(new ColonnesJouintureIlaina(colonnes.get(a)));
            if(listeIlaina.get(a).getTabName().equals("t1"))
            {
                if(this.getBase().getTabs().get(indice1).getAttribus().indexOf(colonnes.get(a))==-1)
                {
                    return false;
                }
            }
            else if (listeIlaina.get(a).getTabName().equals("t2"))
            {
                if(this.getBase().getTabs().get(indice2).getAttribus().indexOf(colonnes.get(a))==-1)
                {
                    return false;
                }
            }
        }
        return true;
    }






    public void select(Vector<String> req) throws SelectErrors {
        /* to do
            -jointure
        * */
        String colonnes=req.get(1);
        String tabName=req.get(3);
        Tableau tabFinal=new Tableau();
        if (this.getBase()==null)
        {
            throw new SelectErrors("aucune base selectionnee");
        }
        int indice=this.getBase().isInBase(tabName);

        if(indice==-1)
        {
            throw new SelectErrors("tableau pas dans la base");
        }
        Vector<String> colVect=new Vector<>(Arrays.asList(colonnes.split(",")));
        if(colVect.size()==1 && colVect.get(0).equals("*"))
        {
            //System.out.println("tato eee");
//            this.getBase().getTabs().get(indice).afficherTab();
            tabFinal=this.getBase().getTabs().get(indice);
        }
        else
        {
            //System.out.println("tato koa izy aaan");
            if(this.getBase().getTabs().get(indice).isColNamesOK(colVect)==false){
                throw  new SelectErrors("colonnes invalides");
            }
            Tableau tableau=this.getBase().getTabs().get(indice).projection(colVect);
            tabFinal=tableau;
            //tableau.afficherTab();
        }
        if(req.indexOf("jointure")!=-1)
        {
            System.out.println("nandalo jointure");
            tabFinal=this.jouinture(req);
            //tabFinal.afficherTab();
        }
        else if(req.indexOf("where")!=-1)
        {
            //System.out.println("tato @ conditions izy"+ req.indexOf("where"));
            String conditions=req.get((req.indexOf("where")+1));
            Vector<String> vectConditions=new Vector<String>(Arrays.asList(conditions.split(",")));
            for (int i=0;i<vectConditions.size();i++)
            {
                //System.out.println(vectConditions.get(i)+"   condition");
            }
            Vector<Condition> conditions2=new Vector<>();
            for(int i=0;i<vectConditions.size();i++)
            {
                conditions2.add(this.getCondition(vectConditions.get(i),tabFinal));
            }
            tabFinal=selections(conditions2,tabFinal);
        }
        if(req.indexOf("union")!=-1)
        {
            String tab2Name=req.get((req.indexOf("union")+1));
            int indiceTab2=this.getBase().isInBase(tab2Name);
            if(indiceTab2==-1)
            {
                throw new SelectErrors(tab2Name+"  non existant ");
            }
            tabFinal=tabFinal.union(this.getBase().getTabs().get(indiceTab2));
        }
        else if(req.indexOf("dif")!=-1)
        {
            System.out.println("tato izy");
            String tab2Name=req.get((req.indexOf("dif")+1));
            int indiceTab2=this.getBase().isInBase(tab2Name);
            if(indiceTab2==-1)
            {
                throw new SelectErrors(tab2Name+"  non existant ");
            }
            tabFinal=tabFinal.difference(this.getBase().getTabs().get(indiceTab2));
        }
        else if(req.indexOf("produit")!=-1)
        {
            System.out.println("tato izy");
            String tab2Name=req.get((req.indexOf("produit")+1));
            int indiceTab2=this.getBase().isInBase(tab2Name);
            if(indiceTab2==-1)
            {
                throw new SelectErrors(tab2Name+"  non existant ");
            }
            tabFinal=tabFinal.produit(this.getBase().getTabs().get(indiceTab2));
        }
        else if(req.indexOf("division")!=-1)
        {
            System.out.println("tato izy");
            String tab2Name=req.get((req.indexOf("division")+1));
            int indiceTab2=this.getBase().isInBase(tab2Name);
            if(indiceTab2==-1)
            {
                throw new SelectErrors(tab2Name+"  non existant ");
            }
            tabFinal=tabFinal.division(this.getBase().getTabs().get(indiceTab2));
        }
        tabFinal.afficherTab();
    }

    //jointure

    public Tableau jouinture(Vector<String> requete) throws SelectErrors {
        String tabName1=requete.get(requete.indexOf("jointure")-1);
        String tabName2=requete.get(requete.indexOf("jointure")+1);
        Tableau reponse=null;
        if(this.getBase().getTabByName(tabName1)!=null && this.getBase().getTabByName(tabName2)!=null)
        {
            Tableau tableau1=this.getBase().getTabByName(tabName1);
            Tableau tableau2=this.getBase().getTabByName(tabName2);
            String colCommun=requete.get(requete.indexOf("jointure")+3);
            String col1,col2;
            String[] l=colCommun.split("=");
            col1=l[0];
            col2=l[1];
            reponse=tableau1.produitJouinture(tableau2,col1,col2);
        }
        else
        {
            throw new SelectErrors("tableau innexistant");
        }
        return reponse;

    }




    //get and set
    public MyDataBase getMyDataBase() {
        return myDataBase;
    }

    public void setMyDataBase(MyDataBase myDataBase) {
        this.myDataBase = myDataBase;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
        System.out.println("niova ny base: "+this.getBase().getName());
    }
}
