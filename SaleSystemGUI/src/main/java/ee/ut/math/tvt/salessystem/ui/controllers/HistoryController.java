package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryController implements Initializable {

    private final SalesSystemDAO dao;
    private static final Logger log = LogManager.getLogger(HistoryController.class);

    public HistoryController(SalesSystemDAO dao){
        this.dao = dao;
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
    private TableView<HistoryItem> historyTable;
    @FXML
    private TableView<SoldItem> purchaseTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("History controller initialized");
        historyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) return;
            purchaseTable.setItems(FXCollections.observableList(newSelection.getItems()));
        });
    }
    @FXML
    protected void showBetweenDatesClicked() {
        if (startDate.getValue() == null || endDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Dates not selected");
            alert.setContentText("Please select valid dates");
            alert.showAndWait();
            return;
        }
        LocalDate start = startDate.getValue().minusDays(1);
        LocalDate end = endDate.getValue().plusDays(1);
        List<HistoryItem> historyItems = new ArrayList<>();
        for (HistoryItem historyItem : dao.findHistoryItems()) {
            if (historyItem.getDate().isAfter(start) && historyItem.getDate().isBefore(end))
                historyItems.add(historyItem);
        }
        log.debug("Showing purchases made between " + start + " (including) to " + end + " (including).");
        historyTable.setItems(FXCollections.observableList(historyItems));
    }

    @FXML
    protected void showLastTenClicked() {
        log.debug("Showing last 10 purchases.");
        List<HistoryItem> historyItems = dao.findHistoryItems();
        if (historyItems.size() > 10)
            historyItems = historyItems.subList(historyItems.size()-10, historyItems.size());
        historyTable.setItems(FXCollections.observableList(historyItems));
    }

    @FXML
    protected void showAllClicked() {
        List<HistoryItem> historyItems = dao.findHistoryItems();
        log.debug("Showing all " + historyItems.size() + " past purchases.");
        historyTable.setItems(FXCollections.observableList(historyItems));
    }

}
