package ee.ut.math.tvt.salessystem.logic;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;

import java.util.List;

public class Warehouse {

    private final SalesSystemDAO dao;

    public Warehouse(SalesSystemDAO dao) {
        this.dao = dao;
    }

    public void addToStock(StockItem item){
        dao.addNewStockItem(item);
    }

    public void addExisting(long id, int amount){
        dao.addExistingStockItem(id, amount);
    }

    public void deleteFromStock(long id){
        dao.deleteStockitem(id);
    }

    public List<StockItem> refreshWarehouse(){
        return dao.findStockItems();
    }

    public String autoID() {
        long biggestID = 1L;
        while (true) {
            if (dao.findStockItem(biggestID) == null) {  //Sellist ID ei ole
                break;
            } else {
                biggestID += 1L;
            }
        }
        return String.valueOf(biggestID);
    }
}
