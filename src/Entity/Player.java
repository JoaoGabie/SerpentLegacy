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

    public void attack() {
        // Implementar a lógica de ataque, como verificar se uma cobra está na área de ataque
    }

    public Player(GamePanel gp, KeyHandler keyH, int positionX, int positionY, int speed, int damage, int health) {
        super("Player", positionX, positionY, speed, damage, health);
        this.gp = gp;
        this.keyH = keyH;

                solidArea = new Rectangle(18,24,12,21);

        setDefaultValues();
        getPlayerImage();
    }

    // Getters para positionX e positionY
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
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
            System.out.println("Erro: Caminho da imagem está incorreto ou a imagem não foi encontrada.");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Erro de leitura ao carregar a imagem. Verifique o arquivo e tente novamente.");
        }
    }
    public void update(){
        boolean isMoving = false;

        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
           isMoving = true;
        }

        if(keyH.upPressed == true){
            direction = "up";
//            y -= speed;
        }
        if(keyH.downPressed == true){
            direction = "down";
//            y += speed;
        }
        if(keyH.leftPressed == true){
            direction = "left";
//            x -= speed;
        }
        if(keyH.rightPressed == true){
            direction = "right";
//            x += speed;
        }


        updatePosition();

        if (isMoving) {
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum++;
                if (spriteNum > 3) {  // Supondo que você tenha 2 frames de animação (1 e 2)
                    spriteNum = 2;
                }
                spriteCounter = 0;
            }
        } else {
            // Se não está se movendo, volte para o sprite padrão
            spriteNum = 1;  // Reseta para o sprite padrão (standart)
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
        int limitWidth = 646;
        int limitHeight = 466;

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
    public void draw(Graphics2D g2){

        BufferedImage Image = null;

        switch(direction){
            case "up":
                if(spriteNum==1)
                    Image = upStandard;
                if(spriteNum==2)
                    Image = up1;
                if(spriteNum==3)
                    Image = up2;
                break;

            case "down":
                if(spriteNum==1)
                    Image = downStandard;
                if(spriteNum==2)
                    Image = down1;
                if(spriteNum==3)
                    Image = down2;
                break;
            case "left":
                if(spriteNum==1)
                    Image = leftStandard;
                if(spriteNum==2)
                    Image = left1;
                if(spriteNum==3)
                    Image = leftStandard;
                break;
            case "right":
                if(spriteNum==1)
                    Image = rightStandard;
                if(spriteNum==2)
                    Image = right1;
                if(spriteNum==3)
                    Image = rightStandard;
                break;
        }
        g2.drawImage(Image, positionX, positionY, gp.tileSize, gp.tileSize, null);

    }

    // Método para receber dano
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            // Implementar a lógica para quando a saúde do player chega a zero (game over, etc.)
        }
    }

}
