import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Imenik_GUI {
    static String imena = "Ivan,Daniela";
    static String prezimena = "Eret,Nagy";
    static String adrese = "Slavonska 2,Trg slobode 1";
    static String gradovi = "Krapina,Osijek";
    static String datumi = "1984,1995";
    static String printer;//ovaj string pomaže pri stvaranju outputa, odnosno ispisa u textpanelu

    public Imenik_GUI() {
        but_akcija.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unos = text_input.getText();
                unos(unos);
                pane_output.setText(printer);
                text_input.setText("");
            }
        });
        but_brisanje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unos = text_input.getText();
                brisanje(Integer.parseInt(unos));
                ispis();
                pane_output.setText(printer);
                text_input.setText("");
            }
        });
        but_csv_load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog dialog = new FileDialog((Frame)null, "Odaberite csv datotetu");
                dialog.setMode(FileDialog.LOAD);
                dialog.setVisible(true);
                String file = dialog.getFile();
                System.out.println(file + " učitana");
                imena="";prezimena="";adrese="";gradovi="";datumi="";
                try {
                    Scanner sc = new Scanner(new File(dialog.getDirectory()+dialog.getFile()));
                    sc.useDelimiter(", ");
                    String tako = "";
                    while (sc.hasNextLine())
                    {
                        String line = sc.nextLine();
                        String[] fields = line.split(",");
                        if (fields.length >= 4) // At least one address specified.
                        {
                            for (String field: fields)
                                tako+= Arrays.toString(fields);
                            imena+=fields[0]+",";prezimena+=fields[1]+",";adrese+=fields[2]+",";gradovi+=fields[3]+",";datumi+=fields[4]+",";
                        }
                        else
                        {
                            System.err.println("Invalid record: " + line);
                        }
                    }
                }
                catch (Exception b) {
                    b.getStackTrace();
                }
                brisanje(1);
                JOptionPane.showMessageDialog(null, "Učitan file: "+file);
                ispis();
                pane_output.setText(printer);

            }
        });
        but_ispis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ispis();
                pane_output.setText(printer);
            }
        });
        but_csv_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update_csv();
            }
        });
    }
    //metoda koja pretvara memoriju u čitljiv oblik, liniju po liniju
    static void ispis() {
        String split_ime[] = imena.split(",", 0);
        String split_pre[] = prezimena.split(",", 0);
        String split_adr[] = adrese.split(",", 0);
        String split_gra[] = gradovi.split(",", 0);
        String split_dat[] = datumi.split(",", 0);
        int l = split_ime.length;
        printer="";
        for (int i = 0; i < l; i++) {
            String print = "%s. %s %s, %s, %s, %s".formatted(i + 1, split_ime[i], split_pre[i], split_adr[i], split_gra[i], split_dat[i]);
            if (print.contains("  , ,")) {
                System.out.println("Nema niti jedne stavke!");
            } else {
                System.out.println(print);
                printer+=print+"\n";
            }
        }
    }
    static void unos(String unos) {
        String split_unos[] = unos.split(", ", 0);
        imena+=","+split_unos[0];prezimena+=","+split_unos[1];adrese+=","+split_unos[2];gradovi+=","+split_unos[3];datumi+=","+split_unos[4];
        System.out.println("Spremljeno u output_imenik.csv");
        if (imena.charAt(0) == ',') {
            brisanje(1);
        }
        ispis();
    }
    static void brisanje(int bris){
        String split_ime[] = imena.split(",", 0);
        String split_pre[] = prezimena.split(",", 0);
        String split_adr[] = adrese.split(",", 0);
        String split_gra[] = gradovi.split(",", 0);
        String split_dat[] = datumi.split(",", 0);
        split_ime[bris-1]="789";split_pre[bris-1]="789";split_adr[bris-1]="789";split_gra[bris-1]="789";split_dat[bris-1]="789";
        imena="";prezimena="";adrese="";gradovi="";datumi="";
        int i=0;
        for (String elem : split_ime) {
            if (elem!="789") {imena+=elem+",";};
            i++;
        };imena+="**";i=0;
        for (String elem : split_pre) {
            if (elem!="789") {prezimena+=elem+",";};
            i++;
        };prezimena+="**";i=0;
        for (String elem : split_adr) {
            if (elem!="789") {adrese+=elem+",";};
            i++;
        };adrese+="**";i=0;
        for (String elem : split_gra) {
            if (elem!="789") {gradovi+=elem+",";};
            i++;
        };gradovi+="**";i=0;
        for (String elem : split_dat) {
            if (elem!="789") {datumi+=elem+",";};
            i++;
        };datumi+="**";
        sredi();
        if (imena.contains("**")) {
            imena="";prezimena="";adrese="";gradovi="";datumi="";
        }
    }
    //metoda koja brise nepotrebne repove (zadnji zarez, a ** znakovi još i pomazu pri određivanju je li prazan imenik)
    static void sredi(){
        imena=imena.replace(",**","");
        prezimena=prezimena.replace(",**","");
        adrese=adrese.replace(",**","");
        gradovi=gradovi.replace(",**","");
        datumi=datumi.replace(",**","");
    }
    //metoda za spremanje imenika
    static void update_csv(){
        try {
            FileWriter output = new FileWriter("output_imenik.csv");
            String split_ime[] = imena.split(",", 0);
            String split_pre[] = prezimena.split(",", 0);
            String split_adr[] = adrese.split(",", 0);
            String split_gra[] = gradovi.split(",", 0);
            String split_dat[] = datumi.split(",", 0);
            int l = split_ime.length;
            output.write("Ime,Prezime,Adresa,Godina,Datum rodjenja\n");
            for (int i = 0;i<l;i++) {
                String linija="%s,%s,%s,%s,%s".formatted(split_ime[i],split_pre[i],split_adr[i],split_gra[i],split_dat[i]);
                output.write(linija+"\n");
            }
            output.flush();
            output.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Imenik spremljen kao 'output_imenik.csv' u "+ System.getProperty("user.dir"));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Imenik_GUI");
        frame.setContentPane(new Imenik_GUI().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    private JPanel Panel;
    private JButton but_akcija;
    private JTextField text_input;
    private JButton but_brisanje;
    private JTextPane pane_output;
    private JButton but_csv_load;
    private JButton but_ispis;
    private JButton but_csv_save;

}