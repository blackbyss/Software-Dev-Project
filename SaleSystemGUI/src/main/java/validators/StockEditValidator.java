package validators;

import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import javafx.scene.control.Alert;

public class StockEditValidator {

    private final InMemorySalesSystemDAO dao;

    public StockEditValidator(InMemorySalesSystemDAO dao) {
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
