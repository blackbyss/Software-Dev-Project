package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Encapsulates everything that has to do with the team tab (the tab
 * labelled "Team" in the menu).
 */
public class TeamController implements Initializable {

    private final SalesSystemDAO dao;

    @FXML
    private Label teamName;

    @FXML
    private Label teamLeader;

    @FXML
    private Label teamLeaderEmail;

    @FXML
    private Label teamMembers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: implement
    }
    public TeamController(SalesSystemDAO dao) {
        this.dao = dao;
    }

}
