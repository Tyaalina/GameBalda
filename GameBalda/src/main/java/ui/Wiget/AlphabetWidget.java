package ui.Wiget;

import MyGame.GameModel;
import MyGame.Letter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AlphabetWidget extends JPanel{
    private final GameModel game;

    public AlphabetWidget(GameModel game){
        this.game = game;
        ArrayList<String> letters = new ArrayList<>();
        letters.add("а"); letters.add("б"); letters.add("в"); letters.add("г"); letters.add("д"); letters.add("е");
        letters.add("ж"); letters.add("з"); letters.add("и"); letters.add("й"); letters.add("к"); letters.add("л");
        letters.add("м"); letters.add("н"); letters.add("о"); letters.add("п"); letters.add("р"); letters.add("с");
        letters.add("т"); letters.add("у"); letters.add("ф"); letters.add("х"); letters.add("ц"); letters.add("ч");
        letters.add("ш"); letters.add("щ"); letters.add("ь"); letters.add("ы"); letters.add("ъ"); letters.add("э");
        letters.add("ю"); letters.add("я");


        //Как отображ
        setLayout(new GridLayout(4, 8, 0, 0));
        //Разремер поля без кнопок
        setPreferredSize(new Dimension(400, 200));

        JButton[] alphabet = new JButton[32];

        for(int i=0; i < 32; i++){
            alphabet[i] = new JButton(letters.get(i));
            alphabet[i].setActionCommand(letters.get(i));
            alphabet[i].setPreferredSize(new Dimension(50,50));
            add(alphabet[i]);
            alphabet[i].addActionListener(new ClickAlphabetListener());
        }
    }

    private class ClickAlphabetListener implements ActionListener {
        @Override
        public void actionPerformed(@NotNull ActionEvent e) {
            Letter chooseLetter = new Letter(e.getActionCommand());
            game.getActivePlayer().chooseLetter(chooseLetter);
        }
    }

}
