package MyGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Alphabet {
    private Letter activLetter;

    private ArrayList<Letter> _alphabet = new ArrayList<Letter>();

    // ---------------------- Заполняем алфавит буквами -----------------------------

    private void fillAlphabet() {
        try (FileReader reader = new FileReader("D:\\ООП\\вычислительная модель\\alphabet.txt")) {
            BufferedReader _reader = new BufferedReader(reader);

            String line;
            // читаем построчно
            while ((line = _reader.readLine()) != null) {
                _alphabet.add(new Letter(line));
            }
        } catch (
                IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    // ---------------------- Конструктор -----------------------------
    public Alphabet() {
        fillAlphabet();
    }

    public Alphabet(Letter _activeLetter) {

        fillAlphabet();
        activLetter = _activeLetter;
    }

    // ---------------------- Активная буква -----------------------------

    public void setActivateLetter(Letter letter) {
        activLetter = letter;
    }

    public Letter getActiveLetter() {
        return activLetter;
    }
}
