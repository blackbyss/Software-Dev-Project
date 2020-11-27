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

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private String time;

    public HistoryItem() {

    }

    public HistoryItem(SoldItem soldItem, int quantity, double sum) {
        this.id = soldItem.getId();
        this.soldItem = soldItem;
        this.quantity = quantity;
        this.price = soldItem.getPrice();
        this.sum = sum;
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:00.000"));
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Double getTotal() {
        return sum;
    }

    public String toString(){
        return "xd";
    }

}
