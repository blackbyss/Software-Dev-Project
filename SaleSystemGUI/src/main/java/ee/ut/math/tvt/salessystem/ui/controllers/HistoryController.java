package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryController implements Initializable {

    private final SalesSystemDAO dao;
    private static final Logger log = LogManager.getLogger(HistoryController.class);

    public HistoryController(SalesSystemDAO dao){
        this.dao = dao;
    }

    @FXML
    private Button showBetweenDates;

    @FXML
    private Button showLastTen;

    @FXML
    private Button showAll;

    @FXML
    private DatePicker selectStartDate;

    @FXML
    private DatePicker selectEndDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: implement
    }

}
