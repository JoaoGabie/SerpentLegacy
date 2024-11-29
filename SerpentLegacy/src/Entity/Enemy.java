package Entity;

public abstract class Enemy extends Entity {

    public Enemy(String name, int positionX, int positionY, int speed, int damage, int health) {
        super(name, positionX, positionY, speed, damage, health);
    }
}
