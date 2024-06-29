import java.sql.PreparedStatement;

public class Account{
    private final String name;
    private double acctBalance;
    Db db = new Db();

    public Account(String name, double acctBalance) {
        this.name = name;
        if (acctBalance > 0.0) {
            this.acctBalance = acctBalance;
        }
    }
    public void setAccount ()
    {
        try {
            PreparedStatement stmt = db.connect().prepareStatement("INSERT INTO accounts VALUES(?,?,?,?)");
            stmt.setInt(1, 0);
            stmt.setString(2, this.name);
            stmt.setInt(3, Integer.parseInt(this.setAcctNo()));
            stmt.setDouble(4, this.acctBalance);

            int insertCount = stmt.executeUpdate();
            if (insertCount > 0) {
                System.out.println("Registration was successful");
            } else {
                System.out.println("Registration failed");
            }
            stmt.close();
        }
        catch (Exception e)
        {
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


}
