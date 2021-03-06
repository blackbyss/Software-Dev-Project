package ee.ut.math.tvt.salessystem.dataobjects;

import javax.persistence.*;

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history.
 */
@Entity
@Table(name = "SOLD_ITEM")
public class SoldItem {

    @Id
    @Column(name = "id")
    private Long id;

    @Transient
    private StockItem stockItem;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private double price;

    @Column (name = "total")
    private double sum;

    public SoldItem() {
    }

    /**
     * GUI constructor
     */
    public SoldItem(StockItem stockItem, int quantity, double sum, String name, double price) {
        this.id = stockItem.getId();
        this.stockItem = stockItem;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sum = sum;
    }

    /**
     * CLI constructor
     */
    public SoldItem(StockItem stockItem, int quantity, double sum){
        this.id = stockItem.getId();
        this.stockItem = stockItem;
        this.name = stockItem.getName();
        this.price = stockItem.getPrice();
        this.quantity = quantity;
        this.sum = sum;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }

    @Override
    public String toString() {
        return String.format("SoldItem{id=%d, name='%s'}", id, name);
    }
}
