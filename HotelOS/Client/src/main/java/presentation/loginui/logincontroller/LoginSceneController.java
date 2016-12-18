package presentation.loginui.logincontroller;

import bl.personnelbl.PersonnelBLService;
import bl.personnelbl.impl.PersonnelBLServiceImpl;
import bl.userbl.UserBLService;
import bl.userbl.impl.UserBlServiceImpl;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import presentation.hotelworkerui.hotelworkerscene.ComWorkerScene;
import presentation.userui.userscene.ComUserScene;
import presentation.util.alert.AlertController;
import presentation.webmanagerui.webmanagerscene.WebmanagerComScene;
import presentation.webmarketerui.webmarketerscene.ComMarketerScene;
import util.PersonnelType;
import util.ResultMessage;
import vo.personnel.PersonnelVO;

import java.rmi.RemoteException;

/**
 * Created by wyj on 2016/11/6.
 * Description: 登录界面
 */
public class LoginSceneController {

    @FXML private Button changeToLogin;
    @FXML private Button changeToRegister;
    @FXML private Label slider;
    @FXML private Button buttonLogin;
    @FXML private Button buttonRegister;
    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;
    @FXML private Button managerEntrance;
    @FXML private Button userEntrance;
    @FXML private Label nameLabel;
    @FXML private Label passwordLabel;
    @FXML private ImageView namePic;
    @FXML private ImageView passwordPic;
    @FXML private Pane movingSection;

    @FXML private PasswordField confirmPasswordField;
    @FXML private Label confirmPasswordLabel;

    private UserBLService userBlService;
    private PersonnelBLService personnelBLService;

    private AlertController alertController;

    private String currentUser = null;

    //用于客户登录和工作人员登录界面切换
    private boolean isFromLogin = true;

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
        currentUser = "user";

        alertController = new AlertController();

        try {
            userBlService = new UserBlServiceImpl();
            personnelBLService = new PersonnelBLServiceImpl();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录、注册的切换
     * 滑块slider特效
     *
     * @param x
     * @param showTag
     * @param hideTag
     * @param showButton
     * @param hideButton
     */
    private void changeLoginAndRegister(int x, Button showTag, Button hideTag, Button showButton, Button hideButton) {
        Timeline timeline = new Timeline();
        timeline.setAutoReverse(false);
        KeyValue kv = new KeyValue(slider.layoutXProperty(), x);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        showTag.setTextFill(Color.DEEPSKYBLUE);
        hideTag.setTextFill(Color.BLACK);
        showButton.setVisible(true);
        hideButton.setVisible(false);
        loginUsername.setText("");
        loginPassword.setText("");
    }
    @FXML
    private void changeToLogin() {
        changeLoginAndRegister(236, changeToLogin, changeToRegister, buttonLogin, buttonRegister);
        namePic.setVisible(true);
        passwordPic.setVisible(true);
        nameLabel.setVisible(false);
        passwordLabel.setVisible(false);
        confirmPasswordField.setVisible(false);
        confirmPasswordLabel.setVisible(false);
        isFromLogin = true;
    }
    @FXML
    private void changeToRegister() {
        changeLoginAndRegister(329, changeToRegister, changeToLogin, buttonRegister, buttonLogin);
        namePic.setVisible(false);
        passwordPic.setVisible(false);
        nameLabel.setVisible(true);
        passwordLabel.setVisible(true);
        confirmPasswordField.setVisible(true);
        confirmPasswordLabel.setVisible(true);
        isFromLogin = false;
    }

    /**
     * 客户登录注册界面切换至酒店工作人员界面特效
     */
    @FXML
    private void changeToManager() {
        currentUser = "personnel";

        changeToLogin.setVisible(false);
        changeToRegister.setVisible(false);
        slider.setVisible(false);
        managerEntrance.setVisible(false);
        userEntrance.setVisible(true);
        loginUsername.setText("");
        loginPassword.setText("");
        if (!isFromLogin) {
            nameLabel.setVisible(false);
            namePic.setVisible(true);
            passwordLabel.setVisible(false);
            passwordPic.setVisible(true);
            buttonRegister.setVisible(false);
            buttonLogin.setVisible(true);
        }

        Timeline timeline = new Timeline();
        timeline.setAutoReverse(false);
        KeyValue kvLoginBtn = new KeyValue(movingSection.layoutYProperty(), 165);
        KeyFrame kfLoginBtn = new KeyFrame(Duration.millis(400), kvLoginBtn);
        timeline.getKeyFrames().add(kfLoginBtn);
        timeline.play();

    }

    /**
     * 酒店工作人员登录界面切换至客户登陆注册界面
     */
    @FXML
    private void changeToUser() {
        currentUser = "user";

        changeToLogin.setVisible(true);
        changeToRegister.setVisible(true);
        slider.setVisible(true);
        managerEntrance.setVisible(true);
        userEntrance.setVisible(false);
        loginUsername.setText("");
        loginPassword.setText("");
        if (!isFromLogin) {
            buttonLogin.setVisible(false);
            buttonRegister.setVisible(true);
            nameLabel.setVisible(true);
            passwordLabel.setVisible(true);
            namePic.setVisible(false);
            passwordPic.setVisible(false);
        }

        Timeline timeline = new Timeline();
        timeline.setAutoReverse(false);
        KeyValue kvLoginBtn = new KeyValue(movingSection.layoutYProperty(), 195);
        KeyFrame kfLoginBtn = new KeyFrame(Duration.millis(400), kvLoginBtn);
        timeline.getKeyFrames().add(kfLoginBtn);
        timeline.play();
    }

    /**
     * 关闭窗口
     */
    @FXML
    private void closeWindow() {
        stage.close();
    }

    /**
     * 最小化窗口
     */
    @FXML
    private void minimizeWindow() {
        stage.setIconified(true);
    }

    /**
     * 登录
     */
    @FXML
    private void Login() {

        try {
            if (currentUser.equals("user")) {
                if (!loginUsername.getText().equals("") && !loginPassword.getText().equals("")) {
                    ResultMessage resultMessage = userBlService.login(loginUsername.getText(), loginPassword.getText());

                    if (resultMessage == ResultMessage.UsernameNotExisted) {
                        alertController.showInputWrongAlert("用户名不存在！", "登录失败");
                        System.out.println("not exits");
                    } else if (resultMessage == ResultMessage.PasswordWrong) {
                        alertController.showInputWrongAlert("密码错误！", "登录失败");
                        System.out.println("wrong password");
                    } else if (resultMessage == ResultMessage.Success) {
                        stage.setScene(new ComUserScene(new Group(), stage, loginUsername.getText()));
                        centerStage(stage);
                        System.out.println("success");
                    }
                } else {
                    alertController.showInputWrongAlert("请输入用户名和密码", "错误提示");
                }

            } else {
                if (!loginUsername.getText().equals("") && !loginPassword.getText().equals("")) {
                    if (isFormatTrue(loginUsername.getText())) {
                        ResultMessage resultMessage = personnelBLService.login(Integer.valueOf(loginUsername.getText()), loginPassword.getText());

                        if (resultMessage == ResultMessage.Success) {
                            System.out.println("personnel login success");
                            PersonnelVO personnelVO = personnelBLService.searchPersonnelByID(Integer.valueOf(loginUsername.getText()));
                            if(personnelVO.personnelType == PersonnelType.HotelWorker){
                                stage.setScene(new ComWorkerScene(new Group(), stage, personnelVO.hotelID));
                                centerStage(stage);
                            }else if(personnelVO.personnelType == PersonnelType.WebMarketer){
                                stage.setScene(new ComMarketerScene(new Group(), stage));
                                centerStage(stage);
                            }else {
                                stage.setScene(new WebmanagerComScene(new Group(), stage));
                                centerStage(stage);
                            }

                        } else {
                            alertController.showInputWrongAlert("登录失败！", "登录失败");
                            System.out.println("personnel login failed");
                        }
                    }
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 注册新用户
     */
    @FXML
    private void userRegister() {
        if (!loginUsername.getText().equals("") && !loginPassword.getText().equals("") && !confirmPasswordField.getText().equals("")) {

            if (loginPassword.getText().equals(confirmPasswordField.getText())) {
                try {
                    ResultMessage resultMessage = userBlService.registerUser(loginUsername.getText(), loginPassword.getText());

                    if (resultMessage == ResultMessage.DataExisted) {
                        alertController.showInputWrongAlert("用户名已存在！", "注册失败");
                        System.out.printf("exits");
                    } else if (resultMessage == ResultMessage.Success) {
                        alertController.showUpdateSuccessAlert("注册成功！", "注册成功");
                    }

                    loginUsername.setText(null);
                    loginPassword.setText(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                alertController.showInputWrongAlert("密码不一致", "错误提示");
            }
        } else {
            alertController.showInputWrongAlert("请输入用户名和密码", "错误提示");
        }

    }

    private void centerStage(Stage newStage){
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        newStage.setX((primScreenBounds.getWidth() - newStage.getWidth()) / 2);
        newStage.setY((primScreenBounds.getHeight() - newStage.getHeight()) / 2);
    }

    private boolean isFormatTrue(String str) {
        char ch[] = str.toCharArray();
        boolean result = true;
        for (int i = 0; i<ch.length; i++) {
            if (ch[i] >= '0' && ch[i] <= '9') {
                result = result && true;
            } else {
                result = result && false;
                break;
            }
        }

        return result;
    }
}
