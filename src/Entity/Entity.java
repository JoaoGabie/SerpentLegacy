package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class Entity {



    public String name;
    public int positionX, positionY;
    public Rectangle solidArea, attackHitbox;
    public boolean isHitboxVisible = false;
    public boolean iWannaSeeTheAllHitboxes = false;

    public int speed;
    public int damage;  // Dano que a entidade pode causar
    public int health;  // Saúde da entidade
    public boolean isDying = false;
    public int deathFrame = 0;

    public BufferedImage upStandard, up1, up2, downStandard, down1, down2, leftStandard, left1, rightStandard, right1;
    public BufferedImage  atackLeft1, atackLeft2, atackLeft3, atackLeft4, atackRight1, atackRight2, atackRight3, atackRight4, atackUP1, atackUP2, atackUP3, atackUP4, atackDown1, atackDown2, atackDown3, atackDown4;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    private List<Entity> entityList = new ArrayList<>();
    private static List<Snake> snakes = new ArrayList<>();

    // Construtor
    public Entity(String name, int positionX, int positionY, int speed, int damage, int health) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        this.damage = damage;
        this.health = health;
    }



    public BufferedImage getSprite(String imagePath) {
        BufferedImage image = null;
        try {
            // Carrega a imagem do caminho fornecido
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    public void receiveAttack (Rectangle attackHitbox, int damage) {
        if (this.solidArea.intersects(attackHitbox)) {
            takeDamage(damage);
        }
    }

    // Aplica o dano à entidade
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            onDeath();
            startDeathAnimation();
        }
    }

    public void takeDamageFrom(Entity attacker) {
        takeDamage(attacker.damage);
    }

    public void startDeathAnimation() {
        isDying = true;
        deathFrame = 0;
    }

    //     Método para morte (ação que ocorre quando a entidade morre)
    protected void onDeath() {
        System.out.println(name + " morreu!");
        // Outras ações quando a entidade morre (pode ser removida da lista, animação, etc.)
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getCorrectPositionX(int positionX, int valueCorrect) {
        return positionX + valueCorrect;
    }

    public int getCorrectPositionY(int positionY, int valueCorrect){
        return positionY + valueCorrect;
    }

    public static List<Snake> getListOfSnakes() {
        return snakes; // retorna a lista original
    }
}