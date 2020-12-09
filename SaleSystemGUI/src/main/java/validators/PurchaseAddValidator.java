package validators;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import javafx.scene.control.Alert;


public class PurchaseAddValidator {

    private final SalesSystemDAO dao;

    public PurchaseAddValidator(SalesSystemDAO dao) {
        this.dao = dao;
    }

    public boolean validateAdd(SoldItem item) {

        StringBuilder errors = new StringBuilder();
        StockItem stockItem = item.getStockItem();

        if (item.getQuantity() > item.getStockItem().getQuantity()) {

            errors.append(" -Max quantity exceeded. Max:  ").append(item.getStockItem().getQuantity()).append("\n");

        }
        if (item.getQuantity() <= 0){

            errors.append("- Product with incorrect amount\n");

        }
        if (!stockItem.getName().equals(item.getName())) {

            errors.append("- Product with ID ").append(stockItem.getId()).append(" correct name: ").append(stockItem.getName()).append("\n");

        }
        if (stockItem.getPrice() != item.getPrice())

            errors.append("- Product with ID ").append(stockItem.getId()).append(" correct price: ").append(stockItem.getPrice()).append("\n");

        return errorMessage(errors);

    }

    public boolean validateExisitng(SoldItem inCart, SoldItem toAdd) {

        StringBuilder errors = new StringBuilder();

        if (inCart.getQuantity() + toAdd.getQuantity() > inCart.getStockItem().getQuantity()) {

            errors.append("- Max quantity exceeded. Available: ").append(inCart.getStockItem().getQuantity() - inCart.getQuantity()).append("\n");

        }

        return errorMessage(errors);

    }

    private boolean errorMessage(StringBuilder errors) {
        if (errors.length() > 0) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Insert valid information");
            alert.setContentText(errors.toString());
            alert.showAndWait();

            return false;

        }


        return true;
    }

}