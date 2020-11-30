package validators;

public class LoginValidator {

    private int oigus;

    public LoginValidator(String username, String password){
        if(username.equals("Cashier") && password.equals("Cashier")){
            this.oigus = 1;
            System.out.println("Saadud õigus validaatori poolt: Cashier");
        } else if((username.equals("Warehouse") && password.equals("Warehouse"))){
            this.oigus = 2;
            System.out.println("Saadud õigus validaatori poolt: Warehouse");
        } else if((username.equals("Manager") && password.equals("Manager"))) {
            this.oigus = 3;
            System.out.println("Saadud õigus validaatori poolt: Manager");
        } else if(username.equals("Client") && password.equals("Client")){
            this.oigus = 4;
            System.out.println("Saadud õigus validaatori poolt: Client");
        }
    }

    public int getOigus() {
        return oigus;
    }
}
