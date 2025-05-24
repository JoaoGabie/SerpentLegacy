package Entity;

public class Rabbit extends Enemy {
    public Rabbit(String name, int positionX, int positionY, int speed, int damage, int health) {
        super(name, positionX, positionY, speed, damage, health);
    }
    @Override
    public void update(Player player) {
        // Lógica do gorila aqui (por enquanto pode deixar vazio)
    }
}
