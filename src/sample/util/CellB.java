package sample.util;


import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class CellB implements Callback<ListView<Msg>,ListCell<Msg>> {
    @Override
    public ListCell<Msg> call(ListView<Msg> msgContentListView) {
        ListCell<Msg> cell=new ListCell<Msg>(){
            @Override
            protected void updateItem(Msg MsgContent, boolean b) {
                super.updateItem(MsgContent, b);
                setGraphic(null);
                setText(null);
                if(MsgContent!=null){
                    HBox hBox=new HBox();
                    Text name = new Text(MsgContent.getMsgContent());

                    ImageView stateImageView=new ImageView();
                    Image stateImage=new Image(getClass().getResource("../drawable/"+MsgContent.getIcon()).toString().toLowerCase(),30,30,true,true);
                    stateImageView.setImage(stateImage);
                    hBox.getChildren().addAll(stateImageView,name);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }
}
