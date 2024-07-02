import javax.swing.*;
import java.sql.SQLException;

public class Bank {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        App run = new App();
        try {
            String start = JOptionPane.showInputDialog("Enter 1 to start Bank App");
        if (Integer.parseInt(start) == 1)
        {
            run.app();
        }
        } catch (Exception e) {
            System.out.println(e);

        }
    }
}