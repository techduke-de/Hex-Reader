package readhexcontroller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class filecontroller {
    public String int_to_hex(int val){return String.format("%X", val); }

    public List<Integer> readFile(String fileName){

        List<Integer> x = new ArrayList<Integer>();

        try(InputStream stream = new FileInputStream(fileName)){

            int data = stream.read();

            while(data != -1){
                x.add(data);
                data = stream.read();
            }

            return x;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
