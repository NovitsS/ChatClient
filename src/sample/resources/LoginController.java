package sample.resources;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.NetWork.TcpClient;
import sample.util.ClientTarget;
import sample.util.Constants;
import sample.Main;
import sample.util.DataPacket;
import sample.util.Msg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by HUSTy on 2017/10/20.
 */
public class LoginController implements Initializable,Controller{
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button reverseButton;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField accountText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private TextField ipText;
    @FXML
    private PasswordField newPasswordText;
    private String ip;

    @FXML
    public void onLoginButtonClick(ActionEvent event){
        ip=ipText.getText();
        String me = newPasswordText.getText();
        Constants.setIpMe(me);
        Constants.setServerIP(ip);
        String accountContent=accountText.getText();
        Constants.Me = accountContent;
        String passwordContent=passwordText.getText();
        if(accountContent.length()==0||accountContent.contains(" ")||passwordContent.length()==0||passwordContent.contains(" ")){
            setLoginLabel(Constants.ERROR_illegal);
        }else{
            DataPacket dataPacket=new DataPacket(Constants.KIND_LOGIN);
            dataPacket.setUserAccount(accountContent);
            dataPacket.setUserPassword(passwordContent);
            dataPacket.setUserIP(Constants.IP_ME);
            TcpClient.sendDataToServer(Constants.KIND_SERVER,ip,Constants.PORT_TCP_SERVER,dataPacket);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public  void setLoginLabel(final String content){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loginLabel.setText(content);
            }
        });
    }


    @FXML
    public void onRegisterButtonClick(ActionEvent event){
        ip=ipText.getText();
        Constants.setServerIP(ip);
        String accountContent = accountText.getText();
        String passwordContent = passwordText.getText();
        if(accountContent.length()==0||accountContent.contains(" ")||passwordContent.length()==0||passwordContent.contains(" ")){
            setLoginLabel(Constants.ERROR_illegal);
        }else{
            DataPacket dataPacket=new DataPacket(Constants.KIND_REGISTER);
            dataPacket.setUserAccount(accountContent);
            dataPacket.setUserPassword(passwordContent);
            dataPacket.setUserIP(Constants.IP_ME);
            TcpClient.sendDataToServer(Constants.KIND_SERVER,ip,Constants.PORT_TCP_SERVER,dataPacket);
        }
    }

    @FXML
    public void onReverseButtonClick(ActionEvent event){
        ip=ipText.getText();
        Constants.setServerIP(ip);
        String accountContent = accountText.getText();
        String passwordContent = passwordText.getText();
        String newPasswordContent = newPasswordText.getText();
        if(accountContent.length()==0||accountContent.contains(" ")||passwordContent.length()==0||passwordContent.contains(" ")||newPasswordContent.length()==0||newPasswordContent.contains(" ")){
            setLoginLabel(Constants.ERROR_illegal);
        }else{
            DataPacket dataPacket=new DataPacket(Constants.KIND_REVERSE);
            dataPacket.setUserAccount(accountContent);
            dataPacket.setUserPassword(passwordContent);
            dataPacket.setUserNewPassword(newPasswordContent);
            dataPacket.setUserIP(Constants.IP_ME);
            TcpClient.sendDataToServer(Constants.KIND_SERVER,ip,Constants.PORT_TCP_SERVER,dataPacket);
        }
    }

    @Override
    public void reListView(ClientTarget clientTarget) {

    }

    @Override
    public void reChatPane(Msg msgContent) {

    }
}
