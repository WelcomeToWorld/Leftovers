package presentation.util.buttoncell;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.userui.userscene.OrderDetailUserPane;
import presentation.util.other.ToolTipShow;
import vo.order.OrderVO;

/**
 * Created by wyj on 2016/12/6.
 * Description： 客户订单列表添加按钮的方法：查看订单详情按钮、撤销订单按钮
 */
public class UserOrderListButtonCell extends TableCell<OrderVO, Boolean> {

    final private HBox btnBox = new HBox();
    final private Button checkDetailBtn = new Button();

    private TableView tableView;

    public UserOrderListButtonCell(final Stage stage, final Pane mainPane, final TableView tableView, final String userID) {
        this.tableView = tableView;

        this.getStylesheets().add(UserOrderListButtonCell.class.getResource("/css/hotelworker/hotelworkerstyle.css").toExternalForm());
        Image detailImg = new Image("/img/hotelworker/checkorderdetail.png");
        checkDetailBtn.setGraphic(new ImageView(detailImg));
        checkDetailBtn.getStyleClass().add("TableButtonCell");

        checkDetailBtn.setOnAction(event -> {
            int selectedIndex = getTableRow().getIndex();
            OrderVO orderVO = (OrderVO) tableView.getItems().get(selectedIndex);
            mainPane.getChildren().remove(0);
            mainPane.getChildren().add(new OrderDetailUserPane(stage, mainPane, userID, orderVO.orderID));
        });

    }


    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            btnBox.getChildren().clear();
            checkDetailBtn.setTooltip(ToolTipShow.setTool("查看详情"));
            btnBox.getChildren().add(checkDetailBtn);
            setGraphic(btnBox);
            setText(null);
        }
    }
}
