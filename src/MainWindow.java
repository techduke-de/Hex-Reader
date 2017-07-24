import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import readhexcontroller.*;

public class MainWindow {
    private JPanel panel1;
    private JButton btn_openfile;
    private JTable table_hexvalues;

    public MainWindow(){

        btn_openfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //openFile Button clicked

                //open JFileChooser
                JFileChooser filechooser = new JFileChooser();
                int retval = filechooser.showOpenDialog(null);
                File file = filechooser.getSelectedFile();

                readFiles(file.getAbsoluteFile().toString());
            }
        });

    }

    private filecontroller filecon = new filecontroller();

    public void readFiles(String fileName){

        //JTable Column Header
        Object columnNames[] = {"001", "002", "003", "004", "005", "006", "007", "008", "UNICODE VALUE"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        //Load file content into List of Integer
        List<Integer> list = filecon.readFile(fileName);

        //contains temporary values of items read from file
        String row[] = {"0", "0", "0", "0", "0", "0", "0", "0", "0"};

        int l = 0; //Column Counter variable

        for(int i = 0; i < list.size(); i++){


            if(l == 7){ //if row ist full

                //convert Integer value read from file into a hex value as a String
                row[l] = filecon.int_to_hex(list.get(i));

                //parsing the hex String to a Unicode value
                String unicode_value = "";
                for(int x = 0; x < (row.length - 1); x++){

                    int p = Integer.parseInt(row[x], 16); //parse hex value to a Integer Number

                    //if there is no unicode character
                    if(p == 0){unicode_value += " ";}

                    unicode_value += String.format("%c", p) + " "; //parse Integer value to a unicode character
                }

                row[l+1] = unicode_value;
                model.addRow(row);
                l = 0; //Column Counter variable set to 0 for creating next row
            }
            else{
                row[l] = filecon.int_to_hex(list.get(i));
                l++;
            }
        }

        table_hexvalues.setModel(model);

        //Table Columns formatting
        TableColumn col = null;
        DefaultTableCellRenderer cellcrender = new DefaultTableCellRenderer();
        cellcrender.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        for(int i = 0; i < table_hexvalues.getColumnCount() - 1; i++){
            table_hexvalues.getColumnModel().getColumn(i).setCellRenderer(cellcrender);
            col = table_hexvalues.getColumnModel().getColumn(i);
            col.setPreferredWidth(30);
            col.setMaxWidth(30);
            col.setMinWidth(30);
        }
    }

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Hex-Reader V 0.1");
        frame.setContentPane(new MainWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(ImageIO.read(new File("res/icon.png")));
        frame.pack();
        frame.setVisible(true);
    }
}
