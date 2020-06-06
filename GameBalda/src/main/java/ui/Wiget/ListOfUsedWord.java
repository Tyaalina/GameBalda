package ui.Wiget;

import MyGame.GameModel;
import MyGame.Player;

import javax.swing.*;
import java.awt.*;

public class ListOfUsedWord extends JPanel {
    private final GameModel game;
    private final Player player;

    public ListOfUsedWord(GameModel game, Player player) {
        this.game = game;
        this.player = player;

        createListOfUsedWord();
    }

    public void createListOfUsedWord(){
        removeAll();

        for(String words: this.game.getGameRating().getUsedWord().keySet()){
            if(this.game.getGameRating().getUsedWord().get(words) == player){
                add(new JLabel(words));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        createListOfUsedWord();
        super.paintComponent(g);
    }
}
