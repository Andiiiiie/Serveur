package Base;

import ComposentsBase.MyDataBase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {


    ServerSocket serverSocket;
    Socket socket;
    DataOutputStream envoi;
    DataInputStream lecture;
    MyDataBase myBase;

    public Serveur(int port, MyDataBase myDataBase) throws IOException {
        this.setMyBase(myDataBase);

        this.setServerSocket(new ServerSocket(port));

        this.setSocket(this.getServerSocket().accept());

        this.setLecture(new DataInputStream(new BufferedInputStream(this.getSocket().getInputStream())));
        this.setEnvoi(new DataOutputStream(new BufferedOutputStream(this.getSocket().getOutputStream())));

    }

    public void lecture() throws IOException {
        String line="";
        while (line.equals("exit"))
        {
            line=this.getLecture().readUTF();
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

    public DataOutputStream getEnvoi() {
        return envoi;
    }

    public void setEnvoi(DataOutputStream envoi) {
        this.envoi = envoi;
    }

    public DataInputStream getLecture() {
        return lecture;
    }

    public void setLecture(DataInputStream lecture) {
        this.lecture = lecture;
    }
    public MyDataBase getMyBase() {
        return myBase;
    }

    public void setMyBase(MyDataBase myBase) {
        this.myBase = myBase;
    }
}
