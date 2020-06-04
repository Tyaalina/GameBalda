package ui.Wiget;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class AlphabetWiget {
    JPanel alphabetWidget;

    public AlphabetWiget(){
        //Создаём панель
        JPanel _alphabetWidget = new JPanel();
        //Как отображ
        _alphabetWidget.setLayout(new GridLayout(4, 8, 0, 0));
        //Разремер поля без кнопок
        _alphabetWidget.setPreferredSize(new Dimension(400, 200));

        JButton[] alphabet = new JButton[32];

        alphabet[0] = new JButton("а");
        alphabet[0].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[0]);

        alphabet[1] = new JButton("б");
        alphabet[1].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[1]);

        alphabet[2] = new JButton("в");
        alphabet[2].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[2]);

        alphabet[3] = new JButton("г");
        alphabet[3].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[3]);

        alphabet[4] = new JButton("д");
        alphabet[4].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[4]);

        alphabet[5] = new JButton("е");
        alphabet[5].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[5]);

        alphabet[6] = new JButton("ж");
        alphabet[6].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[6]);

        alphabet[7] = new JButton("з");
        alphabet[7].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[7]);

        alphabet[8] = new JButton("и");
        alphabet[8].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[8]);

        alphabet[9] = new JButton("й");
        alphabet[9].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[9]);

        alphabet[10] = new JButton("к");
        alphabet[10].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[10]);

        alphabet[11] = new JButton("л");
        alphabet[11].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[11]);

        alphabet[12] = new JButton("м");
        alphabet[12].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[12]);

        alphabet[13] = new JButton("н");
        alphabet[13].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[13]);

        alphabet[14] = new JButton("о");
        alphabet[14].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[14]);

        alphabet[15] = new JButton("п");
        alphabet[15].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[15]);

        alphabet[16] = new JButton("р");
        alphabet[16].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[16]);

        alphabet[17] = new JButton("с");
        alphabet[17].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[17]);

        alphabet[18] = new JButton("т");
        alphabet[18].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[18]);

        alphabet[19] = new JButton("у");
        alphabet[19].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[19]);

        alphabet[20] = new JButton("ф");
        alphabet[20].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[20]);

        alphabet[21] = new JButton("х");
        alphabet[21].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[21]);

        alphabet[22] = new JButton("ц");
        alphabet[22].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[22]);

        alphabet[23] = new JButton("ч");
        alphabet[23].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[23]);

        alphabet[24] = new JButton("ш");
        alphabet[24].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[24]);

        alphabet[25] = new JButton("щ");
        alphabet[25].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[25]);

        alphabet[26] = new JButton("ь");
        alphabet[26].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[26]);

        alphabet[27] = new JButton("ы");
        alphabet[27].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[27]);

        alphabet[28] = new JButton("ъ");
        alphabet[28].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[28]);

        alphabet[29] = new JButton("э");
        alphabet[29].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[29]);

        alphabet[30] = new JButton("ю");
        alphabet[30].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[30]);

        alphabet[31] = new JButton("я");
        alphabet[31].setPreferredSize(new Dimension(50,50));
        _alphabetWidget.add(alphabet[31]);

        alphabetWidget = _alphabetWidget;
    }

    public JPanel getAlphabetWidget(){
        return alphabetWidget;
    }
}
