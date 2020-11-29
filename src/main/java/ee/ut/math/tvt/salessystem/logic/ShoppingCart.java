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
    public boolean addItem(SoldItem item) {

        //Add/verify products
        if (item.getQuantity() > item.getStockItem().getQuantity()) {
            return false;
        } else {
            boolean unused = true;
            for (SoldItem soldItem : items) {
                if (soldItem.getStockItem().getId().equals(item.getStockItem().getId())) {
                    if (soldItem.getQuantity() + item.getQuantity() > dao.findStockItem(soldItem.getStockItem().getId()).getQuantity()) {
                        return false;
                    } else {
                        soldItem.setQuantity(soldItem.getQuantity() + item.getQuantity());
                        soldItem.setSum(soldItem.getSum() + item.getQuantity() * item.getPrice());
                    }
                    unused = false;
                    break;
                }
            }
            if (unused) {
                items.add(item);
                log.debug("Added " + item.getName() + " quantity of " + item.getQuantity());
            }
            return true;
        }
    }

    public void removeItem(SoldItem item) {
        items.remove((item));
    }

    public List<SoldItem> getAll() {
        return items;
    }

    public void cancelCurrentPurchase() {
        items.clear();
        log.info("Current purhcase canceled");
    }

    public void submitCurrentPurchase() {
        for (SoldItem soldItem: items){
            soldItem.getStockItem().setQuantity(soldItem.getStockItem().getQuantity() - soldItem.getQuantity());
            if(soldItem.getStockItem().getQuantity() == 0){
                dao.deleteStockitem(soldItem.getId());
                SoldItem item = new SoldItem(soldItem.getStockItem(), soldItem.getQuantity(), soldItem.getSum());
                dao.saveSoldItem(item);
            }
        }
        try {
            List<HistoryItem> hisItem = new ArrayList<>();
            for (SoldItem item : items) {
                HistoryItem item1 = new HistoryItem(item);
                dao.addSoldItem(item);
                dao.addHistoryItem(item1);
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
