package ee.ut.math.tvt.salessystem.logic;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;

import java.util.ArrayList;
import java.util.List;

public class History {

    private final SalesSystemDAO dao;
    public final List<HistoryItem> items = new ArrayList<>();


    public History(SalesSystemDAO dao) {
        this.dao = dao;
    }

    public void addToHistory(HistoryItem item) {

    }

    public void addOrder(List<SoldItem> soldItems) {

    }

    public List<Order> getOrders() {
        return dao.findOrders();
    }

    public Order getOrder(long id){
        return dao.findOrder(id);
    }

    public void removeFromHistory(HistoryItem item) {
        items.remove(item);
    }

    public List<HistoryItem> showBetweenDates() {
        return items;
    }

    public List<HistoryItem> showLast10() {
        return items;
    }

    public List<HistoryItem> showAll() {
        return items;
    }

}
