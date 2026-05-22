import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * ExamFrame - this is the main exam window.
 *
 * Exam instructions:
 *   1. There are 5 multiple-choice questions.
 *   2. Each question has four options (A, B, C, D).
 *   3. Pick one answer per question.
 *   4. Use Previous and Next to move around.
 *   5. A 2-minute timer is running. When it hits zero, the exam submits itself.
 *   6. You can also submit manually on the last question.
 *
 * It shows one question at a time using CardLayout so the student
 * can flip through questions. A countdown timer runs at the top,
 * and the exam auto-submits when time runs out.
 */
public class ExamFrame extends JFrame {
    private JPanel cardPanel;               // holds all the question cards
    private CardLayout cardLayout;          // lets us switch between cards
    private JButton prevButton;             // go to previous question
    private JButton nextButton;             // go to next question or submit
    private JLabel timerLabel;              // shows the countdown clock
    private Timer examTimer;                // swing timer that ticks every second
    private int secondsLeft = 120;          // 2 minutes total for the exam

    // exam data - all hardcoded, no database needed
    private String[] questions = {
        "Which of the following is a Java keyword?",
        "What is the default value of a boolean variable in Java?",
        "Which method is called when an object is created?",
        "Which of the following is NOT a primitive data type in Java?",
        "What is the superclass of all Java classes?"
    };

    private String[][] options = {
        {"A) new", "B) import", "C) class", "D) void"},
        {"A) true", "B) false", "C) 0", "D) null"},
        {"A) main()", "B) finalize()", "C) constructor", "D) start()"},
        {"A) int", "B) double", "C) String", "D) char"},
        {"A) Class", "B) Object", "C) Main", "D) System"}
    };

    // index of the correct option for each question (0 = A, 1 = B, etc.)
    private int[] correctAnswers = {2, 1, 2, 2, 1};

    // stores what the user picked for each question, -1 means no answer yet
    private int[] userAnswers = {-1, -1, -1, -1, -1};

    private int currentCard = 0;            // which question we are currently on

    // button groups so only one radio button can be selected per question
    private ButtonGroup[] buttonGroups = new ButtonGroup[5];
    private JRadioButton[][] optionButtons = new JRadioButton[5][4];

    public ExamFrame() {
        // set up the main window for the exam
        setTitle("Online Exam");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---- top panel with the timer ----
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timerLabel = new JLabel(formatTime(secondsLeft));
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        timerLabel.setForeground(Color.RED); // red so it grabs attention
        topPanel.add(new JLabel("Time Left: "));
        topPanel.add(timerLabel);
        add(topPanel, BorderLayout.NORTH);

        // ---- card panel that holds all questions ----
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // create a card for each question and add it to the panel
        for (int i = 0; i < 5; i++) {
            cardPanel.add(createQuestionCard(i), "card" + i);
        }
        add(cardPanel, BorderLayout.CENTER);

        // ---- bottom navigation buttons ----
        JPanel navPanel = new JPanel();
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToPrevious();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToNext();
            }
        });

        navPanel.add(prevButton);
        navPanel.add(nextButton);
        add(navPanel, BorderLayout.SOUTH);

        updateButtons();  // set initial button states
        setVisible(true);

        // show a quick popup with the instructions before the timer starts
        JOptionPane.showMessageDialog(this,
            "Exam Instructions:\n\n" +
            "1. 5 multiple-choice questions, one at a time.\n" +
            "2. Select an answer for each question.\n" +
            "3. Use Previous and Next to navigate.\n" +
            "4. You have 2 minutes. Timer starts when you click OK.\n" +
            "5. Exam auto-submits when time runs out.\n" +
            "6. Click Submit on the last question to finish early.\n",
            "Instructions",
            JOptionPane.INFORMATION_MESSAGE);

        startTimer();     // kick off the countdown after they read the rules
    }

    /*
     * Builds one question card with the question text and four radio buttons.
     * Each card gets its own ButtonGroup so the options dont interfere.
     */
    private JPanel createQuestionCard(int qIndex) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // the question label
        JLabel questionLabel = new JLabel("Q" + (qIndex + 1) + ". " + questions[qIndex]);
        questionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(questionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // some spacing

        // the four option radio buttons
        buttonGroups[qIndex] = new ButtonGroup();
        for (int j = 0; j < 4; j++) {
            optionButtons[qIndex][j] = new JRadioButton(options[qIndex][j]);
            optionButtons[qIndex][j].setFont(new Font("SansSerif", Font.PLAIN, 14));
            buttonGroups[qIndex].add(optionButtons[qIndex][j]);
            panel.add(optionButtons[qIndex][j]);
        }

        // TODO: add some spacing between options later

        return panel;
    }

    // go back one question
    private void goToPrevious() {
        saveCurrentAnswer(); // remember what the user picked
        if (currentCard > 0) {
            currentCard--;
            cardLayout.show(cardPanel, "card" + currentCard);
            updateButtons();
        }
    }

    // go forward one question, or submit if we are on the last one
    private void goToNext() {
        saveCurrentAnswer();
        if (currentCard == 4) {
            // this is the last question, so treat it as a submit
            submitExam();
        } else if (currentCard < 4) {
            currentCard++;
            cardLayout.show(cardPanel, "card" + currentCard);
            updateButtons();
        }
    }

    // check which radio button is selected and store it in the userAnswers array
    private void saveCurrentAnswer() {
        for (int i = 0; i < 4; i++) {
            if (optionButtons[currentCard][i].isSelected()) {
                userAnswers[currentCard] = i;
                return;
            }
        }
        // nothing selected, mark as unanswered
        userAnswers[currentCard] = -1;
    }

    // enable or disable the Previous button and change Next to Submit on the last card
    private void updateButtons() {
        prevButton.setEnabled(currentCard != 0);
        if (currentCard == 4) {
            nextButton.setText("Submit");
        } else {
            nextButton.setText("Next");
        }
    }

    // start the exam countdown timer, ticks once per second
    private void startTimer() {
        examTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                secondsLeft--;
                timerLabel.setText(formatTime(secondsLeft));

                if (secondsLeft <= 0) {
                    // time is up, force submit
                    examTimer.stop();
                    saveCurrentAnswer();
                    submitExam();
                }
            }
        });
        examTimer.start();
    }

    // turns a number of seconds into a mm:ss string for the timer label
    private String formatTime(int totalSecs) {
        int mins = totalSecs / 60;
        int secs = totalSecs % 60;
        return String.format("%02d:%02d", mins, secs);
    }

    // calculate the score and open the result window
    private void submitExam() {
        // stop the timer if it's still running
        if (examTimer != null && examTimer.isRunning()) {
            examTimer.stop();
        }

        // count how many the user got right
        int score = 0;
        for (int i = 0; i < 5; i++) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }

        // open the result screen with all the details
        new ResultFrame(score, 5, questions, options, correctAnswers, userAnswers);
        dispose(); // close the exam window
    }
}