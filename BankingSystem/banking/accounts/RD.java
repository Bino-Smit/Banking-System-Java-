package banking.accounts;
import java.time.LocalDate;
import java.util.Scanner;

public class RD extends Account {

    static Scanner input;
    double minmonthlyamount;
    double time;
    double penalty;
    LocalDate lastdeposit;
    final private static long serialVersionUID = 954689;

    public RD(String a, String b, String c, String d, String e, String f, double y, double time, double mma) {
        super(a, b, c, d, e, f, y, 0.0);
        this.ROI = getROI(0.0);
        this.minmonthlyamount = mma;
        this.time = time;
        this.penalty = 1;
        this.lastdeposit = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
    }

    public double getROI(double time) {
        return 5.5;
    }

    public double Withdraw(double x) {
        int years2 = startDate.until(LocalDate.now()).getYears();
        return this.Accountbalance * Math.pow(1 + ((this.ROI - penalty) / 12), 2 * years2);
    }

    public boolean update() {
        double time = lastdeposit.until(LocalDate.now()).getMonths()
                + lastdeposit.until(LocalDate.now()).getYears() * 12;
        if (time > 0) {
            double amm = 0;
            while (true) {
                System.out.println("Please deposit Rs. " + (int) ((time) * minmonthlyamount)
                        + " to keep the R.D alive:\nEnter -1 to delete this account");
                amm = input.nextDouble();
                if (amm == -1) {
                    return false;
                }
                if (amm < (int) (time * minmonthlyamount))
                    System.out.println("Amount required is greater please enter again:");
                else if (amm > (int) (time * minmonthlyamount))
                    System.out.println("Amount required is lessr please enter again:");
                else
                    break;
            }
            this.Accountbalance += (int) (time * minmonthlyamount);
            this.lastdeposit = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
            return true;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "Account number: %s\n" +
                        "Account balance: %f\n" +
                        "ROI: %f\n" +
                        "Start Date: %s\nLast Deposit: %s\nNext Deposit:%s",
                Accountnumber, Accountbalance, ROI, startDate.toString(), lastdeposit.toString(),
                lastdeposit.plusMonths(1).toString());
    }
}
