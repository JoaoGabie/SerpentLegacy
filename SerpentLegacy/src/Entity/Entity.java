package Entity;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public String name;
    public int positionX, positionY;
    public Rectangle solidArea;


    public int speed;
    public int damage;       // Dano que a entidade pode causar
    public int health;       // Saúde da entidade

    public BufferedImage upStandard, up1, up2, downStandard, down1, down2, leftStandard, left1, left2, left3,left4,rightStandard,right1,right2, right3, right4;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Construtor
    public Entity(String name, int positionX, int positionY, int speed, int damage, int health) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        this.damage = damage;
        this.health = health;
    }


    // Métodos adicionais (se necessário)
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;

    }
    public void attack(Entity target) {
        target.takeDamage(damage);
    }
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}