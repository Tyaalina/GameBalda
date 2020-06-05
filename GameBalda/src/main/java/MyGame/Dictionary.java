package MyGame;

import MyEvents.AchievementsListListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionary {
    private ArrayList<String> _dictionary = new ArrayList<String>();

    // ---------------------- Заполняем словарь словами -----------------------------

    private void fillInDictionary() {
        try (FileReader reader = new FileReader("D:\\ООП\\вычислительная модель\\dictionary.txt")) {
            BufferedReader _reader = new BufferedReader(reader);

            String line;
            // читаем построчно
            while ((line = _reader.readLine()) != null) {
                _dictionary.add(line);
            }
        } catch (
                IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    // ---------------------- Конструктор -----------------------------

    public Dictionary() {
        fillInDictionary();
    }

    // ---------------------- Генерация случайного слова заданого размера -----------------------------
    public String generatingRandomWord(int lenght) {
        String _word = new String();
        ArrayList<String> wordsSizeN = new ArrayList<String>();

        //Формируем список слов заданного размера
        for (String word : _dictionary) {
            if (word.length() == lenght) {
                wordsSizeN.add(word);
            }
        }

        //Выбираем рандомное слово из списка
        if (wordsSizeN.size() != 0) {
            _word = wordsSizeN.get((int) (Math.random() * wordsSizeN.size()));
        }

        return _word;
    }

    // ---------------------- Наличие слова в словаре -----------------------------
    public boolean isInDictionary(String word) {
        return _dictionary.contains(word);
    }
}
