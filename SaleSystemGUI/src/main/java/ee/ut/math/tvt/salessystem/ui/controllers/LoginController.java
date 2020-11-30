package ee.ut.math.tvt.salessystem.ui.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import validators.LoginValidator;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private int tagastatavOigus;

    @FXML
    private Button loginButton;

    @FXML
    private TextField inputUser;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Label loginStatus;

    @FXML
    void loginButtonClicked(MouseEvent event) {
        System.out.println("Loome validaatori");
        LoginValidator validator = new LoginValidator(inputUser.getText(), inputPassword.getText());
        this.tagastatavOigus = validator.getOigus();
        System.out.println(tagastatavOigus);
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

