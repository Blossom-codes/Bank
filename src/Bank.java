import java.sql.SQLException;

public class Bank {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        App run = new App();
        try {
            run.app();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}