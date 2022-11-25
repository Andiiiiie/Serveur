package Fichier;


import ComposentsBase.MyDataBase;

import java.io.*;

public class Dossier extends File implements Serializable {


    static String toerany="src/";



    String name;
    public Dossier(String name, MyDataBase dataBase) throws IOException, ClassNotFoundException {
        super("src/"+name+".txt");
        this.setName(name);
        if(this.exists()==false)
        {
            this.createNewFile();
            this.Ecriture(dataBase);
        }
        else
        {
            MyDataBase temp=MyDataBase.class.cast(this.lecture());
            for(int i=0;i<temp.getBases().size();i++)
            {
                dataBase.addNewBase(temp.getBases().get(i));
            }
        }

    }


    public void Ecriture(MyDataBase dataBase) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(this.getToerany()+this.getName()+".txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(dataBase);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public Object lecture() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(this.getToerany()+this.getName()+".txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object result = objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return result;
    }

    public void miseAjour(MyDataBase dataBase) throws IOException {
        this.Ecriture(dataBase);
    }


    public static String getToerany() {
        return toerany;
    }

    public static void setToerany(String toerany) {
        Dossier.toerany = toerany;
    }
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
