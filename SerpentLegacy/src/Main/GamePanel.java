package Main;

import Entity.Player;
import Entity.Snake;
import Objects.SuperObject;


import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; //16x16
    final int scale = 3;
    private List<Snake> snakes = new ArrayList<>();

    public int tileSize = originalTileSize * scale; //48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final public int screenWidth = tileSize * maxScreenCol; //768
    final public int screenHeight = tileSize * maxScreenRow; //576
    public int Player = originalTileSize * scale;
    public int positionX;
    public int positionY;
    public int limitWidth = 646;
    public int limitHeight = 466;



    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player;


    public Image backgroundImage;
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/res/tiles/threes.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading background image");
        }

        player = new Player(this, keyH, 100, 200, 5, 10, 1); // Cria uma instância de Player após inicializar a imagem de fundo

        snakes = new ArrayList<>();
        spawnSnakes(); // Cria cobras no início
    }

    private void spawnSnakes() {
        int numberOfSnakes = 10; // Número de cobras a serem criadas
        Random random = new Random();

        for (int i = 0; i < numberOfSnakes; i++) {
            int x = 0;
            int y = 0;

            // Escolhe aleatoriamente uma das quatro regiões fora da área jogável
            int region = random.nextInt(4); // 0 = Acima, 1 = Abaixo, 2 = Esquerda, 3 = Direita

            switch (region) {
                case 0: // Acima da área jogável
                    x = random.nextInt(screenWidth); // Posição horizontal aleatória em toda a largura da tela
                    y = random.nextInt(screenHeight - limitHeight); // Acima da área jogável
                    break;

                case 1: // Abaixo da área jogável
                    x = random.nextInt(screenWidth); // Posição horizontal aleatória em toda a largura da tela
                    y = limitHeight + random.nextInt(screenHeight - limitHeight); // Abaixo da área jogável
                    break;

                case 2: // À esquerda da área jogável
                    x = random.nextInt(screenWidth - limitWidth); // À esquerda da área jogável
                    y = random.nextInt(screenHeight); // Posição vertical aleatória em toda a altura da tela
                    break;

                case 3: // À direita da área jogável
                    x = limitWidth + random.nextInt(screenWidth - limitWidth); // À direita da área jogável
                    y = random.nextInt(screenHeight); // Posição vertical aleatória em toda a altura da tela
                    break;
            }

            // Cria a cobra nas coordenadas geradas
            Snake snake = new Snake(x, y, 2, 5, 10);
            snakes.add(snake);
        }
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; //0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            // 1 Update: atualiza a posição do personagem
            update();
            // 2 ReDraw: quando a informação é atualizada a tela é "reDesenhada"
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
//        collisionChecker.checkScreenLimits(player);

        for (Snake snake : snakes) {
            snake.update(player); // Atualiza a posição da cobra
//            collisionChecker.checkScreenLimits(snake); // Verifica os limites da cobra

            // Verifica se o player colidiu com a cobra
            if (collisionChecker.checkCollision(player, snake)) {
                System.out.println("Colisão detectada entre o player e a cobra!");
                // Você pode implementar alguma lógica aqui, como diminuir vida, reiniciar posição, etc.
            }
        }

        player.update();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Desenha as cobras
        for (Snake snake : snakes) {
            snake.draw(g2, player);
        }
        // Desenha o fundo
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        }
        // Desenha o player
        player.draw(g2);

        g2.dispose();
    }
}
