import java.sql.SQLException;

public interface Functions {
    public String getDigit();

    public String setAcctNo();

    public boolean userExists(String name) throws SQLException, ClassNotFoundException;

    public void deposit(double amount, int acctNo);
    public void checkBalance(int acctNo) throws SQLException, ClassNotFoundException;

    public void transfer(double amount, int sender, int receiver);

    public boolean login(int acctNo, int pin) throws SQLException, ClassNotFoundException;
    public void logout(int acctNo);

    public boolean createAccount(String name, double acctBalance, int pin) throws SQLException, ClassNotFoundException;

    public boolean setAccount() throws SQLException, ClassNotFoundException;
}
