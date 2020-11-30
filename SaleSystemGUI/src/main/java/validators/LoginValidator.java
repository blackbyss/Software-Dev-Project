package validators;

public class LoginValidator {

    private int oigus;

    public LoginValidator(String username, String password){
        if(username.equals("Cashier") && password.equals("Cashier")){
            this.oigus = 1;
            System.out.println("Saadud õigus validaatori poolt: Cashier");
        }
    }

    public int getOigus() {
        return oigus;
    }
}
