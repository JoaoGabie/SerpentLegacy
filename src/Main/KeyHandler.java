package Main;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, buttonAtackPressed, pauseButton, debugButton;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP){
            upPressed = true;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_Z){
            buttonAtackPressed = true;
        }
        if(code == KeyEvent.VK_ENTER){
            pauseButton = true;
        }
        if(code == KeyEvent.VK_F12){
            debugButton = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_Z){
            buttonAtackPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            pauseButton = false;
        }
        if(code == KeyEvent.VK_F12){
            debugButton = false;
        }
    }
}
