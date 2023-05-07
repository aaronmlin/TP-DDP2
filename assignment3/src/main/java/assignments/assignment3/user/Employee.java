package assignments.assignment3.user;

public class Employee extends Member {
    public static int employeeCount = 0;
    public Employee(String nama, String password) {
        super(nama, generateId(nama), password);
    }

    /**
     * Membuat ID employee dari nama employee dengan format
     * NAMA_DEPAN-[jumlah employee, mulai dari 0]
     * Contoh: Dek Depe adalah employee pertama yang dibuat, sehingga IDnya adalah DEK-0
     *
     * @param nama -> Nama lengkap dari employee
     */
    private static String generateId(String nama) {
        String[] split = nama.split(" ");
        String idNama = split[0].toUpperCase();
        String employeeID = idNama + "-"+ employeeCount;
        employeeCount++;
        return employeeID;
    
    }


}
