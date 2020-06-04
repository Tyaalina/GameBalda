package ui.Wiget;

import MyGame.GameField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FieldWiget {
    JPanel fieldWiget;
    private final GameField field;

    public FieldWiget(@NotNull GameField field){
        this.field = field;
        //Создаём панель
        JPanel _fieldWidget = new JPanel();
        //Как отображ
        _fieldWidget.setLayout(new GridLayout(this.field.getSize(), this.field.getSize(), 0, 0));
        //Разремер поля без кнопок
        _fieldWidget.setPreferredSize(new Dimension(300, 300));

        JButton[][] gameField = new JButton[field.getSize()][field.getSize()];
        //Заполняем табличку
        for (int i = 0; i < field.getSize(); i++) {
            for (int j = 0; j < field.getSize(); j++) {
                gameField[i][j] = new JButton("");
                gameField[i][j].setPreferredSize(new Dimension(50,50));
                _fieldWidget.add(gameField[i][j]);
            }
        }
        EmptyBorder border = new EmptyBorder(0, 10, 5, 10);
        _fieldWidget.setBorder(border);

        fieldWiget = _fieldWidget;
    }

    public JPanel getFieldWiget(){
        return fieldWiget;
    }
}
