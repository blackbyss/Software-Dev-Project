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


    /**
     * Transaction methods/not used with current DAO
     */
    @Override
    public void beginTransaction() {
    }

    @Override
    public void rollbackTransaction() {
    }

    @Override
    public void commitTransaction() {
    }


    /**
     * Find items from Stock methods
     */
    @Override
    public List<StockItem> findStockItems() {
        return stockItemList;
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
            if (item.getName().equals(name))
                return item;
        }
        return null;
    }


    /**
     * Find items from Sold
     */
    @Override
    public SoldItem findSoldItem(long id) {
        for (SoldItem item : soldItemList) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }


    /**
     * Add item to Stock methods
     */
    @Override
    public void addNewStockItem(StockItem item) {

        stockItemList.add(item);

    }

    @Override
    public void addExistingStockItem(long id, int amount) {

        StockItem itemForAdd = findStockItem(id);
        itemForAdd.setQuantity(itemForAdd.getQuantity() + amount);

    }


    /**
     * Edit stockItem
     */
    @Override
    public void editStockItemName(long id, String newName) {

        findStockItem(id).setName(newName);

    }

    @Override
    public void editStockItemPrice(long id, double newPrice) {

        findStockItem(id).setPrice(newPrice);

    }


    /**
     * Delete item from Stock methods
     */
    @Override
    public void deleteStockitem(long id) {

        StockItem itemForRemove = findStockItem(id);
        stockItemList.remove(itemForRemove);

    }


    /**
     * Add item to Sold methods
     */
    @Override
    public void addSoldItem(SoldItem item) {
        saveSoldItem(item);
    }


    /**
     * Add item to History methods
     */
    @Override
    public void addHistoryItem(HistoryItem item) {

        saveHistoryItem(item);

    }

    @Override
    public void addOrder(Order order) {
        long id = orderList.size();
        order.setId(id + 1);
        saveOrder(order);
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
    public List<HistoryItem> findHistoryItems() {
        return historyItemList;
    }


    @Override
    public Order findOrder(long id) {
        for (Order order : orderList) {
            if (order.getId() == id) {
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
        orderList.add(order);
    }

    @Override
    public void saveHistoryItem(HistoryItem historyItem) {
        historyItemList.add(historyItem);
    }

    @Override
    public List<Order> showAll() {
        return orderList;
    }

    @Override
    public List<Order> showLast10() {
        int size = orderList.size();

        if (size <= 10){
            return orderList;
        } else {
            return orderList.subList(size - 10, size);
        }
    }

    @Override
    public List<Order> showBetweenDates(LocalDate begin, LocalDate end) {
        return null;
    }


}
