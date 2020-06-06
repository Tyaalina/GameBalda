package ui.Wiget;

import MyGame.GameModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ActivePlayerLabel  extends JLabel {
    private final GameModel game;

    public ActivePlayerLabel(GameModel game) {
        this.game = game;
        createActivePlayerLabel();
    }

    private void createActivePlayerLabel(){
        setText("Активный игрок: "+ game.getActivePlayer().getName());
        setAlignmentX(Component.CENTER_ALIGNMENT);

        EmptyBorder borderActivePlayer = new EmptyBorder(5, 0, 10, 0);
        setBorder(borderActivePlayer);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        createActivePlayerLabel();
    }
}
