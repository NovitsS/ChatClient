package sample.resources;

import javafx.event.ActionEvent;
import sample.util.ClientTarget;
import sample.util.Msg;

public interface Controller {
    void onLoginButtonClick(ActionEvent event);
    void onRegisterButtonClick(ActionEvent event);
    void onReverseButtonClick(ActionEvent event);
    void setLoginLabel(String content);
    void reListView(ClientTarget clientTarget);
    void reChatPane(final Msg msgContent);

}
