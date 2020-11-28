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

    public StockController(SalesSystemDAO dao, StockAddValidator addValidator, StockEditValidator editValidator, Warehouse warehouse) {
        this.dao = dao;
        this.addValidator = addValidator;
        this.editValidator = editValidator;
        this.warehouse = warehouse;
    }

    //Default window state
    private void defaultWindow() {

        warehouseTableView.getSelectionModel().clearSelection();

        //Nuppude kättesaadavus
        confirmButton.disableProperty().unbind();
        confirmButton.setDisable(true);
        insertBar.setDisable(true);
        cancelButton.setDisable(true);
        insertName.setDisable(false);
        refreshButton.setDisable(false);
        insertPrice.setDisable(false);

        //Nuppude/Teksti muutmine
        insertPrice.setText("");
        insertAmount.setText("");
        amountText.setText("Amount:");
        addExisting.setText("Add existing");

        //Värskendame sisu
        updateWarehouseState();

        //Genereerime ID
        insertBar.setText(warehouse.autoID());

        //Lisame nuppude saadavuse
        setBindingsToButtons();
    }

    //"Add Existing" button window state
    private void addExistingWindowState(Long id){
        insertPrice.setDisable(true);
        refreshButton.setDisable(true);
        insertName.setDisable(true);
        insertBar.setText(String.valueOf(id));
        cancelButton.setDisable(false);
        amountText.setText("Increase:");
        addExisting.setText("Add");
        removeItem.disableProperty().unbind();
        editButton.disableProperty().unbind();
        removeItem.setDisable(true);
        editButton.setDisable(true);
    }

    private void setBindingsToButtons(){
        BooleanBinding controlAddItem = insertBar.textProperty().isEmpty().or(insertName.textProperty().isEmpty())
                .or(insertAmount.textProperty().isEmpty()).or(insertPrice.textProperty().isEmpty());
        addItem.disableProperty().bind(controlAddItem);

        removeItem.disableProperty().bind(Bindings.isEmpty(warehouseTableView.getSelectionModel().getSelectedItems()));
        editButton.disableProperty().bind(Bindings.isEmpty(warehouseTableView.getSelectionModel().getSelectedItems()));
        addExisting.disableProperty().bind(Bindings.isEmpty(warehouseTableView.getSelectionModel().getSelectedItems()));
    }

    //TODO- Edit nupu loogika
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Algne kuvand
        defaultWindow();


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

        //Tühistame valitud warehouse toote
        warehouseTableView.getSelectionModel().clearSelection();

        //Lisatava toote tunnused
        long bar = Long.parseLong(insertBar.getText());
        int amount = Integer.parseInt(insertAmount.getText());
        String name = insertName.getText();
        double price = Double.parseDouble(insertPrice.getText());

        //Kontrollime, kas sisendid sobivad
        if (addValidator.validateAdd(amount, price, name)) {
            warehouse.addToStock(new StockItem(bar, name, "", price, amount));
            defaultWindow();
        }

    }

    @FXML
    void addExistingItem(MouseEvent event) {

        //Valime toote mille kogust soovime suurendada
        StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();
        addExisting.disableProperty().unbind();

        //Väljade kontroll, mis aktiveerib "Add" nupu
        BooleanBinding controlAddButton = insertAmount.textProperty().isEmpty();
        addExisting.disableProperty().bind(controlAddButton);

        //Muudame window state, kui alustame toote lisamist
        if (addExisting.getText().equals("Add existing")) {
            addExistingWindowState(valitud.getId());

        //Lisame tootele sisestatud koguse
        } else {

            //Suurendatava toote tunnused
            long bar = Long.parseLong(insertBar.getText());
            int amountInc = Integer.parseInt(insertAmount.getText());

            //Kontrollime, kas sisendid sobivad
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

        //Valime toote mida soovime eemaldada
        StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();
        warehouse.deleteFromStock(valitud.getId());
        updateWarehouseState();

        //Juhul kui eemaldame mõne väiksema, siis vabastame ID
        insertBar.setText(warehouse.autoID());

        //Logging
        log.debug("StockItem to remove: " + valitud.toString());
    }

    //TODO Validator for new price and change attributes in DB via Warehouse class
    @FXML
    void editButtonClicked(MouseEvent event) {

        //Valime toote mida soovime muuta
        StockItem valitud = warehouseTableView.getSelectionModel().getSelectedItem();

        //Edit aknakuva
        refreshButton.setDisable(true);
        cancelButton.setDisable(false);
        insertBar.setText(String.valueOf(valitud.getId()));
        insertAmount.setText(String.valueOf(valitud.getQuantity()));
        insertAmount.setDisable(true);
        addExisting.setDisable(true);
        removeItem.disableProperty().unbind();
        removeItem.setDisable(true);
        nameText.setText("New name:");
        priceText.setText("New price:");



        //Väljade kontroll, mis aktiveerib "Confirm" nupu
        BooleanBinding controlConfirmItem = (insertName.textProperty().isEmpty()).and(insertPrice.textProperty().isEmpty());
        confirmButton.disableProperty().bind(controlConfirmItem);

    }

    @FXML
    void confirmButtonClicked(MouseEvent event) {

        defaultWindow();

    }

    @FXML
    public void refreshButtonClicked() {

        //Tühistame valitud warehouse toote
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



