package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private boolean isAttacking = false;
    private int attackFrame = 0;


    public Player(GamePanel gp, KeyHandler keyH, int positionX, int positionY, int speed, int damage, int health) {
        super("Player", positionX, positionY, speed, damage, health);
        this.gp = gp;
        this.keyH = keyH;

        attackHitbox = new Rectangle(0,0,54, 15);
        solidArea = new Rectangle(0,0,17,40);

        setDefaultValues();
        getPlayerImage();
        getPlayerAtackImage();
    }

    public void setDefaultValues(){
        positionX = 100;
        positionY = 100;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage (){
        try{
            upStandard = ImageIO.read(getClass().getResourceAsStream("/res/player/SpritePlayer_UP_Standart.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/SpritePlayer_UP_walking1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/SpritePlayer_UP_walking2.png"));
            downStandard = ImageIO.read(getClass().getResourceAsStream("/res/player/SpritePlayer_DOWN_Standart.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/SpritePlayer_DOWN_walking1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/SpritePlayer_DOWN_walking2.png"));
            leftStandard = ImageIO.read(getClass().getResourceAsStream("/res/player/SpritePlayer_Left_Standart.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/SpritePlayer_Left_Walking.png"));
            rightStandard = ImageIO.read(getClass().getResourceAsStream("/res/player/SpritePlayer_Right_Standart.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/SpritePlayer_Right_Walking1.png"));

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("Erro: Caminho da imagem do Player está incorreto ou a imagem não foi encontrada.");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Erro de leitura ao carregar a imagem do Player. Verifique o arquivo e tente novamente.");
        }
    }

    public void getPlayerAtackImage (){
        try{
            atackLeft1 = getSprite("/res/player/SpritePlayer_LEFT_Atack1");
            atackLeft2 = getSprite("/res/player/SpritePlayer_LEFT_Atack2");
            atackLeft3 = getSprite("/res/player/SpritePlayer_LEFT_Atack3");
            atackLeft4 = getSprite("/res/player/SpritePlayer_LEFT_Atack4");

            atackRight1 = getSprite("/res/player/SpritePlayer_RIGHT_Atack1");
            atackRight2 = getSprite("/res/player/SpritePlayer_RIGHT_Atack2");
            atackRight3 = getSprite("/res/player/SpritePlayer_RIGHT_Atack3");
            atackRight4 = getSprite("/res/player/SpritePlayer_RIGHT_Atack4");

            atackUP1 = getSprite("/res/player/SpritePlayer_UP_Atack1");
            atackUP2 = getSprite("/res/player/SpritePlayer_UP_Atack2");
            atackUP3 = getSprite("/res/player/SpritePlayer_UP_Atack3");
            atackUP4 = getSprite("/res/player/SpritePlayer_UP_Atack4");

            atackDown1 = getSprite("/res/player/SpritePlayer_DOWN_Atack1");
            atackDown2 = getSprite("/res/player/SpritePlayer_DOWN_Atack2");
            atackDown3 = getSprite("/res/player/SpritePlayer_DOWN_Atack3");
            atackDown4 = getSprite("/res/player/SpritePlayer_DOWN_Atack4");

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("Erro: Caminho da imagem de atack do Player está incorreta ou a imagem não foi encontrada.");
        }
    }

    public void attack() {
        isAttacking = true;
        attackFrame = 1; // Inicia a animação de ataque
    }

    // Variáveis para controle de animação
    private int attackFrameCounter = 0;      // Contador para o delay
    private final double attackFrameDelay = 6.5; // Atraso para tornar a animação mais lenta

    public void update() {
        // Bloqueia o movimento quando está atacando
        if (!isAttacking) {
            // Movimentação
            boolean isMoving = false;

            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                isMoving = true;
            }

            if (keyH.upPressed) direction = "up";
            if (keyH.downPressed) direction = "down";
            if (keyH.leftPressed) direction = "left";
            if (keyH.rightPressed) direction = "right";

            updatePosition();

            // Animação de andar
            if (isMoving) {
                spriteCounter++;
                if (spriteCounter > 10) {
                    spriteNum++;
                    if (spriteNum > 3) spriteNum = 1;  // Retorna ao primeiro sprite após o último
                    spriteCounter = 0;
                }
            } else {
                spriteNum = 1; // Se parado e não atacando, mostra o primeiro sprite
            }
        }

        // Verifica o ataque
        if (keyH.buttonAtackPressed && !isAttacking) {
            attack(); // Inicia o ataque
        }

        // Animação de ataque
        if (isAttacking) {
            attackFrameCounter++; // Incrementa o contador de delay

            if (attackFrameCounter >= attackFrameDelay) {
                attackFrame++; // Avança o quadro da animação de ataque
                attackFrameCounter = 0; // Reseta o contador de delay

                if (attackFrame > 4) { // Assume que existem 4 frames de ataque
                    isAttacking = false; // Finaliza o ataque
                    attackFrame = 1; // Reinicia o frame do ataque
                }
            }
        }
    }

    private void updatePosition() {
        double diffX = 0.0;
        double diffY = 0.0;

        if (keyH.upPressed) {
            diffY -= speed;
        }
        if (keyH.downPressed) {
            diffY += speed;
        }
        if (keyH.leftPressed) {
            diffX -= speed;
        }
        if (keyH.rightPressed) {
            diffX += speed;
        }

        // Calculate the magnitude of the vector
        double magnitude = Math.sqrt(diffX * diffX + diffY * diffY);

        // Normalize the vector
        if (magnitude > 0) {
            diffX = (int)((diffX / magnitude) * speed);
            diffY = (int)((diffY / magnitude) * speed);
        }

        // Limites da área onde o jogador pode se mover
        int limitWidth = 690;
        int limitHeight = 490;

        // Coordenadas dos limites para centralizar a área
        int leftLimit = (gp.screenWidth - limitWidth) / 2;
        int rightLimit = leftLimit + limitWidth - gp.tileSize;
        int topLimit = (gp.screenHeight - limitHeight) / 2;
        int bottomLimit = topLimit + limitHeight - gp.tileSize;


        int newX = positionX + (int) diffX;
        int newY = positionY + (int) diffY;

        if (newX < leftLimit) {
            positionX = leftLimit;
        } else if (newX > rightLimit) {
            positionX = rightLimit;
        } else {
            positionX = newX;
        }

        if (newY < topLimit) {
            positionY = topLimit;
        } else if (newY > bottomLimit) {
            positionY = bottomLimit;
        } else {
            positionY = newY;
        }
    }

//    the class witch sync
public void draw(Graphics2D g2) {

        BufferedImage image = null;

    if (isAttacking) {
        // Escolha o sprite de ataque com base na direção
        switch (direction) {
            case "up":
                if(attackFrame==1)
                    image = atackUP1; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX(), getPositionY() - 48 , 48, 96, null);

                if(attackFrame==2)
                    image = atackUP2; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX(), getPositionY() - 48 , 48, 96, null);

                if(attackFrame==3)
                    image = atackUP3; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX(), getPositionY() - 48 , 48, 96, null);

                if(attackFrame==4)
                    image = atackUP4; // ou o sprite de ataque correto
                    g2.drawImage(image, getPositionX(), getPositionY() - 48 , 48, 96, null);
                break;

            case "down":
                if(attackFrame==1)
                    image = atackDown1; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX(), getPositionY(), 48, 96, null);

                if(attackFrame==2)
                    image = atackDown2; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX(), getPositionY(), 48, 96, null);

                if(attackFrame==3)
                    image = atackDown3; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX(), getPositionY(), 48, 96, null);

                if(attackFrame==4)
                    image = atackDown4; // ou o sprite de ataque correto
                    g2.drawImage(image, getPositionX(), getPositionY(), 48, 96, null);
                break;

            case "left":
                if(attackFrame==1)
                    image = atackLeft1; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX()-48, getPositionY(), 96, 48 , null);

                if(attackFrame==2)
                    image = atackLeft2; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX()-48, getPositionY(), 96, 48 , null);

                if(attackFrame==3)
                    image = atackLeft3; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX()-48, getPositionY(), 96, 48 , null);

                if(attackFrame==4)
                    image = atackLeft4; // ou o sprite de ataque correto
                    g2.drawImage(image, getPositionX()-48, getPositionY(), 96, 48 , null);
                break;

            case "right":
                if(attackFrame==1)
                    image = atackRight1; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX(), getPositionY(), 96, 48 , null);

                if(attackFrame==2)
                    image = atackRight2; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX(), getPositionY(), 96, 48 , null);

                if(attackFrame==3)
                    image = atackRight3; // ou o sprite de ataque correto
                g2.drawImage(image, getPositionX(), getPositionY(), 96, 48 , null);

                if(attackFrame==4)
                    image = atackRight4; // ou o sprite de ataque correto
                    g2.drawImage(image, getPositionX(), getPositionY(), 96, 48 , null);
                break;
        }
    } else {
        // Animação padrão
        switch(direction){
            case "up":
                if(spriteNum==1)
                    image = upStandard;
                     g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                if(spriteNum==2)
                    image = up1;
                    g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                if(spriteNum==3)
                    image = up2;
                    g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                break;

            case "down":
                if(spriteNum==1)
                    image = downStandard;
                     g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                if(spriteNum==2)
                    image = down1;
                    g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                if(spriteNum==3)
                    image = down2;
                    g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                break;
            case "left":
                if(spriteNum==1)
                    image = leftStandard;
                    g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                if(spriteNum==2)
                    image = left1;
                    g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                if(spriteNum==3)
                    image = leftStandard;
                    g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                break;
            case "right":
                if(spriteNum==1)
                    image = rightStandard;
                    g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                if(spriteNum==2)
                    image = right1;
                    g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                if(spriteNum==3)
                    image = rightStandard;
                     g2.drawImage(image, getPositionX(), getPositionY(), gp.tileSize, gp.tileSize, null);
                break;
        }
    }


//        // Draw the solidArea (collision box) for debugging
        g2.setColor(Color.RED); // Set the color for the rectangle
        g2.drawRect(getCorrectPositionX(positionX, 15), getCorrectPositionX(positionY, 5), solidArea.width, solidArea.height);
    }

    // Método para receber dano
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            // Implementar a lógica para quando a saúde do player chega a zero (game over, etc.)
        }
    }
}
