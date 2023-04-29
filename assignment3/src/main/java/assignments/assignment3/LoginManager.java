package assignments.assignment3;

import assignments.assignment1.NotaGenerator;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;

public class LoginManager {
    private final EmployeeSystem employeeSystem;
    private final MemberSystem memberSystem;

    public LoginManager(EmployeeSystem employeeSystem, MemberSystem memberSystem) {
        this.employeeSystem = employeeSystem;
        this.memberSystem = memberSystem;
    }

    /**
     * Method mapping dari ke SystemCLI yang sesuai.
     *
     * @param id -> ID dari user yang akan menggunakan SystemCLI
     * @return SystemCLI object yang sesuai dengan ID, null if  ID tidak ditemukan.
     */
    public SystemCLI getSystem(String id){
        if(memberSystem.isMemberExist(id)){
            return memberSystem;
            
        }
        if(employeeSystem.isMemberExist(id)){
            return employeeSystem;
        }
        return null;
    }

    /**
     * Mendaftarkan member baru dengan informasi yang diberikan.
     *
     * @param nama -> Nama member.
     * @param noHp -> Nomor handphone member.
     * @param password -> Password akun member.
     * @return Member object yang berhasil mendaftar, return null jika gagal mendaftar.
     */
    public Member register(String nama, String noHp, String password) { //Method register untuk mendaftarkan member baru
        String tempId = generateId(nama, noHp);
        if (getSystem(tempId) == null){
            Member member = new Member(nama, tempId, password);
            memberSystem.addMember(member);
            return member;
        }
        return null;
  
    
    }
    public static String generateId(String nama, String nomorHP){ //Method generateID yang diambil dari solusi TP 1 DDP2 2022/23
        String id = "";
        id += (nama.split(" ")[0] + "-").toUpperCase();
        id += nomorHP;

        int checksum = 0;
        for (char c : id.toCharArray()) {
            if (Character.isDigit(c))
                checksum += c - '0';
            else if (Character.isLetter(c))
                checksum += (c - 'A') + 1;
            else
                checksum += 7;
        }
        id += String.format("-%02d", checksum % 100);
        return id;
    }

}