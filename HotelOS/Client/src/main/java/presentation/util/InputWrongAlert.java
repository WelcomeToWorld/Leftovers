package presentation.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 * Created by Hitiger on 2016/11/25.
 * Description :
 */
public class InputWrongAlert extends Alert{

    public InputWrongAlert(String contentText,String title){
        this(AlertType.WARNING,contentText, ButtonType.OK);
        this.setTitle(title);
        this.setHeaderText("");
        this.initStyle(StageStyle.UTILITY);
    }

    public InputWrongAlert(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
    }
}
