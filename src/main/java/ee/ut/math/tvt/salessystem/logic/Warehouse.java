package ee.ut.math.tvt.salessystem.logic;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final SalesSystemDAO dao;
    private final List<StockItem> items = new ArrayList<>();

    public Warehouse(SalesSystemDAO dao) {
        this.dao = dao;
    }

    public void addToStock(StockItem item){
        items.add(item);
    }
    public void removeFromStock(StockItem item){
        items.remove(item);
    }
    public void refreshWarehouse(){
        items.clear();
    }
    public List<StockItem> getAll() {
        return items;
    }
}
