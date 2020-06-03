import MyGame.Cell;
import MyGame.GameField;
import MyGame.Letter;
import MyGame.Status;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {
    Cell cell = new Cell();

    @Test
    public void test_Cell_setPosition(){
        Point pos = new Point(1,2);
        cell.setPosition(pos);

        assertEquals(cell.getPosition(),pos);
    }

    @Test
    public void test_Cell_getPosition(){
        Cell _cell = new Cell(new Point(1,2));
        Point pos = new Point(1,2);

        assertEquals(_cell.getPosition(),pos);
    }

    @Test
    public void test_Cell_createCell(){
        Cell _cell = new Cell(new Point(1,2));

        assert(_cell.getPosition()!=null);
    }

    @Test
    public void test_Cell_equalCell_true(){
        Cell _cell = new Cell(new Point(1,2));
        cell.setPosition(new Point(1,2));

        assert(_cell.equal(cell));
    }

    @Test
    public void test_Cell_equalCell_false(){
        Cell _cell = new Cell(new Point(1,2));
        cell.setPosition(new Point(2,2));

        assert(!_cell.equal(cell));
    }

    @Test
    public void test_Cell_equalCell_null(){
        Cell _cell = new Cell();

        assert(_cell.equal(cell));
    }

    @Test
    public void test_Cell_equalCell_null_false(){
        Cell _cell = new Cell(new Point(1,5));

        assert(!_cell.equal(cell));
    }

    @Test
    public void test_Cell_setStatus(){
        cell.setStatus(Status.ACTIVE);

        assert(cell.getStatus() == Status.ACTIVE);
    }

    @Test
    public void test_Cell_getStatus(){
        Cell _cell = new Cell(Status.BUSY);

        assert(_cell.getStatus() == Status.BUSY);
    }

    @Test
    public void test_Cell_activate_true(){
        cell.setStatus(Status.BUSY);

        assert(cell.activate());
    }

    @Test
    public void test_Cell_activate_false(){
        cell.setStatus(Status.FREE);

        assert(!cell.activate());
    }

    @Test
    public void test_Cell_disable_free(){
        cell.setStatus(Status.ACTIVE);
        cell.disable();

        assert(cell.getStatus() == Status.FREE);
    }

    @Test
    public void test_Cell_disable_busy(){
        cell.setStatus(Status.FREE);
        cell.setLetter(new Letter("a"));
        cell.activate();
        cell.disable();

        assert(cell.getStatus() == Status.BUSY);
    }

    @Test
    public void test_Cell_getLetter(){
        Cell _cell = new Cell(new Letter("n"));
        Letter letter = new Letter("n");

        assert(_cell.getLetter().equal(letter));
    }

    @Test
    public void test_Cell_setLetter_true(){
        Letter letter = new Letter("n");
        cell.setStatus(Status.FREE);
        boolean result = cell.setLetter(letter);

        assert(cell.getLetter().equal(letter));
        assert(result);
    }

    @Test
    public void test_Cell_setLetter_false(){
        Letter letter = new Letter("n");
        cell.setStatus(Status.BUSY);
        boolean result = cell.setLetter(letter);

        assert(!result);
    }

    @Test
    public void test_Cell_isEmpty_true(){
        assert(cell.isEmpty());
    }

    @Test
    public void test_Cell_isEmpty_false(){
        Letter letter = new Letter("n");
        cell.setStatus(Status.FREE);
        cell.setLetter(letter);

        assert(!cell.isEmpty());
    }

    @Test
    public void test_Cell_setGameField(){
        cell.setGameField(new GameField());

        assert(cell.getGameField() != null);
    }

    @Test
    public void test_Cell_getGameField(){
        Cell _cell = new Cell(new GameField());

        assert(_cell.getGameField() != null);
    }

    @Test
    public void test_Cell_getNeighbor_and_setNeighbor(){
        Cell neighbor = new Cell();
        cell.setNeighbor(neighbor);

        assert(cell.getNeighbor() != null);
        assert(cell.getNeighbor().contains(neighbor));
    }

    @Test
    public void test_Cell_busyNeighbor_true(){
        Cell neighbor = new Cell();
        neighbor.setStatus(Status.BUSY);
        cell.setNeighbor(neighbor);

        assert(cell.busyNeighbor());
    }

    @Test
    public void test_Cell_busyNeighbor_false(){
        Cell neighbor = new Cell();
        neighbor.setStatus(Status.FREE);
        cell.setNeighbor(neighbor);

        assert(!cell.busyNeighbor());
    }

    @Test
    public void test_Cell_activeNeighbor_true(){
        Cell neighbor = new Cell();
        neighbor.setStatus(Status.ACTIVE);
        cell.setNeighbor(neighbor);

        assert(cell.activeNeighbor());
    }

    @Test
    public void test_Cell_activeNeighbor_false(){
        Cell neighbor = new Cell();
        neighbor.setStatus(Status.FREE);
        cell.setNeighbor(neighbor);

        assert(!cell.activeNeighbor());
    }
}
