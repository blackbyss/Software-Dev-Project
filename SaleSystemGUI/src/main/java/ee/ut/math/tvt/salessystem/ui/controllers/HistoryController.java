package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;
import ee.ut.math.tvt.salessystem.logic.History;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryController implements Initializable {

    private final SalesSystemDAO dao;
    private static final Logger log = LogManager.getLogger(HistoryController.class);
    private final History history;

    public HistoryController(SalesSystemDAO dao, History history){
        this.dao = dao;
        this.history = history;
    }

    @FXML
    private Button showBetweenDates;

    @FXML
    private Button showLastTen;

    @FXML
    private Button showAll;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TableView<HistoryItem> contentTable;

    @FXML
    private TableView<Order> orderTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        orderTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Order valitud = orderTable.getSelectionModel().getSelectedItem();
                contentTable.setItems(FXCollections.observableList(valitud.getItems()));
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                orderTable.getSelectionModel().clearSelection();
                contentTable.setItems(null);
            }
        });

    }
    @FXML
    protected void showBetweenDatesClicked() {
        LocalDate begin = startDate.getValue();
        LocalDate end = endDate.getValue();
        orderTable.setItems(FXCollections.observableList(history.showBetweenDates(begin, end)));
    }

    @FXML
    protected void showLastTenClicked() {
        orderTable.setItems(FXCollections.observableList(history.showLast10()));
    }

    @FXML
    protected void showAllClicked() {
        orderTable.setItems(FXCollections.observableList(history.showAll()));
    }

}
