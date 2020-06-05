package ui.Wiget;

import MyGame.GameModel;
import MyGame.Player;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ScopeWidget extends JPanel{
    private final GameModel game;

    public ScopeWidget(GameModel game){
        this.game = game;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel scrope = new JLabel("Текущий счёт: "+
                createScopePlayer(this.game.getPlayerList().get(0)) +" : "+
                createScopePlayer(this.game.getPlayerList().get(1)));
        scrope.setAlignmentX(Component.CENTER_ALIGNMENT);

        EmptyBorder border = new EmptyBorder(10, 0, 5, 0);
        scrope.setBorder(border);

        add(scrope);

        JLabel activePlayer = new JLabel("Активный игрок: "+ this.game.getActivePlayer().getName());
        activePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);

        EmptyBorder borderActivePlayer = new EmptyBorder(5, 0, 10, 0);
        activePlayer.setBorder(borderActivePlayer);

        add(activePlayer);
    }

    private @NotNull String createScopePlayer(@NotNull Player player){
        return Integer.toString(player.getRatingOfActivePlayer().getRating().get(player));
    }
}
