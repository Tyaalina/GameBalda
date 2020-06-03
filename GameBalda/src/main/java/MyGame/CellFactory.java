package MyGame;


/**
 * Фабрика, порождающая возможные виды ячеек. Реализует самую простую стратегию
 */
public class CellFactory {

    public Cell createCell(){
        return new Cell();
    }
}
