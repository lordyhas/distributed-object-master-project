package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;

public class UIProgram {

    public static void run() {

        System.out.println("== START ==");

        //SampleUI s = new SampleUI();
        //SampleUI.frameServer.setVisible(true);
        UIProgram mainUI  = new UIProgram();
        //UIProgram.display();
        mainUI.show();
    }

    enum CalculType {
        SURFACE,
        PERIMETER

    }

    public Figure figure = Figure.RECT;

    Calculate calculate = new Calculate();
    CalculType calculType = CalculType.SURFACE;

    public JFrame mainFrame;
    JTextField largeField;
    JLabel largeLabel;
    JLabel longLabel;


    public Font fontText1 =  new Font("Dialog", Font.TRUETYPE_FONT, 16);
    public Font fontText2 =  new Font("Dialog", Font.TRUETYPE_FONT, 14);

    public Font styleButtonFont = new Font("TimesRoman", Font.BOLD, 14);

    Font styleFont2 = new Font("Courier New", Font.BOLD, 14);
    public Border borderLineStyle = new LineBorder(Color.BLACK, 2, true);

    public int mainPanelHeight = 150;
    public int mainPanelWidth = 400;


    String[] figureStrings = { "Rectangle","Carré" };
    String[] operationStrings = { "Surface", "Périmètre" };

    String figName;


    public UIProgram() {
        display();

    }

    void show(){
        mainFrame.setVisible(true);
    }

    public void display(){
        mainFrame = new JFrame();
        //mainFrame.setUndecorated(true);
        mainFrame.getContentPane().setBackground(UIManager.getColor("MenuBar.highlight"));

        //frameServer.setBackground(Color.WHITE);
        mainFrame.setTitle("Application");
        //frameServer.getContentPane().setForeground(Color.gray);
        mainFrame.setBounds(0, 0, 450, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(null);
        //frameServer.setLocationRelativeTo(null);
        mainFrame.getContentPane().setBackground(Color.lightGray);

        /** -- Main Entry JLabel  --
         *
         * Text Label Figure
         * Combo Box Figure
         * Text Label Operation
         * Text Label Operation
         * Combo Box Operation
         * */
        JPanel calculateMainPanel = new JPanel();
        calculateMainPanel.setBounds(10, 10, mainPanelWidth, 320);
        calculateMainPanel.setBackground(Color.lightGray);
        calculateMainPanel.setLayout(null);
        calculateMainPanel.setBorder(BorderFactory.createTitledBorder(borderLineStyle,"Calcul"));


        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("Fichier"));
        menuBar.add(new JMenu("Options"));

        mainFrame.setJMenuBar(menuBar);

        /** -- Text Label Figure --*/
        JLabel figLabel = new JLabel("Figure :");
        figLabel.setFont(fontText1);
        figLabel.setBounds(40, 20, 120, 30);
        calculateMainPanel.add(figLabel);

        /** -- Combo Box Figure --*/
        JComboBox figureComboBox = new JComboBox(figureStrings);
        figureComboBox.setBounds(160, 20, 150, 30);
        figureComboBox.setForeground(new Color(255, 255, 255));
        figureComboBox.setBackground(Color.GRAY);
        calculateMainPanel.add(figureComboBox);

        /** -- Text Label Operation -- */
        JLabel operationLabel = new JLabel("Fonction :"); //Opération
        operationLabel.setFont(fontText1);
        operationLabel.setBounds(40, 60, 120, 30);
        calculateMainPanel.add(operationLabel);

        /** -- Combo Box Operation --*/
        JComboBox operationComboBox = new JComboBox(operationStrings);
        operationComboBox.setBounds(160, 60, 150, 30);

        operationComboBox.setForeground(new Color(255, 255, 255));
        operationComboBox.setBackground(Color.GRAY);
        //petList.addActionListener((ActionListener) this);
        calculateMainPanel.add(operationComboBox);

            /** -- Parameters Panel --
             *
             * */
        JPanel parameterPanel = new JPanel();
        parameterPanel.setBounds(40,60 + 60, mainPanelWidth - 120, 120);
        parameterPanel.setBackground(Color.lightGray);
        parameterPanel.setLayout(null);
        parameterPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.BLACK, 1, true),"Paramètres"));
        calculateMainPanel.add(parameterPanel);
            /** -- Longueur Label  --*/
        longLabel = new JLabel("Longueur : (cm)");
        longLabel.setFont(fontText2);
        longLabel.setBounds(20, 30, 120, 30);
        parameterPanel.add(longLabel);
        parameterPanel.add(longLabel);
            /** -- Largeur Label  --*/
        largeLabel = new JLabel("Largeur : (cm)");
        largeLabel.setFont(fontText2);
        largeLabel.setBounds(20, 70, 120, 30);
        parameterPanel.add(largeLabel);
            /** -- Longueur TextField  --*/
        JTextField longField = new JTextField();
        longField.setFont(styleFont2);
        longField.setForeground(new Color(255, 255, 255));
        longField.setBackground(Color.GRAY);
        longField.setBounds(160, 30, 100, 30);
        longField.setCaretColor(Color.WHITE);
        //textField.setColumns(10);
        parameterPanel.add(longField);

            /** -- Largeur TextField  --*/
        largeField = new JTextField();
        largeField.setFont(styleFont2);
        largeField.setForeground(new Color(255, 255, 255));
        largeField.setBackground(Color.GRAY);
        largeField.setBounds(160, 70, 100, 30);
        largeField.setCaretColor(Color.WHITE);
        //textField.setColumns(10);
        parameterPanel.add(largeField);

            /** -- Result Panel --
             *
             * */
        JPanel resultPanel = new JPanel();
        resultPanel.setBounds(40,60 + 60 + 130, mainPanelWidth - 120, 50);
        resultPanel.setBackground(Color.lightGray);
        resultPanel.setLayout(null);
        resultPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.BLACK, 1, true),"Resultat"));
        calculateMainPanel.add(resultPanel);

        JLabel resultValueLabel = new JLabel("-- -- : -- --");
        resultValueLabel.setFont(new Font("Dialog", 0, 16));
        resultValueLabel.setBounds(20, 15, 200, 20);
        resultValueLabel.setBackground(Color.WHITE);
        resultPanel.add(resultValueLabel);


        //##

        mainFrame.getContentPane().add(calculateMainPanel);

        /** -- Main Panel Button  --
         * Calculer Button
         * */
        //-- Panel Button ----------------------------------------------------
        JPanel buttonCalcMainPanel = new JPanel();
        buttonCalcMainPanel.setBounds(12, 340, mainPanelWidth-4, mainPanelHeight-100);
        buttonCalcMainPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.BLACK, 1, true),""));

        buttonCalcMainPanel.setBackground(Color.lightGray);
        buttonCalcMainPanel.setLayout(null);

        /** -- Calculer Button  --*/
        JButton calculateButton = new JButton("Send");
        calculateButton.setFont(styleButtonFont);
        calculateButton.setForeground(new Color(255, 255, 255));
        calculateButton.setBackground(Color.GREEN.darker());
        calculateButton.setBounds(100, 10, 150, 35);
        buttonCalcMainPanel.add(calculateButton);

        /** -- Quit Button  --*/
        JButton cancelButton = new JButton("Cancel  ");
        cancelButton.setFont(styleButtonFont);
        cancelButton.setForeground(new Color(255, 255, 255));
        cancelButton.setBackground(Color.RED.darker());
        cancelButton.setBounds(270, 10, 100, 35);
        buttonCalcMainPanel.add(cancelButton);



        mainFrame.getContentPane().add(buttonCalcMainPanel);

        figureComboBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if((figureComboBox
                        .getSelectedItem()
                        .toString()
                        .toLowerCase())
                        .contains("carr")){
                    figure = Figure.SQUARE;
                    System.out.println("== CARRE ==");
                    largeField.setEditable(false);
                    largeLabel.setText(" -- -- ");

                    longLabel.setText("Côté : (cm)");

                    figName = "CARRE";


                }
                else{
                    figure = Figure.RECT;
                    System.out.println("== RECTANGLE ==");
                    largeField.setEditable(true);
                    largeLabel.setText("Largeur : (cm) ");
                    longLabel.setText("Longueur : (cm)");

                    figName = "RECTANGLE";
                }
            }
        });

        operationComboBox.addActionListener (e -> {
            if((operationComboBox.getSelectedItem()
                    .toString()
                    .toLowerCase())
                    .contains("surf") ){
                calculType = CalculType.SURFACE;
                System.out.println("== SURFACE ==");

            }else{

                calculType = CalculType.PERIMETER;
                System.out.println("== PERIMETER ==");
            }
        });

        cancelButton.addActionListener(e -> System.exit(0));

        calculateButton.addActionListener(e -> {

            double longueur = largeField.getText().isEmpty()? 0 : Double.parseDouble(longField.getText());
            double largeur = largeField.getText().isEmpty()? 0 : Double.parseDouble(largeField.getText());

            double  res  = 0;

            String opName;

            System.out.println("***== Calcul Button ==****");



            if(calculType == CalculType.PERIMETER){

                res = calculate.perimeter(figure,longueur , largeur);
                resultValueLabel.setText("<html>Le périmètre est :  <b color='green'> "+res+" </b> cm </html>");
                opName = "Perimetre";
            }else{
                res = calculate.surface(figure,longueur , largeur);
                resultValueLabel.setText("<html>La surface est :  <b color='green'> "+res+"</b> cm </html>");
                opName = "Surface";
            }



            ObjectFile file = new ObjectFile("log.text");
            file.createFile();

            LocalDate localDate = LocalDate.now();
            LocalTime localTime = LocalTime.now();
            System.out.println(localDate);
            System.out.println(opName);
            System.out.println(figName);
            System.out.println(res);

            HashSet<String> fileStrings = file.readInFile();
            /*for(String str : fileStrings){
                file.writeInFile(str);
            }*/
            fileStrings.add(localDate+";"+localTime+";"+figName+";"+opName+";"+res);
            file.writeInFile(fileStrings);


        });






    }
}



/**
 *
 * @author lordyhas
 */

enum Figure {
    SQUARE,
    RECT
}
class Calculate {

    public double surface(Figure fig, double lng, double larg){

        if(fig == Figure.SQUARE){
            larg = lng;
            return lng*larg;
        }
        else{
            return lng*larg;
        }
    }

    public double perimeter(Figure fig, double lng, double larg){
        if(fig == Figure.SQUARE){
            larg = lng;
            return lng*4;
        }
        else{
            return (lng*2) + (larg*2);
        }
    }



}


class ObjectFile {

    private int id;
    private String path;
    private byte[] data;
    private String fileExtension;

    public ObjectFile(String path) {
        this.path = path;
    }


    public void setName(String name) {
        this.path = name;
    }



    public String getName() {
        return path;
    }


    public String getFileExtension() {
        return fileExtension;
    }


    public boolean createFile() {

        // accept file name or directory name through
        // command line args


        // pass the filename or directory name to File
        // object
        File file = new File(this.path);

        // apply File class methods on File object
        System.out.println("File name :" + file.getName());
        System.out.println("Path: " + file.getPath());
        System.out.println("Absolute path:"+ file.getAbsolutePath());

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.out.println("An error occurred. While creating file");
                e.printStackTrace();
                return  false;
            }

        }else {
            System.out.println("Is writable:"
                    + file.canWrite());
            System.out.println("Is readable" + file.canRead());
            BasicFileAttributes basicFileAttributes = null;
            try {
                basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.out.println("An error occurred. While printing propriety file");
                e.printStackTrace();
                return  false;
            }
            System.out.println("Is a directory:"
                    + basicFileAttributes.isDirectory());
            System.out.println("File Size in bytes "
                    + basicFileAttributes.size());
            HashSet<String>  head = new HashSet<>();
            head.add("Date; Heures; Figure; Fonction; Resultat");
            //this.writeInFile(head);
        }

        System.out.println("Parent:" + file.getParent());
        System.out.println("Exists :" + file.exists());


        return true;

    }


    HashSet<String> readInFile() {

        Path pathFile = Paths.get(this.path);

        HashSet<String>  fileStrings = new HashSet<>();

        try(BufferedReader bufferedReader = Files.newBufferedReader(pathFile, StandardCharsets.US_ASCII)){
            String str = null;
            while((str = bufferedReader.readLine()) != null ){
                fileStrings.add(str);
                System.out.println(str);
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            e.getStackTrace();

        }

        return  fileStrings;

    }

    void writeInFile(HashSet<String> fileStrings) {

        Path pathFile = Paths.get(this.path);

        //HashSet<String> fileStrings = new HashSet<>();

        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(pathFile, StandardCharsets.US_ASCII)){


            for(String message : fileStrings){
                bufferedWriter.write(message);
                bufferedWriter.newLine();
            }

        }
        catch(IOException e){
            System.out.println(e.getMessage());
            e.getStackTrace();

        }


    }
}






