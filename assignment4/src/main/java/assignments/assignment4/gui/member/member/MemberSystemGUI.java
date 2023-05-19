package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        JButton[] buttonList = new JButton[2];
        JButton laundryButton = new JButton("Saya ingin laundry");
        JButton detailButton = new JButton("Lihat detail nota saya");
        buttonList[0] = laundryButton;
        buttonList[1] = detailButton;
        return buttonList;
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JTextArea textDetailNota = new JTextArea();
        String detailNota = "";
        if(getLoggedInMember().getNotaList().length == 0){
            detailNota = "Belum pernah laundry di CuciCuci, hiks :'(";
        }else{
            for(Nota nota: getLoggedInMember().getNotaList()){
                detailNota += nota.toString() + "\n";
            }
        }
        textDetailNota.setText(detailNota);
        scrollPane.setViewportView(textDetailNota);
        JOptionPane.showMessageDialog(this, scrollPane, "Detail Nota", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(CreateNotaGUI.KEY);
    }

}
