import Base.Serveur;
import ComposentsBase.MyDataBase;
import Requete.Query;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MyDataBase myDataBase=new MyDataBase();
        //Query query=new Query(myDataBase);
        Serveur serveur=new Serveur(111,myDataBase);
    }
}
