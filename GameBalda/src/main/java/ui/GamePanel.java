package ui;

import MyGame.GameField;
import MyGame.GameModel;
import MyGame.Player;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private final GameField field;
    private final GameModel game;

    private JMenuBar menu = null;
    private final String fileItems[] = new String []{"New", "Exit"};

    public GamePanel(GameField field, GameModel game) {
        super();
        this.game = game;

        this.setName("Балда");

        this.field = field;

        //распологаем
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        fillField();
    }

    // --------------------------- Отрисовываем поле ------------------------------
    JButton startButton;
    JButton finishedWordButton;

    private void fillField() {
        add(createScope());

        //Создаём панель кнопок
        JPanel gamePanel = new JPanel();
        //Как отображ
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // По умолчанию натуральная высота, максимальная ширина
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали

        constraints.gridx = 0;      // первая ячейка таблицы по горизонтали
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали
        gamePanel.add(createField(),constraints);

        constraints.gridx = 1;      // нулевая ячейка таблицы по горизонтали
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали
        constraints.gridwidth = 2;    // размер ячейки в две ячейки
        gamePanel.add(createUsedWords(),constraints);

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

        add(createAlphabet());
    }

    // --------------------------- Отрисовываем счёт ------------------------------
    private @NotNull JPanel createScope(){
        //Создаём панель
        JPanel scopePanel = new JPanel();
        scopePanel.setLayout(new BoxLayout(scopePanel, BoxLayout.Y_AXIS));

        JLabel scrope = new JLabel("Текущий счёт: "+
                Integer.toString(game.getPlayerList().get(0).getRatingOfActivePlayer().getRating().get(game.getPlayerList().get(0)))+" : "+
                Integer.toString(game.getPlayerList().get(1).getRatingOfActivePlayer().getRating().get(game.getPlayerList().get(1))));
        scrope.setAlignmentX(Component.CENTER_ALIGNMENT);

        EmptyBorder border = new EmptyBorder(10, 0, 5, 0);
        scrope.setBorder(border);

        scopePanel.add(scrope);

        JLabel activePlayer = new JLabel("Активный игрок: "+game.getActivePlayer().getName());
        activePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);

        EmptyBorder borderActivePlayer = new EmptyBorder(5, 0, 10, 0);
        activePlayer.setBorder(borderActivePlayer);

        scopePanel.add(activePlayer);

        return scopePanel;
    }

    // --------------------------- Отрисовываем список использованных слов ------------------------------
    private @NotNull JPanel createUsedWords(){
        //Создаём панель
        JPanel useWordPanel = new JPanel();
        useWordPanel.setLayout(new BoxLayout(useWordPanel, BoxLayout.Y_AXIS));
        //Разремер поля без кнопок
        useWordPanel.setPreferredSize(new Dimension(100, 300));

        JLabel titelUsingWord = new JLabel("Список использованных слов: ");
        titelUsingWord.setAlignmentX(Component.CENTER_ALIGNMENT);
        useWordPanel.add(titelUsingWord);

        //Создаём панель для игроков и их слов
        JPanel playersWidget = new JPanel();
        //Как отображ
        playersWidget.setLayout(new GridLayout(1, 2, 20, 0));

        JLabel usingWord = new JLabel();

        //Создаём панель для первого игрока
        JPanel firstPlayerWidget = new JPanel();
        firstPlayerWidget.setLayout(new BoxLayout(firstPlayerWidget, BoxLayout.Y_AXIS));

        JLabel firstPlayer = new JLabel(game.getPlayerList().get(0).getName()+":");
        firstPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstPlayerWidget.add(firstPlayer);

        //Создаём панель для второго игрока
        JPanel secondPlayerWidget = new JPanel();
        secondPlayerWidget.setLayout(new BoxLayout(secondPlayerWidget, BoxLayout.Y_AXIS));

        JLabel secondPlayer = new JLabel(game.getPlayerList().get(0).getName()+":");
        secondPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondPlayerWidget.add(secondPlayer);

        for(String words: game.getGameRating().getUsedWord().keySet()){
            usingWord.setText(words);
            if(game.getGameRating().getUsedWord().get(words) == game.getPlayerList().get(0)){
                firstPlayerWidget.add(usingWord);
            }
            else
            {
                secondPlayerWidget.add(usingWord);
            }
        }

        playersWidget.add(firstPlayerWidget);
        playersWidget.add(secondPlayerWidget);

        useWordPanel.add(playersWidget);

        return useWordPanel;
    }

    // --------------------------- Отрисовываем игровое поле ------------------------------
    private @NotNull JPanel createField(){
        //Создаём панель
        JPanel fieldWidget = new JPanel();
        //Как отображ
        fieldWidget.setLayout(new GridLayout(this.field.getSize(), this.field.getSize(), 0, 0));
        //Разремер поля без кнопок
        fieldWidget.setPreferredSize(new Dimension(300, 300));

        JButton[][] gameField = new JButton[field.getSize()][field.getSize()];
        //Заполняем табличку
        for (int i = 0; i < field.getSize(); i++) {
            for (int j = 0; j < field.getSize(); j++) {
                gameField[i][j] = new JButton("");
                gameField[i][j].setPreferredSize(new Dimension(50,50));
                fieldWidget.add(gameField[i][j]);
            }
        }
        EmptyBorder border = new EmptyBorder(0, 10, 5, 10);
        fieldWidget.setBorder(border);

        return fieldWidget;
    }

    // --------------------------- Отрисовываем алфавит ------------------------------
    private @NotNull JPanel createAlphabet(){
        //Создаём панель
        JPanel alphabetWidget = new JPanel();
        //Как отображ
        alphabetWidget.setLayout(new GridLayout(4, 8, 0, 0));
        //Разремер поля без кнопок
        alphabetWidget.setPreferredSize(new Dimension(400, 200));

        JButton[] alphabet = new JButton[32];

        alphabet[0] = new JButton("а");
        alphabet[0].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[0]);

        alphabet[1] = new JButton("б");
        alphabet[1].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[1]);

        alphabet[2] = new JButton("в");
        alphabet[2].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[2]);

        alphabet[3] = new JButton("г");
        alphabet[3].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[3]);

        alphabet[4] = new JButton("д");
        alphabet[4].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[4]);

        alphabet[5] = new JButton("е");
        alphabet[5].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[5]);

        alphabet[6] = new JButton("ж");
        alphabet[6].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[6]);

        alphabet[7] = new JButton("з");
        alphabet[7].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[7]);

        alphabet[8] = new JButton("и");
        alphabet[8].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[8]);

        alphabet[9] = new JButton("й");
        alphabet[9].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[9]);

        alphabet[10] = new JButton("к");
        alphabet[10].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[10]);

        alphabet[11] = new JButton("л");
        alphabet[11].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[11]);

        alphabet[12] = new JButton("м");
        alphabet[12].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[12]);

        alphabet[13] = new JButton("н");
        alphabet[13].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[13]);

        alphabet[14] = new JButton("о");
        alphabet[14].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[14]);

        alphabet[15] = new JButton("п");
        alphabet[15].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[15]);

        alphabet[16] = new JButton("р");
        alphabet[16].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[16]);

        alphabet[17] = new JButton("с");
        alphabet[17].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[17]);

        alphabet[18] = new JButton("т");
        alphabet[18].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[18]);

        alphabet[19] = new JButton("у");
        alphabet[19].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[19]);

        alphabet[20] = new JButton("ф");
        alphabet[20].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[20]);

        alphabet[21] = new JButton("х");
        alphabet[21].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[21]);

        alphabet[22] = new JButton("ц");
        alphabet[22].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[22]);

        alphabet[23] = new JButton("ч");
        alphabet[23].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[23]);

        alphabet[24] = new JButton("ш");
        alphabet[24].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[24]);

        alphabet[25] = new JButton("щ");
        alphabet[25].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[25]);

        alphabet[26] = new JButton("ь");
        alphabet[26].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[26]);

        alphabet[27] = new JButton("ы");
        alphabet[27].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[27]);

        alphabet[28] = new JButton("ъ");
        alphabet[28].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[28]);

        alphabet[29] = new JButton("э");
        alphabet[29].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[29]);

        alphabet[30] = new JButton("ю");
        alphabet[30].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[30]);

        alphabet[31] = new JButton("я");
        alphabet[31].setPreferredSize(new Dimension(50,50));
        alphabetWidget.add(alphabet[31]);

        return alphabetWidget;
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
