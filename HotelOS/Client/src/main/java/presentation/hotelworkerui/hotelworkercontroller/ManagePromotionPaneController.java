package presentation.hotelworkerui.hotelworkercontroller;

import bl.promotionbl.PromotionBLService;
import bl.promotionbl.impl.PromotionBlServiceImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import presentation.util.alert.AlertController;
import presentation.util.other.DisableColumnChangeListener;
import presentation.util.other.MySlider;
import util.PromotionType;
import vo.promotion.PromotionVO;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Hitiger on 2016/11/20.
 * Description :
 */
public class ManagePromotionPaneController {

    //生日优惠
    @FXML private Button birthBtn;
    @FXML private Button addBirthBtn;
    @FXML private Button modifyBirthBtn;
    @FXML private Button deleteBirthBtn;
    @FXML private Button confirmBirthBtn;
    @FXML private Button cancelBirthBtn;
    @FXML private TextField birthDiscountField;
    @FXML private VBox   birthVBox;
    @FXML private HBox   addBirthHBox;
    @FXML private TableView birthTable;
    @FXML private TableColumn birthRoomTypeCol;
    @FXML private TableColumn birthDiscountCol;
    @FXML private TableColumn birthPriceCol;

    //多间预订优惠
    @FXML private Button addRoomBtn;
    @FXML private Button confirmRoomBtn;
    @FXML private Button cancelRoomBtn;
    @FXML private TextField leastRoomsField;
    @FXML private TextField roomDiscountField;
    @FXML private VBox   roomVBox;
    @FXML private HBox   addRoomHBox;
    @FXML private TableView roomTable;
    @FXML private TableColumn roomTypeCol;
    @FXML private TableColumn roomLeastCol;
    @FXML private TableColumn roomDiscountCol;
    @FXML private TableColumn roomPriceCol;
    @FXML private TableColumn roomOpCol;

    //特定期间优惠
    @FXML private Button addTimeBtn;
    @FXML private Button confirmTimeBtn;
    @FXML private Button cancelTimeBtn;
    @FXML private VBox   timeVBox;
    @FXML private HBox   addTimeHBox;
    @FXML private TableView timeTable;
    @FXML private TableColumn timeRoomTypeCol;
    @FXML private TableColumn timeStartCol;
    @FXML private TableColumn timeEndCol;
    @FXML private TableColumn timeDiscountCol;
    @FXML private TableColumn timePriceCol;

    //合作企业优惠
    @FXML private Button addComBtn;
    @FXML private Button modifyComBtn;
    @FXML private Button deleteComBtn;
    @FXML private Button confirmComBtn;
    @FXML private Button cancelComBtn;
    @FXML private VBox   comVBox;
    @FXML private HBox   addComHBox;
    @FXML private TableView comTable;
    @FXML private TableColumn comNameCol;
    @FXML private TableColumn comRoomTypeCol;
    @FXML private TableColumn comDiscountCol;
    @FXML private TableColumn comPriceCol;

    //滑块
    @FXML private Label sliderPromotionLabel;


    private AlertController alertController;
    private ArrayList<VBox> vBoxes;
    //TODO 判断是添加还是修改
    private Boolean isBirthAdd = false;
    private Boolean isRoomAdd = false;
    private Boolean isTimeAdd = false;
    private Boolean isExistCom = false;
    private PromotionBLService promotionBLService;
    private ProListButtonCell proRoomListButtonCell;

    public void launch() {
        alertController = new AlertController();
        vBoxes = new ArrayList<>(Arrays.asList(birthVBox,roomVBox,timeVBox,comVBox));

        //设置生日优惠按钮默认被选中
        makeBirthFocused();
        initService();
        //默认先显示生日优惠
        showBirth();
    }

    private void initService() {
        try {
            promotionBLService = new PromotionBlServiceImpl();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showBirth(){
        showVBox(birthVBox);

        initBirthTable();
        setBirthComponentsVisible(false);

        MySlider.moveSliderLabel(sliderPromotionLabel,36);
    }

    private void initBirthTable() {
        birthRoomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        birthDiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        birthPriceCol.setCellValueFactory(new PropertyValueFactory<>("bestPrice"));
        final TableColumn[] birthColumns = {birthRoomTypeCol, birthDiscountCol, birthPriceCol};
        birthTable.getColumns().addListener(new DisableColumnChangeListener(birthTable, birthColumns));

        initData(birthTable, PromotionType.BirthdayPromotion);
    }

    @FXML
    private void showRoom(){
        showVBox(roomVBox);

        initRoomTable();
        setRoomsComponentsVisible(false);

        MySlider.moveSliderLabel(sliderPromotionLabel,168);
    }

    private void initRoomTable() {
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomLeastCol.setCellValueFactory(new PropertyValueFactory<>("leastRooms"));
        roomDiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        roomPriceCol.setCellValueFactory(new PropertyValueFactory<>("bestPrice"));
        roomOpCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                proRoomListButtonCell = new ProListButtonCell();
                return proRoomListButtonCell;
            }
        });
        final TableColumn[] roomColumns = {roomTypeCol, roomLeastCol, roomDiscountCol, roomPriceCol, roomOpCol};
        roomTable.getColumns().addListener(new DisableColumnChangeListener(roomTable, roomColumns));

        initData(roomTable, PromotionType.MULTI_ROOMS_HP);
    }

    @FXML
    private void showTime(){
        showVBox(timeVBox);

        initTimeTable();

        MySlider.moveSliderLabel(sliderPromotionLabel,300);
    }

    private void initTimeTable() {
        timeRoomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        timeStartCol.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        timeEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        timeDiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        timePriceCol.setCellValueFactory(new PropertyValueFactory<>("bestPrice"));
        final TableColumn[] timeColumns = {timeRoomTypeCol, timeStartCol, timeEndCol, timeDiscountCol, timePriceCol};
        timeTable.getColumns().addListener(new DisableColumnChangeListener(timeTable, timeColumns));

        initData(timeTable, PromotionType.SpecialTimePromotion);
    }

    @FXML
    private void showCom(){
        showVBox(comVBox);
        setAddComComponentsVisible(false);
        setOriComComponentsVisible(true);

        initComTable();

        MySlider.moveSliderLabel(sliderPromotionLabel,432);
    }

    private void initComTable() {
        comNameCol.setCellValueFactory(new PropertyValueFactory<>(""));
        comRoomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        comDiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        comPriceCol.setCellValueFactory(new PropertyValueFactory<>("bestPrice"));
        final TableColumn[] comColumns = {comNameCol, comRoomTypeCol, comDiscountCol, comPriceCol};
        comTable.getColumns().addListener(new DisableColumnChangeListener(comTable, comColumns));

        initData(comTable, PromotionType.EnterprisePromotion);
    }

    private void showVBox(VBox visibleBox){
        for (VBox vbox: vBoxes) {
            if(vbox == visibleBox) vbox.setVisible(true);
            else vbox.setVisible(false);
        }
    }

    private void initData(TableView tableView, PromotionType promotionType) {
        try {
            //TODO 更换hotelID
            tableView.setItems(FXCollections.observableArrayList(promotionBLService.viewPromotionList(522000, promotionType)));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addBirthPromotion(){
        isBirthAdd = true;
        setBirthComponentsVisible(true);
    }

    @FXML
    private void confirmBirthAdd(){
        PromotionVO promotionVO = new PromotionVO();

        try {
            promotionVO.discount = Double.parseDouble(birthDiscountField.getText());
            if(isBirthAdd){
                //TODO 更换hotelID
                promotionVO.hotelID = 522000;
                promotionVO.promotionType = PromotionType.BirthdayPromotion;
                promotionBLService.create(promotionVO);
            }else{
                promotionVO.promotionID = ((PromotionVO) birthTable.getItems().get(0)).promotionID;
                promotionBLService.update(promotionVO);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        initData(birthTable, PromotionType.BirthdayPromotion);
        setBirthComponentsVisible(false);
    }

    @FXML
    private void cancelBirthAdd(){
        setBirthComponentsVisible(false);
    }

    @FXML
    private void modifyBirthPromotion(){
        isBirthAdd = false;
        setBirthComponentsVisible(true);
    }

    @FXML
    private void deleteBirthPromotion(){
        long promotionID = ((PromotionVO) birthTable.getItems().get(0)).promotionID;
        try {
            promotionBLService.delete(promotionID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        initData(birthTable, PromotionType.BirthdayPromotion);
        setBirthComponentsVisible(false);
    }

    private void setBirthComponentsVisible(Boolean isVisible){
        addBirthHBox.setVisible(isVisible);
        confirmBirthBtn.setVisible(isVisible);
        cancelBirthBtn.setVisible(isVisible);

        //如果已添加促销策略 则不再显示添加按钮
        if(birthTable.getItems().isEmpty()){
            setBirthOpBtnVisible(false);
        }else {
            setBirthOpBtnVisible(true);
        }
    }

    private void setBirthOpBtnVisible(Boolean isVisible){
        addBirthBtn.setVisible(!isVisible);
        modifyBirthBtn.setVisible(isVisible);
        deleteBirthBtn.setVisible(isVisible);
    }
    @FXML
    private void addRoomPromotion(){
        isRoomAdd = true;
        setRoomsComponentsVisible(true);
    }

    @FXML
    private void confirmRoomAdd(){
        PromotionVO promotionVO = new PromotionVO();
        try {
            promotionVO.leastRooms = Integer.parseInt(leastRoomsField.getText());
            promotionVO.discount = Double.parseDouble(roomDiscountField.getText());
            if(isRoomAdd){
                //TODO 更换hotelID
                promotionVO.hotelID = 522000;
                promotionVO.promotionType = PromotionType.MULTI_ROOMS_HP;
                promotionBLService.create(promotionVO);
            }else{
                promotionVO.promotionID = ((PromotionVO) roomTable.getItems().get(proRoomListButtonCell.getSelectedIndex())).promotionID;
                promotionBLService.update(promotionVO);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        initData(roomTable, PromotionType.MULTI_ROOMS_HP);
        setRoomsComponentsVisible(false);
    }

    @FXML
    private void cancelRoomAdd(){
        setRoomsComponentsVisible(false);
    }

    private void setRoomsComponentsVisible(Boolean isVisible){
        addRoomHBox.setVisible(isVisible);
        confirmRoomBtn.setVisible(isVisible);
        cancelRoomBtn.setVisible(isVisible);
        addRoomBtn.setVisible(!isVisible);
    }

    @FXML
    private void addTimePromotion(){
    }

    @FXML
    private void confirmTimeAdd(){
    }

    @FXML
    private void cancelTimeAdd(){
        setTimeComponentsVisible(false);
    }

    private void setTimeComponentsVisible(Boolean isVisible){
        addTimeHBox.setVisible(isVisible);
        confirmTimeBtn.setVisible(isVisible);
        cancelTimeBtn.setVisible(isVisible);
        addTimeBtn.setVisible(!isVisible);
    }

    @FXML
    private void addComPromotion(){
        setAddComComponentsVisible(true);
        setOriComComponentsVisible(false);
    }

    @FXML
    private void confirmComAdd(){
        //TODO 如果已添加促销策略 则不再显示添加按钮
        isExistCom = true;
        setAddComComponentsVisible(false);
        setOriComComponentsVisible(true);
    }

    @FXML
    private void cancelComAdd(){
        setAddComComponentsVisible(false);
        setOriComComponentsVisible(true);
    }

    @FXML
    private void modifyComPromotion(){

    }

    @FXML
    private void deleteComPromotion(){

    }

    private void setAddComComponentsVisible(Boolean isVisible){
        addComHBox.setVisible(isVisible);
        confirmComBtn.setVisible(isVisible);
        cancelComBtn.setVisible(isVisible);
    }
    private void setOriComComponentsVisible(Boolean isVisible){
        //TODO 如果已添加促销策略 则不再显示添加按钮
        if (!isExistCom)addComBtn.setVisible(isVisible);
        else {
            modifyComBtn.setVisible(isVisible);
            deleteComBtn.setVisible(isVisible);
        }
    }

    private void makeBirthFocused() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                birthBtn.requestFocus();
            }
        });
    }

    /**
     * 操作栏按钮
     */
    private class ProListButtonCell extends TableCell<PromotionVO, Boolean> {
        final private HBox btnBox = new HBox();
        final private Button modifyButton = new Button();
        final private Button deleteButton = new Button();
        private int selectedIndex;

        public ProListButtonCell() {
            Image modifyImage = new Image("/img/hotelworker/modifyroom.png");
            modifyButton.setGraphic(new ImageView(modifyImage));
            modifyButton.getStyleClass().add("TableButtonCell");

            Image deleteImage = new Image("/img/hotelworker/deleteroom.png");
            deleteButton.setGraphic(new ImageView(deleteImage));
            deleteButton.getStyleClass().add("TableButtonCell");

            modifyButton.setOnAction(event -> {
                //多间预订
                isRoomAdd = false;
                roomTable.setDisable(true);
                setRoomsComponentsVisible(true);

                selectedIndex = getTableRow().getIndex();
            });

            deleteButton.setOnAction(event -> {

                //多间预订
                if (alertController.showConfirmDeleteAlert("您确定要删除此优惠吗？", "确认删除")) {
                    selectedIndex = getTableRow().getIndex();
                    long promotionID = ((PromotionVO) roomTable.getItems().get(selectedIndex)).promotionID;
                    try {
                        promotionBLService.delete(promotionID);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    initData(roomTable, PromotionType.MultipleRoomPromotion);
                    setRoomsComponentsVisible(false);
                }
            });

            btnBox.setSpacing(10);
            btnBox.setAlignment(Pos.CENTER);
            btnBox.setPadding(new Insets(0, 10, 0, 20));
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                btnBox.getChildren().clear();
                btnBox.getChildren().addAll(modifyButton, deleteButton);
                setText(null);
                setGraphic(btnBox);
            }
        }

        public int getSelectedIndex() {
            return selectedIndex;
        }
    }
}
