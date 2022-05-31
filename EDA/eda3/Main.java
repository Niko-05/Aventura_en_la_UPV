import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class Main {
    public static void main(String[] args) {
        try {
            FileInputStream fileStream = new FileInputStream("PracticasParcial1.LabTests");
            ObjectInputStream objStream = new ObjectInputStream(fileStream);

            int cont = 30;
            while ( cont >= 0) {
                System.out.println(objStream.readObject());
            }
            objStream.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }
}

