package ui.Wiget;

import MyGame.GameModel;
import MyGame.Player;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class UsedWordPanel extends JPanel {
    private final GameModel game;
    private final Player player;

    public UsedWordPanel(GameModel game, @NotNull Player player) {
        this.game = game;
        this.player = player;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel playerName = new JLabel(player.getName()+":");
        setAlignmentX(Component.CENTER_ALIGNMENT);
        add(playerName);

        ListOfUsedWord usedWord = new ListOfUsedWord(game,player);
        add(usedWord);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
