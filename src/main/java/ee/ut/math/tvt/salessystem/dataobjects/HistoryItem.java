package ee.ut.math.tvt.salessystem.dataobjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name= "HISTORY_ITEM")
public class HistoryItem {

    @Id
    @Column(name = "id")
    private Long id;

    @Transient
    private SoldItem soldItem;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "total")
    private Double sum;

    public HistoryItem() {

    }

    public HistoryItem(SoldItem soldItem) {
        this.id = soldItem.getId();
        this.soldItem = soldItem;
        this.quantity = soldItem.getQuantity();
        this.price = soldItem.getPrice();
        this.sum = soldItem.getQuantity() * soldItem.getPrice();
    }

    public Double getTotal() {
        return sum;
    }

    public String toString(){
        return "xd";
    }

}
