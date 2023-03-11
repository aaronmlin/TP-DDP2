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
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan = getSisaHari();
        this.idNota = idNota;
        

    }
    public String getPaket(){
        return paket;
    }
    public int getBerat(){
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
    public boolean setIsReady(){
        isReady = true;
        
    }
    // public String getStatus(){
    //     if (getSisaHari() = 0){
    //         return "Sudah dapat diambil!";
    //     }else{
    //         return "Belum dapat diambil :(";
    //     }
    // }
    public int setSisaHari(){
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
    public void nextDaySisa(){
        this.sisaHariPengerjaan -=1;
    }

    
}
