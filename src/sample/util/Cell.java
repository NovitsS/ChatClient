package sample.util;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class Cell implements Callback<ListView<ClientTarget>,ListCell<ClientTarget>>{

    @Override
    public ListCell<ClientTarget> call(ListView<ClientTarget> clientList) {
        ListCell<ClientTarget> cell=new ListCell<ClientTarget>(){
            @Override
            protected void updateItem(ClientTarget clientTarget, boolean b) {
                super.updateItem(clientTarget, b);
                setGraphic(null);
                setText(null);
                if(clientTarget!=null){
                    HBox hBox=new HBox();
                    Text name = new Text(clientTarget.getId());

                    ImageView stateImageView=new ImageView();
                    Image stateImage=new Image(getClass().getResource("../drawable/"+clientTarget.getIsOnLine()+".png").toString().toLowerCase(),16,16,true,true);
                    stateImageView.setImage(stateImage);

                    ImageView iconImageView=new ImageView();
                    Image iconImage=new Image(getClass().getResource("../drawable/default.png").toString().toLowerCase(),50,50,true,true);
                    iconImageView.setImage(iconImage);

                    hBox.getChildren().addAll(stateImageView,iconImageView, name);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }
}
