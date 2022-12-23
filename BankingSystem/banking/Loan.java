package banking;

import java.time.LocalDate;
import java.util.Scanner;

import database.Database;
import database.FileDatabase;

public class Loan extends Banking {
    public static Scanner input;
    public String type;
    private double EMI;
    private double ROI;
    private double Amount;
    private double time;
    private LocalDate lastpayment;
    final private static long serialVersionUID = 8894689;
    public static Database<Loan> db = new FileDatabase<>("loan.bin");

    public Loan(String a, String b, String c, String d, String e, String f, double ROI, String type, double Amount,
            double time) {
        super(a, b, c, d, e, f);
        this.ROI = ROI;
        this.type = type;
        this.Amount = Amount;
        this.time = time;
        this.EMI = getEMI(this.Amount, this.ROI, this.time);
        this.Amount = this.EMI * (time * 12);
        this.lastpayment = LocalDate.now();
    }

    public String toString() {
        return String.format("Amout: %f\nTime: %f\nType: %s\nEMI: %f\nROI: %f\n",
                Amount, time, type, EMI, ROI);
    }

    public boolean update() {
        if (lastpayment.until(LocalDate.now()).getMonths() > 0) {
            double amm = 0;
            double time = lastpayment.until(LocalDate.now()).getMonths()
                    + lastpayment.until(LocalDate.now()).getYears() * 12;
            while (true) {
                System.out.println("Please deposit Rs." + (int) (time * EMI)
                        + " to repay the loan:\nEnter -1 to delete this account(Note:All Assests will be ceased to recover Loan Amount)");
                amm = input.nextDouble();
                if (amm == -1) {
                    return false;
                }
                if (amm < (int) (time * EMI))
                    System.out.println("Amount required is greater please enter again:");
                else if (amm > (int) (time * EMI))
                    System.out.println("Amount required is less than deposited please deposit correct amount");
                else
                    break;
            }
            this.Amount -= time * EMI;
            this.lastpayment = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
            return true;
        }
        return true;
    }

    public double getEMI(double amm, double r, double n) {
        r = r / 12;
        r = r / 100;
        n = n * 12;
        return (amm * r * Math.pow((1 + r), n) / (Math.pow((1 + r), n) - 1));
    }
}
