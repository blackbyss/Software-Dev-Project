package ee.ut.math.tvt.salessystem.logic;

import static org.junit.Assert.*;

import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartTest {

    @Mock
    private ShoppingCart cart;

    private SalesSystemDAO dao;

    @Before
    public void setup() {
        this.dao = new InMemorySalesSystemDAO();
        this.cart = new ShoppingCart(dao);
    }
    private void clearMemory(){
        cart.getAll().clear();
        dao.findStockItems().clear();
        dao.findHistoryItems().clear();
    }
    //Add item testing
    @Test
    public void testAddingExistingItem() {
        // Items for testing
        StockItem stockItem = new StockItem((long) 87, "Apple", "Green", 15, 90);
        dao.saveStockItem(stockItem);
        SoldItem soldItemTest = new SoldItem(stockItem, 2 ,2*stockItem.getPrice());
        cart.addItem(soldItemTest);
        int preAddQuantity = soldItemTest.getQuantity();
        cart.addItem(soldItemTest);
        int postAddQuantity = soldItemTest.getQuantity();
        assert preAddQuantity < postAddQuantity;
        clearMemory();

    }

    @Test
    public void testAddingNewItem() {
        int index = cart.getAll().size();
        StockItem stockItem = new StockItem((long) 87, "Apple", "Green", 15, 90);
        dao.saveStockItem(stockItem);
        SoldItem soldItemTest = new SoldItem(stockItem, 2, 2*stockItem.getPrice());
        cart.addItem(soldItemTest);
        assert cart.getAll().get(index).equals(soldItemTest);
        clearMemory();
    }

    @Test
    public void testAddingItemWithNegativeQuantity() {
        // Items for testing
        StockItem stockItem = new StockItem((long) 87, "Apple", "Green", 15, 90);
        dao.saveStockItem(stockItem);
        SoldItem soldItem = new SoldItem(stockItem, -4, -4 * stockItem.getPrice());

        //Add item
        cart.addItem(soldItem);
        clearMemory();
    }

    @Test
    public void testAddingItemWithQuantityTooLarge() {
        // Items for testing
        StockItem stockItem = new StockItem((long) 87, "Apple", "Green", 15, 90);
        dao.saveStockItem(stockItem);
        SoldItem soldItem = new SoldItem(stockItem, 94, 94 * stockItem.getPrice());

        //Add item
        assertFalse(cart.addItem(soldItem));
        clearMemory();
    }

    @Test
    public void testAddingItemWithQuantitySumTooLarge() {
        //TODO ei tea kuidas seda teha, et see erineks eelmisest
        StockItem stockItem = new StockItem((long) 87, "Apple", "Green", 15, 90);
        dao.saveStockItem(stockItem);
        SoldItem soldItem = new SoldItem(stockItem, 94, 99999 * stockItem.getPrice());
        assertFalse(cart.addItem(soldItem));
        clearMemory();
    }

    //Submit current purchase testing

}