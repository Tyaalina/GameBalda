package ui;

import MyGame.GameField;
import MyGame.GameModel;
import org.jetbrains.annotations.NotNull;
import ui.Wiget.AlphabetWiget;
import ui.Wiget.FieldWiget;
import ui.Wiget.ScopeWiget;
import ui.Wiget.UsedWordsWidget;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private final GameField field;
    private final GameModel game;

    private AlphabetWiget alphabetWiget = new AlphabetWiget();
    private ScopeWiget scopeWiget;
    private FieldWiget fieldWiget;
    private UsedWordsWidget usedWordsWidget;

    public GamePanel(GameField field, GameModel game) {
        super();
        this.game = game;

        this.setName("Балда");

        this.field = game.getGameField();

        scopeWiget = new ScopeWiget(game);
        usedWordsWidget = new UsedWordsWidget(game);
        fieldWiget = new FieldWiget(field);

        //распологаем
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        fillField();
    }

    // --------------------------- Отрисовываем панель ------------------------------
    JButton startButton;
    JButton finishedWordButton;

    private void fillField() {
        add(scopeWiget.getScopeWiget());

        //Создаём панель c игровым полем
        JPanel gamePanel = new JPanel();
        //Как отображ
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // По умолчанию натуральная высота, максимальная ширина
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали

        constraints.gridx = 0;      // первая ячейка таблицы по горизонтали
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали
        gamePanel.add(fieldWiget.getFieldWiget(),constraints);

        constraints.gridx = 1;      // нулевая ячейка таблицы по горизонтали
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали
        constraints.gridwidth = 2;    // размер ячейки в две ячейки
        gamePanel.add(usedWordsWidget.getUseWordWidget(),constraints);

        add(gamePanel);

        //Создаём панель кнопок
        JPanel buttonWidget = new JPanel();
        //Как отображ
        buttonWidget.setLayout(new GridLayout(1, 2, 20, 0));

        JButton buttonStart = new JButton("Начать");
        buttonWidget.add(buttonStart);
        buttonStart.setActionCommand("start");
        startButton = buttonStart;
        buttonStart.addActionListener(new ClickListener());

        JButton buttonFinishWord = new JButton("Закончить формирование слова");
        buttonFinishWord.setEnabled(false);
        buttonWidget.add(buttonFinishWord);
        buttonFinishWord.setActionCommand("finish word");
        finishedWordButton = buttonFinishWord;
        buttonFinishWord.addActionListener(new ClickListener());

        EmptyBorder borderButton = new EmptyBorder(5, 50, 10, 50);
        buttonWidget.setBorder(borderButton);

        add(buttonWidget);

        add(alphabetWiget.getAlphabetWidget());
    }

    // --------------------------- Реагируем на события ------------------------------
    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(@NotNull ActionEvent e) {
            if(e.getActionCommand().equals("start")) {
                game.start();
                finishedWordButton.setEnabled(true);
                startButton.setEnabled(false);

            }
            else if (e.getActionCommand().equals("finish word")){

            }
        }
    }

}
