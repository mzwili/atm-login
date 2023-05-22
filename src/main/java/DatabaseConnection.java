import java.sql.*;


public class DatabaseConnection {

    Statement state = null;
    ResultSet rs = null;
    Connection connect = null;

    int customerId = 0;
    int balance = 0;

    public DatabaseConnection(){

        try{
            //connection to mysql DB Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?characterEncoding=latin1","root","Mzwili@sql1");
            System.out.println("Connected !!!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public  boolean login(String name, String password) throws SQLException {
        state = connect.createStatement();
        rs = state.executeQuery("select * from customer where customerName= '"+name+"' and customerPassword= '"+password+"'");
        if(rs.next()){
            this.customerId = rs.getInt("customerId");
            return true;
        }
        return false;
    }

    public int getBalance(int id) throws SQLException{
        rs = state.executeQuery("select AccountBalance from Account where customerId="+id+"");
        if(rs.next()){
            this.balance = rs.getInt("AccountBalance");

        }
        return this.balance;
    }

    public boolean withDraw(int id, int amount) throws SQLException{
        if( !(this.balance >= amount)){
            System.out.println("Insufficient funds!");
            return false;
        }
        state.executeUpdate("update Account set AccountBalance="+(this.balance-amount)+" where customerId="+id);
        System.out.println("WithDrawal Success!!!");
        return true;

    }

    public boolean deposit(int id, int amount) throws SQLException{
        int success = state.executeUpdate("update Account set AccountBalance="+(this.balance+amount)+" where customerId="+id);
        if( success == 1){
            System.out.println("Deposit Successful !!!");
            return true;
        }
        System.out.println("Deposit unsuccessful !!!");
        return false;

    }
}
