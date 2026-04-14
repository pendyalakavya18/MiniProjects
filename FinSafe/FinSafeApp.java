import java.util.ArrayList;
import java.util.Scanner;

// Custom exception for insufficient funds
class InSufficientFundsException extends Exception {
    public InSufficientFundsException(String message) {
        super(message);
    }
}

// Account class to handle logic
class Account {
    private String accountHolder;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Account(String name, double initialBalance) {
        this.accountHolder = name;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Initial Deposit: $" + initialBalance);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
        addTransaction("Deposited: $" + amount);
        System.out.println("Success! New Balance: $" + balance);
    }

    public void processTransaction(double amount) throws InSufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new InSufficientFundsException("Insufficient funds! Available: $" + balance);
        }
        balance -= amount;
        addTransaction("Withdrew: $" + amount);
        System.out.println("Success! Remaining Balance: $" + balance);
    }

    private void addTransaction(String entry) {
        transactionHistory.add(entry);
        if (transactionHistory.size() > 5) {
            transactionHistory.remove(0);
        }
    }

    public void printMiniStatement() {
        System.out.println("\n--- Mini Statement for " + accountHolder + " ---");
        System.out.println("Current Balance: $" + balance);
        System.out.println("Last 5 Transactions:");
        for (String t : transactionHistory) {
            System.out.println("- " + t);
        }
        System.out.println("----------------------------------\n");
    }
}

// Main Application entry point
public class FinSafeApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to FinSafe Digital Wallet!");
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();

        Account myAccount = new Account(name, 500.0); // Starting with $500

        boolean exit = false;
        while (!exit) {
            System.out.println("\nOptions: (1) Deposit, (2) Withdraw, (3) Mini Statement, (4) Exit");
            System.out.print("Choose (1-4): ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter deposit amount: ");
                        double depAmt = Double.parseDouble(scanner.nextLine());
                        myAccount.deposit(depAmt);
                        break;

                    case "2":
                        System.out.print("Enter amount to spend: ");
                        double wthAmt = Double.parseDouble(scanner.nextLine());
                        myAccount.processTransaction(wthAmt);
                        break;

                    case "3":
                        myAccount.printMiniStatement();
                        break;

                    case "4":
                        exit = true;
                        System.out.println("Thank you for using FinSafe!");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InSufficientFundsException e) {
                System.out.println("FAILED: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
