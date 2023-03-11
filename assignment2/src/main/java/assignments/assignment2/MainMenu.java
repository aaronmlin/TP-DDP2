package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;


import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static Nota[] notaList;
    private static Member[] memberList;
    private static int numMember = 0;
    private static int idNotaCounter = 0;

    public static void main(String[] args) {
        boolean isRunning = true;
        memberList = new Member[0];
        notaList = new Nota[0];
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

    private static void handleGenerateUser() {
        String nama;
        System.out.println("Masukkan nama anda: ");
        nama = input.nextLine();
        String nomorHP;
        System.out.println("Masukkan nomor HP: ");
        nomorHP = input.nextLine();
        long intNomorHP = 0;
        boolean status = false;
        while (!status) {                               //Validasi nomorHP
            try {
                intNomorHP = Long.parseLong(nomorHP); //Meminta input nomorHP sebagai string, mengubahnya menjadi long
                status = true;
            } catch (NumberFormatException e) { //Apabila string input tidak bisa diubah menjadi long, maka input bukan digit
                System.out.println("Field nomor HP hanya menerima digit.");
                System.out.println("Masukkan nomor handphone Anda: ");
                nomorHP = input.next();

            }
        }
        String strNomorHP = nomorHP;
        String tempId = generateId(nama, strNomorHP);
        boolean statusID = true;
        // while(statusID){
            for (int i = 0; i < memberList.length; i++){
                Member member = memberList[i];
                if(member.getId().equalsIgnoreCase(tempId)){
                    System.out.println("Member dengan nama " + nama + " dan nomor hp   " + strNomorHP+ " sudah ada!");
                    return;
                // }else{
                //     statusID = false;
                }
            // }
        }
        Member member = new Member(nama, strNomorHP, tempId);
        memberList = addMembers(member);
        System.out.println("Berhasil membuat member dengan ID: "+ generateId(nama,strNomorHP));
         
    }
    public static Member[] addMembers(Member newMember){
        Member[] copyOfMemberList = Arrays.copyOf(memberList, memberList.length + 1);
        copyOfMemberList[copyOfMemberList.length-1] = newMember;
        return Arrays.copyOf(copyOfMemberList, copyOfMemberList.length);
    }
    public static Nota[] addNotas(Nota newNota){
        Nota[] copyOfNotaList = Arrays.copyOf(notaList, notaList.length +1);
        copyOfNotaList[copyOfNotaList.length-1] = newNota;
        return Arrays.copyOf(copyOfNotaList, copyOfNotaList.length);
    }

    private static void handleGenerateNota() {
        // TODO: handle ambil cucian

        int notaCounter = generateIDNota();
        idNotaCounter++;
        String checkId;
        // while (statusNota){
            System.out.println("Masukkan ID Member: ");
            checkId = input.nextLine();
            Member member = findMemberID(checkId);
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
                
                String tanggalMasuk = fmt.format(cal.getTime());
                String tempNota = notaGenerate((checkId), paket, berat, tanggalMasuk , notaCounter);
                Nota nota = new Nota(member, paket, berat, tanggalMasuk, notaCounter);
                notaList = addNotas(nota);
                System.out.println(tempNota);
                

            
            
            // for (int i = 0; i < memberList.length; i++){
            //     if (memberList[i].getId().equals(checkId)){
            //         memberList[i] = member;
            //         break;
            //     }
            // }
            // if (member == null){
            //     System.out.printf("Member dengan ID %s tidak ditemukan!", checkId);
            //     return;
            // }
               
            
            
        }   
        
    

    private static void handleListNota() {

        String statusNota;
        
        
        if(notaList.length == 0){
            System.out.println("Terdaftar 0 nota dalam sistem.");
        }else{
            System.out.printf("Terdaftar %d nota dalam sistem.\n", notaList.length);
            for (int i = 0; i < notaList.length; i++){
                
                Nota nota = notaList[i];
                if (nota.getSisaHari() > 0){
                    statusNota = "Belum dapat diambil :(";
                }else{
                    statusNota = "Sudah dapat diambil!";
                }

                System.out.printf("- [%s] Status      	: "+ statusNota, nota.getIdNota());
        }
        }
    }

    private static void handleListUser() {
        if (memberList.length == 0){
            System.out.println("Terdaftar 0 member dalam sistem.");
        }else{
            System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.length);
            for (int i = 0; i < memberList.length; i++){
                Member member = memberList[i];
                System.out.printf("- %s   : %s\n", member.getId(), member.getName());
            }
        }
    }
    private static Member findMemberID(String id){
        for (int i = 0; i < memberList.length; i++){
            if (memberList[i].getId().equals(id)){
                return memberList[i];
            }
        }
        return null;
    }
    private static Nota findNotaID(int id){
        for (int i = 0; i < notaList.length; i++){
            if (notaList[i].getIdNota() == id){
                return notaList[i];
            }
        } 
        return null;
    }

    private static void handleAmbilCucian() {
        int checkIDNota;
        System.out.println("Masukkan ID Member: ");
        checkIDNota = input.nextInt();
        Nota nota = findNotaID(checkIDNota);
        if (nota == null){
            System.out.printf("Nota dengan ID %s gagal diambil!", checkIDNota);
            return;
        }

    }

    private static void handleNextDay() {
        System.out.println("Dek Depe tidur hari ini... zzz...");
        cal.add(Calendar.DATE,1);
        // nota.nextDaySisa();
        for (int i = 0; i < notaList.length; i++){
            if(notaList[i].getSisaHari() > 0){
                notaList[i].nextDaySisa();
            }
            notaList[i].setIsReady()
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
        // int days = 0;

        if (paket.equalsIgnoreCase("express")){ //If-else untuk menentukan faktor harga dan hari
            // days = 1;
            faktorHarga = 12000;
        }else if(paket.equalsIgnoreCase("fast")){
            // days = 2;
            faktorHarga = 10000;
        }else if(paket.equalsIgnoreCase("reguler")){
            // days = 3;
            faktorHarga = 7000;
        }
        
        hargaFinal = berat * faktorHarga; //Menentukan harga final berdasarkan berat dan faktor harga

        //String format nota laundry
        System.out.println("Berhasil menambahkan nota!");
        System.out.printf("[ID Nota = %d]\n", notaCounter);

        String finalID = "ID    : " + id;
        String finalPaket = "Paket : " + paket;
        String finalHarga = "Harga :\n" + berat + " kg x " + faktorHarga + " = " + hargaFinal;
        String finalTerima = "Tanggal Terima  : " + tanggalTerima;
        String finalSelesai = "Tanggal Selesai : " + tanggalSelesai;
        String status = "Status      	: Belum bisa diambil :(";

        String output = finalID + "\n" + finalPaket + "\n" + finalHarga + "\n" + finalTerima + "\n" + finalSelesai + "\n" + status;
        return output;
    }
    private static int generateIDNota(){
        return idNotaCounter;
    }
    public static String tanggalStatus(String tanggal){
        String status = "";
        return status;
    }
    
    // public static String formatTanggalKeluar(String tanggal, String paket){
    //     int days = 0;

    //     if (paket.equalsIgnoreCase("express")){ //Berdasarkan parameter paket, menentukan tanggal akan ditambah berapa hari
    //         days = 1;
    //     }else if(paket.equalsIgnoreCase("fast")){
    //         days = 2;
    //     }else if(paket.equalsIgnoreCase("reguler")){
    //         days = 3;
    //     }
    //     String tanggalFinal = 
    // }
    
        
    
    // public static void nextDay(Calendar cal){
    //     String tanggalBaru = cal.add(Calendar.DATE,1);
    // }
    

}

