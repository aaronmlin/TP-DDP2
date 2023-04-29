package assignments.assignment3.nota.service;
import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    private long harga;
    private boolean isDone;
    
    @Override
    public String doWork() { //Override method doWork yang akan mengubah status dari service
        this.isDone = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() { //Mengubah boolean status isDone
        if(isDone == true){
            return true;
        }
        return false;
    }

    @Override
    public long getHarga(int berat) { //Menetapkan harga service sesuai dengan soal
        harga = 500 * berat;
        if(harga < 2000){
            harga = 2000;
        }
        return harga;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
