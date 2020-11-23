package ee.ut.math.tvt.salessystem.dataobjects;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


public class HistoryItem {

    private final LocalDate date;
    private final String time;
    private final Double total;
    private final List<SoldItem> purchase = new ArrayList<>();

    public HistoryItem(List<SoldItem> purchase) {
        this.date = LocalDate.now();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:00.000"));
        this.purchase.addAll(purchase);
        this.total = sum(purchase);
    }

    private static Double sum(List<SoldItem> items) {
        double sum = 0;
        for (SoldItem item : items)
            sum += item.getPrice() * item.getQuantity();
        return sum;
    }

    public List<SoldItem> getItems() {
        return purchase;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Double getTotal() {
        return total;
    }

    public String toString(){
        return time + " cart contents: " + purchase.toString();
    }

}
