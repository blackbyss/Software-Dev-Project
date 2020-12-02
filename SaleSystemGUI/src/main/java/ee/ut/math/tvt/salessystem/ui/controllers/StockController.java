package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import ee.ut.math.tvt.salessystem.logic.Warehouse;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import validators.StockAddValidator;
import validators.StockEditValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    private final SalesSystemDAO dao;
    private final StockAddValidator addValidator;
    private final StockEditValidator editValidator;
    private static final Logger log = LogManager.getLogger(StockController.class);
    private final Warehouse warehouse;

    @FXML
    private Label amountText;

    @FXML
    private Label nameText;

    @FXML
    private Label priceText;

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
    private Button addExisting;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<StockItem> warehouseTableView;

    @FXML
    private Button refreshButton;

    @FXML
    private Button editButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button removeItem;

    @FXML
    private HBox borderPaneHbox;

    public StockController(SalesSystemDAO dao, StockAddValidator addValidator, StockEditValidator editValidator, Warehouse warehouse) {
        this.dao = dao;
        this.addValidator = addValidator;
        this.editValidator = editValidator;
        this.warehouse = warehouse;
    }

    //Default window state
    private void defaultWindow() {

        //Remove tableview selection
        warehouseTableView.getSelectionModel().clearSelection();

        //Remove previous button bindings
        confirmButton.disableProperty().unbind();

        //Availability of buttons
        confirmButton.setDisable(true);
        insertBar.setDisable(true);
        cancelButton.setDisable(true);
        insertAmount.setDisable(false);
        insertName.setDisable(false);
        insertPrice.setDisable(false);
        refreshButton.setDisable(false);

        //Change text of textfields and buttons
        insertPrice.setText("");
        insertAmount.setText("");
        insertName.setText("");
        nameText.setText("Name:");
        priceText.setText("Price:");
        amountText.setText("Amount:");
        addExisting.setText("Add existing");

        //Update warehouse stock
        updateWarehouseState();

        //Generate ID-s
        insertBar.setText(warehouse.autoID());

        //Append bindings to buttons
        setBindingsToButtons();

    }

    //"Add Existing" button window state
    private void addExistingButtonWindowState(StockItem valitud) {

        //Remove tableview selection
        removeItem.disableProperty().unbind();
        editButton.disableProperty().unbind();

        //Availability of buttons
        insertPrice.setDisable(true);
        refreshButton.setDisable(true);
        insertName.setDisable(true);
        removeItem.setDisable(true);
        editButton.setDisable(true);
        cancelButton.setDisable(false);

        //Change text of textfields and buttons
        insertBar.setText(String.valueOf(valitud.getId()));
        amountText.setText("Increase:");
        addExisting.setText("Add");

    }

    //"Edit" button window state
    private void editButtonWindowState(StockItem valitud) {

        //Remove tableview selection
        addExisting.disableProperty().unbind();
        addItem.disableProperty().unbind();
        removeItem.disableProperty().unbind();
        editButton.disableProperty().unbind();

        //Availability of buttons
        refreshButton.setDisable(true);
        insertAmount.setDisable(true);
        removeItem.setDisable(true);
        addExisting.setDisable(true);
        addItem.setDisable(true);
        editButton.setDisable(true);
        cancelButton.setDisable(false);

        //Change text of textfields and buttons
        insertBar.setText(String.valueOf(valitud.getId()));
        insertAmount.setText(String.valueOf(valitud.getQuantity()));
        nameText.setText("New name:");
        priceText.setText("New price:");

    }

    //Bindings for buttons which activate with tableview entity selection
    private void setBindingsToButtons() {

        //BooleanBInding for adding new product
        BooleanBinding controlAddItem = insertBar.textProperty().isEmpty().or(insertName.textProperty().isEmpty())
                .or(insertAmount.textProperty().isEmpty()).or(insertPrice.textProperty().isEmpty());

        //Binded buttons
        addItem.disableProperty().bind(controlAddItem);
        removeItem.disableProperty().bind(Bindings.isEmpty(warehouseTableView.getSelectionModel().getSelectedItems()));
        editButton.disableProperty().bind(Bindings.isEmpty(warehouseTableView.getSelectionModel().getSelectedItems()));
        addExisting.disableProperty().bind(Bindings.isEmpty(warehouseTableView.getSelectionModel().getSelectedItems()));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Default tab view
        defaultWindow();

        //Choosing row and canceling selected
        warehouseTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                warehouseTableView.getSelectionModel().getSelectedItem();
            }
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                warehouseTableView.getSelectionModel().clearSelection();
            }
        });

        borderPaneHbox.getStyleClass().add("HBox");
    }

    @FXML
    void addItemClicked(MouseEvent event) {

        //Remove tableview selection
        warehouseTableView.getSelectionModel().clearSelection();

        //Features of new product
        long bar = Long.parseLong(insertBar.getText());
        int amount = Integer.parseInt(insertAmount.getText());
        String name = insertName.getText();
        double price = Double.parseDouble(insertPrice.getText());

        //Validator for checking product features
        if (addValidator.validateAdd(amount, price, name)) {
            warehouse.addToStock(new StockItem(bar, name, "", price, amount));
            defaultWindow();
        }

    }

    @FXML
    void addExistingItem(MouseEvent event) {

        //Choose product of which amount we want to increase
        StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();
        addExisting.disableProperty().unbind();

        //Binding for "Add Existing" button
        BooleanBinding controlAddButton = insertAmount.textProperty().isEmpty();
        addExisting.disableProperty().bind(controlAddButton);

        //Change window state
        if (addExisting.getText().equals("Add existing")) {
            addExistingButtonWindowState(valitud);

            //Append inserted amount
        } else {

            //Product to be increased features
            long bar = Long.parseLong(insertBar.getText());
            int amountInc = Integer.parseInt(insertAmount.getText());

            //Check for valid inputs
            if (addValidator.validateExisting(amountInc, bar)) {
                warehouse.addExisting(bar, amountInc);
                defaultWindow();
            }
        }

    }

    @FXML
    void cancelButtonClicked(MouseEvent event) {

        defaultWindow();

    }

    @FXML
    void removeItemClicked(MouseEvent event) {

        //Choose product to remove from warehouse
        StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();
        warehouse.deleteFromStock(valitud.getId());

        updateWarehouseState();

        //Incase we remove product with non maximum ID
        insertBar.setText(warehouse.autoID());

        //Logging
        log.debug("StockItem to remove: " + valitud.toString());

    }

    @FXML
    void editButtonClicked(MouseEvent event) {

        //Choose product to edit
        StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();

        //Edit window state
        editButtonWindowState(valitud);

        //Binding for "Confirm" button
        BooleanBinding controlConfirmItem = (insertName.textProperty().isEmpty()).and(insertPrice.textProperty().isEmpty());
        confirmButton.disableProperty().bind(controlConfirmItem);

    }

    @FXML
    void confirmButtonClicked(MouseEvent event) {

        if (!insertPrice.getText().equals("")) {
            if (editValidator.valideeriMuutus(Double.parseDouble(insertPrice.getText()))) {
                warehouse.editStockItemPrice(Long.parseLong(insertBar.getText()), Double.parseDouble(insertPrice.getText()));
                if (!insertName.getText().equals("")){
                    warehouse.editStockItemName(Long.parseLong(insertBar.getText()), insertName.getText());
                }
                defaultWindow();
            }
        }
        else if (!insertName.getText().equals("")) {
            warehouse.editStockItemName(Long.parseLong(insertBar.getText()), insertName.getText());
            if (!insertPrice.getText().equals("")) {
                if (editValidator.valideeriMuutus(Double.parseDouble(insertPrice.getText()))) {
                    warehouse.editStockItemPrice(Long.parseLong(insertBar.getText()), Double.parseDouble(insertPrice.getText()));
                }
            }
            defaultWindow();
        }

    }

    @FXML
    public void refreshButtonClicked() {

        //Remove tableview selection
        warehouseTableView.getSelectionModel().clearSelection();

        updateWarehouseState();
        insertBar.setText(warehouse.autoID());

        //Logging
        log.info("Refreshing");  //Kontroll konsoolile, et veenduda nupu töötamises

    }

    private void updateWarehouseState() {

        warehouseTableView.setItems(FXCollections.observableList(warehouse.refreshWarehouse()));
        warehouseTableView.refresh();

    }
}



