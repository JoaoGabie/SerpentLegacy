package Main;

import Entity.Entity;
import Entity.Snake;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // Verifica se a entidade (player ou cobra) está colidindo com as bordas da tela
    public void checkScreenLimits(Entity entity) {
        // Limites da tela (pode ser ajustado conforme necessário)
        if (entity.positionX < 0) {
            entity.positionX = 0; // Limita à esquerda
        }
        if (entity.positionY < 0) {
            entity.positionY = 0; // Limita ao topo
        }
        if (entity.positionX + entity.solidArea.width > gp.screenWidth) {
            entity.positionX = gp.screenWidth - entity.solidArea.width; // Limita à direita
        }
        if (entity.positionY + entity.solidArea.height > gp.screenHeight) {
            entity.positionY = gp.screenHeight - entity.solidArea.height; // Limita à base
        }
    }

    // Verifica colisão entre o player e as cobras
    public boolean checkCollision(Entity player, Snake snake) {
        // Atualiza as áreas sólidas das entidades com suas posições atuais
        player.solidArea.x = player.positionX;
        player.solidArea.y = player.positionY;
        snake.solidArea.x = snake.positionX;
        snake.solidArea.y = snake.positionY;

        // Verifica se as áreas sólidas se sobrepõem (indicando colisão)
        return player.solidArea.intersects(snake.solidArea);
    }
}

