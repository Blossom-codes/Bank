import java.sql.SQLException;

public class App extends Account {
    public void app() throws SQLException, ClassNotFoundException {
        Input input = new Input();
        int option = input.inputIntDataFromTerminal("Welcome to Test Bank\n" +
                "1 - Login\n" +
                "2 - Create an account\n" +
                "3 - Exit");
        switch (option) {
            case 1 -> { // Login
                int userAcctNo = input.inputIntDataFromTerminal("Enter Account Number to login ");
                int pin = input.inputIntDataFromTerminal("Enter pin to continue: ");

                if (this.login(userAcctNo, pin)) {
                    int newOption = input.inputIntDataFromTerminal("" +
                            "1 - Make a Transfer\n" +
                            "2 - Deposit funds\n" +
                            "3 - Check balance");
                    switch (newOption) {

                        case 1 -> { // Make a Transfer
                            int receiver = input.inputIntDataFromTerminal("Enter account number: ");
                            double amount = input.inputIntDataFromTerminal("Enter amount: ");
                            this.transfer(amount, userAcctNo, receiver);
                            this.logout(userAcctNo);
                        }
                        case 2 -> { // Make a Deposit

                        }
                        case 3 -> { // Check Balance
                            this.checkBalance(userAcctNo);
                            this.logout(userAcctNo);
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
