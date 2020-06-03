package MyGame;
import MyEvents.AchievementsListEvent;
import MyEvents.AchievementsListListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class AchievementsList {
    Dictionary _dictionary = new Dictionary();
    // ----------------------- Формирование счёта ------------------------------
    HashMap<Player, Integer> _rating = new HashMap<>();

    public HashMap<Player, Integer> getRating(){
        return _rating;
    }

    private void updateGameRating(int score,Player activePlayer){
        score = score + _rating.get(activePlayer);
        _rating.put(activePlayer,score);
    }
    // ----------------------- Список используемых слов ------------------------------
    HashMap<String, Player> _usedWord = new HashMap<>();

    public HashMap<String, Player> getUsedWord(){
        return _usedWord;
    }

    public void addUsedWord(String word,Player activePlayer){
        _usedWord.put(word,activePlayer);
    }

    public boolean isUsedWord(String word){
        return (!(_usedWord.get(word) == null));
    }
    // ----------------------- Формирование листа достижений ------------------------------
    public void updateAchievementsList(@NotNull String word, Player activePlayer){
        int size = word.length();

        updateGameRating(size,activePlayer);
        addUsedWord(word,activePlayer);
    }
    // ----------------------- Добавление слова в счёт ------------------------------
    public void addWordInAchievementsList(String word,Player activePlayer){
        //Слово было использовано ранее
        if(isUsedWord(word)){
            //Генерация события
            wordAlreadyUsed();
        }
        //наличие слова в словаре
        else if (!_dictionary.isInDictionary(word)){
            //Генерация события
            wordInDictionary();
        }
        else
            {
            updateAchievementsList(word, activePlayer);

                //Генерация события
                wordAddInAchievementsList();
            }

    }

    // ---------------------- Порождает события -----------------------------

    private ArrayList <AchievementsListListener> _listener = new ArrayList<AchievementsListListener>();

    // Присоединяет слушателя
    public void addAchievementsListListener(AchievementsListListener l) {
        _listener.add(l);
    }

    // Отсоединяет слушателя
    public void removeAchievementsListListener(AchievementsListListener l) {
        _listener.remove(l);
    }

    // Оповещает слушателей о событии наличии слова в словаре
    protected void wordInDictionary() {
        //Для каждого слушателя
        for (AchievementsListListener listener : _listener) {
            //Cоздаём действие
            AchievementsListEvent event = new AchievementsListEvent(this);

            //Оповещаем
            listener.wordNotInDictionary(event);
        }
    }

    // Оповещает слушателей о событии повтороное использование слова
    protected void wordAlreadyUsed() {
        //Для каждого слушателя
        for (AchievementsListListener listener : _listener) {
            //Cоздаём действие
            AchievementsListEvent event = new AchievementsListEvent(this);

            //Оповещаем
            listener.wordAlreadyUsed(event);
        }
    }

    // Оповещает слушателей о событии слово успешно добавлено в список достижений
    protected void wordAddInAchievementsList() {
        //Для каждого слушателя
        for (AchievementsListListener listener : _listener) {
            //Cоздаём действие
            AchievementsListEvent event = new AchievementsListEvent(this);

            //Оповещаем
            listener.wordAddInAchievementsList(event);
        }
    }
}
