package ee.ut.math.tvt.salessystem.dataobjects;


import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Order which contains historyItems
 */
@Entity
@Table(name ="ORDER_HISTORY")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "dateSQL")
    private Date dateSQL;

    @Column(name = "time")
    private String time;

    @Column(name = "total")
    private double total;

    @OneToMany(cascade = CascadeType.ALL)
    private List<HistoryItem> items;

    /**
     * Constructors
     */
    public Order() {

    }

    public Order(List<HistoryItem> items, LocalDate date, String time){
        this.date = date;
        this.dateSQL = Date.valueOf(date);
        this.time = time;
        this.items = items;
        this.total = totalOrder();
    }

    /**
     * Get order total
     */
    public double totalOrder(){
        double total = 0;
        for (HistoryItem item: items) {
            total += item.getSum();
        }
        return total;
    }

    public Long getId() {
        return id;
    }

    /**
     * Getters
     */
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
