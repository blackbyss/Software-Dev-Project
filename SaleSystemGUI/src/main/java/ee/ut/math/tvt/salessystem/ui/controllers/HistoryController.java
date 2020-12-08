package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;
import ee.ut.math.tvt.salessystem.logic.History;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryController implements Initializable {

    private final SalesSystemDAO dao;
    private static final Logger log = LogManager.getLogger(HistoryController.class);
    private final History history;
    private final ToggleGroup toggleGroup = new ToggleGroup();

    public HistoryController(SalesSystemDAO dao, History history) {

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
    private RadioButton showBetweenBestRadio;

    @FXML
    private RadioButton showLastBestRadio;

    @FXML
    private RadioButton showAllBestRadio;

    @FXML
    private TableView<HistoryItem> contentTable;

    @FXML
    private TableView<Order> orderTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setUp();

    }

    @FXML
    protected void showBetweenDatesClicked() {

        try {

            LocalDate begin = startDate.getValue();
            LocalDate end = endDate.getValue();

            if (history.showBetweenDates(begin, end) != null || end != null && begin != null) {
                orderTable.setItems(FXCollections.observableList(history.showBetweenDates(begin, end)));
            } else {
                showHistoryEmpty();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error!");
            alert.setContentText("Choose date");
            alert.show();
        }

    }

    @FXML
    protected void showLastTenClicked() {

        if (history.showLast10() != null) {
            orderTable.setItems(FXCollections.observableList(history.showLast10()));
        } else {
            showHistoryEmpty();
        }

    }

    @FXML
    protected void showAllClicked() {

        if (history.showAll() != null) {
            orderTable.setItems(FXCollections.observableList(history.showAll()));
        } else {
            showHistoryEmpty();
        }

    }

    /**
     * Display error
     */
    protected void showHistoryEmpty() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error!");
        alert.setContentText("No purchases were made during the selected time period");
        alert.show();

    }

    @FXML
    protected void bestSellingButtonClicked(MouseEvent event) {

        try {
            LocalDate begin = startDate.getValue();
            LocalDate end = endDate.getValue();
            HashMap<String, Integer> bestSelling = history.getBestSellingItems(this.toggleGroup.getSelectedToggle().getUserData().toString(), begin, end);
            String[] numbrid = new String[]{"One", "Two", "Three", "Four", "Five"};

            if (bestSelling != null) {

                ArrayList<String> nimed = new ArrayList<>(bestSelling.keySet());
                ArrayList<Integer> kogused = new ArrayList<>(bestSelling.values());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Best-Selling products");
                alert.setHeaderText(numbrid[bestSelling.size() - 1] + " most sold items:");

                StringBuilder items = new StringBuilder();

                for (int i = 0; i < nimed.size(); i++) {
                    items.append(nimed.get(i)).append(": ").append(kogused.get(i)).append("\n");
                }

                alert.setContentText(items.toString());
                alert.show();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("History Error");
                alert.setHeaderText("Error!");
                alert.setContentText("No items were sold during the selected time period");
                alert.show();

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error!");
            alert.setContentText("Choose date");
            alert.show();
        }

    }

    protected void setUp() {

        //Toggelgroup for radio buttons
        showBetweenBestRadio.setToggleGroup(this.toggleGroup);
        showBetweenBestRadio.setUserData("1");
        showLastBestRadio.setToggleGroup(this.toggleGroup);
        showLastBestRadio.setUserData("2");
        showAllBestRadio.setToggleGroup(this.toggleGroup);
        showAllBestRadio.setUserData("3");
        showBetweenBestRadio.setSelected(true);

        //Selecting order
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
}
