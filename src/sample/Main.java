package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.NetWork.TcpServer;
import sample.NetWork.UdpReceiver;
import sample.resources.Controller;
import sample.util.Msg;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class Main extends Application {
    private static TcpServer tcpServer;
    private static Stage stage;
    private static Controller controller;
    public static HashMap<String,ArrayList<Msg>> map;
    public static UdpReceiver udpReceiver=null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage=primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream in=Main.class.getResourceAsStream("resources/login.fxml");
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        fxmlLoader.setLocation(Main.class.getResource("resources/login.fxml"));
        GridPane page=null;
        try {
            page=(GridPane) fxmlLoader.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            in.close();
        }
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(page));
        primaryStage.show();

        controller=fxmlLoader.getController();
        initData();
        map = new HashMap<>();
        map.put("离线消息", new ArrayList<Msg>());
    }


    public static void main(String[] args) {
        launch(args);
    }


    public static void initData(){
        tcpServer=TcpServer.getInstance();
        tcpServer.startLearning();
        tcpServer.setController(controller);
        if(udpReceiver==null){
            udpReceiver = new UdpReceiver(9980, "E:/hello.mv");
            udpReceiver.startReceive();
        }
    }

    public static void replaceScenceContent(String fxml) throws IOException {
        FXMLLoader loader =new FXMLLoader();
        InputStream in=Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        final BorderPane page=(BorderPane) loader.load(in);
        in.close();
        Controller controller=loader.getController();
        tcpServer.setController(controller);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Scene scene=new Scene(page);
                stage.setScene(scene);
            }
        });
    }

}
