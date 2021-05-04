package com.company;

import javax.swing.*;

public class gameFrame extends JFrame {
    public gameFrame(int width, int height, boolean resizable)
    {
        add(new gamePanel(width, height));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setResizable(resizable);
        pack();
        setVisible(true);
    }
}
