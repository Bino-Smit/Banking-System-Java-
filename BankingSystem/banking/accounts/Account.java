package banking.accounts;
import java.time.LocalDate;

import banking.Banking;
import database.Database;
import database.FileDatabase;

public abstract class Account extends Banking {
    public String Accountnumber;
    double Accountbalance;
    double ROI;
    LocalDate startDate;
    final private static long serialVersionUID = 854688;
    public static Database<Account> db = new FileDatabase<>("accounts.bin");

    public Account(String a, String b, String c, String d, String e, String f, double y, double z) {
        super(a, b, c, d, e, f);
        this.Accountnumber = CreateAccount();
        this.Accountbalance = y;
        this.ROI = z;
        startDate = LocalDate.now();
    }

    public double checkbalance() {
        return this.Accountbalance;
    }

    public double checkROI() {
        return this.ROI;
    }

    public String CreateAccount() {
        String AlphaNumericString = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int x = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(x));
        }
        return sb.toString();
    }

    public double Withdraw(double x) {
        if (this.Accountbalance < x) {
            System.out.println("Insufficient Balance:");
            return this.Accountbalance;
        }
        this.Accountbalance -= x;
        return this.Accountbalance;
    }

    public double Deposit(double x) {
        this.Accountbalance += x;
        return this.Accountbalance;
    }

    abstract public double getROI(double time);

    @Override
    public String toString() {
        return String.format(
                "Account number: %s\n" +
                        "Account balance: %f\n" +
                        "ROI: %f\n" +
                        "Start Date: %s",
                Accountnumber, Accountbalance, ROI, startDate.toString());
    }
}