package ee.ut.math.tvt.salessystem.ui;

import ee.ut.math.tvt.salessystem.dao.HibernateSalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.logic.History;
import ee.ut.math.tvt.salessystem.logic.Warehouse;
import ee.ut.math.tvt.salessystem.ui.controllers.*;
import ee.ut.math.tvt.salessystem.logic.ShoppingCart;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validators.PurchaseAddValidator;
import validators.StockAddValidator;
import validators.StockEditValidator;

import java.io.IOException;
import java.net.URL;

/**
 * Graphical user interface of the sales system.
 */
public class SalesSystemUI extends Application {

    private static final Logger log = LogManager.getLogger(SalesSystemUI.class);

    private final SalesSystemDAO dao;
    private final ShoppingCart shoppingCart;
    private final History history;
    private final Warehouse warehouse;
    private final StockAddValidator addValidator;
    private final PurchaseAddValidator purchaseAddValidator;
    private final StockEditValidator editValidator;
    private final LoginController loginController;

    public SalesSystemUI() {
        // Andmebaasi loomine
        // dao = new HibernateSalesSystemDAO();
        dao = new InMemorySalesSystemDAO();
        purchaseAddValidator = new PurchaseAddValidator(dao);
        shoppingCart = new ShoppingCart(dao);
        history = new History(dao);
        warehouse = new Warehouse(dao);
        addValidator = new StockAddValidator(dao);
        editValidator= new StockEditValidator(dao);
        loginController = new LoginController();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("javafx version: " + System.getProperty("javafx.runtime.version"));

        //Login window
        try {
            Stage stage = new Stage();
            Parent loginRoot = (Parent) loadControls("LoginWindow.fxml", loginController);
            Scene loginScene = new Scene(loginRoot,500, 300);
            loginScene.getStylesheets().add(getClass().getResource("DefaultTheme.css").toExternalForm());
            stage.setScene(loginScene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TABS
        Tab purchaseTab = new Tab();
        purchaseTab.setText("Point-of-sale");
        purchaseTab.setClosable(false);
        purchaseTab.setContent(loadControls("PurchaseTab.fxml", new PurchaseController(dao, shoppingCart, history, purchaseAddValidator)));


        Tab stockTab = new Tab();
        stockTab.setText("Warehouse");
        stockTab.setClosable(false);
        stockTab.setContent(loadControls("StockTab.fxml", new StockController(dao, addValidator, editValidator, warehouse)));

        Tab historyTab = new Tab();
        historyTab.setText("History");
        historyTab.setClosable(false);
        historyTab.setContent(loadControls("HistoryTab.fxml", new HistoryController(dao, history)));

        Tab teamTab = new Tab();
        teamTab.setText("Team");
        teamTab.setClosable(false);
        teamTab.setContent(loadControls("TeamTab.fxml", new TeamController()));

        //GROUP AND SCENE
        Group root = new Group();
        Scene scene = new Scene(root, 600, 500, Color.WHITE);
        scene.getStylesheets().add(getClass().getResource("DefaultTheme.css").toExternalForm());

        BorderPane borderPane = new BorderPane();
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        //If login window is closed
        if(loginController.getTagastatavOigus() == 0){
            System.exit(0);
        }
        //Cashier
        if(loginController.getTagastatavOigus() == 1){
            borderPane.setCenter(new TabPane(purchaseTab));
        }
        //Warehouse worker
        else if(loginController.getTagastatavOigus() == 2){
            borderPane.setCenter(new TabPane(stockTab));
        }
        //Manager
        else if(loginController.getTagastatavOigus() == 3){
            borderPane.setCenter(new TabPane(historyTab, teamTab));
        }
        //Software client
        else if(loginController.getTagastatavOigus() == 4){
            borderPane.setCenter(new TabPane(purchaseTab, historyTab, stockTab, teamTab));
        }

        root.getChildren().add(borderPane);

        primaryStage.setTitle("Sales system");
        primaryStage.setScene(scene);
        primaryStage.show();

        log.info("Salesystem GUI started");
    }


    private Node loadControls(String fxml, Initializable controller) throws IOException {
        URL resource = getClass().getResource(fxml);
        if (resource == null) {
            log.error("fxml file not found");
            throw new IllegalArgumentException(fxml + " not found");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        fxmlLoader.setController(controller);
        return fxmlLoader.load();

    }

    public static void main(String[] args) {
        launch(args);
    }

}


