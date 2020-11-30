package validators;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import javafx.scene.control.Alert;

public class StockAddValidator {

    private final SalesSystemDAO dao;

    public StockAddValidator(SalesSystemDAO dao) {
        this.dao = dao;
    }

    //TODO-Kui kogus ei ole int ja kui hind ei ole double.
    public boolean validateAdd(Integer amount, double price, String name){
        StringBuilder errors = new StringBuilder();

        if(amount <= 0 || amount > 100){
            errors.append("- Please enter valid amount(0-100)\n");
        }
        if(price< 0){
            errors.append("- Please enter valid price(0...)\n");
        }
        if (errors.length() > 0) {
            return errorMessage(errors);
        }

        return true;
    }

    public boolean validateExisting(Integer amount, long id){
        StringBuilder errors = new StringBuilder();

        if(amount <= 0){
            errors.append("- Please enter valid amount(More than 0)\n");
        }
        if(dao.findStockItem(id) == null){
            errors.append("- Please enter valid ID(Not found in DataBase)\n");
        }
        if (errors.length() > 0) {
            return errorMessage(errors);
        }
        return true;
    }

    public boolean errorMessage(StringBuilder errors){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Insert valid information");
            alert.setContentText(errors.toString());

            alert.showAndWait();
            return false;
    }

}
