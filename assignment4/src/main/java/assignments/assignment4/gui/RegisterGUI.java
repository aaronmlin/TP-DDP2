package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.spec.NamedParameterSpec;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
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

        nameLabel = new JLabel("Masukkan nama Anda:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(nameLabel, gbc);

        nameTextField = new JTextField();
        nameTextField.setSize(new Dimension(300, 400));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(nameTextField, gbc);

        phoneLabel = new JLabel("Masukkan nomor handphone Anda:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(phoneLabel, gbc);

        phoneTextField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(phoneTextField, gbc);

        passwordLabel = new JLabel("Masukkan password:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(passwordField, gbc);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> handleRegister());
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(registerButton, gbc);

        backButton = new JButton("Kembali");
        backButton.addActionListener(e -> handleBack());
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(backButton, gbc);


    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(HomeGUI.KEY);
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
    
        mainFrame.navigateTo(HomeGUI.KEY);
        
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        long noHP = 0;
        String nama = nameTextField.getText();
        String strNomorHP = phoneTextField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (nama.isEmpty() || strNomorHP.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Semua field diatas harus diisi!", "Empty Field", JOptionPane.ERROR_MESSAGE);
        } else {
            try{
                noHP = Long.parseLong(strNomorHP);
                String nomorHP = String.valueOf(noHP);
                String id = LoginManager.generateId(nama, nomorHP);

                Member registeredMember = loginManager.register(nama, nomorHP, password);
                if(registeredMember == null){
                    JOptionPane.showMessageDialog(this, "User dengan nama " + nama + " dan nomor HP " +  nomorHP + " sudah ada!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                    handleBack();
                } else {
                    JOptionPane.showMessageDialog(this, "Berhasil membuat user dengan ID " + id, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                    handleBack();
                }
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Nomor HP harus berupa angka!", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                phoneTextField.setText("");
            }
        }
    }


    
    // private void recreatePanel(){
    //     mainPanel.removeAll();
    //     initGUI();
    //     mainPanel.revalidate();
    //     mainPanel.repaint();
    // }
}
