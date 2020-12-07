package validators;

public class LoginValidator {

    private int oigus;

    public LoginValidator(String username, String password){
        this.oigus = 0;
        if(username.equals("Cashier") && password.equals("Cashier")){
            this.oigus = 1;
        } else if((username.equals("Warehouse") && password.equals("Warehouse"))){
            this.oigus = 2;
        } else if((username.equals("Manager") && password.equals("Manager"))) {
            this.oigus = 3;
        } else if(username.equals("Client") && password.equals("Client")){
            this.oigus = 4;
        }
    }

    public int getOigus() {
        return this.oigus;
    }
}
