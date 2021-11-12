package com.company;
import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class Main {
    //pocetne vrijednosti, tek toliko da nesto ima u memoriji
    static String imena = "Ivan,Daniela";
    static String prezimena = "Eret,Nagy";
    static String adrese = "Slavonska 2,Trg slobode 1";
    static String gradovi = "Krapina,Osijek";
    static String datumi = "1984,1995";

    public static void main(String[] args) {
        opcije();
        odabir();
    }
    static void opcije(){
        System.out.println("******************");
        System.out.println("1. Ispis\n2. Unos nove osobe\n3. Brisanje\n4. Učitanje csv datoteke\n5. Spremanje csv datoteke\n6. Izlaz");
        System.out.println("******************");
        odabir();
    }
    //biranje opcija
    static void odabir(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nUnesite odabir ('7' za prikaz opcija): ");
        int odabir = sc.nextInt();

        if (odabir==1) {
            ispis();
        } else if (odabir==2) {
            unos();
        } else if (odabir==3) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Unesite redni broj stavke za brisanje: ");
            int bris = scan.nextInt();
            brisanje(bris);
        } else if (odabir==4) {
            ucitaj_csv();
        } else if (odabir==5) {
            update_csv();
        } else if (odabir==6) {
            System.exit(0);
        } else if (odabir==7) {
            opcije();
        }
    }
    //metoda koja pretvara memoriju u čitljiv oblik, liniju po liniju
    static void ispis() {
        String split_ime[] = imena.split(",", 0);
        String split_pre[] = prezimena.split(",", 0);
        String split_adr[] = adrese.split(",", 0);
        String split_gra[] = gradovi.split(",", 0);
        String split_dat[] = datumi.split(",", 0);
        int l = split_ime.length;
        for (int i = 0; i < l; i++) {
            String print = "%s. %s %s, %s, %s, %s".formatted(i + 1, split_ime[i], split_pre[i], split_adr[i], split_gra[i], split_dat[i]);
            if (print.contains("  , ,")) {
                System.out.println("Nema niti jedne stavke!");
            } else {System.out.println(print);}
        }
        odabir();
    }
    static void unos() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite informacije o osobi (ime, prezime, adresa, grad, datum rođenja): ");
        String info = sc.nextLine();
        String split_info[] = info.split(", ", 0);
        imena+=","+split_info[0];prezimena+=","+split_info[1];adrese+=","+split_info[2];gradovi+=","+split_info[3];datumi+=","+split_info[4];
        if (imena.charAt(0) == ',') {
            brisanje(1);
        }
        odabir();
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
        odabir();

    }
    //metoda koja brise nepotrebne repove (zadnji zarez, a ** znakovi još i pomazu pri određivanju je li prazan imenik)
    static void sredi(){
        imena=imena.replace(",**","");
        prezimena=prezimena.replace(",**","");
        adrese=adrese.replace(",**","");
        gradovi=gradovi.replace(",**","");
        datumi=datumi.replace(",**","");
    }
    static void ucitaj_csv(){
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
        catch (Exception e) {
            e.getStackTrace();
        }
        brisanje(1);
        odabir();
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
        System.out.println("Imenik spremljen kao 'output_imenik.csv' u "+ System.getProperty("user.dir"));
    }

}
