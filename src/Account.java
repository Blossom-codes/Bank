import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account implements Functions {
    private String name;
    private int pin;
    private double acctBalance;
    Db db = new Db();




    @Override
    public boolean createAccount(String name, double acctBalance, int pin) throws SQLException, ClassNotFoundException {


            if (!this.userExists(name)) {
                if ( !name.isEmpty()) {
                    this.name = name;
                    if (acctBalance > 0.00) {
                        this.acctBalance = acctBalance;
                        if (Integer.toString(pin).length() == 4) {
                            this.pin = pin;
                            return true;
                        } else {
                            System.out.println("Please enter a valid pin");
                            return false;
                        }
                    } else {
                        System.out.println("Balance cannot be 0.00 while creating an account ");
                        return false;
                    }
                }else {
                    System.out.println("Enter a valid name");
                    return false;
                }
            } else {
                System.out.println("A user with this name exists already");
                return false;
            }
        }


    @Override
    public String getDigit() {
        int digit = (int) (Math.random() * 10);
        return Integer.toString(digit);
    }

    @Override
    public String setAcctNo() {
        return "01" + this.getDigit() + this.getDigit() + this.getDigit() + this.getDigit() + this.getDigit() + this.getDigit() + this.getDigit() + this.getDigit() + this.getDigit();
    }

    @Override
    public boolean setAccount() throws SQLException, ClassNotFoundException {

            PreparedStatement stmt = db.connect().prepareStatement("INSERT INTO accounts (name, accountNumber, accountBalance, pin,status) VALUES(?,?,?,?,?)");

            stmt.setString(1, this.name);
            stmt.setInt(2, Integer.parseInt(this.setAcctNo()));
            stmt.setDouble(3, this.acctBalance);
            stmt.setInt(4, this.pin);
            stmt.setString(5, "");

            int insertCount = stmt.executeUpdate();
            if (insertCount > 0) {
                stmt.close();
                System.out.println("Registration was successful");
                return true;
            } else {
                System.out.println("Registration failed");
                return false;
            }

    }

    @Override
    public boolean userExists(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT name FROM accounts WHERE name = ?";
        PreparedStatement stmt = db.connect().prepareStatement(sql);
        stmt.setString(1, name);
        ResultSet row = stmt.executeQuery();

        return row.next();
    }


    @Override
    public void transfer(double amount, int sender, int receiver) {
        try {
            String sqlReceiver = "SELECT *  FROM accounts WHERE accountNumber = ?";
            PreparedStatement stmtReceiver = db.connect().prepareStatement(sqlReceiver);
            stmtReceiver.setInt(1, receiver);
            ResultSet resultReceiver = stmtReceiver.executeQuery();


            if (resultReceiver.next()) {
                String nameReceiver = resultReceiver.getString("name");
                if (amount >= 1) {
                    if (!nameReceiver.isEmpty()) {
                        System.out.println("Processing Transfer...");
                        Input input = new Input();
                        int cont = input.inputIntDataFromTerminal("You're about to transfer N" + amount + " to " + receiver + " - " + nameReceiver + ", Enter 1 to continue: ");
                        if (cont == 1) {
                            String sqlSender = "SELECT *  FROM accounts WHERE accountNumber = ?";
                            PreparedStatement stmtSender = db.connect().prepareStatement(sqlSender);
                            stmtSender.setInt(1, sender);
                            ResultSet resultSender = stmtSender.executeQuery();

                            if (resultSender.next()) {
//                                DEBIT SENDER ACCOUNT
                                double senderOldBalance = resultSender.getDouble("accountBalance");
                                if (senderOldBalance > amount) {
                                    double senderNewBalance = senderOldBalance - amount;
                                    String updateSenderSql = "UPDATE accounts SET accountBalance = ? WHERE accountNumber = ?";
                                    PreparedStatement updateSenderStmt = db.connect().prepareStatement(updateSenderSql);
                                    updateSenderStmt.setDouble(1, senderNewBalance);
                                    updateSenderStmt.setInt(2, sender);
                                    if (updateSenderStmt.executeUpdate() > 0) {
//                                    CREDIT RECEIVER ACCOUNT
                                        double receiverOldBalance = resultReceiver.getDouble("accountBalance");
                                        double receiverNewBalance = receiverOldBalance + amount;
                                        String updateReceiverSql = "UPDATE accounts SET accountBalance = ? WHERE accountNumber = ?";
                                        PreparedStatement updateReceiverStmt = db.connect().prepareStatement(updateReceiverSql);
                                        updateReceiverStmt.setDouble(1, receiverNewBalance);
                                        updateReceiverStmt.setInt(2, receiver);
                                        if (updateReceiverStmt.executeUpdate() > 0) {
                                            System.out.println("Transfer was Successful!");
                                        } else {
                                            System.out.println("Transfer Failed");
                                        }
//
                                        updateReceiverStmt.close();
                                    }
                                    stmtSender.close();
                                    updateSenderStmt.close();
                                } else {
                                    System.out.println("Insufficient Funds!");
                                }
                            }
                        }
                    } else {
                        System.out.println("Couldn't find account");
                    }
                } else {
                    System.out.println("You can't transfer an amount below N1");
                }

//            }

            } else {
                System.out.println("Couldn't find account");
            }

            stmtReceiver.close();


        } catch (Exception e) {
            System.out.println(e);
        }


    }

    @Override
    public boolean login(int acctNo, int pin) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM accounts WHERE accountNumber = ?";
        PreparedStatement stmt = db.connect().prepareStatement(sql);
        stmt.setInt(1, acctNo);
        ResultSet result = stmt.executeQuery();
        if (result.next() && result.getInt("pin") == pin) {

            String updateSql = "UPDATE accounts SET status = ? WHERE accountNumber = ? ";
            PreparedStatement updateStmt = db.connect().prepareStatement(updateSql);
            String newValue = "online";
            updateStmt.setString(1, newValue);
            updateStmt.setInt(2, acctNo);
            int updateCount = updateStmt.executeUpdate();
            if (updateCount > 0) {
                updateStmt.close();
                stmt.close();
                JOptionPane.showMessageDialog(null, "Login was successful","Notification", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid login credentials");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid login credentials");
            return false;
        }


    }

    @Override
    public void checkBalance(int acctNo) throws SQLException, ClassNotFoundException {

            String sql = "SELECT * FROM accounts WHERE accountNumber = ?";
            PreparedStatement stmt = db.connect().prepareStatement(sql);
            stmt.setInt(1, acctNo);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                System.out.println("Current Account Balance: " + result.getString("accountBalance"));
                stmt.close();
            }




    }

    @Override
    public void deposit(double amount, int acctNo, int pin) {
        try {
            String sql = "SELECT * FROM accounts WHERE accountNumber = ?";
            PreparedStatement stmt = db.connect().prepareStatement(sql);
            stmt.setInt(1, acctNo);
            ResultSet result = stmt.executeQuery();
            if (result.next() && (amount > 0)) {

                String updateSql = "UPDATE accounts SET accountBalance = ? WHERE accountNumber = ? AND pin = ? ";
                PreparedStatement updateStmt = db.connect().prepareStatement(updateSql);
                double newValue = result.getDouble("accountBalance") + amount;
                updateStmt.setDouble(1, newValue);
                updateStmt.setInt(2, acctNo);
                updateStmt.setInt(3, pin);
                int updateCount = updateStmt.executeUpdate();
                if (updateCount > 0) {

                    updateStmt.close();
                    stmt.close();
                    System.out.println("Deposit has been made successfully");
                } else {
                    System.out.println("Error occurred while depositing");
                }
            } else {
                System.out.println("Error occurred while depositing");
            }


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void logout(int acctNo) {
        try {
            String sql = "SELECT * FROM accounts WHERE accountNumber = ?";
            PreparedStatement stmt = db.connect().prepareStatement(sql);
            stmt.setInt(1, acctNo);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {

                String updateSql = "UPDATE accounts SET status = ? WHERE accountNumber = ? ";
                PreparedStatement updateStmt = db.connect().prepareStatement(updateSql);
                String newValue = "offline";
                updateStmt.setString(1, newValue);
                updateStmt.setInt(2, acctNo);
                int updateCount = updateStmt.executeUpdate();
                if (updateCount > 0) {

                    updateStmt.close();
                    stmt.close();
                    System.out.println("Logged Out");
                } else {
                    System.out.println("Couldn't logout");
                }
            } else {
                System.out.println("Couldn't logout");
            }


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
