package MyEvents;
import java.util.EventListener;

/*
 * Слушатель действий игрока
 */
public interface PlayerActionListener extends EventListener{
    void letterIsActive(PlayerActionEvent e);

    void letterAddOnGameField(PlayerActionEvent e);

    void letterIsNotAddOnGameField(PlayerActionEvent e);

    void cellAddInWord(PlayerActionEvent e);

    void cellNotAddInWord(PlayerActionEvent e);

    void cellRemoveInWord(PlayerActionEvent e);
}
