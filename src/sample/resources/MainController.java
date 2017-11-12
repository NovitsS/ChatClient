package sample.resources;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import sample.Main;
import sample.NetWork.UdpSender;
import sample.util.*;
import sample.NetWork.TcpClient;
import sample.util.Cell;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by HUSTy on 2017/10/20.
 */
public class MainController implements Initializable,Controller{
    private static String IP_CURRENT=Constants.IP_SERVER;
    @FXML
    private Button sendButton;
    @FXML
    private Button selectButton;
    @FXML
    private Button addButton;
    @FXML
    private Button offLineButton;
    @FXML
    private TextArea sendTextField;
    @FXML
    private ListView<ClientTarget> clientList;
    @FXML
    private ListView chatPane;
    @FXML
    private TextField addTextArea;
    @FXML
    private Label currentLabel;
    private static String isOnline;


    @FXML
    public void onSendButtonClick(ActionEvent event){
        String content=sendTextField.getText();
        Msg msgContent = new Msg(" ");
        msgContent.setMsgContent(content);
        msgContent.setIcon("icon.JPG");
        reChatPane(msgContent);

        if(isOnline.equals("true")){
            Msg msg=new Msg(Constants.KIND_CLIENT);
            msg.setMsgContent(content);
            msg.setIcon("default.png");
            msg.setId(Constants.Me);
            TcpClient.sendDataToClient(Constants.KIND_CLIENT,Constants.IP_CURRENT_CLIENT,Constants.PORT_CURRENT_CLIENT,msg);
        }else{
            DataPacket dataPacket = new DataPacket(Constants.KIND_OFFLINE_MSG);
            dataPacket.setTargetId(Constants.IP_CURRENT_CLIENT);
            dataPacket.setUserAccount(Constants.Me);
            dataPacket.setContent(content);
            TcpClient.sendDataToServer(Constants.KIND_SERVER,Constants.IP_SERVER,Constants.PORT_TCP_SERVER,dataPacket);
        }
    }

    @FXML
    public void onAddButtonClick(ActionEvent event) {
        String id=addTextArea.getText();
        DataPacket dataPacket=new DataPacket(Constants.KIND_ADD);
        dataPacket.setTargetId(id);
        dataPacket.setUserIP(Constants.IP_ME);
        TcpClient.sendDataToServer(Constants.KIND_SERVER,Constants.IP_SERVER,Constants.PORT_TCP_SERVER,dataPacket);
        addTextArea.setText("");
    }

    @FXML
    public void onOffLineButtonClick(ActionEvent event) {
        DataPacket dataPacket=new DataPacket(Constants.KIND_OFFLINE);
        dataPacket.setUserAccount(Constants.Me);
        TcpClient.sendDataToServer(Constants.KIND_SERVER,Constants.IP_SERVER,Constants.PORT_TCP_SERVER,dataPacket);
    }

    @FXML
    public void onSelectButtonClick(ActionEvent event){
        int result=0;
        File file=null;
        String path=null;
        JFileChooser fileChooser = new JFileChooser();
        FileSystemView fsv=FileSystemView.getFileSystemView();
        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
        fileChooser.setDialogTitle("选择要传输的文件");
        fileChooser.setApproveButtonText("确定");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        result = fileChooser.showOpenDialog(fileChooser);
        if(JFileChooser.APPROVE_OPTION==result){
            path=fileChooser.getSelectedFile().getPath();
            System.out.println(path);
            UdpSender udpSender = new UdpSender(Constants.IP_CURRENT_CLIENT,Constants.PORT_CURRENT_CLIENT, path);
            udpSender.startSend();
            showRecoverText("Me：开始发送"+path+"  ... ...");
        }
        //TODO:调用UDP发包线程
        sendTextField.setText(path);
    }

    public static void showRecoverText(String content){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<ClientTarget> clientTargets = new ArrayList<ClientTarget>();
        setUserList(clientTargets);

        ClientTarget clientTarget=new ClientTarget();
        clientTarget.setIsOnLine("true");
        clientTarget.setIp("127.0.0.1");
        clientTarget.setId("离线消息");
        clientTarget.setPort(Constants.PORT_TCP_CLIENT);
        reListView(clientTarget);

        setChatPane(Main.map.get("离线消息"));

        Constants.IP_CURRENT_CLIENT = clientTarget.getIp();
        Constants.PORT_CURRENT_CLIENT = clientTarget.getPort();
        Constants.USER_CURRENT = clientTarget.getId();
        isOnline = "true";

        clientList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<ClientTarget>() {
                    @Override
                    public void changed(ObservableValue<? extends ClientTarget> observableValue, ClientTarget clientTarget, ClientTarget t1) {
                        currentLabel.setText(t1.getId());
                        setChatPane(Main.map.get(t1.getId()));
                        Constants.IP_CURRENT_CLIENT = t1.getIp();
                        Constants.USER_CURRENT = t1.getId();
                        Constants.PORT_CURRENT_CLIENT = t1.getPort();
                        isOnline = t1.getIsOnLine();
                    }
                });

        requestOfflineMsg();
    }

    private void requestOfflineMsg(){
        DataPacket dataPacket=new DataPacket(Constants.KIND_REQUEST_OFFLINE_MSG);
        dataPacket.setUserAccount(Constants.Me);
        dataPacket.setUserIP(Constants.IP_ME);
        TcpClient.sendDataToServer(Constants.KIND_SERVER,Constants.IP_SERVER,Constants.PORT_TCP_SERVER,dataPacket);
    }

    public void reListView(final ClientTarget clientTarget){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                clientList.getItems().add(clientTarget);
            }
        });
    }

    public void reChatPane(final Msg msgContent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatPane.getItems().add(msgContent);
            }
        });
    }

    public void setUserList(ArrayList<ClientTarget> clientTargets){
        ObservableList<ClientTarget> clients = FXCollections.observableList(clientTargets);
        clientList.setItems(clients);
        clientList.setCellFactory(new Cell());
    }

    public void setChatPane(ArrayList<Msg> list){
        ObservableList<Msg> clients = FXCollections.observableList(list);
        chatPane.setItems(clients);
        chatPane.setCellFactory(new CellB());
    }

    @Override
    public void onLoginButtonClick(ActionEvent event) {

    }

    @Override
    public void onRegisterButtonClick(ActionEvent event) {

    }

    @Override
    public void onReverseButtonClick(ActionEvent event) {

    }

    @Override
    public void setLoginLabel(String content) {

    }

//    public synchronized void addToChat(final String content){
//        final Task<HBox> msg=new Task<HBox>() {
//            @Override
//            protected HBox call() throws Exception {
//                Image image = new Image(getClass().getResource("../drawable/icon.JPG").toString());
//                ImageView imageView = new ImageView(image);
//                imageView.setFitHeight(32);
//                imageView.setFitWidth(32);
//                Text text=new Text(content);
//                HBox x=new HBox();
//                x.getChildren().addAll(imageView,text);
//                return x;
//            }
//        };
//
//        msg.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//            @Override
//            public void handle(WorkerStateEvent workerStateEvent) {
//                chatPane.getItems().add(msg.getValue());
//            }
//        });
//    }

}
