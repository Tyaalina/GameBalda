package MyGame;

import java.util.ArrayList;

import MyEvents.*;
import org.jetbrains.annotations.NotNull;

public class Player {

    // --------------------------------- Имя игрока -------------------------------
    private String _name;

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public Player (String name) {
        _name = name;
        _rating.getRating().put(this,0);
    }

    // ----------------------- Устанавливаем связь с полем -----------------------
    GameField _field;
    AchievementsList _rating = new AchievementsList();

    public Player (GameField field, String name) {
        _field = field;
        _name = name;
        _rating.getRating().put(this,0);
        _field.addGameFieldListener(new PlayerObserverGameField());
        _rating.addAchievementsListListener(new PlayerObserverAchievementsListListener());
    }

    // ----------------------- Устанавливаем связь с игрой -----------------------
    GameModel _gameModel;

    public void setGame(GameModel game){
        _gameModel = game;
    }

    // ----------------------- Выбор буквы в алфавите -----------------------
    Letter _activeLetter;

    private void setActiveLetter(Letter l) {
        _activeLetter = l;
    }

    public Letter getActiveLetter(){return _activeLetter;}

    private void removeActiveLetter() {
        _activeLetter = null;
    }

    public void chooseLetter (Letter letter){
        _gameModel.getAlphabet().setActivateLetter(letter);

        setActiveLetter(letter);

        // Генерируем событие
        letterIsActive();
    }

    // ----------------------- Выбор клетки -----------------------
    Cell _сellWithSettingLetter = new Cell();

    private void setСellWithSettingLetter(Cell c) {
        _сellWithSettingLetter = c;
    }

    public Cell getCellWithSettingLetter(){ return this._сellWithSettingLetter;}

    public void chooseCell (@NotNull Cell cell){
        //Если клетка пустая или с уже установленной активной буквой и активная буква выбрана
        if ((cell.isEmpty()|| cell.equal(_сellWithSettingLetter)) && _activeLetter != null) {

            //Получаем список активных клеток на поле
            ArrayList<Cell> activeCell = _gameModel.getGameField().getAllActiveCell();

            //Удаляем слово
            if (activeCell.size() != 0) {
                _gameModel.getGameField().deletedWord();
                _gameModel.getGameField().getCell(getCellWithSettingLetter().getPosition()).setLetter(null);
                _gameModel.getGameField().getCell(getCellWithSettingLetter().getPosition()).setStatus(Status.FREE);
            }

            //Если буква установлена
            if (cell.setLetter(_activeLetter)) {
                // Генерируем событие
                letterAddOnGameField();

                //Запоминаем клетку с установленной буквой
                setСellWithSettingLetter(cell);

            } else {
                // Генерируем событие
                letterIsNotAddOnGameField();
            }

            //Очищаем активную букву
            removeActiveLetter();
        }
        //Если в выбранной клетке есть буква и активная буква уже установлена на поле
        else if(!cell.isEmpty() && _сellWithSettingLetter.getPosition() != null){
            //Добавляем клетку к формируемому слову
            _gameModel.getGameField().addCellToWord(cell);
        }
        else {
            // Генерируем событие
            letterIsNotAddOnGameField();
        }
    }

    // ----------------------- Завершить формирование слова -----------------

    public void completeWord(){
        if(_gameModel.getGameField().getAllActiveCell().contains(_сellWithSettingLetter)){
            _rating.addWordInAchievementsList(_gameModel.getGameField().getWord(),this);
        }
    }

    public AchievementsList getRatingOfActivePlayer(){ return _rating;}

    // ------------------------- Реагируем на действия поля ------------------

    private class PlayerObserverGameField implements GameFieldListener {

        @Override
        public void addCellToWord(GameFieldEvent e) {
            // Генерируем событие
            cellAddInWord();
        }

        @Override
        public void removeCellFromWord(GameFieldEvent e) {
            // Генерируем событие
            cellRemoveInWord();
        }

        @Override
        public void notAddCellToWord(GameFieldEvent e) {
            // Генерируем событие
            cellNotAddInWord();
        }

        @Override
        public void gameFieldIsFull(GameFieldEvent e) {

        }
    }

    // ------------------------- Реагируем на действия листа достижений ------------------

    private class PlayerObserverAchievementsListListener implements AchievementsListListener {
        @Override
        public void wordNotInDictionary(AchievementsListEvent e) {
            // Генерируем событие
            wordIsMissingFromDictionary();
        }

        @Override
        public void wordAlreadyUsed(AchievementsListEvent e) {
            // Генерируем событие
            wordIsAlreadyUsed();
        }

        @Override
        public void wordAddInAchievementsList(AchievementsListEvent e) {
            // Генерируем событие
            playerFinishHisStep();

        }
    }

    // ---------------------- Порождает события -----------------------------

    private ArrayList <PlayerActionListener> _listener = new ArrayList<PlayerActionListener>();

    // Присоединяет слушателя
    public void addPlayerActionListener(PlayerActionListener l) {
        _listener.add(l);
    }

    // Отсоединяет слушателя
    public void removePlayerActionListener(PlayerActionListener l) {
        _listener.remove(l);
    }

    // Оповещает слушателей о событии активации буквы
    protected void letterIsActive() {
        //Для каждого слушателя
        for (PlayerActionListener listener : _listener) {
            //Cоздаём действие
            PlayerActionEvent event = new PlayerActionEvent(this);

            //Присоединяем действие к метке
            event.setPlayer(this);

            //Оповещаем
            listener.letterIsActive(event);
        }
    }

        // Оповещает слушателей о событии добавление буквы на поле
        protected void letterAddOnGameField() {
            //Для каждого слушателя
            for (PlayerActionListener listener : _listener) {
                //Cоздаём действие
                PlayerActionEvent event = new PlayerActionEvent(this);

                //Присоединяем действие к метке
                event.setPlayer(this);

                //Оповещаем
                listener.letterAddOnGameField(event);
            }
        }

        // Оповещает слушателей о событии не добавлении буквы на поле
        protected void letterIsNotAddOnGameField() {
            //Для каждого слушателя
            for (PlayerActionListener listener : _listener) {
                //Cоздаём действие
                PlayerActionEvent event = new PlayerActionEvent(this);

                //Присоединяем действие к метке
                event.setPlayer(this);

                //Оповещаем
                listener.letterIsNotAddOnGameField(event);
            }
        }

        // Оповещает слушателей о событии о добавлении буквы к слову
        protected void cellAddInWord() {
            //Для каждого слушателя
            for (PlayerActionListener listener : _listener) {
                //Cоздаём действие
                PlayerActionEvent event = new PlayerActionEvent(this);

                //Присоединяем действие к метке
                event.setPlayer(this);

                //Оповещаем
                listener.cellAddInWord(event);
            }
        }

        // Оповещает слушателей о событии о невозможности добавления буквы к слову
        protected void cellNotAddInWord() {
            //Для каждого слушателя
            for (PlayerActionListener listener : _listener) {
                //Cоздаём действие
                PlayerActionEvent event = new PlayerActionEvent(this);

                //Присоединяем действие к метке
                event.setPlayer(this);

                //Оповещаем
                listener.cellNotAddInWord(event);
            }
        }

        // Оповещает слушателей о событии о удалении буквы из слова
        protected void cellRemoveInWord() {
            //Для каждого слушателя
            for (PlayerActionListener listener : _listener) {
                //Cоздаём действие
                PlayerActionEvent event = new PlayerActionEvent(this);

                //Присоединяем действие к метке
                event.setPlayer(this);

                //Оповещаем
                listener.cellRemoveInWord(event);
            }
        }

        // Оповещает слушателей о событии о завершении хода игрока
        protected void playerFinishHisStep() {
            //Для каждого слушателя
            for (PlayerActionListener listener : _listener) {
                //Cоздаём действие
                PlayerActionEvent event = new PlayerActionEvent(this);

                //Присоединяем действие к метке
                event.setPlayer(this);

                //Оповещаем
                listener.playerFinishHisStep(event);
            }
        }

        // Оповещает слушателей о событии об отсутствии слова в словаре
        protected void wordIsMissingFromDictionary() {
            //Для каждого слушателя
            for (PlayerActionListener listener : _listener) {
                //Cоздаём действие
                PlayerActionEvent event = new PlayerActionEvent(this);

                //Присоединяем действие к метке
                event.setPlayer(this);

                //Оповещаем
                listener.wordIsMissingFromDictionary(event);
            }
        }

        // Оповещает слушателей о событии о использованом ранее слове
        protected void wordIsAlreadyUsed() {
            //Для каждого слушателя
            for (PlayerActionListener listener : _listener) {
                //Cоздаём действие
                PlayerActionEvent event = new PlayerActionEvent(this);

                //Присоединяем действие к метке
                event.setPlayer(this);

                //Оповещаем
                listener.wordIsAlreadyUsed(event);
            }
        }
    }
