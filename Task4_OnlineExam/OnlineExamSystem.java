import javax.swing.SwingUtilities;

public class OnlineExamSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginScreen();
            }
        });
    }
}