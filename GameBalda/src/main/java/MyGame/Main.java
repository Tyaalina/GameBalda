package MyGame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame {

        private GameModel game;

        public GamePanel() throws HeadlessException {
            setVisible(true);
            startGame();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private void startGame() {
            setVisible(true);
            game = new GameModel();
            //game.addGameActionListener(new GameController());

            JPanel content = (JPanel) this.getContentPane();
            content.removeAll();
            content.add(new ui.GamePanel(game.getGameField(), game));

            pack();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }
}
