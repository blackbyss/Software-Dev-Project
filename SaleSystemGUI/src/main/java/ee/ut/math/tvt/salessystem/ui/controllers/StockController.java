package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
    private Button removeItem;

    public StockController(SalesSystemDAO dao) {

        this.dao = dao;
    }


    //Window states

    private void defaultWindow() {
        confirmButton.setDisable(true);
        insertBar.setDisable(true);
        refreshStockItems();
        autoID();
        insertPrice.setText("");
        insertName.setText("");
        insertAmount.setText("");
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
                StockItem current = warehouseTableView.getSelectionModel().getSelectedItem();
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                warehouseTableView.getSelectionModel().clearSelection();
            }
        });


        editButton.setOnAction(e -> {

            confirmButton.setDisable(false);
            addItem.setDisable(true);
            removeItem.setDisable(true);
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
                    if (valideeriMuutus()) {
                        if (dao.findStockItem(Long.parseLong(insertBar.getText())) != null) { //Kui toode eksisteerib eelnevalt laos
                            dao.findStockItem(Long.parseLong(insertBar.getText())).setQuantity(dao.findStockItem(Long.parseLong(insertBar.getText())).getQuantity() + Integer.parseInt(insertAmount.getText()));
                        } else {
                            dao.editItemId(id, Long.parseLong(insertBar.getText()));
                        }
                        dao.editItemAmount(id, Long.parseLong(insertAmount.getText()));
                        dao.editItemName(id, String.valueOf(insertName.getText()));
                        dao.editItemPrice(id, Long.parseLong(insertPrice.getText()));
                        addItem.setDisable(false);
                        removeItem.setDisable(false);
                        refreshButton.setDisable(false);
                        refreshStockItems();
                    }
                }
            });
        });

    }

    @FXML
    void addItemClicked(MouseEvent event) {
        if (valideeriSisend()) {            //Kui toode ei eksisteeri
            addStockItem(Long.parseLong(insertBar.getText()), Integer.parseInt(insertAmount.getText())
                    , String.valueOf(insertName.getText()), Double.parseDouble(insertPrice.getText()));
            defaultWindow();
        }
        refreshStockItems();                //Tagastab uuendatud või uue tootega lao seisu
    }

    @FXML
    void removeItemClicked(MouseEvent event) {
        StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();
        warehouseTableView.getItems().remove(valitud);
        warehouseTableView.getSelectionModel().clearSelection();
        refreshStockItems();
        autoID();
    }

    @FXML
    public void refreshButtonClicked() {
        warehouseTableView.getSelectionModel().clearSelection();
        refreshStockItems();
        System.out.println("Värskendab");  //Kontroll konsoolile, et veenduda nupu töötamises
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


    //TODO-Kui kogus ei ole int ja kui hind ei ole double.
    private boolean valideeriSisend() {

        StringBuilder errors = new StringBuilder();

        if(Integer.parseInt(insertAmount.getText()) <= 0 || Integer.parseInt(insertAmount.getText()) > 100){
            errors.append("- Please enter valid amount.(0-100)\n");
        }
        if(Double.parseDouble(insertPrice.getText()) < 0){
            errors.append("- Please enter valid price.(0...)\n");
        }
        if(dao.findStockItem(insertName.getText()) != null){
            errors.append("- Please enter unused product name.\n");
        }
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Insert valid information");
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



