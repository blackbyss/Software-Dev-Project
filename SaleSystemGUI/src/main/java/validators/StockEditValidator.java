package validators;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import javafx.scene.control.Alert;

public class StockEditValidator {

    private final SalesSystemDAO dao;

    public StockEditValidator(SalesSystemDAO dao) {
        this.dao = dao;
    }

    public boolean valideeriMuutus(double price) {
        StringBuilder errors = new StringBuilder();

        if(price < 0){
            errors.append("- Please enter valid price(0 and greater)\n");
        }



        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Field is faulty");
            alert.setContentText(errors.toString());

            alert.showAndWait();
            return false;
        }

        return true;
    }
}
