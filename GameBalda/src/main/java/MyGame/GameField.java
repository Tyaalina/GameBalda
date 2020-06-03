package MyGame;
import MyEvents.GameFieldEvent;
import MyEvents.GameFieldListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.awt.Point;

/**
 *  Квадратное поле, состоящее из клеток
 */
public class GameField {

    // ----------------- Контейнер клеток---------------
// Позиции клеток  задаются строками и столбцами в диапазоне [1, n] и
// [1, n] соответсвенно

    // ------------------------------ Клетки ---------------------------------------
    private ArrayList<Cell> _cellPool = new ArrayList();

    public ArrayList<Cell> getCellPool(){
        return _cellPool;
    }

    //Получить клетку по позиции
    public Cell getCell(Point pos){

        for(Cell obj : _cellPool)
        {
            if(obj.getPosition().equals(pos))
            { return obj; }
        }

        return null;
    }

    public void setCell(Point pos, @NotNull Cell cell){
        // Удаляем старую клетку
        removeCell(pos);

        // Связываем клетку с полем
        cell.setGameField(this);
        cell.setPosition(pos);

        // Добавляем новую клетку
        _cellPool.add(cell);
    }

    public void clear(){
        _cellPool.clear();
    }

    private void removeCell(Point pos){

        Cell obj = getCell(pos);
        if(obj != null)
            _cellPool.remove(obj);
    }

    public void activateAllCells(){
        for (Cell cell:_cellPool){
            cell.activate();
        }
    }

    public void disableAllCells(){
        for (Cell cell:_cellPool){
            cell.disable();
        }
    }

    public boolean isFull(){
        int full = 0;

        for(Cell cell:_cellPool){
            if(!cell.isEmpty()){
                full++;
            }
        }

        if (full == _cellPool.size()){
            //Генерируем событие
            gameFieldIsFull();
            return true;
        }
        else {
            return false;
        }
    }

    // ----------------------- Ширина и высота поля ------------------------------
    private int _width;
    private int _height;

    public void setSize(int n) {

        _width = n;
        _height = n;

        // Удаляем все клетки находящиеся вне поля
        int countCell = _cellPool.size();
        for (int i=0; i< countCell; i++)
        {
            if(!containsRange(_cellPool.get(i).getPosition()) )
            {
                _cellPool.remove(_cellPool.get(i));
            }
        }

    }

    public int getSize(){
        return _height;
    }

    public int width() {
        return _width;
    }

    public int height() {
        return _height;
    }

    public boolean containsRange(@NotNull Point p){
        return p.getX() >= 1 && p.getX() <= _width &&
                p.getY() >= 1 && p.getY() <= _height ;
    }

    // ----------------------------------------------------------------------------
    public GameField() {

        setSize(5);
    }

    // ----------------------- Список с формируемым словом ------------------------------
    private ArrayList<Cell> _word = new ArrayList();

    public void addCellToWord (@NotNull Cell cell){
        //Если в клетке есть буква
        if (cell.getStatus() == Status.BUSY) {

            //Если нет активных соседей
            if (!cell.activeNeighbor()) {
                //Очищаем текущее слово
                deletedWord();
            }

            //Если есть хотя бы один занятой сосед
            if(cell.busyNeighbor() || cell.activeNeighbor()) {
                //Добавляем слово к списку
                addCellToWordList(cell);

                //Генерируем событие
                addCellToWord();
            }
            else {
                //Генерируем событие
                notAddCellToWord();
            }
        }
        else
            if (cell.getStatus() == Status.ACTIVE)
        {
            //Удалить клетку из слова
            deletedCellFromWord(cell);

            //Генерируем событие
            removeCellFromWord();
        }
        else
            {
                //Генерируем событие
                notAddCellToWord();
            }
    }

    private void addCellToWordList (Cell cell){
        if (!_word.contains(cell)) {
            cell.activate();
            _word.add(cell);
        }
    }

    private void deletedCellFromWord (Cell cell){
        int indexOfDeletedCells = _word.indexOf(cell);
        for(int i =_word.size()-1; i >= indexOfDeletedCells; i--)
        {
            _word.get(i).disable();
            _word.remove(i);
        }
    }

    public void deletedWord(){
        for (Cell cell: _word){
            cell.disable();
        }
        _word.clear();
    }

    public ArrayList<Cell> getAllActiveCell(){
        return _word;
    }

    public String getWord(){
        String word = "";
        int lenght = _word.size();

        for (int i=0; i < lenght; i++){
            word = word + _word.get(i).getLetter().ToString();
        }

        return word;
    }

    // ----------------------- Установка слова ------------------------------
    public void setWord(@NotNull String word){
        ArrayList <String> _letters = new ArrayList<String>();

        for (int i= 0; word.length() > i; i++){
            _letters.add(Character.toString(word.charAt(i)));
        }

        int size = this.getSize();

        for(int i = 0; i < size; i++){
            int tmp = size*(size / 2)+i;
            _cellPool.get(size*(size / 2)+i).setLetter(new Letter(_letters.get(i)));
        }

        for(int i = 0; i < size; i++){
            this.addCellToWord(_cellPool.get(size*(size / 2)+i));
        }
    }

    // ---------------------- Порождает события -----------------------------

    private ArrayList <GameFieldListener> _listener = new ArrayList<GameFieldListener>();

    // Присоединяет слушателя
    public void addGameFieldListener(GameFieldListener l) {
        _listener.add(l);
    }

    // Отсоединяет слушателя
    public void removeGameFieldListener(GameFieldListener l) {
        _listener.remove(l);
    }

    // Оповещает слушателей о событии активации буквы
    protected void addCellToWord() {
        //Для каждого слушателя
        for (GameFieldListener listener : _listener) {
            //Cоздаём действие
            GameFieldEvent event = new GameFieldEvent(this);

            //Оповещаем
            listener.addCellToWord(event);
        }
    }

        // Оповещает слушателей о событии активации буквы
        protected void removeCellFromWord() {
            //Для каждого слушателя
            for (GameFieldListener listener : _listener) {
                //Cоздаём действие
                GameFieldEvent event = new GameFieldEvent(this);

                //Оповещаем
                listener.removeCellFromWord(event);
            }
    }

    // Оповещает слушателей о событии активации буквы
    protected void notAddCellToWord() {
        //Для каждого слушателя
        for (GameFieldListener listener : _listener) {
            //Cоздаём действие
            GameFieldEvent event = new GameFieldEvent(this);

            //Оповещаем
            listener.notAddCellToWord(event);
        }
    }

    // Оповещает слушателей о заполнении поля
    protected void gameFieldIsFull() {
        //Для каждого слушателя
        for (GameFieldListener listener : _listener) {
            //Cоздаём действие
            GameFieldEvent event = new GameFieldEvent(this);

            //Оповещаем
            listener.gameFieldIsFull(event);
        }
    }
}
