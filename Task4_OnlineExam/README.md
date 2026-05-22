# Online Exam System (Java Swing)

A simple desktop application built for the Oasis Infobyte Java Internship (Task 4).

It lets a student log in, take a 5-question timed quiz, and see their results at the end.

---

## What It Does

- Shows a login screen where you enter a username and password
- After login, a popup appears with the exam instructions
- Gives you 5 multiple-choice questions, one at a time
- Each question has four options (A, B, C, D) with radio buttons
- Has Previous and Next buttons to move between questions
- The Next button turns into a Submit button on the last question
- A 2-minute countdown timer runs at the top of the screen
- When time runs out, the exam submits automatically
- After submitting, a result window pops up showing:
  - Your overall score (like "You scored 4 out of 5")
  - A breakdown of every question with your answer and the correct one
- A Close button on the result screen exits the program

---

## Login Details

- Username: student
- Password: exam123

(These are hardcoded in LoginScreen.java)

---

## Files in This Project

```
Task4_OnlineExam/
OnlineExamSystem.java - The main class that starts everything
LoginScreen.java - The login window
ExamFrame.java - The actual exam with questions and timer
ResultFrame.java - The score and answer breakdown screen
```

---

## What You Need

- Java JDK 17 or newer
- A terminal or command prompt
- No external libraries, just plain Java Swing

---

## How to Compile and Run

1. Put all four .java files in the same folder
2. Open a terminal in that folder
3. Compile everything: 
   ```bash
   javac *.java
   ```
4. Run the program: 
   ```bash
   java OnlineExamSystem
   ```

---

The login window should pop up. Type in the credentials, read the instructions popup, and start the exam.

---

## Notes

- All the questions and answers are hardcoded inside ExamFrame.java
- The timer is set to 2 minutes, you can change it by editing the secondsLeft variable in ExamFrame
- If you dont answer a question, it counts as wrong
- Exam instructions appear as a popup before the timer starts, and are also written as comments in ExamFrame.java
- There are a couple of TODO comments left in the code as placeholders for future improvements

---