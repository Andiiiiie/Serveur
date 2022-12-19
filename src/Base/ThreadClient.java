package Base;

import ComposentsBase.MyDataBase;
import Requete.Query;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadClient extends Thread{
    Query query;
    DataOutputStream envoi;
    DataInputStream lecture;
    Serveur serveur;



    Socket socket;
    public ThreadClient(Socket socket, MyDataBase myDataBase,Serveur serveur) throws IOException {
        this.setSocket(socket);
        this.setEnvoi(new DataOutputStream(this.getSocket().getOutputStream()));
        this.setLecture(new DataInputStream(this.getSocket().getInputStream()));
        this.setQuery(new Query(myDataBase));
        this.setServeur(serveur);
    }

    @Override
    public void run() {

        String line="";
        while (true)
        {
            try {
                line=this.getLecture().readUTF();
                if(line.equals("exit"))
                {
                    sendResponse("sortie");
                    System.out.println("Un client a quitte");
                    this.getServeur().setNombreClient(this.getServeur().getNombreClient()-1);
                    break;
                }
                sendResponse(this.getQuery().reponseRequete(line));
            } catch (Exception e) {
                System.out.println("PLUS AUCUN CLIENT");
                //throw new RuntimeException(e);
            }

        }
        try {
            this.getEnvoi().flush();
            this.getEnvoi().close();
            this.getLecture().close();
            this.getSocket().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Serveur getServeur() {
        return serveur;
    }

    public void setServeur(Serveur serveur) {
        this.serveur = serveur;
    }



//    public void lecture() throws IOException {
//        String line="";
//        while (!line.equals("exit"))
//        {
//            line=this.getLecture().readUTF();
//            sendResponse(this.getQuery().reponseRequete(line));
//        }
//    }
    public void sendResponse(String reponse) throws IOException {
        this.getEnvoi().writeUTF(reponse);
        this.getEnvoi().flush();
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
    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


}
