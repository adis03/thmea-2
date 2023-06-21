package app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.ApplicationTime;

public class Eingabefenster extends Animation {
    @Override
    protected ArrayList<JFrame> createFrames(ApplicationTime applicationTimeThread) {
        ArrayList<JFrame> frames = new ArrayList<>();


        // Create second Frame
        JFrame secondaryFrame = new JFrame("Eingabe der Systemparameter");
        secondaryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel secondaryPanel = new SecondaryGraphicsContent();
        secondaryFrame.add(secondaryPanel);
        secondaryFrame.pack();
        secondaryFrame.setVisible(true);


        frames.add(secondaryFrame);
        return frames;
    }

    public class SecondaryGraphicsContent extends JPanel {
        public static JTextField inputField1, inputField2, inputField3;//input hinzufügen
        private JButton beispiel1;
        private JButton beispiel2;
        private JButton beispiel3;
        private JLabel outputLabel; //  output hinzufügen
        private JButton calculateButton; // start-button hinzufügen

        public static double startX1 = 455 - 110;
        public static double startY1 = 255;
        public static double vX1 = 30;
        public static double vY1 = 0;
        public static double startX2 = 455;
        public static double startY2 = 255 - 110;
        public static double vX2 = 0;
        public static double vY2 = 30;
        public static double masse1 = 1.0;
        public static double masse2 = 1.0;

        public SecondaryGraphicsContent() {
            setLayout(new GridLayout(8, 1)); // Layout auf ein 5x2 Raster, um die Eingabefelder, den Button und die Ausgabe anzuzeigen

            beispiel1 = new JButton("Beispiel 1"); //  button erstellen und hinzufügen
            add(new JLabel("Zwei Massen bewegen sich im 90° Grad Winkel aufeinander zu:")).setForeground(Color.BLUE); // leeres jlabel für die spaltenausrichtung
            add(beispiel1);

            beispiel2 = new JButton("Beispiel 2"); //  button erstellen und hinzufügen
            add(new JLabel("Eine Masse bewegt sich auf eine ruhige Masse zu:")).setForeground(Color.BLUE); // leeres jlabel für die spaltenausrichtung
            add(beispiel2);

            beispiel3 = new JButton("Beispiel 3"); //  button erstellen und hinzufügen
            add(new JLabel("Eine Masse bewegt sich schräg auf eine parallel zur Y-Achse bewegende Masse zu:")).setForeground(Color.BLUE); // leeres jlabel für die spaltenausrichtung
            add(beispiel3);

            inputField1 = new JTextField();
            add(new JLabel(" Restitutionskoeffizienten ϵ    (0 <= ϵ <= 1)"));
            add(inputField1);

            inputField2 = new JTextField();
            add(new JLabel(" Masse 1"));
            add(inputField2);

            inputField3 = new JTextField();
            add(new JLabel(" Masse 2"));
            add(inputField3);

            calculateButton = new JButton("Starten"); //  button erstellen und hinzufügen
            add(new JLabel()); // leeres jlabel für die spaltenausrichtung
            add(calculateButton);

            outputLabel = new JLabel();
            add(new JLabel("Ausgabe:"));
            add(outputLabel);

            // ActionListener zum Button hinzufügen, der die Berechnung der Ausgabe auslöst
            beispiel1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    werteBeispiel1();
                }
            });
            beispiel2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    werteBeispiel2();
                }
            });
            beispiel3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    werteBeispiel3();
                }
            });
            calculateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    berechneAusgabe();
                }
            });
        }
        public Dimension getPreferredSize() {
            return new Dimension(1100, 550);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.LIGHT_GRAY);
        }
        public void werteBeispiel1(){
            startX1 = 455 - 110;
            startY1 = 255;
            vX1 = 30;
            vY1 = 0;
            startX2 = 455;
            startY2 = 255 - 110;
            vX2 = 0;
            vY2 = 30;
        }
        public void werteBeispiel2(){
            startX1 = 455 - 140;
            startY1 = 255 + 10;
            vX1 = 30;
            vY1 = 0;
            startX2 = 455;
            startY2 = 255;
            vX2 = 0;
            vY2 = 0;
        }
        public void werteBeispiel3(){
            startX1 = 455;
            startY1 = 255;
            vX1 = 30;
            vY1 = 40;
            startX2 = 455 + 80;
            startY2 = 255 ;
            vX2 = 0;
            vY2 = 50;
        }
        public void berechneAusgabe() {
            double koeffizient = Double.parseDouble(inputField1.getText());

            if(koeffizient <= 1 && koeffizient >= 0 && masse1 > 0 && masse2 > 0) {
                outputLabel.setText("Programm wird gestartet!");
                outputLabel.setForeground(Color.GREEN);
                Animation animation = null;
                animation = new _1_Thema2();
                animation.start();
            } else {
                if(koeffizient > 1){
                    outputLabel.setText("Restitutionskoeffizienten ϵ ist zu groß!");
                    outputLabel.setForeground(Color.RED);
                }
                if(koeffizient < 0){
                    outputLabel.setText("Restitutionskoeffizienten ϵ ist zu klein!");
                    outputLabel.setForeground(Color.RED);
                }
            }
        }
    }
}
