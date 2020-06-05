import MyEvents.GameEvent;
import MyEvents.GameListener;
import MyEvents.PlayerActionListener;
import MyGame.Cell;
import MyGame.GameModel;
import MyGame.Letter;
import MyGame.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameModelTest {
    private enum EVENT {GAME_FINISHED,PLAYER_EXCHANGED,DEAD_HEAT}

    private java.util.List<EVENT> events = new ArrayList<>();
    private List<EVENT> expectedEvents = new ArrayList<>();

    private class EventsListener implements GameListener {

        @Override
        public void gameFinished(GameEvent e) {
            events.add(EVENT.GAME_FINISHED);
        }

        @Override
        public void playerExchanged(GameEvent e) {
            events.add(EVENT.PLAYER_EXCHANGED);
        }

        @Override
        public void deadHeat(GameEvent e) {
            events.add(EVENT.DEAD_HEAT);
        }
    }
    GameModel game = new GameModel();

    @BeforeEach
    public void testSetup() {
        // clean events
        events.clear();
        expectedEvents.clear();

        game.addGameListener(new EventsListener());
    }

    @Test
    public void test_Player_createGame(){

        assert(game.getPlayerList().size()==2);
        assert(events.isEmpty());
    }

    @Test
    public void test_Player_start_CellPool(){
        game.start();

        expectedEvents.add(EVENT.PLAYER_EXCHANGED);

        assert(game.getGameField().getCellPool().size() == 25);
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_start_CellNeighbour(){
        game.start();

        expectedEvents.add(EVENT.PLAYER_EXCHANGED);

        assert(game.getGameField().getCellPool().get(0).getNeighbor().size() == 2);
        assert(game.getGameField().getCellPool().get(1).getNeighbor().size() == 3);
        assert(game.getGameField().getCellPool().get(2).getNeighbor().size() == 3);
        assert(game.getGameField().getCellPool().get(3).getNeighbor().size() == 3);
        assert(game.getGameField().getCellPool().get(4).getNeighbor().size() == 2);

        assert(game.getGameField().getCellPool().get(5).getNeighbor().size() == 3);
        assert(game.getGameField().getCellPool().get(6).getNeighbor().size() == 4);
        assert(game.getGameField().getCellPool().get(7).getNeighbor().size() == 4);
        assert(game.getGameField().getCellPool().get(8).getNeighbor().size() == 4);
        assert(game.getGameField().getCellPool().get(9).getNeighbor().size() == 3);

        assert(game.getGameField().getCellPool().get(10).getNeighbor().size() == 3);
        assert(game.getGameField().getCellPool().get(11).getNeighbor().size() == 4);
        assert(game.getGameField().getCellPool().get(12).getNeighbor().size() == 4);
        assert(game.getGameField().getCellPool().get(13).getNeighbor().size() == 4);
        assert(game.getGameField().getCellPool().get(14).getNeighbor().size() == 3);

        assert(game.getGameField().getCellPool().get(15).getNeighbor().size() == 3);
        assert(game.getGameField().getCellPool().get(16).getNeighbor().size() == 4);
        assert(game.getGameField().getCellPool().get(17).getNeighbor().size() == 4);
        assert(game.getGameField().getCellPool().get(18).getNeighbor().size() == 4);
        assert(game.getGameField().getCellPool().get(19).getNeighbor().size() == 3);

        assert(game.getGameField().getCellPool().get(20).getNeighbor().size() == 2);
        assert(game.getGameField().getCellPool().get(21).getNeighbor().size() == 3);
        assert(game.getGameField().getCellPool().get(22).getNeighbor().size() == 3);
        assert(game.getGameField().getCellPool().get(23).getNeighbor().size() == 3);
        assert(game.getGameField().getCellPool().get(24).getNeighbor().size() == 2);
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_start_word(){
        game.start();
        expectedEvents.add(EVENT.PLAYER_EXCHANGED);

        Letter letter;
        String word = "";

        for (int i=0; i<5; i++){
            letter = game.getGameField().getCellPool().get(10+i).getLetter();
            word = word + letter.ToString();
        }

        assert(!game.getGameField().getCellPool().get(10).isEmpty());
        assert(!game.getGameField().getCellPool().get(11).isEmpty());
        assert(!game.getGameField().getCellPool().get(12).isEmpty());
        assert(!game.getGameField().getCellPool().get(13).isEmpty());
        assert(!game.getGameField().getCellPool().get(14).isEmpty());
        assert (game.getGameRating().isUsedWord(word));

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_start_cellsWithWord(){
        game.start();

        expectedEvents.add(EVENT.PLAYER_EXCHANGED);

        assert(!game.getGameField().getCellPool().get(10).isEmpty());
        assert(!game.getGameField().getCellPool().get(11).isEmpty());
        assert(!game.getGameField().getCellPool().get(12).isEmpty());
        assert(!game.getGameField().getCellPool().get(13).isEmpty());
        assert(!game.getGameField().getCellPool().get(14).isEmpty());

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_start_activePlayer(){
        game.start();

        expectedEvents.add(EVENT.PLAYER_EXCHANGED);

        assert(game.getActivePlayer() != null);

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_playerExchanged(){
        game.start();

        expectedEvents.add(EVENT.PLAYER_EXCHANGED);

        game.getGameField().getCellPool().get(0).setLetterAtStartWord(new Letter("к"));
        game.getGameField().getCellPool().get(1).setLetterAtStartWord(new Letter("о"));

        //Выбираем букву
        game.getActivePlayer().chooseLetter(new Letter("т"));

        //уставаливаем её
        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(2));

        //Формируем слово
        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(0));

        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(1));

        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(2));

        //Завершить формирование слова
        game.getActivePlayer().completeWord();
        expectedEvents.add(EVENT.PLAYER_EXCHANGED);

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_finishWithWiner(){
        game.start();

        expectedEvents.add(EVENT.PLAYER_EXCHANGED);

        game.getGameField().getCellPool().get(0).setLetterAtStartWord(new Letter("к"));
        game.getGameField().getCellPool().get(1).setLetterAtStartWord(new Letter("о"));

        //Выбираем букву
        game.getActivePlayer().chooseLetter(new Letter("т"));

        //уставаливаем её
        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(2));

        //Формируем слово
        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(0));

        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(1));

        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(2));

        //Заполняем поле
        game.getGameField().getCellPool().get(3).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(4).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(5).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(6).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(7).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(8).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(9).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(10).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(11).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(12).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(13).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(14).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(15).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(16).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(17).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(18).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(19).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(20).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(21).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(22).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(23).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(24).setLetter(new Letter("ф"));


        //Завершить формирование слова
        game.getActivePlayer().completeWord();
        expectedEvents.add(EVENT.GAME_FINISHED);

        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_Player_finishWithDeadHeat(){
        game.start();

        expectedEvents.add(EVENT.PLAYER_EXCHANGED);

        game.getGameRating().getRating().put(game.getPlayerList().get(1),3);

        game.getGameField().getCellPool().get(0).setLetterAtStartWord(new Letter("к"));
        game.getGameField().getCellPool().get(1).setLetterAtStartWord(new Letter("о"));

        //Выбираем букву
        game.getActivePlayer().chooseLetter(new Letter("т"));

        //уставаливаем её
        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(2));

        //Формируем слово
        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(0));

        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(1));

        game.getActivePlayer().chooseCell(game.getGameField().getCellPool().get(2));

        //Заполняем поле
        game.getGameField().getCellPool().get(3).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(4).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(5).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(6).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(7).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(8).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(9).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(10).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(11).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(12).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(13).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(14).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(15).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(16).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(17).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(18).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(19).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(20).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(21).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(22).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(23).setLetter(new Letter("ф"));
        game.getGameField().getCellPool().get(24).setLetter(new Letter("ф"));


        //Завершить формирование слова
        game.getActivePlayer().completeWord();
        expectedEvents.add(EVENT.DEAD_HEAT);

        assertEquals(events, expectedEvents);
    }
}
