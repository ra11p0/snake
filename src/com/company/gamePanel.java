package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener {
    private int sectionSize = 25, snakeLength = 6;
    private final int[] appleCoords = new int[2];
    private final int[][] snakeCoords = new int[(600/sectionSize)*(600/sectionSize)][2];
    private char direction = 'e';
    boolean running = false;
    Timer timer;

    public gamePanel(int width, int height)
    {
        setPreferredSize(new Dimension(width, height));
        addKeyListener(new myKeyAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        startGame();
    }

    public void startGame()
    {
        addApple();
        running = true;
        timer = new Timer(200, this);
        timer.start();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.white);
        g.drawLine(600, 0, 600, 600);
        g.setColor(Color.GREEN);
        g.fillOval(appleCoords[0], appleCoords[1], sectionSize, sectionSize);
        g.setColor(Color.yellow);
        for (int i = 0; i < snakeLength; i++)
        {
            if (snakeCoords[i][0] < 600)g.fillRect(snakeCoords[i][0], snakeCoords[i][1], sectionSize, sectionSize);
        }
        g.setFont(new Font("Arial", Font.BOLD , 16));
        g.drawString(("Score: " + (snakeLength - 6)), 665, 16);
    }

    public void move()
    {

        if (!running) return;
        switch (direction) {
            case 's' -> snakeCoords[snakeLength - 1][1] += sectionSize;
            case 'e' -> snakeCoords[snakeLength - 1][0] += sectionSize;
            case 'w' -> snakeCoords[snakeLength - 1][0] -= sectionSize;
            case 'n' -> snakeCoords[snakeLength - 1][1] -= sectionSize;
        }
        for(int i = 0; i < snakeLength-1; i++)
        {
            snakeCoords[i][0] = snakeCoords[i+1][0];
            snakeCoords[i][1] = snakeCoords[i+1][1];
        }
        if (checkApple())
        {
            snakeCoords[snakeLength-1][0] = snakeCoords[snakeLength-2][0];
            snakeCoords[snakeLength-1][1] = snakeCoords[snakeLength-2][1];
            for(int i = snakeLength - 1; i > 0; i--)
            {
                snakeCoords[i][0] = snakeCoords[i-1][0];
                snakeCoords[i][1] = snakeCoords[i-1][1];
            }
            snakeCoords[0][0] = snakeCoords[1][0];
            snakeCoords[0][1] = snakeCoords[1][1];
        }
        checkCollisions();
    }

    public void addApple()
    {
        appleCoords[0] = new Random().nextInt((int)(600/sectionSize))*sectionSize;
        appleCoords[1] = new Random().nextInt((int)(600/sectionSize))*sectionSize;
        for(int[] i:snakeCoords)
        {
            if (appleCoords[0] == i[0] && appleCoords[1]==i[1]) addApple();
        }
    }

    public boolean checkApple()
    {
        if(snakeCoords[snakeLength-1][0] == appleCoords[0] && snakeCoords[snakeLength-1][1] == appleCoords[1])
        {
            snakeLength++;
            addApple();
            timer.setDelay(timer.getDelay()-1);
            return true;
        }
        return false;
    }

    public void checkCollisions()
    {
        for (int i = 0; i < snakeLength-2 ; i++)
        {
            if (snakeCoords[snakeLength-1][0] == snakeCoords[i][0] && snakeCoords[snakeLength-1][1] == snakeCoords[i][1])
            {
                gameOver(null);
            }
        }
        if (snakeCoords[snakeLength-1][0] < 0 || snakeCoords[snakeLength-1][1] < 0 || snakeCoords[snakeLength-1][0] > 600 || snakeCoords[snakeLength-1][1] > 600)
        {
            gameOver(null);
        }
    }

    public void gameOver(Graphics g)
    {
        running = false;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        move();
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    if (direction != 's') direction = 'n';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'n') direction = 's';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'w') direction = 'e';
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != 'e') direction = 'w';
                    break;
            }

        }
    }
}
