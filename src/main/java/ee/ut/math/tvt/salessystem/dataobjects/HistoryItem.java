package ee.ut.math.tvt.salessystem.dataobjects;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class HistoryItem {

    private Date date;
    private Time time;
    private Long total;
    private List<SoldItem> purchase;

    public HistoryItem() {
    }

    public HistoryItem(List<SoldItem> purchase) {
        this.purchase = purchase;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<SoldItem> getPurchase() {
        return purchase;
    }

    public void setPurchase(List<SoldItem> purchase) {
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return "HistoryItem{" +
                "date=" + date +
                ", time=" + time +
                ", total=" + total +
                ", purchase=" + purchase +
                '}';
    }

}
