package Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Entity {
    private static List<Enemy> enemies = new ArrayList<>();

    public Enemy(String name, int positionX, int positionY, int speed, int damage, int health) {
        super(name, positionX, positionY, speed, damage, health);
        enemies.add(this);
    }


    public static List<Enemy> getEnemies() {
        return enemies;
    }
    public abstract void update(Player player);
}
