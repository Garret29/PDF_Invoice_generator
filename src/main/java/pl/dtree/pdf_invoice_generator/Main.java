package pl.dtree.pdf_invoice_generator;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String s[]) {
        JFrame frame = new JFrame();
        final JFrame frame1 = new JFrame();
        final JFileChooser chooser = new JFileChooser();
        JButton button = new JButton("zapisz");
        frame1.add(chooser);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame1.setVisible(true);
            }
        };

        button.addActionListener(actionListener);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(button);
        frame.setVisible(true);

    }
}


