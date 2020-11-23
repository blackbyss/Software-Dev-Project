package validators;

import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import javafx.scene.control.Alert;

public class PurchaseAddValidator {

    private final InMemorySalesSystemDAO dao;

    public PurchaseAddValidator(InMemorySalesSystemDAO dao) {
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
