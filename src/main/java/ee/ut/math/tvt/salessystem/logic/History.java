package ee.ut.math.tvt.salessystem.logic;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class History {

    private final SalesSystemDAO dao;
    public final List<HistoryItem> items = new ArrayList<>();


    public History(SalesSystemDAO dao) {
        this.dao = dao;
    }

    public List<Order> showAll() {
        return dao.showAll();
    }

    public List<Order> showLast10() {
        return dao.showLast10();
    }

    public Order getOrder(long id){
        return dao.findOrder(id);
    }

    public List<Order> showBetweenDates(LocalDate begin, LocalDate end) {
        return dao.showBetweenDates(begin, end);
    }

}
