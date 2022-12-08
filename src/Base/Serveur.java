package Base;

import ComposentsBase.MyDataBase;
import Requete.Query;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {


    ServerSocket serverSocket;
    Socket socket;


    MyDataBase myDataBase;

    public Serveur(int port, MyDataBase myDataBase) throws IOException {
        this.setMyDataBase(myDataBase);
        this.setServerSocket(new ServerSocket(port));
        miandry();

    }

    public void miandry() throws IOException {
        while (true)
        {
            this.setSocket(this.getServerSocket().accept());
            ThreadClient threadClient=new ThreadClient(this.getSocket(),this.getMyDataBase());
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
}
