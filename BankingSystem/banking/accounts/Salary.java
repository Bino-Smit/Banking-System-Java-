package banking.accounts;
import java.time.LocalDate;

public class Salary extends Account {
    String buisness;
    LocalDate lastsalary;
    double salary;
    final private static long serialVersionUID = 853689;

    public Salary(String a, String b, String c, String d, String e, String f, double y, String buis, double sal) {
        super(a, b, c, d, e, f, y, 0.0);
        this.ROI = getROI(0.0);
        this.buisness = buis;
        this.lastsalary = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
        this.salary = sal;
    }

    public double getROI(double time) {
        return 2.8;
    }

    public boolean update() {
        double time = lastsalary.until(LocalDate.now()).getMonths() + lastsalary.until(LocalDate.now()).getYears() * 12;
        if (time > 0) {
            this.Accountbalance += time * salary;
            this.lastsalary = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("\nSalary: %f", this.salary);
    }
}
