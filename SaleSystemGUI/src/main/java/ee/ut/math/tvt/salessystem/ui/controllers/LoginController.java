package ee.ut.math.tvt.salessystem.ui.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validators.LoginValidator;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private int tagastatavOigus;

    @FXML
    private Button loginButton;

    @FXML
    private Button selectCashier;

    @FXML
    private Button selectWarehouse;

    @FXML
    private Button selectManager;

    @FXML
    private Button selectClient;

    @FXML
    private TextField inputUser;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Label loginStatus;

    @FXML
    void cashierButtonClicked(MouseEvent event) {
        inputUser.setText("Cashier");
        inputPassword.setText("Cashier");
    }

    @FXML
    void clientClicked(MouseEvent event) {
        inputUser.setText("Client");
        inputPassword.setText("Client");
    }

    @FXML
    void managerClicked(MouseEvent event) {
        inputUser.setText("Manager");
        inputPassword.setText("Manager");
    }

    @FXML
    void warehouseButtonClicked(MouseEvent event) {
        inputUser.setText("Warehouse");
        inputPassword.setText("Warehouse");
    }

    @FXML
    void loginButtonClicked(MouseEvent event) {
        LoginValidator validator = new LoginValidator(inputUser.getText(), inputPassword.getText());
        this.tagastatavOigus = validator.getOigus();
        System.out.println(tagastatavOigus);
        if(this.tagastatavOigus != 0){
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }
    }

    public LoginController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    public int getTagastatavOigus() {
        return tagastatavOigus;
    }

}

