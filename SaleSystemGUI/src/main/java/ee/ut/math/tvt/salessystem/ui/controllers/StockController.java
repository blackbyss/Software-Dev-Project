package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import ee.ut.math.tvt.salessystem.logic.Warehouse;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    private final SalesSystemDAO dao;


    @FXML
    private TextField insertBar;

    @FXML
    private TextField insertAmount;

    @FXML
    private TextField insertName;

    @FXML
    private TextField insertPrice;

    @FXML
    private Button addItem;

    @FXML
    private TableView<StockItem> warehouseTableView;

    @FXML
    private Button refreshButton;

    @FXML
    private Button removeButton;


    public StockController(SalesSystemDAO dao) {
        this.dao = dao;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshStockItems();
        addItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addStockItem(Long.parseLong(insertBar.getText()), Integer.parseInt(insertAmount.getText())
                        , String.valueOf(insertName.getText()), Double.parseDouble(insertPrice.getText()));
                refreshStockItems();
            }
        });
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshButtonClicked();
            }
        });
        removeButton.setOnAction(e -> {
            StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();
            warehouseTableView.getItems().remove(valitud);
        });
    }

    @FXML
    public void refreshButtonClicked() {
        refreshStockItems();
        System.out.println("Värskendab");
    }

    private void refreshStockItems() {
        warehouseTableView.setItems(FXCollections.observableList(dao.findStockItems()));
        warehouseTableView.refresh();
    }

    private void addStockItem(Long id, int amount, String name, double price) {
        StockItem stockItem = new StockItem(id, name, "", price, amount);
        dao.saveStockItem(stockItem);
    }

    private void removeStockItem(){

    }

}
