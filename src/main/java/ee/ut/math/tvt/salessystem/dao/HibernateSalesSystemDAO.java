package ee.ut.math.tvt.salessystem.dao;

import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class HibernateSalesSystemDAO implements SalesSystemDAO {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private List<StockItem> stockItemList;

    public HibernateSalesSystemDAO() {
        // if you get ConnectException/JDBCConnectionException then you
        // probably forgot to start the database before starting the application
        emf = Persistence.createEntityManagerFactory("pos");
        em = emf.createEntityManager();
        List<StockItem> items = new ArrayList<StockItem>();
        items.add(new StockItem(1L, "Lays chips", "Potato chips", 11.0, 5));
        items.add(new StockItem(2L, "Chupa-chups", "Sweets", 8.0, 8));
        items.add(new StockItem(3L, "Frankfurters", "Beer sauseges", 15.0, 12));
        items.add(new StockItem(4L, "Free Beer", "Student's delight", 0.0, 100));
        beginTransaction();
        for (StockItem item:
             items) {
            saveStockItem(item);
        }
        commitTransaction();
        updateState();
    }

    // TODO implement missing methods

    public void close() {
        em.close();
        emf.close();
    }

    public void updateState() {
        String sql = "FROM StockItem";
        stockItemList = em.createQuery(sql, StockItem.class).getResultList();
    }
    
    @Override
    public void beginTransaction() {
        em.getTransaction().begin();
    }

    @Override
    public void rollbackTransaction() {
        em.getTransaction().rollback();
    }

    @Override
    public void commitTransaction() {
        em.getTransaction().commit();
    }

    @Override
    public List<StockItem> findStockItems() {
        Query query = em.createQuery("Select si FROM StockItem si");
        return query.getResultList();
    }

    @Override
    public StockItem findStockItem(long id) {
        return em.find(StockItem.class, id);
    }

    @Override
    public StockItem findStockItem(String name) {
        return null;
    }

    //Add new item
    public void addNewStockItem(StockItem item){
        beginTransaction();
        saveStockItem(item);
        commitTransaction();
    }

    @Override
    public List<HistoryItem> findHistoryItems() {
        return null;
    }

    @Override
    public SoldItem findSoldItem(long id) {
        return null;
    }

    @Override
    public void saveStockItem(StockItem stockItem) {
        em.merge(stockItem);
        updateState();
    }

    @Override
    public void removeStockItem(long id, long amount) {
    }

    @Override
    public void addStockItem(long id, long amount) {

    }

    @Override
    public void editItemId(long id, long newId) {

    }

    @Override
    public void editItemPrice(long id, long price) {

    }

    @Override
    public void editItemName(long id, String name) {

    }

    @Override
    public void editItemAmount(long id, long amount) {

    }

    @Override
    public void addNewStockItem(long id, String name, String description, long price, long quantity) {

    }

    @Override
    public void deleteStockitem(long id) {

    }

    @Override
    public void saveSoldItem(SoldItem item) {

    }

    @Override
    public void saveHistoryItem(HistoryItem item) {

    }
}
