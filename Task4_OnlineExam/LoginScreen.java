import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * LoginScreen - the first window the user sees.
 * It asks for a username and password and checks them against hardcoded values.
 * After successful login, it jumps straight into the exam.
 */
public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        // basic window setup
        setTitle("Login - Online Exam");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the window on screen
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // username label
        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);
        add(userLabel, gbc);

        // username input field
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        // password label
        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);

        // password input field (hides the typed characters)
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        // login button
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 10, 10);
        add(loginButton, gbc);

        // what happens when you click the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // grab whatever the user typed in
                String user = usernameField.getText().trim();
                String pass = new String(passwordField.getPassword()).trim();

                // check against the hardcoded credentials
                if (user.equals("student") && pass.equals("exam123")) {
                    // login success - go straight to the exam
                    new ExamFrame();
                    dispose(); // close the login window
                } else {
                    // login failed - show an error popup
                    JOptionPane.showMessageDialog(LoginScreen.this,
                            "Invalid username or password. Try again.",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}