package ee.ut.math.tvt.salessystem.logic;

import static org.junit.Assert.*;

import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WarehouseTest {

    @Mock
    private Warehouse warehouse;

    private InMemorySalesSystemDAO dao;

    @Before
    public void setUp() {
        this.dao = new InMemorySalesSystemDAO();
        this.warehouse = new Warehouse(dao);
    }

    @Test
    public void testAddingExistingItem() {
        int preAddQuantity = dao.findStockItems().get(0).getQuantity();
        dao.addStockItem(1, 10);
        int postAddQuantity = dao.findStockItems().get(0).getQuantity();
        assert preAddQuantity < postAddQuantity;
    }
    @Test
    public void testAddNewItem(){
        List<StockItem> stockItems = dao.findStockItems();
        int warehouseSize = stockItems.size();
        StockItem stockItem = new StockItem((long) 87, "Apple", "Green", 15, 90);
        dao.saveStockItem(stockItem);
        assert warehouseSize < dao.findStockItems().size();
    }
    @Test
    public void testDeleteItem(){
        List<StockItem> stockItems = dao.findStockItems();
        int warehouseSize = stockItems.size();
        dao.deleteStockitem(1);
        assert warehouseSize > dao.findStockItems().size();
    }
}
