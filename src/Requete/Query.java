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


    public String reponseRequete(String requete) throws IOException, SelectErrors {
        String reponse;
        String[] tab = requete.split(" ");
        Vector<String> request = new Vector<>(List.of(tab));
        //creation base : create database
        //choix base : use
        //afficher les bases: show databases
        //se connecter a la base : ...
        //creer tableau :create table  ( col1,col2 )
        //selection tableau
        //insertion tableau
        //afficher les tableau d'un base
        if(request.indexOf("create")!=-1 && request.indexOf("database")==(request.indexOf("create")+1))
        {
            String baseName=request.get(request.indexOf("create")+2);
            reponse=this.createBase(baseName);
        }
        else if(request.indexOf("use")!=-1)
        {
            String baseName=request.get(request.indexOf("use")+1);
            reponse=defineUsedBase(baseName);
        }
        else if (request.indexOf("show")!=-1 && request.indexOf("databases")==(request.indexOf("show")+1))
        {
            reponse=afficherBases();
        }
        else if (request.indexOf("show")!=-1 && request.indexOf("tables")==(request.indexOf("show")+1))
        {
            reponse=afficherTables();
        }
        else if(request.indexOf("create")!=-1 && request.indexOf("table")==(request.indexOf("create")+1))
        {
            String tableName=request.get(request.indexOf("create")+2);
            if(request.indexOf("(")!=-1 && request.indexOf(")")!=-1)
            {
                Vector<String> colonnes=getNewColNames(request.get(request.indexOf("(")+1));
                reponse=createTable(tableName,colonnes);
            }
            else
            {
                reponse="No collumns defined";
            }
        }
        else if(request.indexOf("save")!=-1)
        {
            this.getMyDataBase().getDossier().miseAjour(this.getMyDataBase());
            reponse="saved";
        }
        else if (request.indexOf("delete")!=-1 && request.indexOf("table")==request.indexOf("delete")+1)
        {
            String tabName=request.get(request.indexOf("delete")+2);
            if(this.getBase().getTabByName(tabName)==null)
            {
                reponse="table doesn't exist";
            }
            else
            {
                this.getBase().getTabs().remove(this.getBase().isInBase(tabName));
                reponse="table removed";
            }
        }
        else if(request.indexOf("insert")!=-1)
        {
            reponse=insertSomething(requete);
        } else if (request.indexOf("select")!=-1) {
            reponse=selecting(requete);
        }
        else if(request.indexOf("update")!=-1) {
            reponse=updateTable(requete);
        }
        else if (request.indexOf("delete")!=-1) {
            reponse=deleteTable(requete);
        } else
        {
            reponse="request not recognized";
        }
        return reponse;
    }
    public String deleteTable(String requete) throws SelectErrors, IOException {
        String[] tab = requete.split(" ");
        Vector<String> request = new Vector<>(List.of(tab));
        String reponse;
        String tabName=request.get(2);
        if(this.getBase()==null)
        {
            reponse="No Base selected";
        }
        else
        {
            if(request.indexOf("where")==-1)
            {
                reponse= this.getBase().getTabByName(tabName).getDonnees().size()+"  rows deleted";
                this.getBase().getTabByName(tabName).setDonnees(new Vector<Ligne>());
            }
            else
            {
                String conditions=request.get((request.indexOf("where")+1));
                Vector<String> vectConditions=new Vector<String>(Arrays.asList(conditions.split(",")));
                Vector<Condition> conditions2=new Vector<>();
                for(int i=0;i<vectConditions.size();i++)
                {
                    conditions2.add(this.getCondition(vectConditions.get(i),this.getBase().getTabByName(tabName)));
                }
                Vector<Integer> listeEsorina=new Vector<>();
                for (int j=0;j<this.getBase().getTabByName(tabName).getDonnees().size();j++)
                {
                    if(this.getBase().getTabByName(tabName).getDonnees().get(j).isOkay(conditions2)==true)
                    {
                        listeEsorina.add(j);
                    }
                }
                for (int k=0;k<listeEsorina.size();k++)
                {
                    this.getBase().getTabByName(tabName).getDonnees().remove(this.getBase().getTabByName(tabName).getDonnees().get(listeEsorina.get(k)));
                }
                System.out.println(this.getBase().getTabByName(tabName).getDonnees().size()+" sisa ny ligne");
                reponse=listeEsorina.size()+" rows deleted";
            }
        }
        this.getMyDataBase().getDossier().miseAjour(this.getMyDataBase());
        return reponse;
    }
    public String updateTable(String requete) throws SelectErrors {
        String[] tab = requete.split(" ");
        Vector<String> request = new Vector<>(List.of(tab));
        String reponse;
        String tabName=request.get(1);
        if(this.getBase()==null)
        {
            reponse="No Base selected";
        }
        else
        {
            if(this.getBase().isInBase(tabName)==-1)
            {
                reponse="Table doesn't exist";
            }
            else
            {
                String changement=request.get(request.indexOf("set")+1);
                String condition=request.get(request.indexOf("where")+1);
                String[] c = changement.split(",");
                Vector<String> CList = new Vector<>(List.of(c));
                Vector<Changement> changements=new Vector<>();
                for (int i=0;i<CList.size();i++)
                {
                    changements.add(new Changement(CList.get(i)));
                }
                //
                Vector<String> vectConditions=new Vector<String>(Arrays.asList(condition.split(",")));
                Vector<Condition> conditions2=new Vector<>();
                for(int i=0;i<vectConditions.size();i++)
                {
                    conditions2.add(this.getCondition(vectConditions.get(i),this.getBase().getTabByName(tabName)));
                }
                Vector<Integer> listeOvaina=new Vector<>();
                for (int j=0;j<this.getBase().getTabByName(tabName).getDonnees().size();j++)
                {
                    if(this.getBase().getTabByName(tabName).getDonnees().get(j).isOkay(conditions2)==true)
                    {
                        listeOvaina.add(j);
                    }
                }
                for (int k=0;k<listeOvaina.size();k++)
                {
                    this.getBase().getTabByName(tabName).getDonnees().get(listeOvaina.get(k)).changer(changements);
                }
                reponse=listeOvaina.size()+" rows changed";
            }
        }
        return  reponse;
    }

    public String afficherTables()
    {
        String reponse;
        if (this.getBase()==null)
        {
            reponse="no database selected";
        }
        else
        {
            reponse="Tables\n";
            for(int i=0;i<this.getBase().getTabs().size();i++)
            {
                reponse=reponse+this.getBase().getTabs().get(i).getName()+"\n";
            }
        }
        return reponse;
    }

    public  String insertSomething(String requete)
    {
        String[] tab = requete.split(" ");
        Vector<String> request = new Vector<>(List.of(tab));
        String reponse;
        if(this.getBase()==null)
        {
            reponse="no Base selected";
        }
        else
        {
            String tabName=request.get(2);
            if(this.getBase().getTabByName(tabName)==null)
            {
                reponse="table "+tabName+" doesn't exist";
            }
            else
            {
                String donnees=request.get(3);
                String[] l = donnees.split(",");
                Vector<String> donnee = new Vector<>(List.of(l));
                if(donnee.size()!=this.getBase().getTabByName(tabName).getAttribus().size())
                {
                    reponse="donnnes incomplets";
                }
                else
                {
                    this.getBase().getTabByName(tabName).addDonnee(new Ligne(donnee,this.getBase().getTabByName(tabName).getAttribus()));
                    reponse="donnees inserees";
                }
            }
        }
        return  reponse;
    }
    public  String selecting(String requete)
    {
        String[] tab = requete.split(" ");
        String reponse;
        Vector<String> request = new Vector<>(List.of(tab));
        try {
            Tableau tableau=select(request);
            tableau.afficherTab();
            reponse=tableau.toString();
        } catch (SelectErrors e) {
            reponse=e.getMessage();
        }
        return reponse;
    }

    public Tableau select(Vector<String> req) throws SelectErrors {
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
            tabFinal=this.jouinture(req);
            //tabFinal.afficherTab();
        }
        else if(req.indexOf("where")!=-1)
        {
            String conditions=req.get((req.indexOf("where")+1));
            Vector<String> vectConditions=new Vector<String>(Arrays.asList(conditions.split(",")));
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
            String tab2Name=req.get((req.indexOf("division")+1));
            int indiceTab2=this.getBase().isInBase(tab2Name);
            if(indiceTab2==-1)
            {
                throw new SelectErrors(tab2Name+"  non existant ");
            }
            tabFinal=tabFinal.division(this.getBase().getTabs().get(indiceTab2));
        }
//        tabFinal.afficherTab();
        return tabFinal;
    }
    public Vector<String> getNewColNames(String req)
    {
        return new Vector<>(Arrays.asList(req.split(",")));
    }

    public String createTable(String tabName,Vector<String> colonnes)
    {
        String reponse;
        if(this.getBase()==null)
        {
            reponse="No base selected";
        }
        else
        {
            this.getBase().addNewTab(new Tableau(tabName,colonnes));
            reponse="table "+tabName+" added to "+this.getBase().getName();
        }
        return reponse;
    }

    public String afficherBases()
    {
        String reponse="Bases : \n";
        for(int i=0;i<this.getMyDataBase().getBases().size();i++)
        {
            reponse=reponse+" "+this.getMyDataBase().getBases().get(i).getName()+"\n";
        }
        return reponse;
    }
    public  String createBase(String baseName)
    {
        String reponse;
        if(this.getMyDataBase().isValidName(baseName))
        {
            this.getMyDataBase().addNewBase(new Base(baseName));
            reponse="Base "+baseName+" created";
        }
        else
        {
            reponse="Name "+baseName+"invalid";
        }
        return reponse;
    }
    public String defineUsedBase(String baseName)
    {
        String reponse;
        if(this.getMyDataBase().getBaseByName(baseName)!=null)
        {
            this.setBase(this.getMyDataBase().getBaseByName(baseName));
            reponse="Base used: "+baseName;
        }
        else
        {
            reponse="base "+baseName+" does not exist";
        }
        return reponse;
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




    public Tableau selection(Condition conditions,Tableau tab) throws SelectErrors {
        Tableau relation=new Tableau("selection",tab.getAttribus());
        int numColonne=tab.getAttribus().indexOf(conditions.getCollumnName());
        for(int i=0;i<tab.getDonnees().size();i++)
        {
            if(conditions.isOkey(tab.getDonnees().get(i),numColonne))
            {
                relation.addDonnee(tab.getDonnees().get(i));
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
