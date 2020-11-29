package ee.ut.math.tvt.salessystem.dao;

import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import org.mockito.internal.matchers.Or;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Object for loading and saving sales system data.
 * Feel free to add methods here!
 * <p>
 * The sample implementation is called {@link InMemorySalesSystemDAO}. It allows you
 * to run the application without configuring a real database. Making changes is simple
 * and all data is lost when restarting the app. Later you will need to create a new
 * implementation {@link HibernateSalesSystemDAO} for SalesSystemDAO that uses a real
 * database to store the data. Keep the existing InMemorySalesSystemDAO implementation,
 * it will be useful when writing tests in lab6.
 * <p>
 * Implementations of this class must only handle storage/retrieval of the data.
 * Business logic and validation should happen in separate specialized classes.
 * Separating data access and business logic allows you to later test the business
 * logic using the InMemorySalesSystemDAO and avoid configuring the database for
 * each test (much faster and more convenient).
 * <p>
 * Note the transaction related methods. These will become relevant when you
 * start using a real database. Transactions allow you to group database operations
 * so that either all of them succeed or nothing at all is done.
 */
public interface SalesSystemDAO {


    //GUI
    void beginTransaction();

    //GUI
    void rollbackTransaction();

    //GUI
    void commitTransaction();

    //GUI
    List<StockItem> findStockItems();

    //GUI
    StockItem findStockItem(long id);

    //GUI
    StockItem findStockItem(String name);

    //GUI
    SoldItem findSoldItem(long id);

    //GUI
    void addNewStockItem(StockItem item);

    //GUI
    void addExistingStockItem(long id, int amount);

    //GUI
    void editStockItemName(long id, String newName);

    //GUI
    void editStockItemPrice(long id, double price);

    //GUI
    void deleteStockitem(long id);

    //GUI
    void addSoldItem(SoldItem item);

    //GUI
    void addHistoryItem(HistoryItem item);

    //GUI
    void addOrder(Order order);

    //GUI
    List<HistoryItem> findHistoryItems();

    Order findOrder(long id);

    void saveStockItem(StockItem stockItem);

    void saveSoldItem(SoldItem item);

    void saveHistoryItem(HistoryItem item);

    void saveOrder(Order order);

    List<Order> showAll();

    List<Order> showLast10();

    List<Order> showBetweenDates(LocalDate begin, LocalDate end);
}
