package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
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


    @FXML
    private Label amountText;

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
    private TableView<StockItem> warehouseTableView;

    @FXML
    private Button refreshButton;

    @FXML
    private Button editButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button removeItem;

    public StockController(SalesSystemDAO dao, StockAddValidator addValidator, StockEditValidator editValidator) {
        this.dao = dao;
        this.addValidator = addValidator;
        this.editValidator = editValidator;
    }


    //Window states
    private void defaultWindow() {
        confirmButton.setDisable(true);
        insertBar.setDisable(true);
        refreshStockItems();
        autoID();
        insertPrice.setText("");
        insertPrice.setDisable(false);
        insertName.setText("");
        insertName.setDisable(false);
        insertAmount.setText("");
        addExisting.setDisable(false);
        refreshButton.setDisable(false);
        amountText.setText("Amount");
        addExisting.setText("Add existing");
    }
    //Window states end


    //TODO- Edit nupu loogika
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //Algne kuvand
        defaultWindow();


        //Väljade kontroll, mis aktiveerib "Add product" nupu
        BooleanBinding controlAddItem = insertBar.textProperty().isEmpty().or(insertName.textProperty().isEmpty())
                .or(insertAmount.textProperty().isEmpty()).or(insertPrice.textProperty().isEmpty());
        addItem.disableProperty().bind(controlAddItem);


        //Valiku kontroll, mis aktiveerib "Remove" nupu
        removeItem.disableProperty().bind(Bindings.isEmpty(warehouseTableView.getSelectionModel().getSelectedItems()));


        //Rea valimine ja rea tühistamine
        warehouseTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                warehouseTableView.getSelectionModel().getSelectedItem();
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                warehouseTableView.getSelectionModel().clearSelection();
            }
        });

    }

    @FXML
    void addItemClicked(MouseEvent event) {
        long bar = Long.parseLong(insertBar.getText());
        int amount = Integer.parseInt(insertAmount.getText());
        String name = insertName.getText();
        double price = Double.parseDouble(insertPrice.getText());
        if (addValidator.validateAdd(amount, price, name)) {
            dao.addNewStockItem(new StockItem(bar, name, "", price, amount));
            defaultWindow();
        }
    }

    @FXML
    void addExistingItem(MouseEvent event) {
        if (addExisting.getText().equals("Add existing")) {
            //Changed window state
            insertBar.setDisable(false);
            insertName.setDisable(true);
            insertPrice.setDisable(true);
            refreshButton.setDisable(true);
            editButton.setDisable(true);
            amountText.setText("Add amount:");
            addExisting.setText("Add");
            //

        } else {
            long bar = Long.parseLong(insertBar.getText());
            int amount = Integer.parseInt(insertAmount.getText());
            dao.addExistingStockItem(bar, amount);
            defaultWindow();
        }
    }

    @FXML
    void removeItemClicked(MouseEvent event) {
        StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();
        dao.deleteStockitem(valitud.getId());
        log.debug("StockItem to remove: " + valitud.toString());
        refreshStockItems();
        autoID();
    }

    @FXML
    public void refreshButtonClicked() {
        warehouseTableView.getSelectionModel().clearSelection();
        //refreshStockItems();
        autoID();
        System.out.println("Värskendab");  //Kontroll konsoolile, et veenduda nupu töötamises
        log.info("Refreshing");  //Kontroll konsoolile, et veenduda nupu töötamises
    }

    void autoID() {
        long biggestID = 1L;
        while (true) {
            if (dao.findStockItem(biggestID) == null) {  //Sellist ID ei ole
                break;
            } else {
                biggestID += 1L;
            }
        }
        insertBar.setText(String.valueOf(biggestID));
    }


    private void refreshStockItems() {
        warehouseTableView.setItems(FXCollections.observableList(dao.findStockItems()));
        warehouseTableView.refresh();
    }
}



