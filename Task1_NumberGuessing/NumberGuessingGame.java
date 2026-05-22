import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

public class NumberGuessingGame {

    public static void main(String[] args) {
        // set up the tools we need
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // how many tries the player gets
        final int MAX_ATTEMPTS = 5;

        // keeps the game running until the user wants to quit
        boolean playAgain = true;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I have picked a number between 1 and 100.");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess it.\n");

        while (playAgain) {
            // pick a fresh secret number for each round
            int secretNumber = random.nextInt(100) + 1;
            int attemptsUsed = 0;
            boolean guessedCorrectly = false;

            // give the player up to MAX_ATTEMPTS tries
            while (attemptsUsed < MAX_ATTEMPTS && !guessedCorrectly) {
                System.out.print("Enter your guess (1-100): ");
                int userGuess;

                // make sure we actually get a number
                try {
                    userGuess = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Oops, that doesn't look like a number. Please try again.");
                    scanner.next(); // clear the bad input
                    continue;       // don't count this as an attempt
                }

                // check if the guess is inside the allowed range
                if (userGuess < 1 || userGuess > 100) {
                    System.out.println("Please guess a number between 1 and 100.");
                    continue;       // don't waste an attempt on a silly guess
                }

                // valid guess – count it
                attemptsUsed++;

                // compare with the secret number
                if (userGuess == secretNumber) {
                    guessedCorrectly = true;
                    System.out.println("Congratulations! You guessed the number in "
                            + attemptsUsed + " attempt(s).");
                } else if (userGuess < secretNumber) {
                    System.out.println("Too low! Attempts left: "
                            + (MAX_ATTEMPTS - attemptsUsed));
                } else {
                    System.out.println("Too high! Attempts left: "
                            + (MAX_ATTEMPTS - attemptsUsed));
                }
            }

            // if they never guessed it, reveal the number
            if (!guessedCorrectly) {
                System.out.println("Sorry, you've used all " + MAX_ATTEMPTS
                        + " attempts. The number was " + secretNumber + ".");
            }

            // ask if they want another round
            System.out.print("\nPlay again? (yes/no): ");
            String answer = scanner.next().trim().toLowerCase();

            if (answer.equals("yes") || answer.equals("y")) {
                playAgain = true;
                System.out.println(); // blank line for readability
            } else {
                playAgain = false;
                System.out.println("Thanks for playing! Goodbye.");
            }
        }

        scanner.close();
    }
}