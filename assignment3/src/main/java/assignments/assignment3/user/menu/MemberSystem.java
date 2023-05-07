package assignments.assignment3.user.menu;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Calendar; 

public class MemberSystem extends SystemCLI {
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();

    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        if(choice == 1){
            addLaundry();
        }else if(choice == 2){
            viewNota();
        }else if (choice == 3){
            logout = true;
            System.out.println("Logging out...");
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        Member[] copyOfMemberList = Arrays.copyOf(memberList, memberList.length + 1);
        copyOfMemberList[copyOfMemberList.length - 1] = member;
        memberList = copyOfMemberList;
    }

    public void addLaundry(){ //Method addLaundry --> jalan kerjanya sama seperti TP 1 dan TP 2
        String paket ="";
            boolean statusPaket = true;
            while(statusPaket){             //Validasi input jenis paket
                System.out.println("Masukkan paket laundry: ");
                showPaket();
                paket = in.nextLine();

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
                    System.out.println("Masukkan berat cucian anda [Kg]: "); 
                    String strBerat = in.nextLine(); //Jalan fungsi sama seperti validasi nomor HP
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
            if(berat < 2){
                System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                berat = 2;
            }

            String tanggalMasuk = fmt.format(cal.getTime());
            Nota nota = new Nota(loginMember, berat, paket, tanggalMasuk);
            NotaManager.addNota(nota); //Menambahkan nota kedalam semua array + class yang sesuai
            loginMember.addNota(nota);

            System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?");
            System.out.println("Hanya tambah 1000 /  kg :0");
            System.out.print("[Ketik x untuk tidak mau]:");

            String choiceSetrika = in.nextLine();
            if(choiceSetrika.equalsIgnoreCase("x") == false){ //Apabila pengguna memilih service setrika --> memasukkannya ke LaundryServices
                SetrikaService setrika = new SetrikaService();
                nota.addService(setrika);
            }
            System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!");
            System.out.println("Cuma 2000 / 4kg, kemudian 500 / kg");
            System.out.print("[Ketik x untuk tidak mau]: ");

            String choiceAntar = in.nextLine();
            if(choiceAntar.equalsIgnoreCase("x") == false){ //Apabila pengguna memilih service antar --> memasukkannya ke LaundryServices
                AntarService antar = new AntarService();
                nota.addService(antar);
            }
            System.out.println("Nota berhasil dibuat!");

            
    }
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }


    public void viewNota(){ //Method untuk memanggil toString yang ada di class Nota. Menampilkan nota yang dimiliki oleh setiap member
        for (Nota nota: this.loginMember.getNotaList()){
           System.out.println(nota);
        }
    }

    public static long getHargaPaket(String paket) { //Method getHargaPaket, getHariPaket, dan generateNota yang diambil dari Solusi TP2 DDP2 2022/2023
        paket = paket.toLowerCase();
        if (paket.equals("express")) return 12000;
        if (paket.equals("fast")) return 10000;
        if (paket.equals("reguler")) return 7000;
        return -1;
    }

    private static int getHariPaket(String paket) {
        paket = paket.toLowerCase();
        if (paket.equals("express")) return 1;
        if (paket.equals("fast")) return 2;
        if (paket.equals("reguler")) return 3;
        return -1;
    }
    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(tanggalTerima.substring(6));
        int month = Integer.parseInt(tanggalTerima.substring(3, 5)) - 1;
        int date = Integer.parseInt(tanggalTerima.substring(0, 2));
        cal.set(year, month, date);

        String nota = "";
        nota += "ID    : " + id + "\n";
        nota += "Paket : " + paket + "\n";
        nota += "Harga :\n";
        nota += String.format("%d kg x %d = %d\n", berat, getHargaPaket(paket), (berat * getHargaPaket(paket)));
        nota += "tanggal terima  : " + tanggalTerima + "\n";
        cal.add(Calendar.DATE, getHariPaket(paket));
        nota += "tanggal telesai : " + formatter.format(cal.getTime());
        return nota;
    }
    

}