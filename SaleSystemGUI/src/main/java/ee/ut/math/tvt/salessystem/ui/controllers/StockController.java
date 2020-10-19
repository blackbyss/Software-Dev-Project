package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private Button editButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button removeButton;


    public StockController(SalesSystemDAO dao) {

        this.dao = dao;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmButton.setDisable(true);
        refreshStockItems();                                   //Kuvab algse lao seisu
        addItem.setOnAction(new EventHandler<ActionEvent>() {  //Toote lisamine lattu
            @Override
            public void handle(ActionEvent event) {             //Vajutades "Add Product" nupule
                if (valideeriSisend()) {
                    if (dao.findStockItem(Long.parseLong(insertBar.getText())) != null) { //Kui toode eksisteerib eelnevalt laos
                        dao.findStockItem(Long.parseLong(insertBar.getText())).setQuantity(dao.findStockItem(Long.parseLong(insertBar.getText())).getQuantity() + Integer.parseInt(insertAmount.getText()));
                    } else {                                                              //Kui toode ei eksisteeri
                        addStockItem(Long.parseLong(insertBar.getText()), Integer.parseInt(insertAmount.getText())
                                , String.valueOf(insertName.getText()), Double.parseDouble(insertPrice.getText()));
                    }
                }
                refreshStockItems();                          //Tagastab uuendatud või uue tootega lao seisu
            }
        });
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {  //Lao seisu värskendamine(Praegu pole erilist kasu, sest uuendatakse automaatselt)
            @Override
            public void handle(ActionEvent event) {
                refreshButtonClicked();
            }
        });
        removeButton.setOnAction(e -> {                     //Hiire vajutusega valitud toote eemaldamine
            StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();
            warehouseTableView.getItems().remove(valitud);
        });

        editButton.setOnAction(e -> {

            confirmButton.setDisable(false);
            addItem.setDisable(true);
            removeButton.setDisable(true);
            refreshButton.setDisable(true);

            StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();

            long id = valitud.getId();
            long amount = valitud.getQuantity();
            String name = valitud.getName();
            long price = (long) valitud.getPrice();

            insertBar.setText(String.valueOf(id));
            insertAmount.setText(String.valueOf(amount));
            insertName.setText(String.valueOf(name));
            insertPrice.setText(String.valueOf(price));

            confirmButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(valideeriMuutus()){
                        if (dao.findStockItem(Long.parseLong(insertBar.getText())) != null) { //Kui toode eksisteerib eelnevalt laos
                            dao.findStockItem(Long.parseLong(insertBar.getText())).setQuantity(dao.findStockItem(Long.parseLong(insertBar.getText())).getQuantity() + Integer.parseInt(insertAmount.getText()));
                        } else {
                            dao.editItemId(id, Long.parseLong(insertBar.getText()));
                        }
                        dao.editItemAmount(id, Long.parseLong(insertAmount.getText()));
                        dao.editItemName(id, String.valueOf(insertName.getText()));
                        dao.editItemPrice(id, Long.parseLong(insertPrice.getText()));
                        addItem.setDisable(false);
                        removeButton.setDisable(false);
                        refreshButton.setDisable(false);
                        refreshStockItems();
                    }
                }
            });
        });

    }

    @FXML
    public void refreshButtonClicked() {

        refreshStockItems();
        System.out.println("Värskendab");  //Kontroll konsoolile, et veenduda nupu töötamises

    }

    /**
     * Värskendab lao seisu
     */
    private void refreshStockItems() {

        warehouseTableView.setItems(FXCollections.observableList(dao.findStockItems()));
        warehouseTableView.refresh();

    }

    /**
     * @param id     Lisatava toote ID
     * @param amount Lisatava toote kogus
     * @param name   Lisatava toote nimetus
     * @param price  Lisatava toote hind
     */
    private void addStockItem(Long id, int amount, String name, double price) {

        StockItem stockItem = new StockItem(id, name, "", price, amount);
        dao.saveStockItem(stockItem);

    }

    /**
     * Juhul kui eelenvalt on laos identse toote ID-ga toode, siis TextField täitmine on automaatne
     * Saab muuta kogust
     */


    private boolean valideeriSisend() {

        StringBuilder errors = new StringBuilder();


        if (insertBar.getText().trim().isEmpty()) {
            errors.append("- Please enter ID of the product you wish to add.\n");
        }
        if (insertAmount.getText().trim().isEmpty() || Integer.parseInt(insertAmount.getText()) <= 0) {
            if (insertAmount.getText().trim().isEmpty()) {
                errors.append("- Please enter the amount you wish to add.\n");
            } else {
                errors.append("- Please enter valid amount.\n");
            }
        }
        if (insertName.getText().trim().isEmpty()) {
            errors.append("- Please enter the name of product.\n");
        }
        if (insertPrice.getText().trim().isEmpty() || Integer.parseInt(insertPrice.getText()) < 0) {
            if(insertPrice.getText().trim().isEmpty()){
                errors.append("- Please enter the product price.\n");
            } else {
                errors.append("- Please enter valid product price\n");
            }

        }


        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty");
            alert.setContentText(errors.toString());

            alert.showAndWait();
            return false;
        }

        return true;
    }

    private boolean valideeriMuutus() {
        StringBuilder errors = new StringBuilder();


        if (Integer.parseInt(insertAmount.getText()) <= 0) {

            errors.append("- Please enter valid amount.\n");
        }
        if (Integer.parseInt(insertPrice.getText()) < 0) {
            errors.append("- Please enter valid price.\n");
        }

        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty");
            alert.setContentText(errors.toString());

            alert.showAndWait();
            return false;
        }

        return true;
    }
}



