package MyGame;

import MyEvents.*;

import java.awt.*;
import java.util.ArrayList;

public class GameModel {
    // -------------------------------- Игровой счёт -------------------------------------
    AchievementsList _rating;

    public AchievementsList getGameRating() {
        return _rating;
    }

    // -------------------------------- Алфавит -------------------------------------
    private final Alphabet alphabet;

    public Alphabet getAlphabet() {
        return alphabet;
    }

    // -------------------------------- Словарь -------------------------------------
    private final Dictionary _dictionary;
    // -------------------------------- Поле -------------------------------------
    private final GameField gameField;

    public GameField getGameField() {
        return gameField;
    }

    // -------------------------------- Игроки -----------------------------------
    private final ArrayList<Player> _playerList = new ArrayList<>();

    //дефолтный игрок поля для слов сгенерированных полем
    private final Player defaultPlayer = new Player("default");

    private int _activePlayer;

    public Player getActivePlayer() {
        return _playerList.get(_activePlayer);
    }

    public ArrayList<Player> getPlayerList() {
        return _playerList;
    }

    // -------------------------------- Конструктор -------------------------------------
    public GameModel() {
        //Создаём слушателей
        PlayerAndFieldObserver observer = new PlayerAndFieldObserver();
        //Создаём игровой счёт
        _rating = new AchievementsList();

        //Создаём алфавит
        alphabet = new Alphabet();

        //Создаём словарь
        _dictionary = new Dictionary();

        //Создаём поле
        gameField = new GameField();
        gameField.addGameFieldListener(observer);

        // Задаем размеры поля по умолчанию
        getGameField().setSize(5);

        // Создаем двух игроков
        Player p;

        p = new Player(getGameField(), "Игрок1");
        p.setGame(this);
        p.addPlayerActionListener(observer); // "Следим" за игроком
        _playerList.add(p);
        _activePlayer = 0;
        _rating.getRating().put(p, 0);

        p = new Player(getGameField(), "Игрок2");
        p.setGame(this);
        p.addPlayerActionListener(observer); // "Следим" за игроком
        _playerList.add(p);
        _rating.getRating().put(p, 0);
    }
    // ---------------------- Порождение обстановки на поле ---------------------

    private CellFactory _cellFactory = new CellFactory();

    private void generateField() {

        getGameField().clear();
        getGameField().setSize(5);
        for (int row = 1; row <= getGameField().height(); row++) {
            for (int col = 1; col <= getGameField().width(); col++) {
                getGameField().setCell(new Point(col, row), _cellFactory.createCell());
            }
        }

        for (Cell cell : getGameField().getCellPool()) {
            int positionX = cell.getPosition().x;
            int positionY = cell.getPosition().y;

            int positionInCellPool = getGameField().getCellPool().indexOf(cell);

            cell.setStatus(Status.FREE);

            if (positionX != getGameField().getSize()) {
                cell.setNeighbor(getGameField().getCellPool().get(positionInCellPool + 1));
            }

            if (positionX != 1) {
                cell.setNeighbor(getGameField().getCellPool().get(positionInCellPool - 1));
            }

            if (positionY != getGameField().getSize()) {
                cell.setNeighbor(getGameField().getCellPool().get(positionInCellPool + getGameField().height()));
            }

            if (positionY != 1) {
                cell.setNeighbor(getGameField().getCellPool().get(positionInCellPool - getGameField().height()));
            }
        }
    }

    // ----------------------------- Игровой процесс ----------------------------

    public void start() {

        // Генерируем поле
        generateField();

        //Генерация случайного слова в середине поля
        String startWord = _dictionary.generatingRandomWord(gameField.width());
        this.getGameField().setWord(startWord);

        //Добавить слово в список использованных
        _rating.addUsedWord(startWord, defaultPlayer);

        // Передаем ход первому игроку
        _activePlayer = _playerList.size() - 1;

        exchangePlayer();
    }

    private void exchangePlayer() {
        //Проверяем поле на заполнение
        if (!gameField.isFull()) {
            // Сменить игрока
            _activePlayer++;
            if (_activePlayer >= _playerList.size()) _activePlayer = 0;

            //Деактивируем клетки на поле
            this.getGameField().disableAllCells();
            this.getGameField().deletedWord();

            // Генерируем событие
            playerExchanged(getActivePlayer());
        }
    }

    // ------------------------- Реагируем на действия игрока и поля------------------

    private class PlayerAndFieldObserver implements PlayerActionListener, GameFieldListener {

        @Override
        public void letterIsActive(PlayerActionEvent e) {

        }

        @Override
        public void letterAddOnGameField(PlayerActionEvent e) {

        }

        @Override
        public void letterIsNotAddOnGameField(PlayerActionEvent e) {

        }

        @Override
        public void cellAddInWord(PlayerActionEvent e) {

        }

        @Override
        public void cellNotAddInWord(PlayerActionEvent e) {

        }

        @Override
        public void cellRemoveInWord(PlayerActionEvent e) {

        }

        @Override
        public void playerFinishHisStep(PlayerActionEvent e) {
            _rating.getRating().put(_playerList.get(_activePlayer), _playerList.get(_activePlayer).getRatingOfActivePlayer().getRating().get(_playerList.get(_activePlayer)));
            exchangePlayer();
        }

        @Override
        public void wordIsMissingFromDictionary(PlayerActionEvent e) {

        }

        @Override
        public void wordIsAlreadyUsed(PlayerActionEvent e) {

        }

        @Override
        public void addCellToWord(GameFieldEvent e) {

        }

        @Override
        public void removeCellFromWord(GameFieldEvent e) {

        }

        @Override
        public void notAddCellToWord(GameFieldEvent e) {

        }

        @Override
        public void gameFieldIsFull(GameFieldEvent e) {
            _rating.getRating();

            boolean evenScore = true;

            for (int i = 1; i < _playerList.size(); i++) {
                evenScore = evenScore && (_rating.getRating().get(_playerList.get(i)) ==
                        _rating.getRating().get(_playerList.get(i - 1)));
            }

            if (evenScore) {
                //Генерируем событие
                deadHeat();
            } else {
                Player winner = getActivePlayer();
                int maxScore = 0;

                for (Player player : _playerList) {
                    if (_rating.getRating().get(player) > maxScore) {
                        winner = player;
                        maxScore = _rating.getRating().get(player);
                    }
                }

                //Генерируем событие
                gameFinished(winner);
            }
        }
    }

    // ------------------------ Порождает события игры ----------------------------

    // Список слушателей
    private ArrayList _listenerList = new ArrayList();

    // Присоединяет слушателя
    public void addGameListener(GameListener l) {
        _listenerList.add(l);
    }

    // Отсоединяет слушателя
    public void removeGameListener(GameListener l) {
        _listenerList.remove(l);
    }

    // Оповещает слушателей о событии окончании игры с победителем
    protected void gameFinished(Player winner) {

        GameEvent event = new GameEvent(this);
        event.setPlayer(winner);
        for (Object listner : _listenerList) {
            ((GameListener) listner).gameFinished(event);
        }
    }

    // Оповещает слушателей о событии окончании игры ничья
    protected void deadHeat() {

        GameEvent event = new GameEvent(this);
        for (Object listner : _listenerList) {
            ((GameListener) listner).deadHeat(event);
        }
    }

    // Оповещает слушателей о событии окончании игры смена игрока
    protected void playerExchanged(Player p) {

        GameEvent event = new GameEvent(this);
        event.setPlayer(p);
        for (Object listner : _listenerList) {
            ((GameListener) listner).playerExchanged(event);
        }
    }


}
