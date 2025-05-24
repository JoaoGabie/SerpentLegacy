package Main;

import Entity.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

//     Verifica se a entidade (‘player’ ou cobra) está a colidir com as bordas da tela
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
    public boolean checkCollision(Entity player, Entity snake) {
        // Atualiza as áreas sólidas das entidades com suas posições atuais

        int centerX = player.getPositionX() + 16;
        int centerY = player.getPositionY() + 5;
        player.solidArea.x = player.getCorrectPositionX(player.positionX, 16);
        player.solidArea.y = player.getCorrectPositionY(player.positionY, 5);
        snake.solidArea.x = snake.positionX;
        snake.solidArea.y = snake.positionY;

        // Verifica se as áreas sólidas se sobrepõem (indicando colisão)
        if (player.solidArea.intersects(snake.solidArea)) {
            resolveCollision(player, snake);
            return true;
        }
        return false;
    }

    private void resolveCollision(Entity player, Entity snake) {
        // Calcula a sobreposição no eixo X e Y entre as entidades
        int overlapX = Math.min(player.solidArea.x + player.solidArea.width, snake.solidArea.x + snake.solidArea.width)
                - Math.max(player.solidArea.x, snake.solidArea.x);
        int overlapY = Math.min(player.solidArea.y + player.solidArea.height, snake.solidArea.y + snake.solidArea.height)
                - Math.max(player.solidArea.y, snake.solidArea.y);

        // Ajusta apenas a posição de entity2 para evitar sobreposição com entity1
        if (overlapX < overlapY) {
            // Colisão no eixo X
            if (player.positionX < snake.positionX) {
                snake.positionX += overlapX;  // Move entity2 para a direita
            } else {
                snake.positionX -= overlapX;  // Move entity2 para a esquerda
            }
        } else {
            // Colisão no eixo Y
            if (player.positionY < snake.positionY) {
                snake.positionY += overlapY;  // Move entity2 para baixo
            } else {
                snake.positionY -= overlapY;  // Move entity2 para cima
            }
        }
    }

//--------------------------------------------------------------------------------------

    // Atualiza o solidArea para refletir a posição real
    private void updateSolidArea(Entity entity) {
        entity.solidArea.x = entity.getPositionX();
        entity.solidArea.y = entity.getPositionY();
    }

    // Verifica colisão entre todas as cobras na lista
    public void checkAllSnakesCollision(List<Snake> snakes) {
        for (int i = 0; i < snakes.size(); i++) {
            Snake snake1 = snakes.get(i);
            updateSolidArea(snake1);

            for (int j = i + 1; j < snakes.size(); j++) {
                Snake snake2 = snakes.get(j);
                updateSolidArea(snake2);

                if (checkCollisionBetweenSnakes(snake1, snake2)) {
                    resolveCollision(snake1, snake2);
                }
            }
        }
    }

    // Verifica colisão entre duas cobras específicas
    public boolean checkCollisionBetweenSnakes(Snake snake1, Snake snake2) {
        return snake1.solidArea.intersects(snake2.solidArea);
    }

    // Ajusta a posição para resolver a colisão
    private void resolveCollision(Snake snake1, Snake snake2) {
        int overlapX = Math.min(snake1.solidArea.x + snake1.solidArea.width, snake2.solidArea.x + snake2.solidArea.width)
                - Math.max(snake1.solidArea.x, snake2.solidArea.x);
        int overlapY = Math.min(snake1.solidArea.y + snake1.solidArea.height, snake2.solidArea.y + snake2.solidArea.height)
                - Math.max(snake1.solidArea.y, snake2.solidArea.y);

        if (overlapX < overlapY) {
            // Colisão no eixo X
            if (snake1.positionX < snake2.positionX) {
                snake1.positionX -= overlapX / 2;
                snake2.positionX += overlapX / 2;
            } else {
                snake1.positionX += overlapX / 2;
                snake2.positionX -= overlapX / 2;
            }
        } else {
            // Colisão no eixo Y
            if (snake1.positionY < snake2.positionY) {
                snake1.positionY -= overlapY / 2;
                snake2.positionY += overlapY / 2;
            } else {
                snake1.positionY += overlapY / 2;
                snake2.positionY -= overlapY / 2;
            }
        }

        updateSolidArea(snake1);
        updateSolidArea(snake2);
    }
    public Enemy checkAttackCollision(Rectangle attackHitbox, List<Enemy> enemyList) {
        for (Enemy enemy : enemyList) {
            if (attackHitbox.intersects(enemy.solidArea)) {
                System.out.println("Colisão detectada com: " + enemy.getClass().getSimpleName());
                return enemy;
            }
        }
        return null;
    }

}

