package validators;

import ee.ut.math.tvt.salessystem.dao.HibernateSalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import javafx.scene.control.Alert;

public class PurchaseAddValidator {

    private final SalesSystemDAO dao;

    public PurchaseAddValidator(SalesSystemDAO dao) {
        this.dao = dao;
    }

    //TODO-Kui kogus ei ole int ja kui hind ei ole double.
    public boolean validateAdd(Integer soldItemAmount, Integer stockItemAmount){
        StringBuilder errors = new StringBuilder();

        if(soldItemAmount > stockItemAmount){
            errors.append("- You can't add more items than in stock. Max: ").append(stockItemAmount);
        }

        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Insert valid information");
            alert.setContentText(errors.toString());

            alert.showAndWait();
            return false;
        }

        return true;
    }
}
