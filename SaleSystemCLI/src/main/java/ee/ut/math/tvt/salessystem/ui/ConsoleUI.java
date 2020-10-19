package ee.ut.math.tvt.salessystem.ui;

import ee.ut.math.tvt.salessystem.SalesSystemException;
import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import ee.ut.math.tvt.salessystem.logic.ShoppingCart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
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
        SalesSystemDAO dao = new InMemorySalesSystemDAO();
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
        printUsage();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("> ");
            processCommand(in.readLine().trim().toLowerCase());
            System.out.println("Done. ");
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

    private void removeStock(long id, long amount){
        dao.removeStockItem(id, amount);
        showStock();
    }

    private void addStock(long id, long amount){
        dao.addStockItem(id, amount);
        showStock();
    }

    private void editStockID(long id, long newId){
        dao.editItemId(id, newId);
        showStock();
    }

    private void editStockName(long id, String name){
        dao.editItemName(id, name);
        showStock();
    }

    private void editStockPrice(long id, long price){
        dao.editItemPrice(id, price);
        showStock();
    }

    private void editStockAmount(long id, long amount){
        dao.editItemAmount(id, amount);
        showStock();
    }

    private void addNewStock(long id, String name, String description, long price, long quantity){
        dao.addNewStockItem(id, name, description, price, quantity);
        showStock();
    }

    private void deleteStock(long id){
        dao.deleteStockitem(id);
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
        try (InputStream input = new FileInputStream(dir)) {
            Properties prop = new Properties();
            prop.load(input);

            System.out.println("Team name: "+prop.getProperty("teamName"));
            System.out.println("Team leader: "+prop.getProperty("teamLeader"));
            System.out.println("Team leader email: "+prop.getProperty("teamLeaderEmail"));
            System.out.println("Team members: "+prop.getProperty("teamMembers"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("-------------------------");
    }

    private void printUsage() {
        System.out.println("\n----------------------HELP-----------------------");
        System.out.println("h\t\t\tShow the help menu");
        System.out.println("clr\t\t\tClear the screen");
        System.out.println("t\t\t\tShow team contents");

        System.out.println("\n--------------------WAREHOUSE--------------------");
        System.out.println("w\t\t\t\tShow warehouse contents");
        System.out.println("wr <ID> <AMOUNT>\tRemove AMOUNT of item with this specific ID");
        System.out.println("wa <ID> <AMOUNT>\tAdd AMOUNT of item with this specific ID");
        System.out.println();
        System.out.println("To add a new item to the warehouse");
        System.out.println("\twni <ID> <Name> <Description> <Price> <Amount>");
        System.out.println();
        System.out.println("To edit the specifics of a single warehouse item, use the command");
        System.out.println("\twe <property> <ID> <Parameter>");
        System.out.println("\tSuitable property values\t(name,id,price,amount)");
        System.out.println("\tParameter value must suit the property that is being changed");
        System.out.println("\tEXAMPLE : we name 1 Estrella - Changes the name of item with id 1 to Estrella");

        System.out.println("\n----------------------CART-----------------------");
        System.out.println("c\t\t\t\tShow cart contents");
        System.out.println("a <ID> <AMOUNT>\tAdd AMOUNT of stock item with index ID to the cart");
        System.out.println("p\t\t\t\tPurchase the shopping cart");
        System.out.println("r\t\t\t\tReset the shopping cart");
        System.out.println("--------------------------------------------------");
    }

    private void processCommand(String command) throws IOException {
        String[] c = command.split(" ");

        if (c[0].equals("h"))
            printUsage();
        else if (c[0].equals("q"))
            System.exit(0);
        else if (c[0].equals("t"))
            showTeam();
        else if (c[0].equals("w"))
            showStock();
        else if (c[0].equals("wr"))
            removeStock(Integer.parseInt(c[1]), Integer.parseInt(c[2])); // TODO implement safety net
        else if (c[0].equals("wa"))
            addStock(Integer.parseInt(c[1]), Integer.parseInt(c[2]));
        else if (c[0].equals("we")){
            if (c[1].equals("id"))
                editStockID(Integer.parseInt(c[2]), Integer.parseInt(c[3])); // TODO implement safety net
            else if (c[1].equals("name"))
                editStockName(Integer.parseInt(c[2]), c[3]); // TODO implement safety net
            else if (c[1].equals("price"))
                editStockPrice(Integer.parseInt(c[2]), Integer.parseInt(c[3])); // TODO implement safety net
            else if (c[1].equals("amount"))
                editStockAmount(Integer.parseInt(c[2]), Integer.parseInt(c[3])); // TODO implement safety net
            else
                System.out.println("Invalid command");
        }
        else if (c[0].equals("wni"))
            addNewStock(Integer.parseInt(c[1]), c[2], c[3], Integer.parseInt(c[4]), Integer.parseInt(c[5]));
        else if (c[0].equals("wri"))
            deleteStock(Integer.parseInt(c[1]));
        else if (c[0].equals("c"))
            showCart();
        else if (c[0].equals("p"))
            cart.submitCurrentPurchase();
        else if (c[0].equals("r"))
            cart.cancelCurrentPurchase();
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
                    cart.addItem(new SoldItem(item, Math.min(amount, item.getQuantity())));
                } else {
                    System.out.println("no stock item with id " + idx);
                }
            } catch (SalesSystemException | NoSuchElementException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            System.out.println("unknown command");
        }
    }

}
