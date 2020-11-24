package ee.ut.math.tvt.salessystem.logic;

import static org.junit.Assert.*;

import ee.ut.math.tvt.salessystem.dao.HibernateSalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class ShoppingCartTest {

    @Mock
    private ShoppingCart shoppingCart;

    private SalesSystemDAO salesSystemDAO;

    @Before
    public void setup(){
        shoppingCart = new ShoppingCart(salesSystemDAO);
    }

    //Add item testing
    @Test
    public void testAddingExistingItem(){
        // Items for testing
        StockItem stockItem = new StockItem((long) 87, "Apple", "Green", 15, 90);


    }

    @Test
    public void testAddingNewItem(){

    }

    @Test
    public void testAddingItemWithNegativeQuantity(){

    }

    @Test
    public void testAddingItemWithQuantityTooLarge(){

    }

    @Test
    public void testAddingItemWithQuantitySumTooLarge(){

    }

    //Submit current purchase testing

}