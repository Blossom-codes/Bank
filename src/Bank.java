import java.sql.SQLException;

public class Bank {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Account run = new Account("Hakeem Olajuwon", 430.35);
        run.setAccount();
    }
}