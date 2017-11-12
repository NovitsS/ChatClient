package sample.NetWork;


import sample.Main;
import sample.resources.Controller;
import sample.util.ClientTarget;
import sample.util.Constants;
import sample.util.Msg;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by HUSTy on 2017/10/20.
 */
public class TcpServer {
    private ServerSocket serverSocket;
    private volatile static TcpServer instance;
    private Controller controller=null;
    public static TcpServer getInstance(){
        if(instance==null)
            synchronized (TcpServer.class){
            if (instance==null)
                instance=new TcpServer();
            }
        return instance;
    }

    private TcpServer(){
        try {
            serverSocket = new ServerSocket(Constants.PORT_TCP_CLIENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class RunServer implements Runnable{
        @Override
        public void run() {
            while (true){
                try {
                    Socket socket=serverSocket.accept();
                    InputStream inputStream=socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    final Msg msg = (Msg) objectInputStream.readObject();
                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            dispatchMsg(msg);
                        }
                    });
                    thread.start();
                    objectInputStream.close();
                    inputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startLearning(){
        Thread thread=new Thread(new RunServer());
        thread.start();
    }

    private void dispatchMsg(Msg msg){
        if(msg.kind.equals(Constants.KIND_SERVER)){
            switch (msg.content){
                case Constants.ERROR_ACCOUNT_PASSWORD:
                    controller.setLoginLabel(Constants.ERROR_ACCOUNT_PASSWORD);
                    break;
                case Constants.ERROR_HAS:
                    controller.setLoginLabel(Constants.ERROR_HAS);
                    break;
                case Constants.ERROR_NO_MATCH:
                    controller.setLoginLabel(Constants.ERROR_NO_MATCH);
                    break;
                case Constants.SUCESS_LOGIN:
                    controller.setLoginLabel(Constants.SUCESS_LOGIN);
                    try {
                        Main.replaceScenceContent(Constants.MainSrc);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.SUCESS_REGISTER:
                    controller.setLoginLabel(Constants.SUCESS_REGISTER);
                    break;
                case Constants.SUCESS_REVERSE:
                    controller.setLoginLabel(Constants.SUCESS_REVERSE);
                    break;
                case Constants.SUCESS_ADD:
                    ClientTarget clientTarget=new ClientTarget();
                    clientTarget.setId(msg.getId());
                    clientTarget.setIp(msg.getIp());
                    clientTarget.setIsOnLine(msg.getIsOnLine());
                    clientTarget.setPort(msg.getPort());
                    controller.reListView(clientTarget);
                    if(!Main.map.containsKey(msg.getId())){
                        Main.map.put(msg.getId(),new ArrayList<Msg>());
                    }
                    break;
                case "offlineMsg":
                    if (Constants.USER_CURRENT.equals(msg.getId())) {
                        controller.reChatPane(msg);
                    }else {
                        Main.map.get("离线消息").add(msg);
                    }
                    break;
            }
        }else{
            if(Constants.USER_CURRENT.equals(msg.getId()))
                controller.reChatPane(msg);
            else{
                if(!Main.map.containsKey(msg.getId())) {
                    Main.map.put(msg.getId(), new ArrayList<Msg>());
                    Main.map.get(msg.getId()).add(msg);
                }
                else Main.map.get(msg.getId()).add(msg);
            }
        }

    }

    public void setController(Controller controller){
        this.controller=controller;
    }

}
