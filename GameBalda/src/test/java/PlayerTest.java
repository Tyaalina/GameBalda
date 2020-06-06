import MyEvents.GameFieldEvent;
import MyEvents.GameFieldListener;
import MyEvents.PlayerActionEvent;
import MyEvents.PlayerActionListener;
import MyGame.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    private enum EVENT {LETTER_IS_ACTIVE, LETTER_ADD_ON_FIELD, LETTER_IS_NOT_ADD_ON_FIELD, CELL_ADD_IN_WORD,
                        CELL_NOT_ADD_IN_WORD, CELL_REMOVE_IN_WORD}

    private java.util.List<EVENT> events = new ArrayList<>();
    private List<EVENT> expectedEvents = new ArrayList<>();

    private class EventsListener implements PlayerActionListener {

        @Override
        public void letterIsActive(PlayerActionEvent e) {
            events.add(EVENT.LETTER_IS_ACTIVE);
        }

        @Override
        public void letterAddOnGameField(PlayerActionEvent e) {
            events.add(EVENT.LETTER_ADD_ON_FIELD);
        }

        @Override
        public void letterIsNotAddOnGameField(PlayerActionEvent e) {
            events.add(EVENT.LETTER_IS_NOT_ADD_ON_FIELD);
        }

        @Override
        public void cellAddInWord(PlayerActionEvent e) {
            events.add(EVENT.CELL_ADD_IN_WORD);
        }

        @Override
        public void cellNotAddInWord(PlayerActionEvent e) {
            events.add(EVENT.CELL_NOT_ADD_IN_WORD);
        }

        @Override
        public void cellRemoveInWord(PlayerActionEvent e) {
            events.add(EVENT.CELL_REMOVE_IN_WORD);
        }

        @Override
        public void cellWithSettingLetterInWord(PlayerActionEvent e) {

        }
    }

    GameModel game = new GameModel();
    Player player = new Player(game.getGameField(),"0");

    @BeforeEach
    public void testSetup() {
        // clean events
        events.clear();
        expectedEvents.clear();

        player.addPlayerActionListener(new EventsListener());
    }

    @Test
    public void test_Player_getName(){

        assert(player.getName().equals("0"));
        assert(events.isEmpty());
    }

    @Test
    public void test_Player_setName(){
        player.setName("example");

        assert(player.getName().equals("example"));
        assert(events.isEmpty());
    }

    @Test
    public void test_Player_setActiveLetter(){
        player.setName("example");

        assert(player.getName().equals("example"));
        assert(events.isEmpty());
    }

    @Test
    public void test_Player_chooseLetter(){
        Letter letter = new Letter("а");
        player.setGame(game);

        player.chooseLetter(new Letter("а"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        assert(player.getActiveLetter().equal(letter));
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_choose_and_changeLetter(){
        Letter letter = new Letter("м");
        player.setGame(game);

        player.chooseLetter(new Letter("а"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        player.chooseLetter(new Letter("м"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        assert(player.getActiveLetter().equal(letter));
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_addLetter(){
        player.setGame(game);

        player.chooseLetter(new Letter("а"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        //Создание поля
        Cell cell = new Cell(new Letter("в"));
        game.getGameField().setCell(new Point(1,1), cell);

        Cell cell1 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,2), cell1);

        Cell cell2 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,3), cell2);

        //Присоединение соседей
        cell.setNeighbor(cell1);

        cell1.setNeighbor(cell);
        cell1.setNeighbor(cell2);

        cell2.setNeighbor(cell1);

        player.chooseCell(cell1);
        expectedEvents.add(EVENT.LETTER_ADD_ON_FIELD);

        assert(player.getCellWithSettingLetter().equal(cell1));
        assert(cell1.getLetter().equal(new Letter("а")));
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_addLetterInBusyCell(){
        player.setGame(game);

        player.chooseLetter(new Letter("а"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        //Создание поля
        Cell cell = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,1), cell);

        Cell cell1 = new Cell(new Letter("и"));
        game.getGameField().setCell(new Point(1,2), cell1);

        Cell cell2 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,3), cell2);

        //Присоединение соседей
        cell.setNeighbor(cell1);

        cell1.setNeighbor(cell);
        cell1.setNeighbor(cell2);

        cell2.setNeighbor(cell1);

        player.chooseCell(cell1);
        expectedEvents.add(EVENT.LETTER_IS_NOT_ADD_ON_FIELD);

        assert(player.getCellWithSettingLetter().getPosition() == null);
        assert(cell1.getLetter().equal(new Letter("и")));
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_formationWord(){
        player.setGame(game);

        player.chooseLetter(new Letter("о"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        //Создание поля
        Cell cell = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,1), cell);

        Cell cell1 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,2), cell1);

        Cell cell2 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,3), cell2);

        Cell cell3 = new Cell(new Letter("к"));
        game.getGameField().setCell(new Point(2,1), cell3);

        Cell cell4 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(2,2), cell4);

        Cell cell5 = new Cell(new Letter("т"));
        game.getGameField().setCell(new Point(2,3), cell5);

        Cell cell6 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,1), cell6);

        Cell cell7 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,2), cell7);

        Cell cell8 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,3), cell8);

        //Присоединение соседей
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

        game.getGameField().setSize(3);

        //Устанавливаем букву
        player.chooseCell(cell4);
        expectedEvents.add(EVENT.LETTER_ADD_ON_FIELD);

        //Формируем слово
        player.chooseCell(cell3);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell4);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell5);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        assert(game.getGameField().getWord().equals("кот"));
        assert(cell3.getStatus() == Status.ACTIVE);
        assert(cell4.getStatus() == Status.ACTIVE);
        assert(cell5.getStatus() == Status.ACTIVE);

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_addLetterWhenWordNotNull(){
        player.setGame(game);

        player.chooseLetter(new Letter("о"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        //Создание поля
        Cell cell = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,1), cell);

        Cell cell1 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,2), cell1);

        Cell cell2 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,3), cell2);

        Cell cell3 = new Cell(new Letter("к"));
        game.getGameField().setCell(new Point(2,1), cell3);

        Cell cell4 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(2,2), cell4);

        Cell cell5 = new Cell(new Letter("т"));
        game.getGameField().setCell(new Point(2,3), cell5);

        Cell cell6 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,1), cell6);

        Cell cell7 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,2), cell7);

        Cell cell8 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,3), cell8);

        //Присоединение соседей
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

        game.getGameField().setSize(3);

        //Устанавливаем букву
        player.chooseCell(cell4);
        expectedEvents.add(EVENT.LETTER_ADD_ON_FIELD);

        //Формируем слово
        player.chooseCell(cell3);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell4);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell5);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseLetter(new Letter("а"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        //Устанавливаем букву
        player.chooseCell(cell6);
        expectedEvents.add(EVENT.LETTER_ADD_ON_FIELD);

        Letter letter = new Letter("а");

        assert(player.getCellWithSettingLetter().equal(cell6));
        assert(cell6.getLetter().equal(letter));
        assert(cell4.isEmpty() && cell4.getStatus() == Status.FREE  );
        assert(game.getGameField().getWord().equals(""));

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_completeWord(){
        player.setGame(game);
        game.getGameRating().getRating().put(player,0);

        player.chooseLetter(new Letter("о"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        //Создание поля
        Cell cell = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,1), cell);

        Cell cell1 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,2), cell1);

        Cell cell2 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,3), cell2);

        Cell cell3 = new Cell(new Letter("к"));
        game.getGameField().setCell(new Point(2,1), cell3);

        Cell cell4 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(2,2), cell4);

        Cell cell5 = new Cell(new Letter("т"));
        game.getGameField().setCell(new Point(2,3), cell5);

        Cell cell6 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,1), cell6);

        Cell cell7 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,2), cell7);

        Cell cell8 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,3), cell8);

        //Присоединение соседей
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

        game.getGameField().setSize(3);

        //Устанавливаем букву
        player.chooseCell(cell4);
        expectedEvents.add(EVENT.LETTER_ADD_ON_FIELD);

        //Формируем слово
        player.chooseCell(cell3);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell4);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell5);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        //Завершить формирование слова
        player.completeWord();

        assert(game.getGameRating().getRating().get(player) == 3);
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_completeWord_IsAlreadyUsed(){
        player.setGame(game);

        player.chooseLetter(new Letter("о"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        //Создание поля
        Cell cell = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,1), cell);

        Cell cell1 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,2), cell1);

        Cell cell2 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,3), cell2);

        Cell cell3 = new Cell(new Letter("к"));
        game.getGameField().setCell(new Point(2,1), cell3);

        Cell cell4 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(2,2), cell4);

        Cell cell5 = new Cell(new Letter("т"));
        game.getGameField().setCell(new Point(2,3), cell5);

        Cell cell6 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,1), cell6);

        Cell cell7 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,2), cell7);

        Cell cell8 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,3), cell8);

        //Присоединение соседей
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

        game.getGameField().setSize(3);

        game.getGameRating().getUsedWord().put("кот",player);

        //Устанавливаем букву
        player.chooseCell(cell4);
        expectedEvents.add(EVENT.LETTER_ADD_ON_FIELD);

        //Формируем слово
        player.chooseCell(cell3);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell4);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell5);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        //Завершить формирование слова
        player.completeWord();

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_completeWord_NotInDictionary(){
        player.setGame(game);

        player.chooseLetter(new Letter("ш"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        //Создание поля
        Cell cell = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,1), cell);

        Cell cell1 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,2), cell1);

        Cell cell2 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,3), cell2);

        Cell cell3 = new Cell(new Letter("ш"));
        game.getGameField().setCell(new Point(2,1), cell3);

        Cell cell4 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(2,2), cell4);

        Cell cell5 = new Cell(new Letter("ш"));
        game.getGameField().setCell(new Point(2,3), cell5);

        Cell cell6 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,1), cell6);

        Cell cell7 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,2), cell7);

        Cell cell8 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,3), cell8);

        //Присоединение соседей
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

        game.getGameField().setSize(3);

        //Устанавливаем букву
        player.chooseCell(cell4);
        expectedEvents.add(EVENT.LETTER_ADD_ON_FIELD);

        //Формируем слово
        player.chooseCell(cell3);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell4);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell5);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        //Завершить формирование слова
        player.completeWord();

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_completeWord_CellRemove(){
        player.setGame(game);

        player.chooseLetter(new Letter("ш"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        //Создание поля
        Cell cell = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,1), cell);

        Cell cell1 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,2), cell1);

        Cell cell2 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,3), cell2);

        Cell cell3 = new Cell(new Letter("ш"));
        game.getGameField().setCell(new Point(2,1), cell3);

        Cell cell4 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(2,2), cell4);

        Cell cell5 = new Cell(new Letter("ш"));
        game.getGameField().setCell(new Point(2,3), cell5);

        Cell cell6 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,1), cell6);

        Cell cell7 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,2), cell7);

        Cell cell8 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,3), cell8);

        //Присоединение соседей
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

        game.getGameField().setSize(3);

        //Устанавливаем букву
        player.chooseCell(cell4);
        expectedEvents.add(EVENT.LETTER_ADD_ON_FIELD);

        //Формируем слово
        player.chooseCell(cell3);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell4);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell5);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell5);
        expectedEvents.add(EVENT.CELL_REMOVE_IN_WORD);


        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_completeWord_NotAddToWord(){
        player.setGame(game);

        player.chooseLetter(new Letter("ш"));
        expectedEvents.add(EVENT.LETTER_IS_ACTIVE);

        //Создание поля
        Cell cell = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,1), cell);

        Cell cell1 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,2), cell1);

        Cell cell2 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(1,3), cell2);

        Cell cell3 = new Cell(new Letter("ш"));
        game.getGameField().setCell(new Point(2,1), cell3);

        Cell cell4 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(2,2), cell4);

        Cell cell5 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(2,3), cell5);

        Cell cell6 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,1), cell6);

        Cell cell7 = new Cell(Status.FREE);
        game.getGameField().setCell(new Point(3,2), cell7);

        Cell cell8 = new Cell(new Letter("ш"));
        game.getGameField().setCell(new Point(3,3), cell8);

        //Присоединение соседей
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

        game.getGameField().setSize(3);

        //Устанавливаем букву
        player.chooseCell(cell4);
        expectedEvents.add(EVENT.LETTER_ADD_ON_FIELD);

        //Формируем слово
        player.chooseCell(cell3);
        expectedEvents.add(EVENT.CELL_ADD_IN_WORD);

        player.chooseCell(cell8);
        expectedEvents.add(EVENT.CELL_NOT_ADD_IN_WORD);

        assertEquals(events, expectedEvents);
    }
}
