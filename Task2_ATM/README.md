# Oasis Infobyte – Task 2: ATM Interface

A simple console-based ATM simulation written in Java 17.
No external libraries, no database – everything is held in memory.

---

## How to Compile and Run

1. Open a terminal in the folder containing `ATMInterface.java`.
2. Compile: 
   ```bash
   javac ATMInterface.java
   ```
3. Run: 
   ```bash
   java ATMInterface
   ```

Make sure you have Java 17 (or later) installed.

---

## Default Login Credentials

Two sample users are hardcoded into the program:

| User ID  | PIN  | Starting Balance |
|----------|------|------------------|
| `gift`    | `1234` | $2500.00       |

---

## Features

- **Login system** – user must enter a valid User ID and PIN.
- **Check Balance** – displays the current account balance.
- **Deposit Money** – asks for an amount, updates the balance, and records the transaction.
- **Withdraw Money** – asks for an amount, checks that it is positive and that sufficient funds exist, then updates the balance and records the transaction.
- **Transaction History** – lists all deposits and withdrawals made during the session (stored as strings).
- **Input validation** – the program handles non-numeric input gracefully and asks again.
- **Menu loop** – after each action the user returns to the main menu until they choose Exit.

---
