package MyEvents;

import java.util.EventListener;

public interface GameListener extends EventListener {
     void gameFinished(GameEvent e);
     void playerExchanged(GameEvent e);
     void deadHeat(GameEvent e);
}
