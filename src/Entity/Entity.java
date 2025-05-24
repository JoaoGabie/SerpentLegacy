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

    public BufferedImage upStandard, up1, up2, downStandard, down1, down2, leftStandard, left1, rightStandard, right1;
    public BufferedImage  atackLeft1, atackLeft2, atackLeft3, atackLeft4, atackRight1, atackRight2, atackRight3, atackRight4, atackUP1, atackUP2, atackUP3, atackUP4, atackDown1, atackDown2, atackDown3, atackDown4;

    private List<Entity> entityList = new ArrayList<>();
    private static List<Snake> snakes = new ArrayList<>();

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public boolean isDying = false;
    public int deathFrame = 0;

    protected BufferedImage[] upWalking;
    protected BufferedImage[] downWalking;
    protected BufferedImage[] rightWalking;
    protected BufferedImage[] leftWalking;

    protected BufferedImage[] upDying;
    protected BufferedImage[] downDying;
    protected BufferedImage[] leftDying;
    protected BufferedImage[] rightDying;

    protected BufferedImage[] upIdle;
    protected BufferedImage[] downIdle;
    protected BufferedImage[] leftIdle;
    protected BufferedImage[] rightIdle;

    protected BufferedImage[] upAttack;
    protected BufferedImage[] downAttack;
    protected BufferedImage[] leftAttack;
    protected BufferedImage[] rightAttack;

    //Estados
    protected AnimationState animationState = AnimationState.IDLE;
    protected String direction;

    // Getters genéricos (podem ser sobrescritos se precisar lógica diferente)
    public abstract BufferedImage getWalkingFrame(String direction, int frame);
    public abstract BufferedImage getIdleFrame(String direction, int frame);
    public abstract BufferedImage getAttackingFrame(String direction, int frame);
    public abstract BufferedImage getDyingFrame(String direction, int frame);


    // Construtor
    public Entity(String name, int positionX, int positionY, int speed, int damage, int health) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        this.damage = damage;
        this.health = health;
    }

    public enum AnimationState {
        IDLE, WALKING, ATTACKING, DYING
    }

    public void draw(Graphics2D g2, Player player) {
        switch (animationState) {
            case DYING:
                drawDying(g2);
                break;
            case ATTACKING:
                drawAttacking(g2);
                break;
            case WALKING:
                drawWalking(g2);
                break;
            case IDLE:
            default:
                drawIdle(g2);
                break;
        }
    }

    // Métodos padrão, podem ser sobrescritos depois
    public void drawDying(Graphics2D g2) {
        g2.drawImage(getDeathImage(), positionX, positionY, null);
    }
    public void drawAttacking(Graphics2D g2) {
        g2.drawImage(getAttackImage(), positionX, positionY, null);
    }
    public void drawWalking(Graphics2D g2) {
        g2.drawImage(getWalkImage(), positionX, positionY, null);
    }
    public void drawIdle(Graphics2D g2) {
        g2.drawImage(getIdleImage(), positionX, positionY, null);
    }

    public BufferedImage getDeathImage() {
        // Exemplo genérico
        return deathSprite;
    }
    public BufferedImage getAttackImage() {
        // Usa direção/spriteNum para escolher, exemplo:
        if (direction.equals("left")) return atackLeft1;
        // ...e assim por diante
    }

    @Override
    public BufferedImage getWalkImage() {
        // Idem para andar
        if (direction.equals("up")) {
            if (spriteNum == 1) return up1;
            if (spriteNum == 2) return up2;
            else return upIdle;
        }
        // ...etc.
    }

    @Override
    public BufferedImage getIdleImage() {
        // Escolha padrão para parado
        return downStandard;
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