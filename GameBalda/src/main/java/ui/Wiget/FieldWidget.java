package ui.Wiget;

import MyGame.Cell;
import MyGame.GameModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FieldWidget extends JPanel{
    private final GameModel game;

    private void fillField(){
        for (int j = 1; j < game.getGameField().getSize()+1; j++) {
            for (int i = 1; i < game.getGameField().getSize()+1; i++) {
                Point position = new Point(i,j);
                FieldButton cell = new FieldButton(game,position);
                add(cell);
            }
        }
    }

    public FieldWidget(@NotNull GameModel game){
        this.game = game;

        //Как отображ
        setLayout(new GridLayout(this.game.getGameField().getSize(), this.game.getGameField().getSize(), 0, 0));
        //Разремер поля без кнопок
        setPreferredSize(new Dimension(300, 300));

        fillField();

        EmptyBorder border = new EmptyBorder(0, 10, 5, 10);
        setBorder(border);
    }
}
