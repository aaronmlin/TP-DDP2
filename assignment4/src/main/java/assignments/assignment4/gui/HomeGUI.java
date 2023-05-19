package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;
import assignments.assignment3.nota.NotaManager;
import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.Calendar;

import static assignments.assignment3.nota.NotaManager.toNextDay;

public class HomeGUI extends JPanel {
    // public Calendar cal = Calendar.getInstance();
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;
    // private static HomeGUI currentInstance;

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

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
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(5, 5, 5, 5);

        titleLabel = new JLabel("Selamat datang di CuciCuci System!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleToLogin());
        mainPanel.add(loginButton, gbc);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> handleToRegister());
        mainPanel.add(registerButton, gbc);

        toNextDayButton = new JButton("Next Day");
        toNextDayButton.addActionListener(e -> handleNextDay());
        mainPanel.add(toNextDayButton, gbc);

        dateLabel = new JLabel("Today is " + NotaManager.fmt.format(NotaManager.cal.getTime()), SwingConstants.CENTER);
        add(dateLabel,  BorderLayout.SOUTH);
        
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        NotaManager.toNextDay();
        String tanggalHariIni = NotaManager.fmt.format(NotaManager.cal.getTime());
        dateLabel.setText((String.format("Today is %s", tanggalHariIni)));
        JOptionPane.showMessageDialog(this, "Kamu tidur hari ini...zzz...", "Next Day", JOptionPane.INFORMATION_MESSAGE);

    }


    
}
