import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Account {
    private String name;
    private double acctBalance;
    Db db = new Db();

    public Account(String name, double acctBalance) {
        try {
            if (!this.userExists(name)) {
                this.name = name;
                if (acctBalance > 0.00) {
                    this.acctBalance = acctBalance;
                } else {
                    System.out.println("Balance cannot be 0.00 while creating an account ");
                }
            } else {
                System.out.println("A user with this name exists already");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String digit() {
        int digit = (int) (Math.random() * 10);
        return Integer.toString(digit);
    }

    public String setAcctNo() {
        return "01" + this.digit() + this.digit() + this.digit() + this.digit() + this.digit() + this.digit() + this.digit() + this.digit() + this.digit();
    }

    public void setAccount() {
        try {
            PreparedStatement stmt = db.connect().prepareStatement("INSERT INTO accounts (name, accountNumber, accountBalance) VALUES(?,?,?)");

            stmt.setString(1, this.name);
            stmt.setInt(2, Integer.parseInt(this.setAcctNo()));
            stmt.setDouble(3, this.acctBalance);

            int insertCount = stmt.executeUpdate();
            if (insertCount > 0) {
                System.out.println("Registration was successful");
            } else {
                System.out.println("Registration failed");
            }
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public boolean userExists(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT name FROM accounts WHERE name = ?";
        PreparedStatement stmt = db.connect().prepareStatement(sql);
        stmt.setString(1,name);
        ResultSet row = stmt.executeQuery();

        return row.next();
    }
}
