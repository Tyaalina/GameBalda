package ui.Wiget;

import MyGame.Cell;
import MyGame.GameModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static MyGame.Status.*;

public class FieldButton extends JButton {
    private final GameModel gameModel;
    private final Point point;

    public FieldButton(@NotNull GameModel gameModel, Point point) {
        this.gameModel = gameModel;
        this.point = point;
        createCell();

        addActionListener(e -> {
            Cell cellToChoose = gameModel.getGameField().getCell(this.point);
            gameModel.getActivePlayer().chooseCell(cellToChoose);
            setText(cellToChoose.getLetter().ToString());
            if (cellToChoose.getStatus() == ACTIVE){
                this.setBackground(Color.GRAY);
            }
            else {
                this.setBackground(null);
            }
            repaint();
        });
        setPreferredSize(new Dimension(50, 50));
    }

    private void createCell(){
        Cell cell = gameModel.getGameField().getCell(this.point);
        if (cell != null){
            if (cell.getLetter() != null) {
                setText(cell.getLetter().ToString());
            }
            else {
                setText("");
            }

            if (cell.getStatus() == ACTIVE){
                this.setBackground(Color.GRAY);
            }
            else {
                this.setBackground(null);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        createCell();
    }
}
