package ui;

import MyEvents.GameEvent;
import MyEvents.GameListener;
import MyGame.GameModel;
import MyGame.Player;
import org.jetbrains.annotations.NotNull;
import ui.Wiget.AlphabetWidget;
import ui.Wiget.FieldWidget;
import ui.Wiget.ScopeWidget;
import ui.Wiget.UsedWordsWidget;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class GamePanel extends JPanel {
    private final GameModel game;

    private AlphabetWidget alphabetWidget;
    private ScopeWidget scopeWidget;
    private FieldWidget fieldWidget;
    private UsedWordsWidget usedWordsWidget;

    public GamePanel(GameModel game) {
        super();
        this.game = game;
        
        scopeWidget = new ScopeWidget(game);
        usedWordsWidget = new UsedWordsWidget(game);
        fieldWidget = new FieldWidget(game);
        alphabetWidget = new AlphabetWidget(game);

        //Подписываемся на события
        game.addGameListener(new EventsListener());

        //распологаем
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        fillField();
    }

    // --------------------------- Отрисовываем панель ------------------------------
    JButton startButton;
    JButton finishedWordButton;

    private void fillField() {
        add(scopeWidget);

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
        gamePanel.add(fieldWidget,constraints);

        constraints.gridx = 1;      // нулевая ячейка таблицы по горизонтали
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали
        constraints.gridwidth = 2;    // размер ячейки в две ячейки
        gamePanel.add(usedWordsWidget,constraints);

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

        add(alphabetWidget);
    }

    private void informationAboutWordInDictionary(){
        final String MESSAGE_TITLE = "УПС! Ошибка!";
        showMessageDialog(this, "Такого слова в словаре нет!", MESSAGE_TITLE, ERROR_MESSAGE);
    }

    private void informationAboutWordAlreadyUsed(){
        final String MESSAGE_TITLE = "УПС! Ошибка!";
        showMessageDialog(this, "Это слово уже кто-то использовал! Нужно новое!", MESSAGE_TITLE, ERROR_MESSAGE);
    }

    private void informationAboutNotUsedNewLetterInWord(){
        final String MESSAGE_TITLE = "УПС! Ошибка!";
        showMessageDialog(this, "Слово составлено без установленной тобой буквы! Её нужно использовать!", MESSAGE_TITLE, ERROR_MESSAGE);
    }

    private void informationAboutChangePlayer(){
        final String MESSAGE_TITLE = "Поздравляем!";
        showMessageDialog(this, "Слово составлено верно! Ход следующего игрока.", MESSAGE_TITLE, PLAIN_MESSAGE);
    }

    private void informationAboutGameFinish(@NotNull Player winner){
        final String MESSAGE_TITLE = "Поздравляем!";
        showMessageDialog(this, "Игра окончена! Победил "+ winner.getName(), MESSAGE_TITLE, PLAIN_MESSAGE);
    }

    private void informationAboutGameFinishWithDeadHeat(){
        final String MESSAGE_TITLE = "Поздравляем! Ничья";
        showMessageDialog(this, "Игра окончена! Победила дружба!", MESSAGE_TITLE, PLAIN_MESSAGE);
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
                game.getActivePlayer().completeWord();
            }
        }
    }

    private class EventsListener implements GameListener {

        @Override
        public void gameFinished(@NotNull GameEvent e) {
            informationAboutGameFinish(e.getPlayer());
        }

        @Override
        public void playerExchanged(GameEvent e) {
            informationAboutChangePlayer();
        }

        @Override
        public void deadHeat(GameEvent e) {
            informationAboutGameFinishWithDeadHeat();
        }

        @Override
        public void wordNotFromDictionary(GameEvent e) {
            informationAboutWordInDictionary();
        }

        @Override
        public void wordIsAlreadyUsedOnField(GameEvent e) {
            informationAboutWordAlreadyUsed();
        }

        @Override
        public void wordIsNotContainNewLetter(GameEvent e) {
            informationAboutNotUsedNewLetterInWord();
        }
    }
}
