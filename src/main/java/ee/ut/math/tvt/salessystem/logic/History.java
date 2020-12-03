package ee.ut.math.tvt.salessystem.logic;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class History {

    private final SalesSystemDAO dao;
    public final List<HistoryItem> items = new ArrayList<>();


    public History(SalesSystemDAO dao) {
        this.dao = dao;
    }

    /**
     * Methods used by HibernateDAO to retrieve orders
     */
    public List<Order> showAll() {
        return dao.showAll();
    }

    public List<Order> showLast10() {
        return dao.showLast10();
    }

    public List<Order> showBetweenDates(LocalDate begin, LocalDate end) {
        return dao.showBetweenDates(begin, end);
    }

    /**
     * Getters
     */
    public Order getOrder(long id){
        return dao.findOrder(id);
    }

    /**
     * Best-selling items
     */
    public HashMap<String, Integer> getBestSellingItems(String id, LocalDate begin, LocalDate end){

        HashMap<String, Integer> bestSelling = new HashMap<>();

        if (id.equals("1")){
            bestSelling = calculateBestSellingItems(dao.showBetweenDates(begin, end), bestSelling);
        } else if(id.equals("2")){
            bestSelling = calculateBestSellingItems(dao.showLast10(), bestSelling);
        } else {
            bestSelling = calculateBestSellingItems(dao.showAll(), bestSelling);
        }

        //If HashMap is not empty
        if (bestSelling != null){
            //Sort Hashmap
            bestSelling = bestSelling.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }
        return bestSelling;

    }

    /**
     *  Creates HashMap for selected HistoryItems
     */
    public HashMap<String, Integer> calculateBestSellingItems(List<Order> orders, HashMap<String, Integer> toodeteKogused){
        
        //yhtegi ostu pole sooritatud
        if(orders == null){
            return null;
        }

        for (Order order: orders) {
            for (HistoryItem item : order.getItems()){
                if(!toodeteKogused.containsKey(item.getName())){
                    toodeteKogused.put(item.getName(), item.getQuantity());
                } else {
                    toodeteKogused.replace(item.getName(), toodeteKogused.get(item.getName()) + item.getQuantity());
                }
            }
        }

        return toodeteKogused;
    }
}
