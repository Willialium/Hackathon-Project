package BoomerUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class BoomerUI implements ActionListener
{
    JButton inputButton;
    File inputMidiFile;

    public static void main (String[] args)
    {
        new BoomerUI();
    }
    public BoomerUI ()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {}

        JFrame frame = new JFrame("Isolate (Boomer Edition)");
        JPanel mainPanel = new JPanel(new BorderLayout());

        inputButton = new JButton("Choose MIDI");
        inputButton.setActionCommand("choose input midi");
        inputButton.addActionListener(this);
        mainPanel.add(inputButton);
        mainPanel.add(inputButton, BorderLayout.NORTH);

        frame.add(mainPanel);
        frame.setSize(700, 500);
        frame.setPreferredSize(frame.getSize());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("choose input midi"))
        {
            JFileChooser fileChooser = new JFileChooser();
            //int option = fileChooser.showOpenDialog(frame);
            //if (option == JFileChooser.APPROVE_OPTION)
            //{
                //inputMidiFile = fileChooser.getSelectedFile();
                //inputButton.setText(inputMidiFile.getAbsolutePath());
            //}
        }
    }
}
