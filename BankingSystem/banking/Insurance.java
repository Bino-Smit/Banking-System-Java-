package banking;
import java.time.LocalDate;
import java.util.Scanner;

import database.Database;
import database.FileDatabase;

public class Insurance extends Banking {

    public static Scanner input;
    double EMI;
    private LocalDate Maturitydate;
    private LocalDate lastemi;
    private LocalDate nextemi;
    public String type;
    private String info;
    double claimamount;
    final private static long serialVersionUID = 894689;
    public static Database<Insurance> db = new FileDatabase<>("insurance.bin");

    public Insurance(String a, String b, String c, String d, String e, String f, double EMI,
            String type, String info, LocalDate mdate, double claim) {
        super(a, b, c, d, e, f);
        this.EMI = EMI;
        this.Maturitydate = mdate;
        this.lastemi = LocalDate.now();
        this.nextemi = LocalDate.now().plusMonths(1);
        this.type = type;
        this.info = info;
        this.claimamount = claim;
    }

    @Override
    public String toString() {
        return String.format(
                "Type: %s\nInfo: %s\nClaim amount: %f\nEMI: %f\nMaturity date: %s\nLast emi: %s\nNext emi: %s\n",
                type, info, claimamount, EMI, Maturitydate.toString(), lastemi.toString(), nextemi.toString());
    }

    public boolean update() {
        if (LocalDate.now().compareTo(Maturitydate) >= 0) {
            System.out.println("Insurance Expired");
            return false;
        } else if (LocalDate.now().compareTo(this.nextemi) > 0) {
            double time = lastemi.until(LocalDate.now()).getMonths() + lastemi.until(LocalDate.now()).getYears() * 12;
            System.out.println("Please deposit Rs." + (int) (time * EMI)
                    + " to keep the insurance alive\nEnter -1 to stop this insurance");
            double x = input.nextDouble();
            if (x != -1) {
                this.lastemi = LocalDate.now();
                this.nextemi = LocalDate.of(LocalDate.now().getYear(), (LocalDate.now().getMonthValue() + 1) % 13, 1);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean claim() {
        System.out.println("Insurance Claimed.Amount Recived is:" + this.claimamount);
        return false;
    }
}
