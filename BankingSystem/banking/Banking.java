package banking;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import database.Database;
import database.FileDatabase;

public class Banking implements Serializable {
    public String username;
    public String password;
    public String name;
    public String dob;
    public String address;
    public String sex;
    public static Scanner input;
    public static Banking loggedin = null;
    public static Database<Banking> db = new FileDatabase<>("banking.bin");
    final private static long serialVersionUID = 854687;

    public Banking(String a, String b, String c, String d, String e, String f) {
        this.username = a;
        this.password = b;
        this.name = c;
        this.dob = d;
        this.address = e;
        this.sex = f;
    }

    public static void login() {
        String username, password;
        System.out.println("Enter Username - ");
        username = input.nextLine();
        System.out.println("Enter Password - ");
        password = input.nextLine();
        loggedin = db.getObject(x -> x.username.equals(username) && x.password.equals(password));
        if (loggedin == null) {
            System.out.println("Incorrect password or username");
        } else {
            System.out.println("Logged in successfully");
        }
    }

    public static void register() {
        String a, b, c, d, e, f;
        System.out.println("Enter Username - ");
        a = input.nextLine();
        System.out.println("Enter Password - ");
        b = input.nextLine();
        System.out.println("Enter Full name - ");
        c = input.nextLine();
        while (true) {
            System.out.println("Enter Date of Birth in the format - dd/mm/yyyy - ");
            d = input.nextLine();
            if (d == null) {
                System.out.println("Error please enter Date Of Birth");
                continue;
            } else {
                SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
                sdfrmt.setLenient(false);
                try {
                    sdfrmt.parse(d);
                    break;
                } catch (ParseException e1) {
                    System.out.println("Invalid Date format");
                    continue;
                }
            }
        }
        System.out.println("Enter Address - ");
        e = input.nextLine();
        while (true) {
            System.out.println("Enter Sex(M/F) - ");
            String M = "M";
            String F = "F";
            f = input.nextLine();
            if (!f.toUpperCase().equals(M) && !f.toUpperCase().equals(F)) {
                System.out.println("Enter Valid Sex");
                continue;
            }
            break;
        }
        Banking kl = new Banking(a, b, c, d, e, f);
        Banking.loggedin = kl;
        db.addObject(kl);
    }
}