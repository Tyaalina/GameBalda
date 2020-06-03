package MyEvents;

import MyGame.Player;

import java.util.EventObject;

public class AchievementsListEvent extends EventObject {
    private Player _player;

    public void setPlayer(Player p){
        _player = p;
    }

    public Player player(){
        return _player;
    }
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public AchievementsListEvent(Object source) {
        super(source);
    }
}
