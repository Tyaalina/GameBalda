package MyGame;
import org.jetbrains.annotations.NotNull;

import java.awt.Point;
import java.util.ArrayList;

public class Cell {
    // --------------------- Конструктор -----------------------
    private Point _position;

    public Cell() {

    }

    public Cell(Status status){
        this._status = status;
    }

    public Cell(@NotNull Point pos){

        this._position = pos;
    }

    public Cell (Letter letter){
        this._letter = letter;
        _status = Status.BUSY;
    }

    public Cell(GameField field){
        this._field = field;
    }
    // --------------------- Позиция буквы -----------------------

    public void setPosition(Point pos){
        this._position = pos;
    }

    public Point getPosition() {
        return this._position;
    }

    public boolean equal(@NotNull Cell cell){
            return  (this._position == null && cell._position == null) || ((this._position != null) && (cell._position != null) &&
                    (this._position.x == cell._position.x) && (this._position.y == cell._position.y));
    }

    // --------------------- Статус буквы -----------------------
    private Status _status;

    public void setStatus(Status stat){
        this._status = stat;
    }

    public Status getStatus(){
        return this._status;
    }

    public boolean activate(){
        if(getStatus() == Status.BUSY){
            setStatus(Status.ACTIVE);
            return true;
        }
        return false;
    }

    public void disable(){
        if (this.isEmpty()){
            setStatus(Status.FREE);
        }
        else
            setStatus(Status.BUSY);
    }

    // --------------------------------- Соседи -------------------------------
    private ArrayList<Cell> neighborCells = new ArrayList();

    public void setNeighbor(Cell cell) {
        this.neighborCells.add(cell);
    }

    public ArrayList<Cell> getNeighbor(){return this.neighborCells;}

    public boolean activeNeighbor(){
        for (Cell cell : neighborCells){
            if(cell.getStatus() == Status.ACTIVE){
                return true;
            }
        }
        return false;
    }

    public boolean busyNeighbor(){
        for (Cell cell : neighborCells){
            if(cell.getStatus() == Status.BUSY){
                return true;
            }
        }
        return false;
    }

    // --------- Буква, принадлежит полю. Принадлежность задает само поле ------
    private GameField _field;

    public void setGameField(GameField f){
        this._field = f;
    }

    public GameField getGameField(){return this._field;}

    // --------------------- Буква, принадлежащая клетке -----------------------
    Letter _letter;

    public Letter getLetter(){
        return this._letter;
    }

    public boolean isEmpty(){
        return this._letter == null;
    }

    public boolean setLetter(Letter letter) {
        if( letter != null) {
            if (this.getStatus() == Status.FREE) {
                letter.setCell(this);
                _letter = letter;
                _status = Status.BUSY;

                return true;
            }
        }
        else {
            _letter = null;
            return false;
        }
        return false;
    }
}
