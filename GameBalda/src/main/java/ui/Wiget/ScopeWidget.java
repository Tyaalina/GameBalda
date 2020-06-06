package ui.Wiget;

import MyGame.GameModel;
import MyGame.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ScopeWidget extends JPanel{
    private final GameModel game;

    public ScopeWidget(@NotNull GameModel game){
        this.game = game;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ScopeLabel scrope = new ScopeLabel(game);
        add(scrope);

        ActivePlayerLabel activePlayer = new ActivePlayerLabel(game);
        add(activePlayer);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaint();
    }
}
