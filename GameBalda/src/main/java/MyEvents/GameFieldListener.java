package MyEvents;

import java.util.EventListener;

public interface GameFieldListener extends EventListener {
    void addCellToWord(GameFieldEvent e);

    void removeCellFromWord(GameFieldEvent e);

    void notAddCellToWord(GameFieldEvent e);

    void gameFieldIsFull (GameFieldEvent e);
}
