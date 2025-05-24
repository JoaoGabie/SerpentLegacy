package Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Snake extends Enemy {
    private int currentFrame = 0;
    private int frameDelay = 10; // Controla a velocidade da animação
    private int spriteCounter = 0;
    private int spriteNum = 0;
    private boolean facingRight = true;

    public Snake(int positionX, int positionY, int speed, int damage, int health) {
        super("Snake", positionX, positionY, speed, damage, health);
        this.health = 1;
        this.damage = 1;

        rightWalking = new BufferedImage[4];
        leftWalking = new BufferedImage[4];
        leftDying = new BufferedImage[5];
        rightDying = new BufferedImage[5];
        leftAttack = new BufferedImage[10];
        rightAttack = new BufferedImage[10];
        setDefaultValues();
        getSnakeImage();
        getSnakeDieImage();
//        getSnakeAttackImage();                    // <-- Não esta funcionando

        solidArea = new Rectangle(0,0,22,22);
    }

    public void setDefaultValues() {
        this.speed = 3;
        this.direction = "right"; // Começa movendo para a direita por padrão
    }

    public void getSnakeImage () {
        try {
            // Carregar os sprites da pasta
            leftWalking[0] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Walking/Snake_Left_walking1.png"));
            leftWalking[1] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Walking/Snake_Left_walking2.png"));
            leftWalking[2] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Walking/Snake_Left_walking3.png"));
            leftWalking[3] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Walking/Snake_Left_walking4.png"));
            rightWalking[0] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Walking/Snake_Right_walking1.png"));
            rightWalking[1] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Walking/Snake_Right_walking2.png"));
            rightWalking[2] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Walking/Snake_Right_walking3.png"));
            rightWalking[3] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Walking/Snake_Right_walking4.png"));
            System.out.println("Sprites carregados com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar sprites da cobra.");
        }
    }

    public void getSnakeDieImage () {
        try {
            // Carregar os sprites da pasta
            leftDying[0] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Dying/Snake_Left_dying1.png"));
            leftDying[1] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Dying/Snake_Left_dying2.png"));
            leftDying[2] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Dying/Snake_Left_dying3.png"));
            leftDying[3] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Dying/Snake_Left_dying4.png"));
            leftDying[4] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Dying/Snake_Left_dying5.png"));

            rightDying[0] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Dying/Snake_Right_dying1.png"));
            rightDying[1] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Dying/Snake_Right_dying2.png"));
            rightDying[2] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Dying/Snake_Right_dying3.png"));
            rightDying[3] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Dying/Snake_Right_dying4.png"));
            rightDying[4] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/Snake/Dying/Snake_Right_dying5.png"));
            System.out.println("Sprites carregados com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar sprites da cobra de morrer.");
        }
    }

    public void getSnakeAttackImage () {
        try {
            // Carregar os sprites da pasta
            leftAttack[0] = getSprite("/enemy/Snake/Attack/Snake_Left_attack1.png");
            leftAttack[1] = getSprite("/enemy/Snake/Attack/Snake_Left_attack2.png");
            leftAttack[2] = getSprite("/enemy/Snake/Attack/Snake_Left_attack3.png");
            leftAttack[3] = getSprite("/enemy/Snake/Attack/Snake_Left_attack4.png");
            leftAttack[4] = getSprite("/enemy/Snake/Attack/Snake_Left_attack5.png");
            leftAttack[5] = getSprite("/enemy/Snake/Attack/Snake_Left_attack6.png");
            leftAttack[6] = getSprite("/enemy/Snake/Attack/Snake_Left_attack7.png");
            leftAttack[7] = getSprite("/enemy/Snake/Attack/Snake_Left_attack8.png");
            leftAttack[8] = getSprite("/enemy/Snake/Attack/Snake_Left_attack9.png");
            leftAttack[9] = getSprite("/enemy/Snake/Attack/Snake_Left_attack10.png");

            rightAttack[0] = getSprite("/enemy/Snake/Attack/Snake_Right_attack1.png");
            rightAttack[1] = getSprite("/enemy/Snake/Attack/Snake_Right_attack2.png");
            rightAttack[2] = getSprite("/enemy/Snake/Attack/Snake_Right_attack3.png");
            rightAttack[3] = getSprite("/enemy/Snake/Attack/Snake_Right_attack4.png");
            rightAttack[4] = getSprite("/enemy/Snake/Attack/Snake_Right_attack5.png");
            rightAttack[5] = getSprite("/enemy/Snake/Attack/Snake_Right_attack6.png");
            rightAttack[6] = getSprite("/enemy/Snake/Attack/Snake_Right_attack7.png");
            rightAttack[7] = getSprite("/enemy/Snake/Attack/Snake_Right_attack8.png");
            rightAttack[8] = getSprite("/enemy/Snake/Attack/Snake_Right_attack9.png");
            rightAttack[9] = getSprite("/enemy/Snake/Attack/Snake_Right_attack10.png");




            System.out.println("Sprites carregados com sucesso!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar sprites da cobra de Attack.");
        }
    }

    public void update(Player player) {
        if (isDying) {
            deathFrame++;
            if (deathFrame >= leftDying.length * frameDelay) {
                // Animação concluída, sinalize para o CollisionChecker remover a cobra
                isDying = false;
                System.out.println("Animação de morte concluída. Cobra pronta para remoção.");
            }
            return;
        }

        // Atualiza a posição da cobra com base na posição do jogador
        followPlayer(player);

        // Atualiza a animação da cobra
        updateAnimation();
    }

    public void followPlayer(Player player) {
        // Calcular a diferença entre a posição da cobra e do jogador
        int centralizePostionX = 7;
        int centralizePostionY = 15;
        int dx = player.getPositionX() - this.positionX + centralizePostionX;
        int dy = player.getPositionY() - this.positionY + centralizePostionY;

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
    }



    private void updateAnimation() {
        spriteCounter++;
        if (spriteCounter > frameDelay) { // Ajuste o número para controlar a velocidade da animação
            spriteNum++;
            if (spriteNum >= leftWalking.length) { // Como temos 4 sprites
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
        spriteNum = spriteNum % leftWalking.length;

        // Cálculo do deslocamento entre a cobra e o jogador
        int dx = player.getPositionX() - this.positionX;
        int dy = player.getPositionY() - this.positionY;

        // Defina o fator de escala (por exemplo, 2.0 para dobrar o tamanho)
        double scaleFactor = 1.4;

        if (isDying) {
            int frameIndex = deathFrame / frameDelay;
            BufferedImage deathImage = facingRight ? rightDying[frameIndex] : leftDying[frameIndex];
            g2.drawImage(deathImage, positionX, positionY, null);
            return;
        }

        if (Math.abs(dx) > Math.abs(dy)) {
            // Cobra se move mais horizontalmente
            if (dx > 0) {
                image = rightWalking[spriteNum];  // Movendo para a direita
                direction = "right";
            } else {
                image = leftWalking[spriteNum];   // Movendo para a esquerda
                direction = "left";
            }
        } else {
            // Cobra se move mais verticalmente
            if (dy > 0) {
                image = rightWalking[spriteNum];  // Use o sprite de "right" para baixo
                direction = "down";
            } else {
                image = leftWalking[spriteNum];   // Use o sprite de "left" para cima
                direction = "up";
            }


            // Draw the solidArea (collision box) for debugging
            if(player.iWannaSeeTheAllHitboxes){
                g2.setColor(Color.blue);
                g2.drawRect(positionX, positionY, solidArea.width, solidArea.height);
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

