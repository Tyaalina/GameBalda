package MyGame;

public class Letter {

    // --------------- Клетка, которой прнадлежит буква. Задает сама клетка -------
    private Cell _cell;
    private String _symbol;

    public Letter(String symbol) {
        _symbol = symbol;
    }

    public Letter(String symbol, Cell cell) {

        _symbol = symbol;
        _cell = cell;
    }

    public String ToString() {
        return _symbol;
    }

    public void setCell(Cell cell) {
        _cell = cell;
    }

    public Cell getCell() {
        return _cell;
    }

    public boolean equal(Letter letter) {
        if (this._symbol == letter._symbol) {
            return true;
        } else {
            return false;
        }
    }
}
