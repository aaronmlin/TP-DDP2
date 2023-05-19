package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        idLabel = new JLabel("Masukkan ID Anda:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(idLabel, gbc);

        idTextField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(idTextField, gbc);


        passwordLabel = new JLabel("Masukkan password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(loginButton, gbc);

        backButton = new JButton("Kembali");
        backButton.addActionListener(e -> handleBack());
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(backButton, gbc);
        

    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(HomeGUI.KEY);
        idTextField.setText("");
        passwordField.setText("");
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        String id = idTextField.getText();
        String password = String.valueOf(passwordField.getPassword());

        SystemCLI systemCLI = loginManager.getSystem(id);
        if (systemCLI != null){
            if (MainFrame.getInstance().login(id, password)) {
                idTextField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "ID atau password invalid.", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                idTextField.setText("");
                passwordField.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(this, "ID atau password invalid.", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");
            passwordField.setText("");
        }
    }
}
