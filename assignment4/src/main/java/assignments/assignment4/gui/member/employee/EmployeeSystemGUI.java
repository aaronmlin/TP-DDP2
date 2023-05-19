package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }


    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        return new JButton[]{
                new JButton("It's Nyuci Time"),
                new JButton("Display List Nota")
        };
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
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        String notaStatus = "";
        Nota[] notalist = NotaManager.notaList;
        if(notalist.length == 0){
            notaStatus = "Belum ada nota";
        }else{
            for(Nota nota: notalist){
                notaStatus += nota.getNotaStatus() + "\n";
            }
        }
        JOptionPane.showMessageDialog(this, notaStatus, "List Nota", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        String doWork = "";
        Nota[] notaList = NotaManager.notaList; //Mengambil array dari TP2
        JOptionPane.showMessageDialog(this, "Stand back! " + loggedInMember.getNama() + " beginning to nyuci!", "Nyuci Time", JOptionPane.INFORMATION_MESSAGE);
        if (notaList.length == 0){
            doWork = "Nothing to cuci here";
        }else {
            for(Nota nota: notaList){
                doWork += nota.kerjakan() + "\n"; //Method kerjakan dari TP3
            }
        }
        JOptionPane.showMessageDialog(this, doWork, "Nyuci Results", JOptionPane.INFORMATION_MESSAGE);
    }
}
