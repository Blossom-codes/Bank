import javax.swing.*;
import java.sql.SQLException;

public class App extends Account {
    public void app() throws SQLException, ClassNotFoundException {
        Input input = new Input();
        String option = JOptionPane.showInputDialog("Welcome to Test Bank\n" +
                "1 - Login\n" +
                "2 - Create an account\n" +
                "3 - Exit");
//        int option = input.inputIntDataFromTerminal("Welcome to Test Bank\n" +
//                "1 - Login\n" +
//                "2 - Create an account\n" +
//                "3 - Exit");
        switch (Integer.parseInt(option)) {
            case 1 -> { // Login
                String userAcctNo = JOptionPane.showInputDialog("Enter Account Number to login ");

                String pin = JOptionPane.showInputDialog("Enter pin to continue: ");

                if (this.login(Integer.parseInt(userAcctNo), Integer.parseInt(pin))) {
                    String newOption = JOptionPane.showInputDialog("" +
                            "1 - Make a Transfer\n" +
                            "2 - Deposit funds\n" +
                            "3 - Check balance");
                    switch (Integer.parseInt(newOption)) {

                        case 1 -> { // Make a Transfer
                            int receiver = input.inputIntDataFromTerminal("Enter account number: ");
                            double amount = input.inputIntDataFromTerminal("Enter amount: ");
                            this.transfer(amount, Integer.parseInt(userAcctNo), receiver);
                            this.logout(Integer.parseInt(userAcctNo));
                        }
                        case 2 -> { // Make a Deposit
                            int amount = input.inputIntDataFromTerminal("Enter amount to deposit: ");
                            this.deposit(amount, Integer.parseInt(userAcctNo), Integer.parseInt(pin));
                            this.logout(Integer.parseInt(userAcctNo));
                        }
                        case 3 -> { // Check Balance
                            this.checkBalance(Integer.parseInt(userAcctNo));
                            this.logout(Integer.parseInt(userAcctNo));
                        }
                    }
                }
            }
            case 2 -> { // Create an account
                String fullName = input.inputStringDataFromTerminal("Enter your Full name: ");
                int deposit = input.inputIntDataFromTerminal("Make a deposit to continue (N):");
                int pin = input.inputIntDataFromTerminal("Create a four digit pin: ");
                if (this.createAccount(fullName, deposit, pin)) {
                    this.setAccount();
                }

            }
            default -> {
                System.out.println("Goodbye");
            }
        }
    }
}
