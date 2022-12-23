package banking.accounts;
public class Savings extends Account {
    double minbalance;
    int transactionsperday;
    final private static long serialVersionUID = 854889;

    public Savings(String a, String b, String c, String d, String e, String f, double y) {
        super(a, b, c, d, e, f, y, 0.0);
        this.ROI = getROI(0.0);
        this.minbalance = 10000.0;
        this.transactionsperday = 3;
    }

    public double getROI(double time) {
        return 5.0;
    }
}
