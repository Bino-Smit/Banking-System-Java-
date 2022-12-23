import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import banking.Banking;
import banking.Insurance;
import banking.Loan;
import banking.accounts.Account;
import banking.accounts.Current;
import banking.accounts.FD;
import banking.accounts.RD;
import banking.accounts.Salary;
import banking.accounts.Savings;

public class Main {
    private static Scanner input;

    static void exit() {
        Banking.db.close();
        Account.db.close();
        Insurance.db.close();
        Loan.db.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        input = new Scanner(System.in);
        Banking.input = input;
        Insurance.input = input;
        Loan.input = input;
        while (true) {
            while (Banking.loggedin == null) {
                System.out.println("Enter:-\n" +
                        "1 - to login\n" +
                        "2 - to register\n" +
                        "Anything else to exit");
                int ch = input.nextInt();
                input.nextLine();
                if (ch == 1) {
                    Banking.login();
                } else if (ch == 2) {
                    Banking.register();
                } else {
                    exit();
                }
            }
            while (true) {
                System.out.println("Enter the number to go to the indicated menu:-\n" +
                        "1 - Accounts\n" +
                        "2 - Insurance\n" +
                        "3 - Loan\n" +
                        "4 - To Logout");
                int ch = input.nextInt();
                input.nextLine();
                if (ch == 1) {
                    accountsMenu();
                } else if (ch == 2) {
                    insuranceMenu();
                } else if (ch == 3) {
                    loanMenu();
                } else if (ch == 4) {
                    Banking.loggedin = null;
                    break;
                }
            }
        }
    }

    static void accountsMenu() {
        while (true) {
            System.out.println("Enter:-\n" + "1-To check your accounts\n" + "2-To Create a new account\n"
                    + "Anything else to exit");
            int x = input.nextInt();
            if (x == 1) {
                var accounts = Account.db.getObjects(acc -> acc.username.equals(Banking.loggedin.username));
                ArrayList<Savings> a1 = new ArrayList<>();
                ArrayList<Current> a2 = new ArrayList<>();
                ArrayList<Salary> a3 = new ArrayList<>();
                ArrayList<FD> a4 = new ArrayList<>();
                ArrayList<RD> a5 = new ArrayList<>();
                for (var acc : accounts) {
                    if (acc instanceof Savings) {
                        a1.add((Savings) acc);
                    } else if (acc instanceof Current) {
                        a2.add((Current) acc);
                    } else if (acc instanceof Salary) {
                        a3.add((Salary) acc);
                    } else if (acc instanceof FD) {
                        a4.add((FD) acc);
                    } else {
                        a5.add((RD) acc);
                    }
                }
                if (accounts.size() == 0) {
                    System.out.println("You have no accounts");
                    continue;
                } else {
                    System.out.println("Choose the option:- ");
                    int option = 1;
                    if (a1.size() != 0) {
                        System.out.println("Savings accounts:-");
                        for (var acc : a1) {
                            System.out.println(option + " - Account " + acc.Accountnumber);
                            ++option;
                        }
                    }
                    if (a2.size() != 0) {
                        System.out.println("Current accounts:-");
                        for (var acc : a2) {
                            System.out.println(option + " - Account " + acc.Accountnumber);
                            ++option;
                        }
                    }
                    if (a3.size() != 0) {
                        System.out.println("Salary accounts:-");
                        for (var acc : a3) {
                            System.out.println(option + " - Account " + acc.Accountnumber);
                            ++option;
                        }
                    }
                    if (a4.size() != 0) {
                        System.out.println("FD accounts:-");
                        for (var acc : a4) {
                            System.out.println(option + " - Account " + acc.Accountnumber);
                            ++option;
                        }
                    }
                    if (a5.size() != 0) {
                        System.out.println("RD accounts:-");
                        for (var acc : a5) {
                            System.out.println(option + " - Account " + acc.Accountnumber);
                            ++option;
                        }
                    }
                    System.out.println("Anything else to go back");
                }
                int ch = input.nextInt();
                if (1 <= ch) {
                    if (ch <= a1.size()) {
                        savingsMenu(a1.get(ch - 1));
                    } else if ((ch -= a1.size()) <= a2.size()) {
                        currentMenu(a2.get(ch - 1));
                    } else if ((ch -= a2.size()) <= a3.size()) {
                        salaryMenu(a3.get(ch - 1));
                    } else if ((ch -= a3.size()) <= a4.size()) {
                        FDMenu(a4.get(ch - 1));
                    } else if ((ch -= a4.size()) <= a5.size()) {
                        RDMenu(a5.get(ch - 1));
                    }
                }
                continue;
            } else if (x == 2) {
                System.out.println("Enter:-\n" + "1-Create F.D(Fixed Deposit)\n" + "2-Create Savings Account\n"
                        + "3-Create Current Account\n"
                        + "4-Create Salary Account\n" + "5-Create Recurring Deposit Account\n" + "Anything To go back");
                x = input.nextInt();
                input.nextLine();
                Account toAdd = null;
                if (x == 1) {
                    System.out.println("Enter Amount of fixed deposit:");
                    double bal = input.nextDouble();
                    System.out.println("Enter Time(in years) of Fixed Deposit");
                    double time = input.nextDouble();
                    toAdd = new FD(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, bal, time);
                } else if (x == 2) {
                    System.out.println("Enter Amount to be deposited:(Minimum Balance=Rs.10,000)");
                    double bal = input.nextDouble();
                    toAdd = new Savings(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, bal);
                } else if (x == 3) {
                    System.out.println("Enter Amount to be deposited:(Minimum Balance=Rs.5,000)");
                    double bal = input.nextDouble();
                    toAdd = new Current(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, bal);
                } else if (x == 4) {
                    System.out.println("Enter the buisness tie up with salary Account - ");
                    String buis = input.nextLine();
                    System.out.println("Enter Salary:");
                    double sal = input.nextDouble();
                    toAdd = new Salary(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, 0, buis, sal);
                } else if (x == 5) {
                    System.out.println("Enter Monthly Deposit ammount - ");
                    double mma = input.nextDouble();
                    System.out.println("Enter duration of Recurring Deposit (in Years)- ");
                    double time = input.nextDouble();
                    toAdd = new RD(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, mma, time, mma);
                } else {
                    continue;
                }
                if (toAdd != null) {
                    Account.db.addObject(toAdd);
                    continue;
                }
            } else {
                return;
            }
        }
    }

    static void savingsMenu(Savings acc) {
        while (true) {
            System.out.println(acc);
            System.out.println("Chose action:-\n" +
                    "1 - Withdraw money\n" +
                    "2 - Deposit money\n" +
                    "Anything else - Go back");
            int ch = input.nextInt();
            if (ch == 1 || ch == 2) {
                double amount;
                System.out.print("Enter amount: ");
                amount = input.nextDouble();
                if (ch == 1) {
                    acc.Withdraw(amount);
                } else {
                    acc.Deposit(amount);
                }
                continue;
            } else {
                return;
            }
        }
    }

    static void currentMenu(Current acc) {
        while (true) {
            System.out.println(acc);
            System.out.println("Chose action:-\n" +
                    "1 - Withdraw money\n" +
                    "2 - Deposit money\n" +
                    "Anything else - Go back");
            int ch = input.nextInt();
            if (ch == 1 || ch == 2) {
                double amount;
                System.out.print("Enter amount: ");
                amount = input.nextDouble();
                if (ch == 1) {
                    acc.Withdraw(amount);
                } else {
                    acc.Deposit(amount);
                }
                continue;
            } else {
                return;
            }
        }
    }

    static void salaryMenu(Salary acc) {
        while (true) {
            acc.update();
            System.out.println(acc);
            System.out.println("Chose action:-\n" +
                    "1 - Withdraw money\n" +
                    "2 - Deposit money\n" +
                    "Anything else - Go back");
            int ch = input.nextInt();
            if (ch == 1 || ch == 2) {
                double amount;
                System.out.print("Enter amount: ");
                amount = input.nextDouble();
                if (ch == 1) {
                    acc.Withdraw(amount);
                } else {
                    acc.Deposit(amount);
                }
                continue;
            } else {
                return;
            }
        }
    }

    static void FDMenu(FD acc) {
        while (true) {
            if (acc.update()) {
                System.out.println(acc);
                System.out.println("Chose action:-\n" +
                        "1 - Break\n" +
                        "Anything else - Go back");
                int ch = input.nextInt();
                if (ch == 1) {
                    System.out.println("You have receive " + acc.Withdraw(0) + " rupees");
                    Account.db.removeObject(acc);
                    return;
                } else {
                    return;
                }
            } else {
                Account.db.removeObject(acc);
                return;
            }
        }
    }

    static void RDMenu(RD acc) {
        if (acc.update()) {
            System.out.println(acc);
            System.out.println("Choose action:-\n" +
                    "1 - Break\n" +
                    "Anything else - Go back");
            int ch = input.nextInt();
            if (ch == 1) {
                System.out.println("You have received " + acc.Withdraw(0) + " rupees");
                Account.db.removeObject(acc);
            }
        } else {
            Account.db.removeObject(acc);
        }
    }

    static void insuranceMenu() {
        while (true) {
            System.out.println("Enter:-\n" + "1-To List your Insurances\n" + "2-To get new Insurance\n"
                    + "Anything else to go back");
            int x = input.nextInt();
            if (x == 1) {
                var insurances = Insurance.db.getObjects(loan -> loan.username.equals(Banking.loggedin.username));
                int option = 1;
                if (insurances.size() != 0) {
                    System.out.println("Choose the option:-");
                    for (Insurance in : insurances) {
                        System.out.println(option + " - " + in.type);
                        ++option;
                    }
                    System.out.println("Anything else to go back");
                } else {
                    System.out.println("You have no insurances");
                    continue;
                }
                int ch = input.nextInt();
                if (1 <= ch && ch <= insurances.size()) {
                    Insurance in = insurances.get(ch - 1);
                    if (in.update()) {
                        System.out.println(in);
                        System.out.println("Press 1 to claim insurance");
                        int ch2 = input.nextInt();
                        if (ch2 == 1) {
                            in.claim();
                            Insurance.db.removeObject(in);
                        }
                    } else {
                        Insurance.db.removeObject(in);
                    }
                }
                continue;
            } else if (x == 2) {
                System.out.println(
                        "Enter:-\n1-Get Life Insurance\n2-Get Vechicle Insurance\n3-Get Home Insurance\n4-Buisness Insurance\nAnything else to go back");
                x = input.nextInt();
                input.nextLine();
                Insurance toAdd = null;
                if (x == 1) {
                    System.out.println("Enter Your age");
                    String age = input.nextLine();
                    toAdd = new Insurance(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, 500.0,
                            "Life Insurance", age, LocalDate.now().plusYears(50), 10000000.0);
                } else if (x == 2) {
                    System.out.println("Enter:-\n1-Two Wheeler\n2-Four Wheeler\nAnything else to go back");
                    int ch = input.nextInt();
                    input.nextLine();
                    System.out.println("Enter License Plate Number:");
                    String lic = input.nextLine();
                    if (ch == 1) {
                        toAdd = new Insurance(Banking.loggedin.username, Banking.loggedin.password,
                                Banking.loggedin.name, Banking.loggedin.dob, Banking.loggedin.address,
                                Banking.loggedin.sex, 50.0, "Two Wheeler Insurance", lic, LocalDate.now().plusYears(10),
                                200000);
                    } else if (ch == 2) {
                        toAdd = new Insurance(Banking.loggedin.username, Banking.loggedin.password,
                                Banking.loggedin.name, Banking.loggedin.dob, Banking.loggedin.address,
                                Banking.loggedin.sex, 100.0, "Four Wheeler Insurance", lic,
                                LocalDate.now().plusYears(10), 2000000);
                    } else {
                        continue;
                    }
                } else if (x == 3) {
                    System.out.println("Enter Address");
                    String adr = input.nextLine();
                    toAdd = new Insurance(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, 1000.0,
                            "Home Insurance", adr, LocalDate.now().plusYears(20), 10000000.0);
                } else if (x == 4) {
                    System.out.println("Enter Buisness Name-");
                    String name = input.nextLine();
                    toAdd = new Insurance(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, 4000.0,
                            "Buisness Insurance", name, LocalDate.now().plusYears(40), 100000000);
                } else {
                    continue;
                }
                if (toAdd != null) {
                    Insurance.db.addObject(toAdd);
                    continue;
                }
            } else {
                return;
            }
        }
    }

    static void loanMenu() {
        while (true) {
            System.out.println(
                    "Enter:-\n" + "1-To List your Loans\n" + "2-To get a new Loan\n" + "Anything else to go back");
            int x = input.nextInt();
            input.nextLine();
            if (x == 1) {
                var loans = Loan.db.getObjects(l -> l.username.equals(Banking.loggedin.username));
                if (loans.size() != 0) {
                    System.out.println("Choose the option:- ");
                    int option = 1;
                    for (Loan lo : loans) {
                        System.out.println(option + "- " + lo.type);
                        ++option;
                    }
                    System.out.println("Anything else to go back");
                    int ch = input.nextInt();
                    if (1 <= ch && ch <= loans.size()) {
                        Loan lo = loans.get(ch - 1);
                        lo.update();
                        System.out.println(lo);
                        continue;
                    }
                } else {
                    System.out.println("You have no loans");
                    loanMenu();
                }
            } else if (x == 2) {
                System.out.println(
                        "Enter:-\n1-Get Personal Loan\n2-Get Vechicle Loan\n3-Get Home Loan\n4-Get Buisness Loan\n5-Get Gold Loan\n6-Get Education Loan\nAnything else to go back");
                x = input.nextInt();
                input.nextLine();
                Loan toAdd = null;
                if (x == 1) {
                    double amm = 0;
                    while (true) {
                        System.out.println("Enter Loan Amount:");
                        amm = input.nextDouble();
                        if (amm > 1000000) {
                            System.out.println("Maximum Loan amount is Rs.10,00,000 please enter a lower amount");
                        } else {
                            break;
                        }
                    }
                    System.out.println("Enter Duration of Loan(in years):");
                    double time = input.nextDouble();
                    toAdd = new Loan(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, 10.5 + 0.00001 * amm,
                            "Personal Loan", amm, time);
                } else if (x == 2) {
                    double amm = 0;
                    while (true) {
                        System.out.println("Enter Loan Amount:");
                        amm = input.nextDouble();
                        if (amm > 1000000) {
                            System.out.println("Maximum Loan amount is Rs.1,00,00,000 please enter a lower amount");
                        } else {
                            break;
                        }
                    }
                    System.out.println("Enter Duration of Loan(in years):");
                    double time = input.nextDouble();
                    toAdd = new Loan(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, 10.5 + 0.00001 * amm,
                            "Vehicle Loan", amm, time);
                } else if (x == 3) {
                    double amm = 0;
                    while (true) {
                        System.out.println("Enter Loan Amount:");
                        amm = input.nextDouble();
                        if (amm > 1000000) {
                            System.out.println("Maximum Loan amount is Rs.10,00,00,000 please enter a lower amount");
                        } else {
                            break;
                        }
                    }
                    System.out.println("Enter Duration of Loan(in years):");
                    double time = input.nextDouble();
                    toAdd = new Loan(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, 10.5 + 0.000001 * amm,
                            "Home Loan", amm, time);
                } else if (x == 4) {
                    double amm = 0;
                    while (true) {
                        System.out.println("Enter Loan Amount:");
                        amm = input.nextDouble();
                        if (amm > 1000000) {
                            System.out.println("Maximum Loan amount is Rs.50,00,00,000 please enter a lower amount");
                        } else {
                            break;
                        }
                    }
                    System.out.println("Enter Duration of Loan(in years):");
                    double time = input.nextDouble();
                    toAdd = new Loan(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex,
                            10.5 + 0.0000005 * amm, "Buisness Loan", amm, time);
                } else if (x == 5) {
                    double amm = 0;
                    while (true) {
                        System.out.println("Enter Loan Amount:");
                        amm = input.nextDouble();
                        if (amm > 1000000) {
                            System.out.println("Maximum Loan amount is Rs.10,00,000 please enter a lower amount");
                        } else {
                            break;
                        }
                    }
                    System.out.println("Enter Duration of Loan(in years):");
                    double time = input.nextDouble();
                    toAdd = new Loan(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, 10.5 + 0.00001 * amm,
                            "Gold Loan", amm, time);
                } else if (x == 6) {
                    double amm = 0;
                    while (true) {
                        System.out.println("Enter Loan Amount:");
                        amm = input.nextDouble();
                        if (amm > 1000000) {
                            System.out.println("Maximum Loan amount is Rs.1,00,00,000 please enter a lower amount");
                        } else {
                            break;
                        }
                    }
                    System.out.println("Enter Duration of Loan(in years):");
                    double time = input.nextDouble();
                    toAdd = new Loan(Banking.loggedin.username, Banking.loggedin.password, Banking.loggedin.name,
                            Banking.loggedin.dob, Banking.loggedin.address, Banking.loggedin.sex, 10.5 + 0.000001 * amm,
                            "Education Loan", amm, time);
                } else {
                    continue;
                }
                if (toAdd != null) {
                    Loan.db.addObject(toAdd);
                    continue;
                }
            } else {
                return;
            }
        }
    }
}
