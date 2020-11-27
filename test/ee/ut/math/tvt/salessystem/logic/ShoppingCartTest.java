package ee.ut.math.tvt.salessystem.logic;

import static org.junit.Assert.*;

import ee.ut.math.tvt.salessystem.dao.HibernateSalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartTest {

    @Mock
    private ShoppingCart shoppingCart;

    private SalesSystemDAO salesSystemDAO;

    @Before
    public void setup() {
        shoppingCart = new ShoppingCart(salesSystemDAO);
    }

    //Add item testing
    @Test
    public void testAddingExistingItem() {
        // Items for testing
        StockItem stockItem = new StockItem((long) 87, "Apple", "Green", 15, 90);


    }

    @Test
    public void testAddingNewItem() {

    }

    @Test
    public void testAddingItemWithNegativeQuantity() {
        // Items for testing
        StockItem stockItem = new StockItem((long) 87, "Apple", "Green", 15, 90);
        SoldItem soldItem = new SoldItem(stockItem, -4, -4 * stockItem.getPrice());

        //Add item
        shoppingCart.addItem(soldItem);
    }

    @Test
    public void testAddingItemWithQuantityTooLarge() {
        // Items for testing
        StockItem stockItem = new StockItem((long) 87, "Apple", "Green", 15, 90);
        SoldItem soldItem = new SoldItem(stockItem, 94, 94 * stockItem.getPrice());

        //Add item
        assertFalse(shoppingCart.addItem(soldItem));
    }

    @Test
    public void testAddingItemWithQuantitySumTooLarge() {

    }

    //Submit current purchase testing

}