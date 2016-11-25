package presentation.userui.userscene;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.userui.usercontroller.EvaluateOrderController;

import java.io.IOException;

/**
 * Created by wyj on 2016/11/25.
 */
public class EvaluateOrderPane extends Pane {

    public EvaluateOrderPane(Stage primaryStage) {
        loadFxml(primaryStage);
    }

    private void loadFxml(Stage primaryStage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/user/EvaluateOrder.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EvaluateOrderController evaluateOrderController = fxmlLoader.getController();
        evaluateOrderController.launch(primaryStage);
    }
}
