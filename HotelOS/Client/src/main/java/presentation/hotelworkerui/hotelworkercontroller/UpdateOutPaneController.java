package presentation.hotelworkerui.hotelworkercontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.hotelworkerui.hotelworkerscene.FindOrderPane;
import presentation.hotelworkerui.hotelworkerscene.UpdateOfflinePane;

/**
 * Created by Hitiger on 2016/11/20.
 * Description :
 */
public class UpdateOutPaneController {
    private Stage stage;
    private Pane mainPane;

    //选择入住方式的组件
    @FXML Button outOnlineBtn;
    @FXML Button outOfflineBtn;

    public void launch(Stage primaryStage,Pane mainPane) {
        this.stage = primaryStage;
        this.mainPane = mainPane;
    }
    @FXML
    private void closeWindow(){
        stage.close();
    }

    @FXML
    private void minWindow(){
        stage.setIconified(true);
    }

    @FXML
    private void outOnline(){
        mainPane.getChildren().remove(0);
        mainPane.getChildren().add(new FindOrderPane(stage,mainPane,false));
    }

    @FXML
    private void outOffline(){
        mainPane.getChildren().remove(0);
        mainPane.getChildren().add(new UpdateOfflinePane(stage,mainPane));
    }
}
