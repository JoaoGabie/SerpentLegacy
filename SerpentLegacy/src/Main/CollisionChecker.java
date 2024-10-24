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
        if (player.solidArea.intersects(snake.solidArea)) {
            resolveCollision(player, snake);
            return true;
        }

        return false;
    }

    private void resolveCollision(Entity player, Snake snake) {
        // Calcula a sobreposição no eixo X e Y entre o player e a snake
        int overlapX = Math.min(player.solidArea.x + player.solidArea.width, snake.solidArea.x + snake.solidArea.width)
                - Math.max(player.solidArea.x, snake.solidArea.x);
        int overlapY = Math.min(player.solidArea.y + player.solidArea.height, snake.solidArea.y + snake.solidArea.height)
                - Math.max(player.solidArea.y, snake.solidArea.y);

        // Ajusta apenas a posição da snake para evitar sobreposição
        if (overlapX < overlapY) {
            // Colisão no eixo X
            if (player.positionX < snake.positionX) {
                snake.positionX += overlapX;  // Move a snake para a direita
            } else {
                snake.positionX -= overlapX;  // Move a snake para a esquerda
            }
        } else {
            // Colisão no eixo Y
            if (player.positionY < snake.positionY) {
                snake.positionY += overlapY;  // Move a snake para baixo
            } else {
                snake.positionY -= overlapY;  // Move a snake para cima
            }
        }
    }
}

