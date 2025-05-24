package Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Entity {
    private static List<Enemy> enemies = new ArrayList<>();

    public Enemy(String name, int positionX, int positionY, int speed, int damage, int health) {
        super(name, positionX, positionY, speed, damage, health);
        enemies.add(this);
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
        // Remover da lista global de inimigos, se estiver lá
        if (this instanceof Enemy) {
            Enemy.getEnemies().remove(this);
        }
    }

    public static List<Enemy> getEnemies() {
        return enemies;
    }
    public abstract void update(Player player);
}
