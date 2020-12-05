package ee.ut.math.tvt.salessystem.logic;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShoppingCart {
    // TODO create logger (currently throws errors)
    private static final Logger log = LogManager.getLogger(ShoppingCart.class);

    private final SalesSystemDAO dao;
    private final List<SoldItem> items = new ArrayList<>();

    public ShoppingCart(SalesSystemDAO dao) {
        this.dao = dao;
    }


    /**
     * Add new SoldItem to table.
     */
    public void addItem(SoldItem item) {

        items.add(item);
        log.debug("Added " + item.getName() + " quantity of " + item.getQuantity());

    }

    public void addExisting(SoldItem soldItem, SoldItem item){

        soldItem.setQuantity(soldItem.getQuantity() + item.getQuantity());
        soldItem.setSum(soldItem.getSum() + item.getQuantity() * item.getPrice());

    }

    public void removeItem(SoldItem item) {
        items.remove((item));
    }

    public List<SoldItem> getAll() {
        return items;
    }

    public SoldItem getSoldItem(long id) {
        SoldItem tagasta = null;
        for (SoldItem item : items) {
            if (id == item.getId()) {
                tagasta = item;
            }
        }
        return tagasta;
    }

    public double getTotal(List<SoldItem> items){
        double total = 0;
        for (SoldItem item: items) {
            total += item.getQuantity() * item.getPrice();
        }


        return total;
    }

    public void cancelCurrentPurchase() {
        items.clear();
        log.info("Current purhcase canceled");
    }

    public void submitCurrentPurchase() {
        for (SoldItem soldItem : items) {
            soldItem.getStockItem().setQuantity(soldItem.getStockItem().getQuantity() - soldItem.getQuantity());
            if (soldItem.getStockItem().getQuantity() == 0) {
                dao.deleteStockitem(soldItem.getId());
                SoldItem item = new SoldItem(soldItem.getStockItem(), soldItem.getQuantity(), soldItem.getSum(), soldItem.getName(), soldItem.getPrice());
                dao.saveSoldItem(item);
            }
        }
        try {
            List<HistoryItem> hisItem = new ArrayList<>();
            for (SoldItem item : items) {
                HistoryItem item1 = new HistoryItem(item);
                dao.addSoldItem(item);
                hisItem.add(item1);
            }
            System.out.println(hisItem);
            dao.addOrder(new Order(hisItem, LocalDate.now(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"))));
            items.clear();
            log.info("Current purhcase submitted");
        } catch (Exception e) {
            dao.rollbackTransaction();
            throw e;
        }
    }

}
