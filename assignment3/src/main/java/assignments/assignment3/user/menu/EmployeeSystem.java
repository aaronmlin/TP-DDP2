package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.Member;

import static assignments.assignment3.nota.NotaManager.notaList;

public class EmployeeSystem extends SystemCLI {

    /**
     * Membuat object baru EmployeeSystem dan mendaftarkan Employee pada CuciCuci
     */
    public EmployeeSystem() {
        memberList = new Member[]{
                new Employee("Dek Depe", "akuDDP"),
                new Employee("Depram", "musiktualembut"),
                new Employee("Lita Duo", "gitCommitPush"),
                new Employee("Ivan Hoshimachi", "SuamiSahSuisei"),
        };
    }

    /**
     * Memproses pilihan dari employee yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        if(choice == 1){
            cuciTime();
        }else if(choice == 2){
            displayNota();
        }else if(choice == 3){
            logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Employee.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. It's nyuci time");
        System.out.println("2. Display List Nota");
        System.out.println("3. Logout");
    }

    public void cuciTime(){ //Method cuciTime yang akan memanggil method kerjakan di setiap nota yang terdaftar di notaList
        System.out.println("Stand back! " + loginMember.getNama() + " beginning to nyuci!");
        for (int i = 0; i < notaList.length; i++) {
            System.out.println(notaList[i].kerjakan());
        }
    }

    public void displayNota(){ //Method displayNota yang akan memanggil method getNotaStatus di setiap nota
        for (int i = 0; i < notaList.length; i++) {
            System.out.println(notaList[i].getNotaStatus());
        }
    }
}