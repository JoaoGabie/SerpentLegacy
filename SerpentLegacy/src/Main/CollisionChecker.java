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

//        player.solidArea.x += player.positionX;
//        player.solidArea.y += player.positionY;
//
//        snake.solidArea.x += snake.positionX;
//        snake.solidArea.y += snake.positionY;

        if (player.solidArea.intersects(snake.solidArea)) {
            resolveCollision(player, snake);
            return true;
        }// Verifica se as áreas sólidas se sobrepõem (indicando colisão)


        return false;
    }
    private void resolveCollision(Entity player, Snake snake) {
        // Lógica para ajustar a posição dos objetos
        // Verificar de que lado a colisão aconteceu e ajustar a posição adequadamente

        if (player.positionX < snake.positionX) {
            // Move entity1 para a esquerda e entity2 para a direita
            player.positionX -= 1; // Ajuste a quantidade conforme necessário
            snake.positionX += 1;
        } else if (player.positionX > snake.positionX) {
            // Move entity1 para a direita e entity2 para a esquerda
            player.positionX += 1;
            snake.positionX -= 1;
        }

        if (player.positionY < snake.positionY) {
            // Move entity1 para cima e entity2 para baixo
            player.positionY -= 1;
            snake.positionY += 1;
        } else if (player.positionY > snake.positionY) {
            // Move entity1 para baixo e entity2 para cima
            player.positionY += 5;
            snake.positionY -= 1;
        }
    }
}

