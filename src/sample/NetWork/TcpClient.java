package sample.NetWork;


import com.sun.media.sound.DataPusher;
import sample.util.Constants;
import sample.util.DataPacket;
import sample.util.Msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.Socket;

/**
 * Created by HUSTy on 2017/10/20.
 */
public class TcpClient {


    static class ClientSendData implements Runnable{
        private String ipAddress;
        private int portAddress;
        private DataPacket dataPacket;
        private Msg msg=null;
        private String kind;

        public ClientSendData(String kind,String ipAddress,int portAddress,DataPacket dataPacket){
            this.ipAddress=ipAddress;
            this.portAddress=portAddress;
            this.dataPacket = dataPacket;
            this.kind=kind;
        }

        public ClientSendData(String kind, String ipAddress, int portAddress, Msg msg) {
            this.kind = kind;
            this.ipAddress = ipAddress;
            this.portAddress = portAddress;
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                Socket socket=new Socket(ipAddress,portAddress);
                OutputStream outputStream=socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                if(kind.equals(Constants.KIND_SERVER))
                    objectOutputStream.writeObject(dataPacket);
                else objectOutputStream.writeObject(msg);
                objectOutputStream.flush();
                objectOutputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendDataToServer(String kind,String ipAddress, int portAddress, DataPacket dataPacket){
        ClientSendData clientSendData = new ClientSendData(kind,ipAddress,portAddress,dataPacket);
        Thread thread = new Thread(clientSendData);
        thread.start();
    }

    public static void sendDataToClient(String kind,String ipAddress,int portAddress,Msg msg){
        ClientSendData clientSendData = new ClientSendData(kind, ipAddress, portAddress, msg);
        Thread thread = new Thread(clientSendData);
        thread.start();
    }
}
