package ui.Wiget;

import MyGame.GameModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ScopeWiget {
    JPanel scopeWiget;

    private final GameModel game;

    public ScopeWiget(GameModel game){
        this.game = game;
        //Создаём панель
        JPanel scopePanel = new JPanel();
        scopePanel.setLayout(new BoxLayout(scopePanel, BoxLayout.Y_AXIS));

        JLabel scrope = new JLabel("Текущий счёт: "+
                Integer.toString(this.game.getPlayerList().get(0).getRatingOfActivePlayer().getRating().get(this.game.getPlayerList().get(0)))+" : "+
                Integer.toString(this.game.getPlayerList().get(1).getRatingOfActivePlayer().getRating().get(this.game.getPlayerList().get(1))));
        scrope.setAlignmentX(Component.CENTER_ALIGNMENT);

        EmptyBorder border = new EmptyBorder(10, 0, 5, 0);
        scrope.setBorder(border);

        scopePanel.add(scrope);

        JLabel activePlayer = new JLabel("Активный игрок: "+ this.game.getActivePlayer().getName());
        activePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);

        EmptyBorder borderActivePlayer = new EmptyBorder(5, 0, 10, 0);
        activePlayer.setBorder(borderActivePlayer);

        scopePanel.add(activePlayer);

        scopeWiget = scopePanel;
    }

    public JPanel getScopeWiget(){
        return scopeWiget;
    }
}
