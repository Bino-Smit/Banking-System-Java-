package banking.accounts;
public class Current extends Account {
    double minbalance;
    final private static long serialVersionUID = 858689;

    public Current(String a, String b, String c, String d, String e, String f, double y) {
        super(a, b, c, d, e, f, y, 0.0);
        this.ROI = getROI(0.0);
        this.minbalance = 5000.0;
    }

    public double getROI(double time) {
        return 2.8;
    }
}
