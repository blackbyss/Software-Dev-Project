package ee.ut.math.tvt.salessystem.dao;

import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class HibernateSalesSystemDAO implements SalesSystemDAO {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public HibernateSalesSystemDAO() {
        // if you get ConnectException/JDBCConnectionException then you
        // probably forgot to start the database before starting the application
        emf = Persistence.createEntityManagerFactory("pos");
        em = emf.createEntityManager();
    }

    // TODO implement missing methods

    public void close() {
        em.close();
        emf.close();
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
        return null;
    }

    @Override
    public List<HistoryItem> findHistoryItems() {
        return null;
    }

    @Override
    public StockItem findStockItem(long id) {
        return null;
    }

    @Override
    public StockItem findStockItem(String name) {
        return null;
    }

    @Override
    public SoldItem findSoldItem(long id) {
        return null;
    }

    @Override
    public void saveStockItem(StockItem stockItem) {

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