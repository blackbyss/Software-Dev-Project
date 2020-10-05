package ee.ut.math.tvt.salessystem.logic;

import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;

import java.util.ArrayList;
import java.util.List;

public class History {

    public final List<HistoryItem> items = new ArrayList<>();
    public void addToHistory(HistoryItem item){
        items.remove(item);
    }
    public void removeFromHistory(HistoryItem item){
        items.remove(item);
    }
    public List<HistoryItem> showBetweenDates(){
        return items;
    }
    public List<HistoryItem> showLast10(){
        return items;
    }
    public List<HistoryItem> showAll(){
        return items;
    }

}
