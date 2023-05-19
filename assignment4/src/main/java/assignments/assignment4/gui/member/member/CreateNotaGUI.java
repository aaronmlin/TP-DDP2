package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.HomeGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    private JPanel mainPanel;
    public static final String KEY = "CREATE_NOTA";
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        // set top 75 biar agak ketengah semua komponenya
        mainPanel.setBorder(BorderFactory.createEmptyBorder(75, 10, 10, 10));

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
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(5, 5, 5, 5);

        paketLabel = new JLabel("Paket Laundry:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(paketLabel, gbc);

        paketComboBox = new JComboBox<>(new String[]{"Express", "Fast", "Reguler"});
        gbc.gridx = 1;
        mainPanel.add(paketComboBox, gbc);

        showPaketButton = new JButton("Show Paket");
        showPaketButton.addActionListener(e -> showPaket());
        gbc.gridx = 2;
        mainPanel.add(showPaketButton, gbc);

        beratLabel = new JLabel("Berat Cucian (Kg):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(beratLabel, gbc);

        beratTextField = new JTextField();
        gbc.gridx = 1;
        mainPanel.add(beratTextField, gbc);

        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000 / kg)");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(setrikaCheckBox, gbc);

        antarCheckBox = new JCheckBox("Tambah Antar Service (2000 / 4 kg pertama, kemudian 500 / kg)");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(antarCheckBox, gbc);

        createNotaButton = new JButton("Buat Nota");
        createNotaButton.addActionListener(e -> createNota());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        mainPanel.add(createNotaButton, gbc);

        backButton = new JButton("Kembali");
        backButton.addActionListener(e -> handleBack());
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(backButton, gbc);
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        try {
            int berat = Integer.parseInt(beratTextField.getText()); //Urutan validasi yang hampir sama dengan TP 1-3
            String paket = String.valueOf(paketComboBox.getSelectedItem());
            if (berat <= 0) {
                JOptionPane.showMessageDialog(this, "Berat cucian harus dalam bentuk bilangan positif!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (berat == 1) {
                    JOptionPane.showMessageDialog(this, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg.",
                            "Info", JOptionPane.INFORMATION_MESSAGE);
                    berat = 2;
                }

                String tanggalHariIni = NotaManager.fmt.format(NotaManager.cal.getTime());
                Nota nota = new Nota(memberSystemGUI.getLoggedInMember(), berat, paket, tanggalHariIni);

                if (setrikaCheckBox.isSelected()) {
                    nota.addService(new SetrikaService());
                }
                if (antarCheckBox.isSelected()) {
                    nota.addService(new AntarService());
                }

                JOptionPane.showMessageDialog(this, "Nota berhasil dibuat!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                memberSystemGUI.getLoggedInMember().addNota(nota);
                NotaManager.addNota(nota);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Berat Cucian harus berisi angka!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        paketComboBox.setSelectedIndex(0); //Mengembalikan semua field di panel ke default state
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);

        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(MemberSystemGUI.KEY);
    }
}
