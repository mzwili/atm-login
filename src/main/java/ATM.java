import java.sql.SQLException;
import java.util.Scanner;

public class ATM {
    Scanner input = new  Scanner(System.in);
    int loginTries = 3;
    int personId = 0;

    DatabaseConnection DBconnect;

    public ATM() throws SQLException{
        DBconnect = new DatabaseConnection();
    }

    public void loginCustomer() throws SQLException{
        loginTries--;
        System.out.println("Enter Your Name: ");
        String name = input.next();
        System.out.println("Enter Customer Password: ");
        String password = input.next();
        boolean credentials = DBconnect.login(name, password);
        if(credentials){
            System.out.println("Login Success");
            displayMenu();
        }
        while (loginTries > 0){
            System.out.println("Login Failed, Try Again");
            loginCustomer();
            System.out.println("You've been locked out contact Customer Serve - 0800883300!!!");
            System.exit(0);
        }

    }

    public void displayMenu() throws SQLException{
        System.out.println("************ MENU ************");
        System.out.println("1. Check Balance");
        System.out.println("2. WithDraw");
        System.out.println("3. Deposit");
        System.out.println("4. LogOut");
        System.out.println("******************************");
        System.out.println("Select your Choice: ");
        int choice = input.nextInt();

        switch (choice){
            case 1 : checkBalance();
                    break;
            case 2 : moneyWithdrawal();
                break;
            case 3 : moneyDeposit();
                break;
            case 4 : logOut();
                break;
            default:
                System.out.println("Select Valid Choice no. 1-4!!!");
                break;
        }

    }

    public void checkBalance() throws SQLException{
        personId = DBconnect.customerId;
        System.out.println("R"+DBconnect.getBalance(personId));
        displayMenu();
    }

    public void moneyWithdrawal() throws SQLException{
        System.out.println("Enter Amount : ");
        int amount = input.nextInt();
        DBconnect.withDraw(personId, amount);
        displayMenu();
    }

    public void moneyDeposit() throws SQLException{
        System.out.println("Enter Amount : ");
        int amount = input.nextInt();
        DBconnect.deposit(personId, amount);
        displayMenu();
    }
    public void logOut(){
        System.out.println("Remove Card");
        System.exit(0);
    }

    public static void main(String[] args) throws SQLException{
        ATM machine = new ATM();
        machine.loginCustomer();
    }

}
