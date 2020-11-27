package ee.ut.math.tvt.salessystem.dataobjects;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Order which contains historyItems which are made out of soldItem
 */
@Entity
@Table(name ="ORDER_HISTORY")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private String time;

    @Column(name = "total")
    private double total;

    @Transient
    private List<HistoryItem> items;

    public Order() {

    }

    public Order(List<HistoryItem> items, LocalDate date, String time){
        this.date = date;
        this.time = time;
        this.items = items;
        this.total = totalOrder();
    }

    public double totalOrder(){
        double total = 0;
        for (HistoryItem item: items) {
            total += item.getTotal();
        }
        return total;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public double getTotal() {
        return total;
    }

    public List<HistoryItem> getItems() {
        return items;
    }
}
