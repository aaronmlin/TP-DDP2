package assignments.assignment1;

import java.util.Arrays;
import java.util.Scanner;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class tryme {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option = 5;
    
        while (option != 0) {
            
            try {
                printMenu();
                System.out.println("Pilihan: ");
                option = Integer.parseInt(input.nextLine());

                if (option == 1) {
                    
                    // String id = generateId();
                    System.out.println("ID anda adalah: "+ generateId());
    
                } else if (option == 2) {
                    String id = generateId();
                    String tanggalTerima = "";
                    System.out.println("Masukkan tanggal terima: ");
                    tanggalTerima = input.nextLine();
                    String paket ="";
                    // int days = 0;
                    // int faktorHarga = 0;
                    

                    boolean statusPaket = true;
                    // String tanggalSelesai ="";

                    while(statusPaket){
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
                        // formatTanggal(tanggalSelesai, paket);

                        // if (paket.toUpperCase().equals("EXPRESS")){
                        //     tanggalSelesai = formatTanggal(tanggalSelesai,paket);
                        // }else if(paket =="fast"){
                        //     tanggalSelesai = formatTanggal(tanggalSelesai, paket = "fast");
                        // }else if(paket == "reguler"){
                        //     tanggalSelesai = formatTanggal(tanggalSelesai, paket = "reguler");
                        // }else if(paket == "?"){
                        //     showPaket();
                        // }else{
                        //     System.out.println("Paket " + paket + " tidak diketahui");
                        //     System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                        //     // System.out.println("Masukkan paket laundry: ");
                        //     continue;
                        // }
                        // statusPaket = false;

                                           
                  }
                
                    int berat = 0;
                    boolean statusBerat = true;
                    while (statusBerat){
                        try{
                            System.out.println("Masukkan berat: ");
                            String strBerat = input.nextLine();
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
                    
                    // String tanggalSelesai = formatTanggal(tanggal, paket);
                    // System.out.println(tanggalSelesai);
                    System.out.println(generateNota(id, paket, berat, tanggalTerima));
                    

                
                }
            } catch (NumberFormatException e) {
                System.out.println("Perintah tidak diketahui, silahkan periksa kembali. ");
            }
        }

        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
        input.close();
    }

    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
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

  
    public static String generateId(){
        System.out.println("Masukkan nama Anda: ");
        String nama = input.nextLine();
        String nomorHP;
        System.out.println("Masukkan nomor HP: ");
        nomorHP = input.nextLine();
        long intNomorHP = 0;
        boolean status = false;
        while (!status) {
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
    
        String idNama =" ";

        String caps = nama.toUpperCase();
        String[] words = caps.split("\\s+"); 
        // System.out.println(Arrays.toString(words));   
        
        idNama = words[0];

        String idHP = strNomorHP;
        String idAwal = idNama +"-"+idHP;



        String checkSumValue = Integer.toString(checkSum(idAwal));
        
   
        String id = idNama +"-"+ idHP +"-"+ checkSumValue;

        return id;
    }
    public static int checkSum(String idAwal){
        int counter = 0;
        for (char c : idAwal.toCharArray()) {
            if (Character.isLetter(c)) {
                counter += Character.toUpperCase(c) - 65 + 1; // subtract the ASCII value of 'A' to get the alphabetical order value
            } else if (Character.isDigit(c)) {
                counter += Character.getNumericValue(c);
            } else {
                counter += 7;
            }
        }return counter;
    }

    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        
        if(berat <2){
            berat = 2;
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        }
        String tanggalSelesai = formatTanggal(tanggalTerima, paket);

        int faktorHarga = 0;
        int hargaFinal = 0;
        int days = 0;

        if (paket.equalsIgnoreCase("express")){
            days = 1;
            faktorHarga = 12000;
        }else if(paket.equalsIgnoreCase("fast")){
            days = 2;
            faktorHarga = 10000;
        }else if(paket.equalsIgnoreCase("reguler")){
            days = 3;
            faktorHarga = 7000;

        }
        
        hargaFinal = berat * faktorHarga;
        String output = "Nota Laundry" + "\nID  :" + id + "\nPaket  :" + paket + "\nHarga  :\n" + (berat+ " kg x " + faktorHarga +" = " + hargaFinal ) + "\nTanggal Terima     :\n " + tanggalTerima + "\nTanggal Selesai    :\n" + tanggalSelesai; 
    
        return output;
    }
    public static String formatTanggal(String tanggal, String paket){

        DateTimeFormatter formatTanggalTerima = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inputTanggal = LocalDate.parse(tanggal, formatTanggalTerima);


        
        int days = 0;

        if (paket.equalsIgnoreCase("express")){
            days = 1;
        }else if(paket.equalsIgnoreCase("fast")){
            days = 2;
        }else if(paket.equalsIgnoreCase("reguler")){
            days = 3;
        }
        
        LocalDate newDate = inputTanggal.plusDays(days);
        String tanggalFinal = formatTanggalTerima.format(newDate);
    

    

        return tanggalFinal;
        
    }

    }
    