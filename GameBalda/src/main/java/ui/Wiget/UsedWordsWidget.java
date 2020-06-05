package ui.Wiget;

import MyGame.GameModel;

import javax.swing.*;
import java.awt.*;

public class UsedWordsWidget extends JPanel{
    private final GameModel game;

    public  UsedWordsWidget(GameModel game){
        this.game = game;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //Разремер поля без кнопок
        setPreferredSize(new Dimension(100, 300));

        JLabel titelUsingWord = new JLabel("Список использованных слов: ");
        titelUsingWord.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titelUsingWord);

        //Создаём панель для игроков и их слов
        JPanel playersWidget = new JPanel();
        //Как отображ
        playersWidget.setLayout(new GridLayout(1, 2, 20, 0));

        JLabel usingWord = new JLabel();

        //Создаём панель для первого игрока
        JPanel firstPlayerWidget = new JPanel();
        firstPlayerWidget.setLayout(new BoxLayout(firstPlayerWidget, BoxLayout.Y_AXIS));

        JLabel firstPlayer = new JLabel(this.game.getPlayerList().get(0).getName()+":");
        firstPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstPlayerWidget.add(firstPlayer);

        //Создаём панель для второго игрока
        JPanel secondPlayerWidget = new JPanel();
        secondPlayerWidget.setLayout(new BoxLayout(secondPlayerWidget, BoxLayout.Y_AXIS));

        JLabel secondPlayer = new JLabel(this.game.getPlayerList().get(1).getName()+":");
        secondPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondPlayerWidget.add(secondPlayer);

        for(String words: this.game.getGameRating().getUsedWord().keySet()){
            usingWord.setText(words);
            if(this.game.getGameRating().getUsedWord().get(words) == this.game.getPlayerList().get(0)){
                firstPlayerWidget.add(usingWord);
            }
            else
            {
                secondPlayerWidget.add(usingWord);
            }
        }

        playersWidget.add(firstPlayerWidget);
        playersWidget.add(secondPlayerWidget);

        add(playersWidget);
    }

}
