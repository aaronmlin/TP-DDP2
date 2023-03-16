package assignments.assignment2;
import assignments.assignment1.NotaGenerator;

public class Member {
    String nama; //Constructor untuk class Member
    String noHp;
    String id;
    int bonusCounter;
    public Member(String nama, String noHp, String id) {
        this.nama = nama;
        this.noHp = noHp;
        this.id = id;
        this.bonusCounter = 0;
    }
    public String getName(){ //Method getter nama
        return nama;
    }
    public String getNoHP(){ //Method getter no HP
        return noHp;
    }
    public String getId(){ //Method getter user ID
        return id;
    }
    public int getBonusCounter() { //Method getter bonusCounter
        return bonusCounter;
    }
    public void incrementBonusCounter(){ //Menambah bonus counter setiap hari
        bonusCounter++;
    }
    public void resetBonusCounter(){//Method untuk reset bonus counter
        bonusCounter = 0;
    }
    
}
