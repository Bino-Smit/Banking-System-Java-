package banking.accounts;
import java.time.LocalDate;

public class FD extends Account {
    double time; // in years
    double penalty;
    LocalDate maturedate;
    final private static long serialVersionUID = 854689;

    public FD(String a, String b, String c, String d, String e, String f, double y, double time) {
        super(a, b, c, d, e, f, y, 0.0);
        this.ROI = getROI(time);
        this.time = time;
        this.penalty = 1.0;
        this.maturedate = LocalDate.of(LocalDate.now().getYear() + (int) this.time,
                (LocalDate.now().getMonthValue()) + (int) ((this.time - (int) this.time) * 12),
                LocalDate.now().getDayOfMonth());
    }

    public double getROI(double time) {
        return 0.5 * time;
    }

    public double Withdraw(double x) {
        int years = startDate.until(LocalDate.now()).getYears();
        return this.Accountbalance * Math.pow(1 + ((this.ROI - this.penalty) / 2), 2 * years);
    }

    public boolean update() {
        if (LocalDate.now().compareTo(maturedate) > 0) {
            System.out.println(
                    "Your F.D with account number" + this.Accountnumber + "\nHas Matured you have recieved amount:");
            double am = this.Accountbalance * Math.pow(1 + ((this.ROI) / 2), 2 * time);
            System.out.println(am);
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                "\nMature Date:%s", maturedate.toString());
    }
}
