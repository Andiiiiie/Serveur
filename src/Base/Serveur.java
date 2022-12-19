package Base;

import ComposentsBase.MyDataBase;
import Requete.Query;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Serveur {
    int nombreClient;


    ServerSocket serverSocket;
    Socket socket;


    MyDataBase myDataBase;

    public Serveur(int port, MyDataBase myDataBase) throws IOException {
        this.setMyDataBase(myDataBase);
        this.setServerSocket(new ServerSocket(port));
        this.setNombreClient(0);
        miandry();

    }

    public void miandry() throws IOException {
        while (true)
        {
            this.setSocket(this.getServerSocket().accept());
            this.setNombreClient(this.getNombreClient()+1);
            System.out.println("Un client accepte("+this.getNombreClient()+")");
            ThreadClient threadClient=new ThreadClient(this.getSocket(),this.getMyDataBase(),this);
            threadClient.start();
        }
    }






    //get and set
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public MyDataBase getMyDataBase() {
        return myDataBase;
    }

    public void setMyDataBase(MyDataBase myDataBase) {
        this.myDataBase = myDataBase;
    }
    public int getNombreClient() {
        return nombreClient;
    }

    public void setNombreClient(int nombreClient) {
        this.nombreClient = nombreClient;
    }
}
