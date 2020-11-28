package ee.ut.math.tvt.salessystem.dao;

import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HibernateSalesSystemDAO implements SalesSystemDAO {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private List<StockItem> stockItemList;

    public HibernateSalesSystemDAO() {

        //Create emf and em
        emf = Persistence.createEntityManagerFactory("pos");
        em = emf.createEntityManager();
        List<StockItem> items = new ArrayList<StockItem>();

        //Default Stock
        items.add(new StockItem(1L, "Lays chips", "Potato chips", 11.0, 5));
        items.add(new StockItem(2L, "Chupa-chups", "Sweets", 8.0, 8));
        items.add(new StockItem(3L, "Frankfurters", "Beer sauseges", 15.0, 12));
        items.add(new StockItem(4L, "Free Beer", "Student's delight", 0.0, 100));

        //Add default stockItems to STOCK_ITEM DB
        beginTransaction();
        for (StockItem item :
                items) {
            saveStockItem(item);
        }
        commitTransaction();
    }

    // TODO implement missing methods

     public void close() {
        em.close();
        emf.close();
    }


    /**
     * Transaction methods
     */
    //GUI
    @Override
    public void beginTransaction() {
        em.getTransaction().begin();
    }

    //GUI
    @Override
    public void rollbackTransaction() {
        em.getTransaction().rollback();
    }

    //GUI
    @Override
    public void commitTransaction() {
        em.getTransaction().commit();
    }


    /**
     * Find items from Stock methods
     */
    //GUI
    @Override
    public List<StockItem> findStockItems() {
        Query query = em.createQuery("Select e FROM StockItem e");
        return query.getResultList();
    }

    //GUI
    @Override
    public StockItem findStockItem(long id) {
        return em.find(StockItem.class, id);
    }

    //GUI
    @Override
    public StockItem findStockItem(String name) {
        return em.find(StockItem.class, name);
    }


    /**
     * Add item to Stock methods
     */
    //GUI
    @Override
    public void addNewStockItem(StockItem item) {
        beginTransaction();
        saveStockItem(item);
        commitTransaction();
    }

    //GUI
    @Override
    public void addExistingStockItem(long id, int amount) {
        beginTransaction();

        StockItem itemForAdd = findStockItem(id);
        itemForAdd.setQuantity(itemForAdd.getQuantity() + amount);

        commitTransaction();
    }


    /**
     * Delete item from Stock methods
     */
    //GUI
    @Override
    public void deleteStockitem(long id) {
        beginTransaction();

        StockItem itemForRemove = findStockItem(id);
        em.remove(itemForRemove);
        commitTransaction();
    }


    /**
     * Find items from History methods
     */
    //GUI
    @Override
    public List<HistoryItem> findHistoryItems() {
        Query query = em.createQuery("Select e FROM HistoryItem e");
        return query.getResultList();
    }

    /**
     * Methods to show Orders
     */
    //GUI
    @Override
    public List<Order> showAll() {
        return em.createQuery("select e FROM Order e").getResultList();
    }

    //GUI
    @Override
    public List<Order> showLast10() {
        return em.createQuery("select e FROM Order e ORDER BY e.id desc").setMaxResults(10).getResultList();
    }

    //GUI
    @Override
    public List<Order> showBetweenDates(LocalDate begin, LocalDate end) {
        return em.createQuery("select e FROM Order e where e.dateSQL between :begin and :end")
                .setParameter("begin", Date.valueOf(begin))
                .setParameter("end", Date.valueOf(end))
                .getResultList();
    }

    @Override
    public Order findOrder(long id) {
        return em.find(Order.class, id);
    }

    @Override
    public void saveStockItem(StockItem stockItem) {
        em.merge(stockItem);
    }

    @Override
    public void saveSoldItem(SoldItem item) {
        em.merge(item);
    }

    @Override
    public void saveHistoryItem(HistoryItem item) {
        em.merge(item);
    }

    @Override
    public void saveOrder(Order order){
        em.merge(order);
    }


}

