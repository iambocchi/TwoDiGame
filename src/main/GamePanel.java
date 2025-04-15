package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS

    final int ORIGINALTILESIZE = 16; // 16x16 tile
    final int SCALE = 3;

    final int TILESIZE = ORIGINALTILESIZE * SCALE; // 48x48 tile
    final int MAXSCREENCOL = 16;
    final int MAXSCREENROW = 12;
    final int SCREENWIDTH = TILESIZE * MAXSCREENCOL; // 768 pixel
    final int SCREENHEIGHT = TILESIZE * MAXSCREENROW; // 576 pixel

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // starts the run
    }

    // @Override
    // GAME LOOP #1 METHOD
    // public void run() {

    // double drawInterval = 1000000000 / FPS; // 0.01666 seconds
    // double nextDrawTime = System.nanoTime() + drawInterval;

    // while (gameThread != null) {

    // // System.out.println("this is running");

    // // 1 UPDATE: update information such as character position
    // update();
    // // 2 DRAW: draw the screen with the updated information
    // // the paintComponent method == repain();
    // repaint();

    // // GAME LOOP #1
    // // try {
    // // double remainingTime = nextDrawTime - System.nanoTime();
    // // remainingTime = remainingTime / 1000000;

    // // if (remainingTime < 0) {
    // // remainingTime = 0;
    // // }
    // // Thread.sleep((long) remainingTime);
    // // nextDrawTime += drawInterval;

    // // } catch (InterruptedException e) {

    // // e.printStackTrace();
    // // }
    // }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            // timer += (currentTime - lastTime); GET FPS!
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                // drawcount++; GET FPS!
            }
            // GET FPS!
            // if (timer >= 1000000000) {
            // System.out.println("FPS: " + drawcount);
            // drawcount = 0;
            // timer = 0;
            // }
        }
    }

    public void update() {

        if (keyH.upPressed == true) {
            playerY -= playerSpeed;

        } else if (keyH.downPressed == true) {
            playerY += playerSpeed;

        } else if (keyH.leftPressed) {
            playerX -= playerSpeed;

        } else if (keyH.rightPressed) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, TILESIZE, TILESIZE);
        g2.dispose();
    }
}
