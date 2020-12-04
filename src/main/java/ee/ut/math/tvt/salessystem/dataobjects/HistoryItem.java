package ee.ut.math.tvt.salessystem.dataobjects;


import javax.persistence.*;

@Entity
@Table(name= "HISTORY_ITEM")
public class HistoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "total")
    private Double sum;

    public HistoryItem() {

    }

    public HistoryItem(SoldItem soldItem) {
        this.name = soldItem.getName();
        this.quantity = soldItem.getQuantity();
        this.price = soldItem.getPrice();
        this.sum = soldItem.getQuantity() * soldItem.getPrice();
    }

    /**
     * Getters
     */
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getSum() {
        return sum;
    }

    public String toString(){
        return " ";
    }

}
