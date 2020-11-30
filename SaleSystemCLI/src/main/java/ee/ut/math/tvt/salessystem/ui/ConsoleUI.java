package ee.ut.math.tvt.salessystem.ui;

import ee.ut.math.tvt.salessystem.SalesSystemException;
import ee.ut.math.tvt.salessystem.dao.HibernateSalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.HistoryItem;
import ee.ut.math.tvt.salessystem.dataobjects.Order;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import ee.ut.math.tvt.salessystem.logic.History;
import ee.ut.math.tvt.salessystem.logic.ShoppingCart;
import ee.ut.math.tvt.salessystem.logic.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * A simple CLI (limited functionality).
 */
public class ConsoleUI {
    private static final Logger log = LogManager.getLogger(ConsoleUI.class);

    private final SalesSystemDAO dao;
    private final ShoppingCart cart;

    public ConsoleUI(SalesSystemDAO dao) {
        this.dao = dao;
        cart = new ShoppingCart(dao);
    }

    public static void main(String[] args) throws Exception {
        SalesSystemDAO dao = new HibernateSalesSystemDAO();
        ConsoleUI console = new ConsoleUI(dao);
        console.run();
    }

    /**
     * Run the sales system CLI.
     */
    public void run() throws IOException {
        System.out.println("===========================");
        System.out.println("=       Sales System      =");
        System.out.println("===========================");
        log.info("Sales System started");
        printUsage();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("> ");
            processCommand(in.readLine().trim().toLowerCase());
            log.info("Sales System finished work");
        }
    }

    private void showStock() {
        List<StockItem> stockItems = dao.findStockItems();
        System.out.println("-------------------------");
        for (StockItem si : stockItems) {
            System.out.println(si.getId() + " " + si.getName() + " " + si.getPrice() + " Euro (" + si.getQuantity() + " units)");
        }
        if (stockItems.size() == 0) {
            System.out.println("\tNothing");
        }
        System.out.println("-------------------------");
    }

    private void removeStock(long id){
        dao.deleteStockitem(id);
        log.debug("StockItem removed: "+id);
        showStock();
    }

    private void addStock(long id, int amount){
        dao.addExistingStockItem(id, amount);
        log.debug("StockItem added: "+id +" "+ amount);
        showStock();
    }

    private void editStockName(long id, String name){
        dao.editStockItemName(id, name);
        log.debug("StockItem name edited: "+id +" "+name);
        showStock();
    }

    private void editStockPrice(long id, long price){
        dao.editStockItemPrice(id, price);
        log.debug("StockItem price edited: "+id +" "+price);
        showStock();
    }

    private void addNewStock(long id, String name, String description, long price, int quantity){
        dao.addNewStockItem(new StockItem(id, name, description, price, quantity));
        log.debug("New stockItem added: "+id +" "+name+" "+description+" "+price+" "+quantity);
        showStock();
    }

    private void deleteStock(long id){
        dao.deleteStockitem(id);
        log.debug("New stockItem deleted: "+id);
        showStock();
    }

    private void showCart() {
        System.out.println("-------------------------");
        for (SoldItem si : cart.getAll()) {
            System.out.println(si.getName() + " " + si.getPrice() + "Euro (" + si.getQuantity() + " items)");
        }
        if (cart.getAll().size() == 0) {
            System.out.println("\tNothing");
        }
        System.out.println("-------------------------");
    }

    private void showTeam() throws IOException {
        System.out.println("-------------------------");
        String dir = System.getProperty("user.dir");
        dir = dir.replace("SaleSystemCLI", "src/main/resources/application.properties");
        log.debug("application.properties location: "+dir);
        try (InputStream input = new FileInputStream(dir)) {
            Properties prop = new Properties();
            prop.load(input);

            System.out.println("Team name: "+prop.getProperty("teamName"));
            System.out.println("Team leader: "+prop.getProperty("teamLeader"));
            System.out.println("Team leader email: "+prop.getProperty("teamLeaderEmail"));
            System.out.println("Team members: "+prop.getProperty("teamMembers"));
            log.debug("Team info: "+prop.getProperty("teamName")+prop.getProperty("teamLeader")+
                    prop.getProperty("teamLeaderEmail")+prop.getProperty("teamMembers"));
        }
        
        catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        System.out.println("-------------------------");
    }
    private void showOrders(List<Order> orders){
        System.out.println("-------------------------");
        if (orders.size() == 0) {
            System.out.println("\tNothing");
        }
        else {
            for (Order order : orders) {
                System.out.println("ID: " + order.getId() + " Date: " + order.getDate() + " Time: " + order.getTime() + " Total: " + order.getTotal());
            }
        }
        System.out.println("-------------------------");
    }
    private void showHistoryAll() {
        List<Order> orders = dao.showAll();
        showOrders(orders);
    }
    private void showHistoryTen() {
        List<Order> orders = dao.showLast10();
        showOrders(orders);
    }
    private void showHistoryDetail(long index){
        List<Order> orders = dao.showAll();
        boolean exists = false;
        for (Order order: orders) {
            if(order.getId().equals(index)){
                exists = true;
                break;
            }
        }
        System.out.println("-------------------------");
        if (!exists) {
            System.out.println("\tNothing");
        }
        else {
            Order order = dao.findOrder(index);
            List<HistoryItem> items = order.getItems();
            for (HistoryItem item: items) {
                System.out.println("ID: "+item.getId()+" Name: "+ item.getName()+ " Price: "+item.getPrice()
                +" Quantity: "+item.getQuantity()+" Sum: "+item.getSum());
            }

        }
        System.out.println("-------------------------");
    }

    private void showHistoryBetweenDates(String string1, String string2) {
        LocalDate date1 = LocalDate.parse(string1);
        LocalDate date2 = LocalDate.parse(string2);
        List<Order> orders = dao.showBetweenDates(date1, date2);
        showOrders(orders);
    }

    private void printUsage() {
        System.out.println("\n----------------------HELP-----------------------");
        System.out.println("h\t\t\tShow the help menu");
        System.out.println("clr\t\t\tClear the screen");
        System.out.println("t\t\t\tShow team contents");

        System.out.println("\n--------------------WAREHOUSE--------------------");
        System.out.println("w\t\t\t\tShow warehouse contents");
        System.out.println("wa <ID> <AMOUNT>\tAdd AMOUNT of item with this specific ID");
        System.out.println();
        System.out.println("To add a new item to the warehouse");
        System.out.println("\twni <ID> <Name> <Description> <Price> <Amount>");
        System.out.println();
        System.out.println("To DELETE an item from the warehouse");
        System.out.println("\twr <ID>");
        System.out.println();
        System.out.println("To edit the specifics of a single warehouse item, use the command");
        System.out.println("\twe <property> <ID> <Parameter>");
        System.out.println("\tSuitable property values\t(name,price)");
        System.out.println("\tParameter value must suit the property that is being changed");
        System.out.println("\tEXAMPLE : we name 1 Estrella - Changes the name of item with id 1 to Estrella");

        System.out.println("\n----------------------HISTORY-----------------------");
        System.out.println("ha\t\t\tShow all past sales");
        System.out.println("ht\t\t\tShow last 10 sales");
        System.out.println("hd <DATE> <DATE>\t\t\tShow last 10 sales");
        System.out.println("hi <ID>\t\t\tShow sale id contents");
        System.out.println("--------------------------------------------------");

        System.out.println("\n----------------------CART-----------------------");
        System.out.println("c\t\t\t\tShow cart contents");
        System.out.println("a <ID> <AMOUNT>\tAdd AMOUNT of stock item with index ID to the cart");
        System.out.println("p\t\t\t\tPurchase the shopping cart");
        System.out.println("r\t\t\t\tReset the shopping cart");
        System.out.println("--------------------------------------------------");
    }

    private void processCommand(String command) throws IOException {
        String[] c = command.split(" ");

        if (c[0].equals("h")) {
            log.info("Showing help");
            printUsage();
        }
        else if (c[0].equals("q")) {
            log.info("Quitting application");
            System.exit(0);
        }
        else if (c[0].equals("t")) {
            log.info("Showing Team info");
            showTeam();
        }
        else if (c[0].equals("ha")) {
            log.info("Showing all history info");
            showHistoryAll();
        }
        else if (c[0].equals("ht")) {
            log.info("Showing last 10 history info");
            showHistoryTen();
        }
        else if (c[0].equals("hi")) { // TODO implement safety net
            int idx = Integer.parseInt(c[1]);
            log.info("Showing details of sale: "+idx);
            showHistoryDetail(idx);
        }
        else if (c[0].equals("hd")) { // TODO implement safety net
            log.info("Showing history info between dates");
            showHistoryBetweenDates(c[1], c[2]);
        }
        else if (c[0].equals("w")) {
            log.info("Showing Stock info");
            showStock();
        }
        else if (c[0].equals("wr"))
            removeStock(Integer.parseInt(c[1])); // TODO implement safety net
        else if (c[0].equals("wa"))
            addStock(Integer.parseInt(c[1]), Integer.parseInt(c[2]));
        else if (c[0].equals("we")){
            if (c[1].equals("name"))
                editStockName(Integer.parseInt(c[2]), c[3]); // TODO implement safety net
            else if (c[1].equals("price"))
                editStockPrice(Integer.parseInt(c[2]), Integer.parseInt(c[3])); // TODO implement safety net
            else {
                log.error("Invalid command");
            }

        }
        else if (c[0].equals("wni"))
            addNewStock(Integer.parseInt(c[1]), c[2], c[3], Integer.parseInt(c[4]), Integer.parseInt(c[5]));
        else if (c[0].equals("c"))
            showCart();
        else if (c[0].equals("p")) {
            cart.submitCurrentPurchase();
        }
        else if (c[0].equals("r")) {
            cart.cancelCurrentPurchase();
        }
        else if (c[0].equals("clr") || c[0].equals("clear")) {
            System.out.println(System.lineSeparator().repeat(50));
            System.out.println("Type h for help");
        }
        else if (c[0].equals("a") && c.length == 3) {
            try {
                long idx = Long.parseLong(c[1]);
                int amount = Integer.parseInt(c[2]);
                StockItem item = dao.findStockItem(idx);
                if (item != null) {
                    cart.addItem(new SoldItem(item, Math.min(amount, item.getQuantity()), item.getPrice() * item.getQuantity()));
                } else {
                    log.error("no stock item with id "+ idx);
                }
            } catch (SalesSystemException | NoSuchElementException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            log.error("unknown command");
        }
    }

}
