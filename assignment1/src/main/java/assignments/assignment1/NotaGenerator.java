package assignments.assignment1;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);
    public static void main(String[] args) {                        //Main Method menjalankan program
        Scanner input = new Scanner(System.in);
        int option = 5;                                             
    
        while (option != 0) {
            try {                                                    //Try-except untuk validasi pilihan program
                printMenu();                
                System.out.println("Pilihan: ");
                option = Integer.parseInt(input.nextLine());

                if (option == 1) {                                     //Option 1 == IDGenerator
                    System.out.println("Masukkan nama Anda: ");
                    String nama = input.nextLine();
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
                            System.out.println("Nomor HP hanya menerima digit.");
                            System.out.println("Masukkan nomor HP: ");
                            nomorHP = input.next();

                        }
                    }
                    String strNomorHP = nomorHP;
                    System.out.println("ID anda adalah: "+ generateId(nama,strNomorHP)); //Memanggil dan mengeprint method generateId
    
                } else if (option == 2) {
                    System.out.println("Masukkan nama Anda: ");
                    String nama = input.nextLine();
                    String nomorHP;
                    System.out.println("Masukkan nomor HP: ");
                    nomorHP = input.nextLine();
                    long intNomorHP = 0;
                    boolean status = false;
                    while (!status) {                       //Fungsi validasi nomor HP yang sama
                        try {
                            intNomorHP = Long.parseLong(nomorHP);
                            status = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Nomor HP hanya menerima digit.");
                            System.out.println("Masukkan nomor HP: ");
                            nomorHP = input.next();

                        }
                    }
                    String strNomorHP = nomorHP;
                    String id = generateId(nama, strNomorHP);

                    String tanggalTerima = ""; //Penetapan variabel-variabel untuk method generateNota
                    System.out.println("Masukkan tanggal terima: ");
                    tanggalTerima = input.nextLine();
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
                    System.out.println("Nota Laundry");
                    System.out.println(generateNota(id, paket, berat, tanggalTerima)); //Memanggil method generateNota
                }
            } catch (NumberFormatException e) {
                System.out.println("Perintah tidak diketahui, silahkan periksa kembali. ");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
        input.close(); //Penyelesaian method main
    }
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
    public static String generateId(String nama, String strNomorHP){ //Method generate ID
    
        String idNama =" ";                             //Penetapan variabel Awal
        String caps = nama.toUpperCase();
        String[] words = caps.split("\\s+");         
        idNama = words[0];
        String idHP = strNomorHP;
        String idAwal = idNama +"-"+idHP;

        String checkSumValue = checkSum(idAwal); //Pemanggilan method checkSum untuk menghitung checkSum
        String id = idNama +"-"+ idHP +"-"+ checkSumValue; //Return id

        return id;
    }
    public static String checkSum(String idAwal){ //Method checkSum
        int counter = 0;
        for (char c : idAwal.toCharArray()) { //Mengecek setiap character di ID yang telah dibuat
            if (Character.isLetter(c)) { // Apabila huruf, maka dicari value ASCII dan dikurangi dengan 65 untuk mendapatkan urutan di alfabet
                counter += Character.toUpperCase(c) - 65 + 1; 
            } else if (Character.isDigit(c)) { // Apabila digit, maka hanya diambil value dari digit tersebut
                counter += Character.getNumericValue(c);
            } else {
                counter += 7; //Selain angka dan alfabet, maka value dari simbol-simbol tersebut = 7
            }
        
        }
        String counterFinal = Integer.toString(counter); //Formatting value dari checkSum
        String checkSumFinal = "";

        if(counterFinal.length() >= 2){ //Mengambil 2 digit terakhir jika hasil checksum lebih dari 2 digit
            checkSumFinal = counterFinal.substring(counterFinal.length()-2);
        }else{
            checkSumFinal = String.format("%02d", counter); // Jika hanya 1 digit, menambahkan 0 di depan value
        }
        
        return checkSumFinal;
    }

    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        if(berat <2){ //Apabila berat paket < 2kg, maka akan dianggap sebagai 2 kg
            berat = 2;
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        }
        String tanggalSelesai = formatTanggal(tanggalTerima, paket); //Memanggil method formatTanggal untuk mendapat value tanggal selesai

        int faktorHarga = 0;
        int hargaFinal = 0;
        int days = 0;

        if (paket.equalsIgnoreCase("express")){ //If-else untuk menentukan faktor harga dan hari
            days = 1;
            faktorHarga = 12000;
        }else if(paket.equalsIgnoreCase("fast")){
            days = 2;
            faktorHarga = 10000;
        }else if(paket.equalsIgnoreCase("reguler")){
            days = 3;
            faktorHarga = 7000;
        }
        
        hargaFinal = berat * faktorHarga; //Menentukan harga final berdasarkan berat dan faktor harga

        //SAtring format nota laundry
        String finalID = "ID    : " + id;
        String finalPaket = "Paket : " + paket;
        String finalHarga = "Harga :\n" + berat + " kg x " + faktorHarga + " = " + hargaFinal;
        String finalTerima = "Tanggal Terima  : " + tanggalTerima;
        String finalSelesai = "Tanggal Selesai : " + tanggalSelesai;

        String output = finalID + "\n" + finalPaket + "\n" + finalHarga + "\n" + finalTerima + "\n" + finalSelesai;
        return output;
    }
    public static String formatTanggal(String tanggal, String paket){ //Method untuk menentukan tanggal selesai pesanan

        DateTimeFormatter formatTanggalTerima = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Menentukan format dd/mm/yyyy
        LocalDate inputTanggal = LocalDate.parse(tanggal, formatTanggalTerima);

        int days = 0;

        if (paket.equalsIgnoreCase("express")){ //Berdasarkan parameter paket, menentukan tanggal akan ditambah berapa hari
            days = 1;
        }else if(paket.equalsIgnoreCase("fast")){
            days = 2;
        }else if(paket.equalsIgnoreCase("reguler")){
            days = 3;
        }
        LocalDate newDate = inputTanggal.plusDays(days);
        String tanggalFinal = formatTanggalTerima.format(newDate); //Return tanggal selesai

        return tanggalFinal;
        
    }

    }
    
    
