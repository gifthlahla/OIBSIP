# Number Guessing Game

---

## Description
A simple console‑based number guessing game. The computer picks a random number between 1 and 100, and you have 5 chances to guess it. After each wrong guess you get a hint (too high or too low) and the number of remaining attempts. If you guess correctly you win; otherwise the secret number is revealed and you can play again.

---

## How to Compile and Run
1. Make sure you have **Java 17** (or newer) installed.
2. Save the code above as `NumberGuessingGame.java`.
3. Open a terminal / command prompt in the folder containing the file.
4. Compile the game: 
   ```bash
   javac NumberGuessingGame.java
   ```
   
5. Run the game:
   ```bash 
   java NumberGuessingGame
   ```
   
---   

## Game Rules
- The game generates a random number between 1 and 100.
- You have **5 attempts** to guess the number.
- After each incorrect guess the game tells you whether your guess was too high or too low, and how many attempts you still have.
- If you guess correctly, you win and the game shows how many attempts you used.
- If you run out of attempts without guessing correctly, the game reveals the secret number.
- After each round you are asked **“Play again? (yes/no)”** – typing `yes` (or `y`) starts a new round with a fresh number and reset attempts; any other answer exits the game.
- Non‑numeric input and guesses outside 1‑100 are handled gently and do **not** count as an attempt.

---
