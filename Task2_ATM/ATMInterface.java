import java.util.*;

public class ATMInterface {

    // a simple class to hold all data for one user
    static class User {
        String userId;
        String pin;
        double balance;
        ArrayList<String> transactions; // keep a list of what the user did

        public User(String userId, String pin, double balance) {
            this.userId = userId;
            this.pin = pin;
            this.balance = balance;
            this.transactions = new ArrayList<>();
        }
    }

    // simple HashMap in memory
    static HashMap<String, User> userDB = new HashMap<>();
    static Scanner sc = new Scanner(System.in);
    static User currentUser = null; // who is logged in right now

    public static void main(String[] args) {
        // set up sample user
        userDB.put("gift", new User("gift", "1234", 2500.0));

        System.out.println("=== Welcome to the CBZ ATM ===");

        // keep asking for login until success (or user quits)
        while (currentUser == null) {
            currentUser = login();
            if (currentUser == null) {
                System.out.println("Login failed. Try again? (y/n)");
                String choice = sc.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) {
                    System.out.println("Exiting ATM. Goodbye!");
                    return;
                }
            }
        }

        System.out.println("Login successful! Hello, " + currentUser.userId + "!");

        // main menu loop
        boolean exit = false;
        while (!exit) {
            showMenu();
            int option = getIntInput("Choose an option: ");
            switch (option) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    showTransactionHistory();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        sc.close();
    }

    // ask for user id and pin, check them against map
    static User login() {
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine().trim();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine().trim();

        if (userDB.containsKey(userId)) {
            User u = userDB.get(userId);
            if (u.pin.equals(pin)) {
                return u; // everything matches
            } else {
                System.out.println("Incorrect PIN.");
            }
        } else {
            System.out.println("User ID not found.");
        }
        return null;
    }

    // display the main menu options
    static void showMenu() {
        System.out.println("\nATM Main Menu");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transaction History");
        System.out.println("5. Exit");
        
        // TODO: maybe add a transfer feature later
    }

    // simple balance printout
    static void checkBalance() {
        System.out.printf("Your current balance is: $%.2f\n", currentUser.balance);
    }

    // handles depoit – ask for amount, update balance, record it
    static void deposit() {
        double amount = getPositiveDouble("Enter amount to deposit: $");
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        currentUser.balance += amount;
        String record = "Deposited $" + String.format("%.2f", amount);
        currentUser.transactions.add(record);
        System.out.println(record + " successfully.");
        // System.out.println("DEBUG: new balance = " + currentUser.balance); // commented debug line
    }

    // handles withdrawal – check funds, update balance, record it
    static void withdraw() {
        double amount = getPositiveDouble("Enter amount to withdraw: $");
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (amount > currentUser.balance) {
            System.out.println("Insufficient funds. Your balance is $" +
                    String.format("%.2f", currentUser.balance));
            return;
        }
        currentUser.balance -= amount;
        String record = "Withdrew $" + String.format("%.2f", amount);
        currentUser.transactions.add(record);
        System.out.println(record + " successfully.");
    }

    // print all the stored transaction lines for the logged-in user
    static void showTransactionHistory() {
        System.out.println("\nTransaction History for " + currentUser.userId);
        if (currentUser.transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (int i = 0; i < currentUser.transactions.size(); i++) {
                System.out.println((i + 1) + ". " + currentUser.transactions.get(i));
            }
        }
    }

    // reads an int, keep asking until we get one
    static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a number: ");
            sc.next(); // throw away the bad input
        }
        int value = sc.nextInt();
        sc.nextLine(); // consume the leftover newline
        return value;
    }

    // reads a positive double, keep asking if it's not a number
    static double getPositiveDouble(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            System.out.print("Please enter a valid amount: ");
            sc.next();
        }
        double value = sc.nextDouble();
        sc.nextLine(); // consume the leftover newline
        return value;
    }
}