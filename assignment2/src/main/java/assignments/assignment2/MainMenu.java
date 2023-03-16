//Tugas Pemrograman 2(Objects and Classes)
//Nama  : Aaron Mario Lin
//NPM   : 2206082341
//Kelas : DDP 2 - C
//Asdos : APH
package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.Calendar;              //Meng-import semua utility yang diperlukan pada TP 2 kali ini
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList; //Struktur data yang digunakan pada tugas ini adalah ArrayList
    private static ArrayList<Member> memberList;
    private static int idNotaCounter = 0; //Variabel untuk menghitung idNota

    public static void main(String[] args) { //Main menu pilihan
        boolean isRunning = true;
        memberList = new ArrayList<Member>(); //Inisiasi arrayList
        notaList = new ArrayList<Nota>();
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleGenerateUser() { //Method generate user ID
        String nama;
        System.out.println("Masukkan nama anda: ");
        nama = input.nextLine();
        String strCheckHP = "";
        boolean statusHP  = false;
        
        System.out.println("Masukkan nomor HP: "); //Validasi nomor HP
        while (statusHP == false) {
            strCheckHP = input.nextLine();
            int counter = 0;
            char[] charString = strCheckHP.toCharArray(); //Meng-iterasi tiap karakter di input nomorHP
            for (int i = 0; i <strCheckHP.length(); i++){
                if ((int)charString[i] < 48 || (int)charString[i] > 57) {
                    System.out.println("Field nomor HP hanya menerima digit!");
                    counter += 1;
                    break;
                }
            }
            if (counter == 0) {
                statusHP = true;
            }
        }
        long nomorHP = Integer.valueOf(strCheckHP);
        String strNomorHP = Long.toString(nomorHP);

        String tempId = generateId(nama, strNomorHP);
        for (int i = 0; i < memberList.size(); i++){ //Validasi member yang telah terdaftar di memberList
            Member member = memberList.get(i);
            if(member.getId().equalsIgnoreCase(tempId)){
                System.out.println("Member dengan nama " + nama + " dan nomor hp   " + strNomorHP+ " sudah ada!");
                return;
                }  
        }
        Member member = new Member(nama, strNomorHP, tempId);//Jika lolos validasi, maka akan diklasifikasikan ke class Member
        memberList.add(member);
        System.out.println("Berhasil membuat member dengan ID: "+ generateId(nama,strNomorHP));
         
    }

    private static void handleGenerateNota() { //Method generate user Nota
        int notaCounter = generateIDNota(); //Method untuk memanggil idNota
        idNotaCounter++;
        String checkId;
            System.out.println("Masukkan ID Member: ");
            checkId = input.nextLine();
            Member member = findMemberID(checkId);// Mencari member yang dicari untuk membuat nota
            if (member == null){
                System.out.printf("Member dengan ID %s tidak ditemukan!", checkId);
                return;
            }
            String paket ="";
            boolean statusPaket = true;
            while(statusPaket){             //Validasi input jenis paket
                System.out.println("Masukkan paket laundry: ");
                paket = input.nextLine();

                if (paket.equalsIgnoreCase("express") || paket.equalsIgnoreCase("fast") ||paket.equalsIgnoreCase("reguler")){
                    statusPaket = false; 
                }else if(paket.equalsIgnoreCase("?")){
                    showPaket();
                }else{
                    System.out.println("Paket " + paket + " tidak diketahui");
                    System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                }
           }
            int berat = 0;
            boolean statusBerat = true;
            while (statusBerat){                //Validasi input berat paket
                try{
                    System.out.println("Masukkan berat: "); 
                    String strBerat = input.nextLine(); //Jalan fungsi sama seperti validasi nomor HP
                    berat = Integer.valueOf(strBerat);
                    if(berat > 0){
                        statusBerat = false;
                    }else{
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    System.out.println("Harap memasukkan berat cucian Anda dalam bentuk positif. ");
                }
            }
                
                String tanggalMasuk = fmt.format(cal.getTime()); //String tanggalMasuk = tanggal hari ini
                member.incrementBonusCounter();
                String tempNota = notaGenerate((checkId), paket, berat, tanggalMasuk , notaCounter);
                Nota nota = new Nota(member, paket, berat, tanggalMasuk, notaCounter); //Mengklasifikasikan nota ke class Nota
                notaList.add(nota); //Memasukkan nota yang telah dibuat ke notaList
               
                System.out.println(tempNota);

        }   
        
    private static void handleListNota() { //Method untuk melihat status nota yang terdaftar dalam notaList
        String statusNota;

        if(notaList.size() == 0){
            System.out.println("Terdaftar 0 nota dalam sistem.");
        }else{
            System.out.printf("Terdaftar %d nota dalam sistem.\n", notaList.size());
            for (int i = 0; i < notaList.size(); i++){
                Nota nota = notaList.get(i);
                if (nota != null){
                    if (nota.getSisaHari() > 0){                    //Jika sisa hari pada nota lebih dari 0, belum dapat diambil
                        statusNota = "Belum dapat diambil :(";
                    }else{
                        statusNota = "Sudah dapat diambil!";
                    }
                    System.out.printf("- [%s] Status      	: "+ statusNota, nota.getIdNota());
                }else{
                    System.out.println("Terdaftar 0 nota dalam sistem. ");
                }
            }
        }
    }

    private static void handleListUser() { //Method untuk melihat status user yang terdaftar dalam memberList
        if (memberList.size() == 0){
            System.out.println("Terdaftar 0 member dalam sistem.");
        }else{
            System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
            for (int i = 0; i < memberList.size(); i++){                //Menuliskan setiap member yang terdaftar di memberList
                Member member = memberList.get(i);
                System.out.printf("- %s   : %s\n", member.getId(), member.getName());
            }
        }
    }

    private static void handleAmbilCucian() { //Method untuk mengambil cucian yang telah bisa diambil

        boolean isRemoved = false;
        String strCheck = "";
        boolean statusAmbil  = false;
        
        System.out.println("Masukkan ID Nota: "); //Validasi idNota sama dengan validasi nomor HP
        while (statusAmbil == false) {
            strCheck = input.nextLine();
            int counter = 0;
            char[] charString = strCheck.toCharArray();
            for (int i = 0; i <strCheck.length(); i++){
                if ((int)charString[i] < 48 || (int)charString[i] > 57) {
                    System.out.println("ID nota berbentuk angka!");
                    counter += 1;
                    break;
                }
            }
            if (counter == 0) {
                statusAmbil = true;
            }
        }
        int checkIDNota = Integer.valueOf(strCheck);
        Nota nota = findNotaID(checkIDNota); //Memanggil method find ID Nota
        if (nota == null){
            System.out.printf("Nota dengan ID %s tidak ditemukan!",checkIDNota);
        }else if(nota.getSisaHari() > 0){  //Mengecek apakah nota yang ingin diambil sudah dapat diambil
            System.out.printf("Nota dengan ID %s gagal diambil!", checkIDNota);
            return;
        }else{
            for (int i = 0; i <notaList.size(); i++){ //Apabila sudah, mengecek apabila nota yang ingin diambil dari notaList tidak null dan cocok dengan input
                if(notaList.get(i) != null && notaList.get(i).getSisaHari() == 0 && notaList.get(i).getIdNota() == checkIDNota){
                    notaList.remove(notaList.get(i));
                    System.out.printf("Nota dengan ID %d berhasil diambil!\n", checkIDNota);
                    isRemoved = true;
                    break;
                }
            }
        }

    }

    private static void handleNextDay() { //Method untuk menambah hari
        System.out.println("Dek Depe tidur hari ini... zzz...");
        cal.add(Calendar.DATE,1); //Menambah satu hari ke cal.getInstance()
        
        for (int i = 0; i < notaList.size(); i++){
            if(notaList.get(i).getSisaHari() > 0){
                notaList.get(i).nextDaySisa();
            }
            notaList.get(i).setIsReady(); //Mengecek apabila nota sudah dapat diambil
            if(notaList.get(i).getIsReady() == true){
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil\n",notaList.get(i).getIdNota());
            }
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
        
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
    public static String notaGenerate(String id, String paket, int berat, String tanggalTerima, int notaCounter){
        if(berat <2){ //Apabila berat paket < 2kg, maka akan dianggap sebagai 2 kg
            berat = 2;
    
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        }
        String tanggalSelesai = formatTanggal(tanggalTerima, paket); //Memanggil method formatTanggal untuk mendapat value tanggal selesai

        int faktorHarga = 0;
        int hargaFinal = 0;

        if (paket.equalsIgnoreCase("express")){ //If-else untuk menentukan faktor harga dan hari
            faktorHarga = 12000;
        }else if(paket.equalsIgnoreCase("fast")){
            faktorHarga = 10000;
        }else if(paket.equalsIgnoreCase("reguler")){
            faktorHarga = 7000;
        }

        int hargaDiskon;
   
        //String format nota laundry
        System.out.println("Berhasil menambahkan nota!");
        System.out.printf("[ID Nota = %d]\n", notaCounter);

        String finalID = "ID    : " + id;
        String finalPaket = "Paket : " + paket;
        Member member = findMemberID(id);
        String finalHarga;
        if (member.getBonusCounter() < 3){
            hargaFinal = berat * faktorHarga;
            finalHarga = "Harga :\n" + berat + " kg x " + faktorHarga + " = " + hargaFinal; 
        }else{
            hargaFinal = berat * faktorHarga;
            hargaDiskon = hargaFinal / 2;
            finalHarga = "Harga :\n" + berat + " kg x " + faktorHarga + " = " + hargaFinal + " = " + hargaDiskon + " (Discount member 50%!!!)";
            member.resetBonusCounter();
        }
    
        String finalTerima = "Tanggal Terima  : " + tanggalTerima;
        String finalSelesai = "Tanggal Selesai : " + tanggalSelesai;
        String status = "Status      	: Belum bisa diambil :(";

        String output = finalID + "\n" + finalPaket + "\n" + finalHarga + "\n" + finalTerima + "\n" + finalSelesai + "\n" + status;
        return output;
    }
    private static int generateIDNota(){
        return idNotaCounter;
    }
  
    private static Member findMemberID(String id){ //Method untuk mencari member berdasarkan id yang diberikan
        for (int i = 0; i < memberList.size(); i++){
            if (memberList.get(i).getId().equals(id)){
                return memberList.get(i);
            }
        }
        return null;
    }
    private static Nota findNotaID(int id){ //Method untuk mencari nota berdasarkan id yang diberikan
        for (int i = 0; i < notaList.size(); i++){
            if (notaList.get(i).getIdNota() == id){
                return notaList.get(i);
            }
        } 
        return null;
    }
    

}

