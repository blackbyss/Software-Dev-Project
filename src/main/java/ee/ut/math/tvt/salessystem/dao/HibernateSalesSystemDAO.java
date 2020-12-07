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
     * Find items from Sold
     */
    //GUI
    @Override
    public SoldItem findSoldItem(long id) {
        return em.find(SoldItem.class, id);
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
     * Edit stockItem
     */
    //GUI
    @Override
    public void editStockItemName(long id, String newName) {
        findStockItem(id).setName(newName);
    }

    //GUI
    @Override
    public void editStockItemPrice(long id, double newPrice) {
        findStockItem(id).setPrice(newPrice);
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
     * Add item to Sold methods
     */
    @Override
    //GUI
    public void addSoldItem(SoldItem item){
        beginTransaction();

        saveSoldItem(item);

        commitTransaction();
    }


    /**
     * Add item to History methods
     */
    @Override
    //GUI
    public void addHistoryItem(HistoryItem item) {
        beginTransaction();

        saveHistoryItem(item);

        commitTransaction();
    }


    /**
     * Add new Order
     */
    @Override
    //GUI
    public void addOrder(Order order) {
        beginTransaction();

        saveOrder(order);

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

    //GUI
    @Override
    public List<Order> showAll() {
        List<Order> orders = em.createQuery("select e FROM Order e").getResultList();
        if (orders.size() == 0){
            return null;
        } else {
            return orders;
        }
    }


    /**
     * Find Order
     */
    @Override
    public Order findOrder(long id) {
        return em.find(Order.class, id);
    }


    /**
     * Save Order
     */
    @Override
    public void saveOrder(Order order) {
        em.merge(order);
    }

    //GUI
    @Override
    public List<Order> showLast10() {
        List<Order> orders = em.createQuery("select e FROM Order e ORDER BY e.id desc").setMaxResults(10).getResultList();
        if (orders.size() == 0){
            return null;
        } else {
            return orders;
        }
    }

    //GUI
    @Override
    public List<Order> showBetweenDates(LocalDate begin, LocalDate end) {

        if (begin == null || end == null){
            return null;
        }

        return em.createQuery("select e FROM Order e where e.dateSQL between :begin and :end")
                .setParameter("begin", Date.valueOf(begin))
                .setParameter("end", Date.valueOf(end))
                .getResultList();
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



}

