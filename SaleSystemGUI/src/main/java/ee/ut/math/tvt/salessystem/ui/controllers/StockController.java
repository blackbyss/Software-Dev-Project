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

        //Nuppude kättesaadavus
        confirmButton.setDisable(true);
        insertBar.setDisable(true);
        cancelButton.setDisable(true);
        insertName.setDisable(false);
        addExisting.setDisable(false);
        refreshButton.setDisable(false);
        editButton.setDisable(false);
        insertPrice.setDisable(false);

        //Nuppude/Teksti muutmine
        insertPrice.setText("");
        insertAmount.setText("");
        amountText.setText("Amount");
        addExisting.setText("Add existing");

        //Värskendame sisu
        updateWarehouseState();

        //Genereerime ID
        insertBar.setText(warehouse.autoID());

    }

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

        //Tühistame valitud warehouse toote
        warehouseTableView.getSelectionModel().clearSelection();

        //Muudame window state, kui alustame toote lisamist
        if (addExisting.getText().equals("Add existing")) {
            insertPrice.setDisable(true);
            refreshButton.setDisable(true);
            insertName.setDisable(true);
            editButton.setDisable(true);
            insertBar.setDisable(false);
            cancelButton.setDisable(false);
            amountText.setText("Increase:");
            addExisting.setText("Add");

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



