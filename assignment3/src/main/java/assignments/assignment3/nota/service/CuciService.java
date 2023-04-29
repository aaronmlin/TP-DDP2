package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    private boolean isDone;
    private long harga;

    @Override
    public String doWork() { //Override method doWork yang akan mengubah status dari service
        this.isDone = true;
        return "Sedang mencuci...";
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
        harga = 0;
        return harga;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
