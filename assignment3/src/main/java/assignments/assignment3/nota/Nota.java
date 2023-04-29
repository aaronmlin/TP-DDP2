package assignments.assignment3.nota;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.MemberSystem;
import java.util.Arrays;

public class Nota {
    private Member member;
    private String paket;
    private LaundryService[] services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private  int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone;
    static public int totalNota;
    private int idNota;

    public Nota(Member member, String paket, int berat, String tanggalMasuk) { //Constructor class nota beserta semua atributnya
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.sisaHariPengerjaan = setSisaHari();
        this.idNota = totalNota;
        this.tanggalMasuk = tanggalMasuk;
        this.services = new LaundryService[0]; 
        totalNota++;

    }
    public void addService(LaundryService service){ //Method untuk menambahkan service ke dalam array Services
        LaundryService[] copyofServices = Arrays.copyOf(services, services.length + 1);
        copyofServices[copyofServices.length - 1] = service;
        services = copyofServices;
        }

    public String kerjakan(){ //Method kerjakan untuk melakukan method doWork pada setiap class Service
        for (int i = 0; i < services.length; i++) {
            if(services[i].isDone() == false){
                return services[i].doWork();
            } 
        }
        isDone = true;
        return "Sudah selesai.";
    }
    public void toNextDay() { //Method toNextDay yang menambah 1 hari pada kalender
        if(isDone == false){
            this.nextDaySisa();
        }
    }
    public long calculateHarga(){
        long hargaFinalService = 0;
        for (int i = 0; i < services.length; i++) {
            hargaFinalService += services[i].getHarga(berat);
        }
        if(this.sisaHariPengerjaan < 0){
            for (int i = 0; i < Math.abs(sisaHariPengerjaan); i++) {
                if ((hargaFinalService - 2000) > 0){
                    hargaFinalService -= 2000;
                }
            }
        }
        return hargaFinalService + (berat * MemberSystem.getHargaPaket(paket));
    }

    public String getNotaStatus(){
        if(isDone == true){
            return "Sudah selesai.";
        }
        return "Belum selesai.";
    }
    public void setIsDone(){
        if(getSisaHariPengerjaan() == 0){
            this.isDone = true;
        }
    }

    @Override
    public String toString() {
        String hargaService = "\n--- SERVICE LIST ---\n";

        for (int i = 0; i < services.length; i++) {
            hargaService += "-" + services[i].getServiceName() + " @ Rp. " + services[i].getHarga(berat) + "\n";
        }
        if(this.sisaHariPengerjaan < 0 && this.isDone == false){
            String kompensasi = "Ada kompensasi keterlambatan " + (-sisaHariPengerjaan) + " * 2000 hari" ;
            return String.format("[ID Nota = %d]%n", idNota) + MemberSystem.generateNota(member.getId(), paket,berat,tanggalMasuk) + hargaService + "Harga Akhir: " + calculateHarga() +" " + kompensasi +"\n";
        }
        return String.format("[ID Nota = %d]%n", idNota) + MemberSystem.generateNota(member.getId(), paket,berat,tanggalMasuk) + hargaService + "Harga Akhir: " + calculateHarga() +"\n";

    }
    public int setSisaHari(){ //Method untuk men-setting sisa hari setiap apket
        if(paket.toUpperCase().equals("REGULER")){
            return 3;
        }else if(paket.toUpperCase().equals("FAST")){
            return 2;
        }else if(paket.toUpperCase().equals("EXPRESS")){
            return 1;
        }else{
            return 0;
        }
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }

    public LaundryService[] getServices(){
        return services;
    }
    public int getIdNota(){
        return idNota;
    }
    public Member getMember(){
        return member;
    }
    public void nextDaySisa(){
        this.sisaHariPengerjaan -=1;
    }
    

}
