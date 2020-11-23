package validators;

import ee.ut.math.tvt.salessystem.dao.HibernateSalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import javafx.scene.control.Alert;

public class StockEditValidator {

    private final SalesSystemDAO dao;

    public StockEditValidator(SalesSystemDAO dao) {
        this.dao = dao;
    }


    //TODO-Kogu valideerimine
    private boolean valideeriMuutus() {
        StringBuilder errors = new StringBuilder();



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
