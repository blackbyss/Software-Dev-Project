package ee.ut.math.tvt.salessystem.dao;

import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;

import java.util.ArrayList;
import java.util.List;

public class InMemorySalesSystemDAO implements SalesSystemDAO {

    private List<StockItem> stockItemList;
    private final List<SoldItem> soldItemList;
    private final List<HistoryItem> historyItemList;

    public InMemorySalesSystemDAO() {
        List<StockItem> items = new ArrayList<StockItem>();
        items.add(new StockItem(1L, "Lays chips", "Potato chips", 11.0, 5));
        this.stockItemList = items;
        this.soldItemList = new ArrayList<>();
        this.historyItemList = new ArrayList<>();
    }


    @Override
    public void removeStockItem(long id, long amount){
        for (StockItem item : stockItemList){
            if (item.getId() == id){
                int newAmount = item.getQuantity() - (int)amount;
                if (newAmount < 0) newAmount =0;
                item.setQuantity(newAmount);
            }
        }
    }

    @Override
    public void addNewStockItem(long id, String name, String description, long price, long quantity){
        stockItemList.add(new StockItem(id,name,description,price, (int)quantity));
    }

    @Override
    public void deleteStockitem(long id){
        for (StockItem stockItem : stockItemList) {
            if (stockItem.getId() == id){
                stockItemList.remove(stockItem);
                break;
            }
        }
    }

    @Override
    public void addStockItem(long id, long amount){
        for (StockItem item : stockItemList){
            if (item.getId() == id){
                item.setQuantity(item.getQuantity() + (int)amount);
            }
        }
    }

    @Override
    public void editItemId(long id, long newId){
        for (StockItem item : stockItemList){
            if (item.getId() == id){
                item.setId(newId);
            }
        }
    }

    @Override
    public void editItemPrice(long id, long price){
        for (StockItem stockItem : stockItemList) {
            if (stockItem.getId() == id){
                stockItem.setPrice(price);
            }
        }

    }

    @Override
    public void editItemName(long id, String name){
        for (StockItem stockItem : stockItemList) {
            if (stockItem.getId() == id){
                stockItem.setName(name);
            }
        }
    }

    @Override
    public void editItemAmount(long id, long amount){
        for (StockItem stockItem : stockItemList) {
            if (stockItem.getId() == id){
                stockItem.setQuantity((int)amount);
            }
        }
    }

    @Override
    public List<StockItem> findStockItems() {
        return stockItemList;
    }

    @Override
    public List<HistoryItem> findHistoryItems(){
        return historyItemList;
    }

    @Override
    public StockItem findStockItem(long id) {
        for (StockItem item : stockItemList) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    @Override
    public StockItem findStockItem(String name){
        for (StockItem item : stockItemList){
            if(item.getName().toLowerCase().equals(name.toLowerCase()))
                return item;
        }
        return null;
    }

    @Override
    public SoldItem findSoldItem(long id) {
        for (SoldItem item : soldItemList) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    @Override
    public void saveSoldItem(SoldItem item) {
        soldItemList.add(item);
    }

    @Override
    public void saveStockItem(StockItem stockItem) {
        stockItemList.add(stockItem);
    }

    @Override
    public void saveHistoryItem(HistoryItem historyItem){
        historyItemList.add(historyItem);
    }

    @Override
    public void beginTransaction() {
    }

    @Override
    public void rollbackTransaction() {
    }

    @Override
    public void commitTransaction() {
    }
}
