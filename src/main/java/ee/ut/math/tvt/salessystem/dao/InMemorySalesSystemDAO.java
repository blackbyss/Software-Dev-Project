package ee.ut.math.tvt.salessystem.dao;

import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemorySalesSystemDAO implements SalesSystemDAO {

    private List<StockItem> stockItemList;
    private final List<SoldItem> soldItemList;
    private final List<HistoryItem> historyItemList;
    private final List<Order> orderList;

    public InMemorySalesSystemDAO() {
        List<StockItem> items = new ArrayList<StockItem>();
        items.add(new StockItem(1L, "Lays chips", "Potato chips", 11.0, 5));
        items.add(new StockItem(2L, "Chupa-chups", "Sweets", 8.0, 8));
        items.add(new StockItem(3L, "Frankfurters", "Beer sauseges", 15.0, 12));
        items.add(new StockItem(4L, "Free Beer", "Student's delight", 0.0, 100));
        this.stockItemList = items;
        this.soldItemList = new ArrayList<>();
        this.historyItemList = new ArrayList<>();
        this.orderList = new ArrayList<>();
    }


    public void removeStockItem(long id, long amount) {
        for (StockItem item : stockItemList) {
            if (item.getId() == id) {
                int newAmount = item.getQuantity() - (int) amount;
                if (newAmount < 0) newAmount = 0;
                item.setQuantity(newAmount);
                break;
            }
        }
    }

    public void addNewStockItem(long id, String name, String description, long price, long quantity) {
        stockItemList.add(new StockItem(id, name, description, price, (int) quantity));
    }

    @Override
    public void deleteStockitem(long id) {
        for (StockItem stockItem : stockItemList) {
            if (stockItem.getId() == id) {
                stockItemList.remove(stockItem);
                break;
            }
        }
    }

    @Override
    public void addSoldItem(SoldItem item) {

    }

    @Override
    public void addHistoryItem(HistoryItem item) {

    }

    @Override
    public void addOrder(Order order) {
        orderList.add(order);

    }

    public void addStockItem(long id, long amount) {
        for (StockItem item : stockItemList) {
            if (item.getId() == id) {
                item.setQuantity((int) (item.getQuantity() +  amount));
                break;
            }
        }
    }

    public void editItemId(long id, long newId) {
        for (StockItem item : stockItemList) {
            if (item.getId() == id) {
                item.setId(newId);
                break;
            }
        }
    }

    public void editItemPrice(long id, long price) {
        for (StockItem stockItem : stockItemList) {
            if (stockItem.getId() == id) {
                stockItem.setPrice(price);
                break;
            }
        }

    }

    public void editItemName(long id, String name) {
        for (StockItem stockItem : stockItemList) {
            if (stockItem.getId() == id) {
                stockItem.setName(name);
                break;
            }
        }
    }

    public void editItemAmount(long id, long amount) {
        for (StockItem stockItem : stockItemList) {
            if (stockItem.getId() == id) {
                stockItem.setQuantity((int) amount);
                break;
            }
        }
    }

    @Override
    public List<StockItem> findStockItems() {
        return stockItemList;
    }

    @Override
    public List<HistoryItem> findHistoryItems() {
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
    public StockItem findStockItem(String name) {
        for (StockItem item : stockItemList) {
            if (item.getName().toLowerCase().equals(name.toLowerCase()))
                return item;
        }
        return null;
    }

    public SoldItem findSoldItem(long id) {
        for (SoldItem item : soldItemList) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    @Override
    public Order findOrder(long id) {
        for (Order order:orderList) {
            if(order.getId() == id){
                return order;
            }
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
    public void saveOrder(Order order) {

    }

    @Override
    public List<Order> showAll() {
        return null;
    }

    @Override
    public List<Order> showLast10() {
        return null;
    }

    @Override
    public List<Order> showBetweenDates(LocalDate begin, LocalDate end) {
        return null;
    }

    @Override
    public void saveHistoryItem(HistoryItem historyItem) {
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

    @Override
    public void addNewStockItem(StockItem item) {

    }

    @Override
    public void addExistingStockItem(long id, int maount) {

    }

    @Override
    public void editStockItemName(long id, String newName) {

    }

    @Override
    public void editStockItemPrice(long id, double price) {

    }
}
