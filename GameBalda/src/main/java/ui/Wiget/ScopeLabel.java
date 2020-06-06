package ui.Wiget;

import MyGame.GameModel;
import MyGame.Player;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ScopeLabel extends JLabel {
    private final GameModel game;

    public ScopeLabel(GameModel game) {
        this.game = game;
        createScopeLabel();
    }

    private void createScopeLabel(){
        setText("Текущий счёт: "+
                createScopePlayer(this.game.getPlayerList().get(0)) +" : "+
                createScopePlayer(this.game.getPlayerList().get(1)));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        EmptyBorder border = new EmptyBorder(10, 0, 5, 0);
        setBorder(border);
    }

    private @NotNull String createScopePlayer(@NotNull Player player){
        return Integer.toString(game.getGameRating().getRating().get(player));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        createScopeLabel();
    }
}
