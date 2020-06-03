import MyEvents.AchievementsListEvent;
import MyEvents.AchievementsListListener;
import MyEvents.GameFieldEvent;
import MyEvents.GameFieldListener;
import MyGame.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameFieldTest {
    private enum EVENT {ADD_CELL_TO_WORD, REMOVE_CELL_FROM_WORD, NOT_ADD_CELL_TO_WORD,GAME_FIELD_IS_FULL}

    private List<EVENT> events = new ArrayList<>();
    private List<EVENT> expectedEvents = new ArrayList<>();

    private class EventsListener implements GameFieldListener {
        @Override
        public void addCellToWord(GameFieldEvent e) {
            events.add(EVENT.ADD_CELL_TO_WORD);
        }

        @Override
        public void removeCellFromWord(GameFieldEvent e) {
            events.add(EVENT.REMOVE_CELL_FROM_WORD);
        }

        @Override
        public void notAddCellToWord(GameFieldEvent e) {
            events.add(EVENT.NOT_ADD_CELL_TO_WORD);
        }

        @Override
        public void gameFieldIsFull(GameFieldEvent e) {
            events.add(EVENT.GAME_FIELD_IS_FULL);
        }
    }

    GameField field = new GameField();

    @BeforeEach
    public void testSetup() {
        // clean events
        events.clear();
        expectedEvents.clear();

        field.addGameFieldListener(new EventsListener());
    }

    @Test
    public void test_GameField_setCell(){
        Cell cell = new Cell();
        field.setCell(new Point(1,2), cell);

        assert(field.getCellPool().contains(cell));
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_getCellPool(){
        Cell cell = new Cell();
        field.setCell(new Point(1,2), cell);

        assert(field.getCellPool() != null);
        assert(field.getCellPool().contains(cell));
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_getCell(){
        Cell cell = new Cell();
        Point pos = new Point(1,2);
        field.setCell(pos, cell);

        Cell cellClone = new Cell(new Point(1,2));

        assert(field.getCell(pos).equal(cellClone));
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_clear(){
        Cell cell = new Cell();
        field.setCell(new Point(1,2), cell);
        field.clear();

        assert(field.getCellPool().size() == 0);
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_removeCell(){
        Cell cell = new Cell();
        field.setCell(new Point(1,2), cell);
        Cell cellNew = new Cell();
        field.setCell(new Point(1,2),cellNew);

        assert(!field.getCellPool().contains(cell));
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_activeAll(){
        Cell cell = new Cell(Status.BUSY);
        field.setCell(new Point(1,2), cell);
        Cell cellNew = new Cell(Status.BUSY);
        field.setCell(new Point(1,1),cellNew);

        field.activateAllCells();

        assert(field.getCellPool().get(0).getStatus() == Status.ACTIVE);
        assert(field.getCellPool().get(1).getStatus() == Status.ACTIVE);
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_deactiveAll(){
        Cell cell = new Cell(Status.ACTIVE);
        field.setCell(new Point(1,2), cell);

        field.disableAllCells();

        assert(field.getCellPool().get(0).getStatus() == Status.FREE);
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_isFull_true(){
        Cell cell = new Cell(new Letter("n"));
        field.setCell(new Point(1,1), cell);
        Cell cellNew = new Cell(new Letter("s"));
        field.setCell(new Point(1,2),cellNew);

        expectedEvents.add(EVENT.GAME_FIELD_IS_FULL);

        assert(field.isFull());
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_GameField_isFull_false(){
        Cell cell = new Cell(new Letter("n"));
        field.setCell(new Point(1,1), cell);
        Cell cellNew = new Cell();
        field.setCell(new Point(1,2),cellNew);

        assert(!field.isFull());
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_setSize_and_removeCell(){
        Cell cell = new Cell(new Letter("n"));
        field.setCell(new Point(1,1), cell);
        Cell cellNew = new Cell();
        field.setCell(new Point(1,2),cellNew);

        field.setSize(1);

        assert(!field.getCellPool().contains(cellNew));
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_setSize(){

        field.setSize(5);

        assert(field.getSize() == 5);
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_width(){

        field.setSize(6);

        assert(field.width() == 6);
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_height(){

        field.setSize(5);

        assert(field.height() == 5);
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_containsRange_true(){

        field.setSize(5);

        assert(field.containsRange(new Point(1,3)));
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_containsRange_false_height(){

        field.setSize(5);

        assert(!field.containsRange(new Point(1,7)));
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_containsRange_false_width(){

        field.setSize(5);

        assert(!field.containsRange(new Point(9,2)));
        assert(events.isEmpty());
    }

    @Test
    public void test_GameField_addCellToWord(){
        Cell cell = new Cell(new Letter("n"));
        field.setCell(new Point(1,1), cell);

        Cell cellNeighbor = new Cell(new Letter("s"));
        field.setCell(new Point(1,2),cellNeighbor);

        cell.setNeighbor(cellNeighbor);

        field.addCellToWord(cell);

        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);
        
        assert(field.getAllActiveCell().size() == 1);
        assert(field.getAllActiveCell().contains(cell));
        assert(field.getWord().equals("n"));

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_GameField_addCellToWord_andDeletePreviousWord(){
        Cell cell = new Cell(new Letter("n"));
        field.setCell(new Point(1,1), cell);

        Cell cellInWord = new Cell(new Letter("d"));
        field.setCell(new Point(1,3),cellInWord);

        Cell cellNeighbor = new Cell(new Letter("s"));
        field.setCell(new Point(1,2),cellNeighbor);

        cellInWord.setNeighbor(cellNeighbor);

        field.addCellToWord(cellInWord);
        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);

        cell.setNeighbor(cellNeighbor);

        field.addCellToWord(cell);
        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);

        assert(field.getAllActiveCell().size() == 1);
        assert(field.getAllActiveCell().contains(cell));
        assert(field.getWord().equals("n"));

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_GameField_deleteCellInWord(){
        Cell cell = new Cell(new Letter("n"));
        field.setCell(new Point(1,1), cell);

        Cell cellNeighbor = new Cell(new Letter("s"));
        field.setCell(new Point(1,2),cellNeighbor);

        cell.setNeighbor(cellNeighbor);
        cellNeighbor.setNeighbor(cell);

        field.addCellToWord(cell);
        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);

        field.addCellToWord(cellNeighbor);
        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);

        field.addCellToWord(cellNeighbor);
        expectedEvents.add(EVENT.REMOVE_CELL_FROM_WORD);

        assert(field.getAllActiveCell().size() == 1);
        assert(field.getWord().equals("n"));

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_GameField_deleteCellsInWord(){
        Cell cell = new Cell(new Letter("n"));
        field.setCell(new Point(1,1), cell);

        Cell cellNeighbor = new Cell(new Letter("s"));
        field.setCell(new Point(1,2),cellNeighbor);

        cell.setNeighbor(cellNeighbor);
        cellNeighbor.setNeighbor(cell);

        field.addCellToWord(cell);
        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);

        field.addCellToWord(cellNeighbor);
        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);

        field.addCellToWord(cell);
        expectedEvents.add(EVENT.REMOVE_CELL_FROM_WORD);

        assert(field.getAllActiveCell().size() == 0);
        assert(field.getWord().equals(""));

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_GameField_notAddCellToWord(){
        Cell cell = new Cell(new Letter("n"));
        field.setCell(new Point(1,1), cell);

        field.addCellToWord(cell);
        expectedEvents.add(EVENT.NOT_ADD_CELL_TO_WORD);

        assert(field.getAllActiveCell().size() == 0);
        assert(field.getWord().equals(""));

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_GameField_deletedWord(){
        Cell cell = new Cell(new Letter("n"));
        field.setCell(new Point(1,1), cell);

        Cell cellNeighbor = new Cell(new Letter("s"));
        field.setCell(new Point(1,2),cellNeighbor);

        cell.setNeighbor(cellNeighbor);

        field.addCellToWord(cell);
        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);

        field.deletedWord();

        assert(field.getAllActiveCell().size() == 0);
        assert(cell.getStatus() == Status.BUSY);
        assert(field.getWord().equals(""));
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_GameField_setWord(){
        Cell cell = new Cell(Status.FREE);
        field.setCell(new Point(1,1), cell);

        Cell cell1 = new Cell(Status.FREE);
        field.setCell(new Point(1,2), cell1);

        Cell cell2 = new Cell(Status.FREE);
        field.setCell(new Point(1,3), cell2);

        Cell cell3 = new Cell(Status.FREE);
        field.setCell(new Point(2,1), cell3);

        Cell cell4 = new Cell(Status.FREE);
        field.setCell(new Point(2,2), cell4);

        Cell cell5 = new Cell(Status.FREE);
        field.setCell(new Point(2,3), cell5);

        Cell cell6 = new Cell(Status.FREE);
        field.setCell(new Point(3,1), cell6);

        Cell cell7 = new Cell(Status.FREE);
        field.setCell(new Point(3,2), cell7);

        Cell cell8 = new Cell(Status.FREE);
        field.setCell(new Point(3,3), cell8);

        cell.setNeighbor(cell1);
        cell.setNeighbor(cell3);

        cell1.setNeighbor(cell);
        cell1.setNeighbor(cell2);
        cell1.setNeighbor(cell4);

        cell2.setNeighbor(cell1);
        cell2.setNeighbor(cell5);

        cell3.setNeighbor(cell);
        cell3.setNeighbor(cell6);
        cell3.setNeighbor(cell4);

        cell4.setNeighbor(cell3);
        cell4.setNeighbor(cell1);
        cell4.setNeighbor(cell5);
        cell4.setNeighbor(cell7);

        cell5.setNeighbor(cell2);
        cell5.setNeighbor(cell4);
        cell5.setNeighbor(cell8);

        cell6.setNeighbor(cell3);
        cell6.setNeighbor(cell7);

        cell7.setNeighbor(cell6);
        cell7.setNeighbor(cell4);
        cell7.setNeighbor(cell8);

        cell8.setNeighbor(cell7);
        cell8.setNeighbor(cell5);

        field.setSize(3);

        field.setWord("сон");
        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);
        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);
        expectedEvents.add(EVENT.ADD_CELL_TO_WORD);

        assert(field.getWord().equals("сон"));

        assert(field.getCellPool().get(0).getStatus() == Status.FREE && field.getCellPool().get(0).isEmpty());
        assert(field.getCellPool().get(1).getStatus() == Status.FREE && field.getCellPool().get(1).isEmpty());
        assert(field.getCellPool().get(2).getStatus() == Status.FREE && field.getCellPool().get(2).isEmpty());
        assert(field.getCellPool().get(6).getStatus() == Status.FREE && field.getCellPool().get(6).isEmpty());
        assert(field.getCellPool().get(7).getStatus() == Status.FREE && field.getCellPool().get(7).isEmpty());
        assert(field.getCellPool().get(8).getStatus() == Status.FREE && field.getCellPool().get(8).isEmpty());

        assert(field.getCellPool().get(3).getStatus() == Status.ACTIVE && !field.getCellPool().get(3).isEmpty());
        assert(field.getCellPool().get(4).getStatus() == Status.ACTIVE && !field.getCellPool().get(4).isEmpty());
        assert(field.getCellPool().get(5).getStatus() == Status.ACTIVE && !field.getCellPool().get(5).isEmpty());

        assertEquals(events, expectedEvents);
    }
}
