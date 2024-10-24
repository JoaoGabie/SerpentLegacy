package Entity;
import java.awt.*;
import Entity.Player;
import Main.CollisionChecker;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Snake extends Entity {
    private BufferedImage[] rightSprites;
    private BufferedImage[] leftSprites;
    private int currentFrame = 0;
    private int frameDelay = 10; // Controla a velocidade da animação
    private int spriteCounter = 0;
    private int spriteNum = 0;
    private boolean facingRight = true;

    public Snake(int positionX, int positionY, int speed, int damage, int health) {
        super("Snake", positionX, positionY, speed, damage, health);
        rightSprites = new BufferedImage[4];
        leftSprites = new BufferedImage[4];
        setDefaultValues();
        getSnakeImage();
        solidArea = new Rectangle(0,9,22,15);
    }

    public void setDefaultValues() {
//        this.positionX = 100;
//        this.positionY = 200;
        this.speed = 3;
        this.direction = "right"; // Começa movendo para a direita por padrão
    }

    public void getSnakeImage () {
        try {
            // Carregar os sprites da pasta
            leftSprites[0] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake_Left_walking1.png"));
            leftSprites[1] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake_Left_walking2.png"));
            leftSprites[2] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake_Left_walking3.png"));
            leftSprites[3] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake_Left_walking4.png"));
            rightSprites[0] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake_Right_walking1.png"));
            rightSprites[1] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake_Right_walking2.png"));
            rightSprites[2] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake_Right_walking3.png"));
            rightSprites[3] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake_Right_walking4.png"));
            System.out.println("Sprites carregados com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar sprites da cobra.");
        }
    }

    public void update(Player player) {
        // Atualiza a posição da cobra com base na posição do jogador
        followPlayer(player);

        // Atualiza a animação da cobra
        updateAnimation();
    }

    public void followPlayer(Player player) {
        // Calcular a diferença entre a posição da cobra e do jogador
        int dx = player.getPositionX() - this.positionX;
        int dy = player.getPositionY() - this.positionY;

        // Calcular a distância entre a cobra e o jogador
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Se a distância for maior que 0, normaliza o vetor de movimento
        if (distance > 0) {
            dx = (int) (dx / distance * speed);
            dy = (int) (dy / distance * speed);

            // Atualiza a posição da cobra
            this.positionX += dx;
            this.positionY += dy;

            // Definir a direção com base na posição em relação ao jogador
            if (Math.abs(dx) > Math.abs(dy)) {
                direction = dx > 0 ? "right" : "left";
            } else {
                direction = dy > 0 ? "down" : "up";
            }
        }
//        updateAnimation();
    }


    private void updateAnimation() {
        spriteCounter++;
        if (spriteCounter > frameDelay) { // Ajuste o número para controlar a velocidade da animação
            spriteNum++;
            if (spriteNum >= leftSprites.length) { // Como temos 4 sprites
                spriteNum = 0;
            }
            spriteCounter = 0;
        }
    }

    public void attackPlayer(Player player) {
        // Verificar se a cobra colidiu com o player
        // Se sim, chamar player.takeDamage(damage);
    }
    public void draw(Graphics2D g2, Player player) {
        BufferedImage image = null;

        // Garantir que spriteNum fique dentro dos limites da array de sprites
        spriteNum = spriteNum % leftSprites.length;

        // Cálculo do deslocamento entre a cobra e o jogador
        int dx = player.getPositionX() - this.positionX;
        int dy = player.getPositionY() - this.positionY;

        // Defina o fator de escala (por exemplo, 2.0 para dobrar o tamanho)
        double scaleFactor = 1.4;

        if (Math.abs(dx) > Math.abs(dy)) {
            // Cobra se move mais horizontalmente
            if (dx > 0) {
                image = rightSprites[spriteNum];  // Movendo para a direita
                direction = "right";
            } else {
                image = leftSprites[spriteNum];   // Movendo para a esquerda
                direction = "left";
            }
        } else {
            // Cobra se move mais verticalmente
            if (dy > 0) {
                image = rightSprites[spriteNum];  // Use o sprite de "right" para baixo
                direction = "down";
            } else {
                image = leftSprites[spriteNum];   // Use o sprite de "left" para cima
                direction = "up";
            }
        }

        if (image != null) {
            // Desenhar a imagem redimensionada
            int newWidth = (int) (image.getWidth() * scaleFactor);
            int newHeight = (int) (image.getHeight() * scaleFactor);
            g2.drawImage(image, positionX, positionY, newWidth, newHeight, null);
        } else {
            System.out.println("Imagem da cobra é nula para a direção: " + direction);
        }
    }
}

