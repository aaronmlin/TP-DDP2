package assignments.assignment2;
import assignments.assignment1.NotaGenerator;

public class Nota {
    Member member;
    String paket;
    String tanggalMasuk;
    int berat;
    int idNota;
    int sisaHariPengerjaan;
    boolean isReady;
    public Nota(Member member, String paket, int berat, String tanggalMasuk, int idNota) {
        this.member = member; //Constructor untuk class Nota
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan = setSisaHari(); //sisa Hari dialokasikan ke method setSisaHari
        this.idNota = idNota;
    }
    public String getPaket(){ //Method getter paket
        return paket;
    }
    public int getBerat(){ //Method getter berat
        return berat;
    }
    public String tanggalMasuk(){ 
        return tanggalMasuk;
    }
    public Member getMember(){
        return member;
    }
    public int getSisaHari(){
        return sisaHariPengerjaan;
    }
    public int getIdNota(){
        return idNota;
    }
    public void setIsReady(){
        if(getSisaHari() == 0){
            this.isReady = true;
        }
    }
    public boolean getIsReady(){
        return isReady;
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
    public void nextDaySisa(){ //Method mengurangi sisa hari setiap next day
        this.sisaHariPengerjaan -=1;
    }

}
