import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * ResultFrame - shows the student their score after the exam.
 * It displays the overall score and a breakdown of every question
 * so the student can see what they got right and wrong.
 */
public class ResultFrame extends JFrame {

    public ResultFrame(int score, int total, String[] questions,
                       String[][] options, int[] correctAnswers, int[] userAnswers) {
        setTitle("Exam Result");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---- top panel with the overall score ----
        JPanel topPanel = new JPanel();
        JLabel scoreLabel = new JLabel("You scored " + score + " out of " + total);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        topPanel.add(scoreLabel);
        add(topPanel, BorderLayout.NORTH);

        // ---- center area with detailed breakdown ----
        JTextArea detailArea = new JTextArea();
        detailArea.setEditable(false); // this is just for display
        detailArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        // build a big string showing each question's result
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < total; i++) {
            sb.append("Q");
            sb.append(i + 1);
            sb.append(": ");
            sb.append(questions[i]);
            sb.append("\n");

            // figure out what the user answered
            String userAnsStr;
            if (userAnswers[i] == -1) {
                userAnsStr = "No answer";
            } else {
                userAnsStr = options[i][userAnswers[i]];
            }
            sb.append("   Your answer: ");
            sb.append(userAnsStr);
            sb.append("\n");

            // show the correct answer
            sb.append("   Correct answer: ");
            sb.append(options[i][correctAnswers[i]]);
            sb.append("\n");

            // mark it right or wrong
            if (userAnswers[i] == correctAnswers[i]) {
                sb.append("   -> Correct\n\n");
            } else {
                sb.append("   -> Wrong\n\n");
            }
        }
        detailArea.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(detailArea);
        add(scrollPane, BorderLayout.CENTER);

        // ---- bottom panel with the close button ----
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // close the whole program
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}